package com.hrkj.scalp.order.controller;

import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hrkj.scalp.account.entity.ScalpAccount;
import com.hrkj.scalp.account.service.IScalpAccountService;
import com.hrkj.scalp.accountLog.entity.ScalpAccountLog;
import com.hrkj.scalp.accountLog.service.IScalpAccountLogService;
import com.hrkj.scalp.merchant.entity.Merchant;
import com.hrkj.scalp.merchant.entity.QrCode;
import com.hrkj.scalp.merchant.service.IQrCodeService;
import com.hrkj.scalp.shiro.vo.DefContants;
import com.hrkj.scalp.system.entity.SystemEarn;
import com.hrkj.scalp.system.service.ISystemEarnService;
import com.hrkj.scalp.merchant.service.IMerchantService;
import com.hrkj.scalp.util.CacheInfo;
import com.hrkj.scalp.util.StringUtil;
import com.hrkj.scalp.util.UsedCode;
import com.hrkj.scalp.util.gsonadapter.GsonUtil;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.order.entity.ScalpOrder;
import com.hrkj.scalp.order.service.IScalpOrderService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: scalp_order
 * @Author: jeecg-boot
 * @Date: 2020-03-09
 * @Version: V1.0
 */
@RestController
@RequestMapping("/order")
@Slf4j
@Component
public class ScalpOrderController extends JeecgController<ScalpOrder, IScalpOrderService> {
    @Autowired
    private IScalpOrderService scalpOrderService;
    @Autowired
    private IScalpAccountService scalpAccountService;
    @Autowired
    private IScalpAccountLogService scalpAccountLogService;
    @Autowired
    private IMerchantService merchantService;
    @Autowired
    private ISystemEarnService systemEarnService;
    @Autowired
    private static CacheInfo cacheInfo;
    @Autowired
    private IQrCodeService qrCodeService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * @param cacheInfo
     * @Title:SendMsgUtil
     * @Description:项目初始化时注入cacheUtil
     */
    @Autowired
    public ScalpOrderController(CacheInfo cacheInfo) {
        ScalpOrderController.cacheInfo = cacheInfo;
    }

    /**
     * 储存订单过期时间
     */
    /*private Map<Long, String> putOrderPast = new LinkedHashMap<>();*/

    /**
     * 分页列表查询
     *
     * @param scalpOrder
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @PostMapping(value = "/list")
    public Result<?> queryPageList(ScalpOrder scalpOrder,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req, String openTime, String endTime, String number) {
        QueryWrapper<ScalpOrder> queryWrapper = QueryGenerator.initQueryWrapper(scalpOrder, req.getParameterMap());
        if(!StringUtil.isEmpty(openTime)) queryWrapper.ge("order_time", openTime);//.ge 添加 >= 的条件判断
        if(!StringUtil.isEmpty(endTime)) queryWrapper.le("order_time", endTime);//.le 添加 >= 的条件判断
        if(!StringUtil.isEmpty(number)) queryWrapper.eq("order_id", number).or().eq("out_id", number);
        //查询数据限制  管理员无限制查询 码商/商户查询个人数据
        String token = req.getHeader(DefContants.X_ACCESS_TOKEN);
        String loginType = JwtUtil.getLoginType(token);
        String id = JwtUtil.getId(token);
        if(StringUtil.isEmpty(loginType)) return Result.error("token为空");
        if(loginType.equals(UsedCode.LOGIN_TYPE_ADMIN) && !StringUtil.isEmpty(number))
            queryWrapper.or().eq("user_id", number).or().eq("merchant_id", number);
        else if(loginType.equals(UsedCode.LOGIN_TYPE_MERCHANT)){
            queryWrapper.or().eq("merchant_id", id);
        }else if(loginType.equals(UsedCode.LOGIN_TYPE_USER)){
            queryWrapper.or().eq("user_id", id);
        }
        Page<ScalpOrder> page = new Page<ScalpOrder>(pageNo, pageSize);
        IPage<ScalpOrder> pageList = scalpOrderService.page(page, queryWrapper);
        return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
    }

    /**
     * 管理员审核账目
     * @return
     */
    @PostMapping(value = "/audit")
    public Result<?> audit( String id,String remark, Integer orderStatus,Integer payStatus, HttpServletRequest req) {
        try {
//            log.info("{}",map.size());
            String token = req.getHeader(DefContants.X_ACCESS_TOKEN);
            if(StringUtil.isEmpty(token))return Result.error("token为空");
            String loginType = JwtUtil.getLoginType(token);
//            String id  = (String) map.get("id");
//            Integer orderStatus = Integer.parseInt((String)map.get("orderStatus"));
//            String remark  = (String) map.get("remark");
            log.info("订单审核参数：loginType：{},id：{},orderStatus:{},payStatus:{},remark:{}",loginType,id,orderStatus, payStatus,remark);
            ScalpOrder order = scalpOrderService.getById(id);
            if (order.getOrderStatus() != UsedCode.ORDER_NO_PAYMENT && order.getPayStatus() != UsedCode.ORDER_NO_BACK) {
                return Result.error("审核失败，当前状态不为-待支付，未回调状态");
            }
            if (orderStatus == UsedCode.ORDER_YES_PAYMENT) {//确认
                order.setRemitTime(System.currentTimeMillis());
                order.setUserPassType(UsedCode.ORDER_USER_PASS);
                order.setPayType(UsedCode.ORDER_YES_PAYMENT);
            }else{
                order.setPayType(UsedCode.ORDER_PAST_DUE);
                if(loginType==UsedCode.LOGIN_TYPE_ADMIN){ //管理员操作
                    if (StringUtil.isEmpty(remark)) return Result.error("审核失败，驳回时需要写理由！");
                    order.setRemark(remark);
                    order.setPayType(UsedCode.ORDER_PAST_DUE);
                }
            }
            if(loginType==UsedCode.LOGIN_TYPE_ADMIN){   //管理员操作回调
//                Integer payStatus = Integer.parseInt((String)map.get("payStatus"));
                order.setSystemId(JwtUtil.getId(token));
                order.setPayStatus(payStatus);
            }
            order.setOrderStatus(orderStatus);
            return scalpOrderService.putFreezeMoney(order,orderStatus,Object-> {
                //删除订单键
                redisUtil.delete(UsedCode.ORDER_PREFIX+order.getOrderId());
                return new Result<>().success("审核成功");
            });
        } catch (Exception e) {
            log.error("审核失败：{}", e);
            return Result.error("审核失败，请稍后再试！");
        }
    }

    /**
     * 添加
     *@RequestBody
     * @param scalpOrder
     * @return
     */
    @PostMapping(value = "/add")
    public Result<?> add(ScalpOrder scalpOrder) {
        log.info("订单添加：{}",GsonUtil.boToString(scalpOrder));
        try {
            if(scalpOrder.getShopNum()!=1) return Result.error("商品只能论个买！");
            Long nowTime = System.currentTimeMillis();
            Integer overTime = Integer.parseInt(cacheInfo.getCommonToKey("scalp.order+over.time"));
            Long pastTime = nowTime + (overTime * 60 * 1000);
            String orderId = StringUtil.getOrderId();
            scalpOrder.setOrderId(orderId);
            scalpOrder.setOrderTime(nowTime);
            scalpOrder.setPastTime(pastTime);
            scalpOrder.setUserPassType(UsedCode.ORDER_NOT_PASS);
            Map<String,Object> qrInfo = getPaymentCode(scalpOrder.getMerchantId(),scalpOrder.getShopId(),scalpOrder.getEarnMoney());
            scalpOrder.setQrType(Integer.parseInt(String.valueOf(qrInfo.get("qrType"))));
//            scalpOrder.setQrType(1);
            scalpOrder.setQrId(Integer.parseInt(String.valueOf(qrInfo.get("qrId"))));
//            scalpOrder.setQrId(5);
            scalpOrder.setUserId((String) qrInfo.get("userId"));
//            scalpOrder.setUserId("1111111");
            String qrUrl = (String) qrInfo.get("qrUrl");
//            String qrUrl = "56";
            scalpOrderService.save(scalpOrder);
//            将订单号存储到redis中并设置过期时间
            redisUtil.ins("order_"+orderId,orderId,Integer.valueOf(cacheInfo.getCommonToKey("scalp.order+over.time")), TimeUnit.MINUTES);
//            Message message = new Message((String) qrInfo.get("userId"));
            //TODO 推送订单通知
//            sendMassageService.sendMsg("");
            return scalpOrderService.planOrder(scalpOrder, Object -> Result.ok(qrUrl));
        } catch (Exception e) {
            log.error("订单创建失败：{}",e);
            return Result.error("添加失败，请稍后再试！");
        }
    }

    /**
     * 编辑
     *
     * @param scalpOrder
     * @return
     */
    @PostMapping(value = "/edit")
    public Result<?> edit(@RequestBody ScalpOrder scalpOrder) {
        scalpOrderService.updateById(scalpOrder);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        scalpOrderService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.scalpOrderService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        ScalpOrder scalpOrder = scalpOrderService.getById(id);
        if (scalpOrder == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(scalpOrder);
    }

    /**
     * 获取码商 || 商户   今日昨日共计金额订单数利润
     * @param id
     * @return
     */
    @GetMapping(value = "queryOrderInfo")
    public Result<?> queryOrderInfo(String id,HttpServletRequest request){
        String loginType = JwtUtil.getLoginType(request.getHeader(DefContants.X_ACCESS_TOKEN));
        if(StringUtil.isEmpty(id) || StringUtil.isEmpty(loginType)){
            return Result.error("TOKEN 失效");
        }
        Map<String,Object> allmap = null;
        if(loginType.equals(UsedCode.LOGIN_TYPE_USER)){
            allmap = scalpOrderService.queryOrderInfo(id,null,0);
            Map<String,Object> daymap = scalpOrderService.queryOrderInfo(id,null,1);
            allmap.put("todayMoney",daymap.get("money"));
            allmap.put("todayNum",daymap.get("num"));
            allmap.put("todayProfit",daymap.get("profit"));
            Map<String,Object> yesmap = scalpOrderService.queryOrderInfo(id,null,2);
            allmap.put("yesterdayMoney",yesmap.get("money"));
            allmap.put("yesterdayNum",yesmap.get("num"));
            allmap.put("yesterdayProfit",yesmap.get("profit"));
        }else if(loginType.equals(UsedCode.LOGIN_TYPE_MERCHANT)){
            allmap = scalpOrderService.queryOrderInfo(null,id,0);
            Map<String,Object> daymap = scalpOrderService.queryOrderInfo(null,id,1);
            allmap.put("todayMoney",daymap.get("money"));
            allmap.put("todayNum",daymap.get("num"));
            allmap.put("todayProfit",daymap.get("profit"));
            Map<String,Object> yesmap = scalpOrderService.queryOrderInfo(null,id,2);
            allmap.put("yesterdayMoney",yesmap.get("money"));
            allmap.put("yesterdayNum",yesmap.get("num"));
            allmap.put("yesterdayProfit",yesmap.get("profit"));
        }
        return Result.ok(allmap);
    }

    /**
     * app获取码商支付宝微信今日和历史的收款金额,今日和历史付款金额
     * @return
     */
    @GetMapping(value = "queryEarnMoneyForApp")
    public Result<?> queryEarnMoneyForApp(HttpServletRequest request ){
        String userId = request.getParameter("userId");
        JSONObject jsonObject = new JSONObject();
        //获取码商支付宝微信今日和历史的收款金额
        List<Map<String,Object>> list = scalpOrderService.queryEarnMoneyForApp(userId);
        for(Map<String,Object> map : list){
            int type = (int) map.get("qrType");
            if(UsedCode.QR_TYPE_ALIPAY == type){
                jsonObject.put("alipayTodayMoney",map.get("todayMoney"));
                jsonObject.put("alipaylocationMoney",map.get("locationMoney"));
            }else{
                jsonObject.put("wechatTodayMoney",map.get("todayMoney"));
                jsonObject.put("wechatlocationMoney",map.get("locationMoney"));
            }
        }
        // 获取码商今日和历史付款金额
        Map<String,Object> map = scalpAccountService.queryActualMoney(userId);
        if(null == map) {
            jsonObject.put("todayPayMoney",0);
            jsonObject.put("locationPayMoney",0);
        }else{
            jsonObject.put("todayPayMoney",map.get("todayPayMoney"));
            jsonObject.put("locationPayMoney",map.get("locationPayMoney"));
        }

        return Result.ok(jsonObject);
    }

    /**
     * 统计排行榜
     * @return
     */
    @PostMapping(value = "/getRanking")
    public Result<?> getRanking(String userId){
        try {
            String money = cacheInfo.getCommonToKey("scalp.order+ranking.small");
            List<Map<String,Object>> allRanking=scalpOrderService.allRanking();             //所有单排行
            List<Map<String,Object>> smallRanking=scalpOrderService.smallRanking(money);    //小金单排行
            List<Map<String,Object>> inviteRanking=scalpOrderService.inviteRanking();       //推广量排行
            Map<String,Object> allUnit=scalpOrderService.allUnit(userId);                   //个人所有单排行
            Map<String,Object> smallUnit=scalpOrderService.smallUnit(userId,money);         //个人小金单排行
            Map<String,Object> inviteUnit=scalpOrderService.inviteUnit(userId);             //个人推广量排行
            Map<String,Object> all=new HashMap<>();         //统计
            all.put("allRanking",allRanking);
            all.put("smallRanking",smallRanking);
            all.put("inviteRanking",inviteRanking);
            all.put("allUnit",allUnit);
            all.put("smallUnit",smallUnit);
            all.put("inviteUnit",inviteUnit);
            return Result.okData(all);
        } catch (Exception e) {
            log.error("统计失败getRanking：{}",e);
            return Result.error("统计失败");
        }
    }


    /**
     * 将未结算的单子改为过期
     * @param req
     * @return
     */
    @PostMapping(value = "/orderPutOut")
    public Result<?> orderPutOut(HttpServletRequest req) {
        HttpSession session = req.getSession();
        try {
            List<ScalpOrder> orders = scalpOrderService.list(new QueryWrapper<ScalpOrder>().eq("order_status", 0).eq("pay_type", 0));
            if (orders.size() != 0) {
                orders.forEach(order -> order.setSystemId((String) session.getAttribute("user")));
                orders.forEach(order -> order.setOrderStatus(UsedCode.ORDER_PAST_DUE));
                scalpOrderService.updateBatchById(orders);
                return Result.ok("已将待审核订单改为过期！");
            } else {
                return Result.ok("目前没有待审核订单！");
            }
        } catch (Exception e) {
            log.error("orderPutOut：{}",e);
            return Result.error("订单取消失败!");
        }
    }

    /**
     * APP端查询订单列表
     * 0全部  1已发货（已确认） 2待发货（待确认）   3已完成（已回调）
     * @return
     */
    @GetMapping(value = "/queryOrderList")
    public Result<?> queryOrderList(HttpServletRequest request,
                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        Map<String, String> map = StringUtil.request2Map(request);
        log.info("订单列表查询参数：{}", JSON.toJSONString(map));
        String userId = map.get("userId");
        int status = Integer.parseInt(map.get("status"));
        if(!StringUtil.isEmpty(map.get("pageNo"))){
            int no = Integer.parseInt(map.get("pageNo"));
            if(no != 1){
                pageNo = no;
            }
        }
        if(StringUtil.isEmpty(userId)){
            return Result.error("参数缺失");
        }
        Map<String,Object> params = new HashMap<>();
        if(status == 1 ){
            params.put("user_pass",1);
        }else if(status == 2){
            params.put("userPassType",2);
        }else if(status == 3){
            params.put("payStatus",1);
        }
        params.put("userId",userId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String,Object>> list = scalpOrderService.queryOrderList(params);
        return Result.ok(list);

    }

    /**
     * 商户提现审核
     * @param id
     * @param status
     * @param remark
     * @return
     */
    @PostMapping(value="/auditWith")
    public Result<?> auditWith(String id,Integer status,String remark,HttpServletRequest req){
        HttpSession session = req.getSession();
        try {
            ScalpAccount scalpAccount=scalpAccountService.getById(id);
            Merchant merchant=merchantService.getById(scalpAccount.getUserId());
            if(scalpAccount.getStatus()!=UsedCode.ACCOUNT_STATUS_WAIT) return Result.error("审核失败，当前账目不在待审核状态！");
            if(status==UsedCode.ACCOUNT_STATUS_CANCEL){         //账目驳回
                if(StringUtil.isEmpty(remark)) return Result.error("驳回需要填写理由！");
                merchant.setBalance(merchant.getBalance()+scalpAccount.getOperationMoney());
            }
            merchant.setWithFrozen(merchant.getWithFrozen()-scalpAccount.getOperationMoney());
            scalpAccount.setStatus(status);
            scalpAccount.setRemark(remark);
            scalpAccount.setSystemId((String)session.getAttribute("user"));
            //修改账目记录信息
            ScalpAccountLog scalpAccountLog=scalpAccountLogService.getOne(new QueryWrapper<ScalpAccountLog>().eq("account_num",scalpAccount.getAccountNum()));
            scalpAccountLog.setIsShow(1);   //有效
            //添加管理员收益
            SystemEarn systemEarn=new SystemEarn(merchant.getId(),(String)session.getAttribute("user"),scalpAccount.getServiceMoney(),new Date());
            //修改信息
            merchantService.updateById(merchant);
            scalpAccountService.updateById(scalpAccount);
            scalpAccountLogService.updateById(scalpAccountLog);
            systemEarnService.save(systemEarn);
            return Result.ok("审核成功！");
        } catch (Exception e) {
            log.error("商户提现审核auditWith：auditWith{}",e);
            return Result.error("审核订单失败");
        }
    }





    /**
     * 五秒执行一次，判断订单是否超时
     * 根据订单过期时间储存列，查看是否有过期订单，节约查询数据库资源
     * 查询订单，并修改状态
     */
//    @Scheduled(fixedRate = 300000)
    private void getOrderOverTime() {
        QueryWrapper<ScalpOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_status", 0).eq("pay_status", 0);
        queryWrapper.lt("past_time", System.currentTimeMillis());
        List<ScalpOrder> pastOrders = scalpOrderService.list(queryWrapper);
        if (pastOrders.size() != 0) {
            for (ScalpOrder order:pastOrders) {
                order.setOrderStatus(UsedCode.ORDER_PAST_DUE);
                scalpOrderService.updateById(order);
                scalpOrderService.putFreezeMoney(order,UsedCode.ORDER_PAST_DUE,Object->{
                    log.error("订单"+order.getOrderId()+"已超时！");
                    return Result.ok();
                });
            }
        }
    }

    /**
     * 根据商户获取开业中，库存足够，收款码金额相匹配， （TODO 权重），随机抽取码商并分配收款码
     * @param merchantId
     * @param shopId
     * @return
     */
    public Map<String,Object> getPaymentCode(String merchantId,String shopId,int money){
        Map<String,Object> params = new HashMap<>();
        params.put("merchantId",merchantId);
        params.put("shopId",shopId);
        params.put("money",money);
        List<Map<String,Object>> list = qrCodeService.getPaymentCode(params);
        Random random = new Random();
        Map<String,Object> result = list.get(random.nextInt(list.size()));
        return result;
    }
}
