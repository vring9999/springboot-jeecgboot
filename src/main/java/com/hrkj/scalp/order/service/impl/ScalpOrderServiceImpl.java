package com.hrkj.scalp.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hrkj.scalp.accountLog.entity.ScalpAccountLog;
import com.hrkj.scalp.accountLog.service.IScalpAccountLogService;
import com.hrkj.scalp.config.entity.Group;
import com.hrkj.scalp.config.mapper.GroupMapper;
import com.hrkj.scalp.merchant.entity.Merchant;
import com.hrkj.scalp.merchant.entity.QrCode;
import com.hrkj.scalp.merchant.mapper.MerchantMapper;
import com.hrkj.scalp.merchant.mapper.QrCodeMapper;
import com.hrkj.scalp.order.entity.ScalpOrder;
import com.hrkj.scalp.order.entity.SubCommission;
import com.hrkj.scalp.order.mapper.ScalpOrderMapper;
import com.hrkj.scalp.order.mapper.SubCommissionMapper;
import com.hrkj.scalp.order.service.IScalpOrderService;
import com.hrkj.scalp.stock.entity.UserStock;
import com.hrkj.scalp.stock.mapper.UserStockMapper;
import com.hrkj.scalp.system.entity.Manghe;
import com.hrkj.scalp.system.mapper.MangheMapper;
import com.hrkj.scalp.user.entity.User;
import com.hrkj.scalp.user.service.IUserService;
import com.hrkj.scalp.util.CacheInfo;
import com.hrkj.scalp.util.UsedCode;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @Description: scalp_order
 * @Author: jeecg-boot
 * @Date:   2020-03-09
 * @Version: V1.0
 */
@Service
@Slf4j
public class ScalpOrderServiceImpl extends ServiceImpl<ScalpOrderMapper, ScalpOrder> implements IScalpOrderService {

    @Autowired
    private ScalpOrderMapper orderMapper;
    @Resource
    private IUserService userService;
    @Resource
    private MerchantMapper merchantMapper;
    @Resource
    private IScalpAccountLogService accountLogService;
    @Resource
    private SubCommissionMapper subCommissionMapper;
    @Resource
    private UserStockMapper stockMapper;
    @Resource
    private GroupMapper groupMapper;
    @Resource
    private MangheMapper mangheMapper;
    @Autowired
    private static CacheInfo cacheInfo;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private QrCodeMapper qrCodeMapper;


    /**
     * @param cacheInfo
     * @Title:SendMsgUtil
     * @Description:项目初始化时注入cacheUtil
     */
    @Autowired
    public ScalpOrderServiceImpl(CacheInfo cacheInfo) {
        ScalpOrderServiceImpl.cacheInfo = cacheInfo;
    }
    @Override
    public List<Map<String,Object>> queryEarnMoneyForApp(String userId) {
        return orderMapper.queryEarnMoneyForApp(userId);
    }

    @Override
    public Integer queryUserEarnForApp(String userId) {
        return orderMapper.queryUserEarnForApp(userId);
    }

    @Override
    public List<Map<String, Object>> queryOrderList(Map<String, Object> params) {
        return orderMapper.queryOrderList(params);
    }

    @Override
    public Map<String, Object> queryOrderInfo(String userId, String merchantId, int flag) {
        return orderMapper.queryOrderInfo(userId,merchantId,flag);
    }

    @Override
    public List<Map<String, Object>> allRanking() {
        return orderMapper.allRanking();
    }

    @Override
    public List<Map<String, Object>> smallRanking(String money) {
        return orderMapper.smallRanking(money);
    }

    @Override
    public List<Map<String, Object>> inviteRanking() {
        return orderMapper.inviteRanking();
    }

    @Override
    public Map<String, Object> allUnit(String userId) {
        return orderMapper.allUnit(userId);
    }

    @Override
    public Map<String, Object> smallUnit(String userId, String money) {
        return orderMapper.smallUnit(userId,money);
    }

    @Override
    public Map<String, Object> inviteUnit(String userId) {
        return orderMapper.inviteUnit(userId);
    }

    /**
     * 添加订单类型账目
     * @param function
     * @return
     */
    public Result<?> planOrder(ScalpOrder scalpOrder, Function<Result<?>, Result<?>> function) {
        log.info("订单添加完毕，开始处理订单账目信息");
        try {
            /**----获取基础信息----*/
            User user = userService.getById(scalpOrder.getUserId());    //获取码商信息
            Merchant merchant = merchantMapper.selectById(scalpOrder.getMerchantId());  //获取商户信息
            Group group = groupMapper.selectOne(new QueryWrapper<Group>().eq("group_name", user.getGroupName()).eq("type", 1));
            List<ScalpAccountLog> accountLogs = new ArrayList<>();
            /**----初始化所需数据----*/
            //Double M_platformProfit=0.0;                          //商户--计算商户的订单费率 = 码商费率 + 平台利润

            /**----获取码商的费率----*/
            Double U_rateRatio = group.getDefaultPer();  //码商费率

            /**----计算商户费率（码商费率 + 平台利润）----*/
            //M_platformProfit=Double.parseDouble(cacheInfo.getCommonToKey("scalp.platform+order.profit"))+U_rateRatio;
            //获取码商该商品库存
            UserStock userStock=stockMapper.selectOne(new QueryWrapper<UserStock>().eq("user_id",scalpOrder.getUserId()).eq("m_id",scalpOrder.getShopId()));
            int U_lostShopNum=userStock.getNum()-1;//操作后码商的库存数量

            //获取商品信息
            int M_payMoney=scalpOrder.getEarnMoney();    //商户--计算拿到的钱
            int M_allMoney=merchant.getBalance()+M_payMoney; //商户--计算后的钱总和

            /**----初始化商户，码商的账目信息----*/
            //码商--设置一笔扣除保证金记录
            ScalpAccountLog accountLose = new ScalpAccountLog(scalpOrder.getOrderId(), scalpOrder.getUserId(), UsedCode.GET_USER,
                    scalpOrder.getShopId(),userStock.getNum(),U_lostShopNum,1, UsedCode.BALANCE_REDUCE, UsedCode.RECORD_TYPE_ORD,
                    0.0, UsedCode.getEnsTxt(1), 0);
            accountLogs.add(accountLose);       //码商--扣除保证金记录
            //码商--设置一笔添加利润记录
            ScalpAccountLog accountAdd = new ScalpAccountLog(scalpOrder.getOrderId(), scalpOrder.getUserId(), UsedCode.GET_USER,
                    scalpOrder.getShopId(),U_lostShopNum,U_lostShopNum, 0, UsedCode.BALANCE_ADD, UsedCode.RECORD_TYPE_ORD,
                    U_rateRatio, "", 0);
            accountLogs.add(accountAdd);        //码商--添加利润记录
            //商户--设置一笔添加客户支付记录
            ScalpAccountLog accountAddPay = new ScalpAccountLog(scalpOrder.getOrderId(), scalpOrder.getMerchantId(), UsedCode.GET_MERCHANT,
                    merchant.getBalance(), M_allMoney, M_payMoney, UsedCode.BALANCE_ADD, UsedCode.RECORD_TYPE_ORD,
                    0.0, UsedCode.getPayTxt(M_payMoney),0);
            accountLogs.add(accountAddPay);     //商户--添加客户支付记录


            /**----修改商户，码商冻结金额----*/
            //商户--修改冻结金额
            merchant.setFrozen(merchant.getFrozen()+M_payMoney);
            //码商--修改冻结库存
            userStock.setOrderFrozen(userStock.getOrderFrozen()+1);
            userStock.setNum(userStock.getNum()-1);

            //批零添加账目信息
            accountLogService.saveBatch(accountLogs);
            merchantMapper.updateById(merchant);
            stockMapper.updateById(userStock);

            return function.apply(Result.ok());
        } catch (Exception e) {
            log.error("订单创建失败planOrder：{}",e);
            return Result.error("添加订单失败");
        }
    }

    /**
     * 审核通过时-计算 码商，商户 冻结金额
     * @param function
     * @return
     */
    public Result<?> putFreezeMoney(ScalpOrder order, int status, Function<Result<?>, Result<?>> function){
        try {
            /**----获取基础信息----*/
            User user = userService.getById(order.getUserId());    //获取码商信息
            Merchant merchant = merchantMapper.selectById(order.getMerchantId());  //获取商户信息
            if(status== UsedCode.ORDER_YES_PAYMENT){     //订单通过
                if(!fixedInto(user,merchant,order)) return Result.error("库存结算失败！");
            }else{      //订单取消，过期
                if(!refuseInfo(user,merchant,order))return Result.error("库存结算失败！");
            }
            return function.apply(Result.ok());
        } catch (Exception e) {
            log.error("订单审核计算失败putFreezeMoney：{}",e);
            return Result.error("冻结金额结算失败！");
        }
    }

    /**
     * 订单通过--码商(分润、库存、收益)计算，商户收益，订单、账目更新
     * @param user
     * @return
     */
    private boolean fixedInto(User user,Merchant merchant,ScalpOrder order){
        try {
            //获取分组
            Group group = groupMapper.selectOne(new QueryWrapper<Group>().eq("group_name", user.getGroupName()).eq("type", 1));
            Manghe manghe=mangheMapper.selectById(order.getShopId());
            int profit=manghe.getProfit();
            int profitMoney=new BigDecimal(profit*group.getDefaultPer()).setScale(0,   BigDecimal.ROUND_HALF_UP).intValue();
            int allPro=manghe.getProfit();   //计算利润总扣后的钱
            //获取码商的商品信息
            UserStock userStock=stockMapper.selectOne(new QueryWrapper<UserStock>().eq("user_id",user.getId()).eq("m_id",order.getShopId()));
            List<User> users=new ArrayList<>();
            String parentId=user.getParentId();
            //获取利润分润
            Integer proType=Integer.parseInt(cacheInfo.getCommonToKey("scalp.order+profit.type"));
            SubCommission subCommission=new SubCommission(order.getOrderId(),user.getId());
            if(user.getProxyLevel() != 1) { //判断是否是一级码商
                for (int i = 0; i < user.getProxyLevel(); i++) {    //循环父级码商
                    User user1 = userService.getOne(new QueryWrapper<User>().eq("id", parentId));
                    if (proType == UsedCode.ORDER_PROFIT) {     //分润计算
                        Group group1 = groupMapper.selectOne(new QueryWrapper<Group>().eq("group_name", user1.getGroupName()).eq("type", 1));
                        profitMoney=new BigDecimal(profit*group1.getDefaultPer()).setScale(0,   BigDecimal.ROUND_HALF_UP).intValue();
                        profit=profitMoney;
                    }else{      //固定利润

                    }
                    subCommission=updSubComm(subCommission,user1.getId(),user1.getProxyLevel(),profitMoney);
                    allPro -= profitMoney;
                    user1.setVoucherMoney(user1.getVoucherMoney() + profitMoney);
                    user1.setProxyMoney(user1.getProxyMoney() + profitMoney);
                    user1.setDayAccount(user1.getDayAccount()+profitMoney);
                    users.add(user1);
                    if (user1.getProxyLevel() != 1) {
                        parentId = user1.getParentId();
                    }else{
                        break;
                    }
                }
            }
            /**----计算码商冻结金额----*/
            List<ScalpAccountLog> accountLogs=accountLogService.list(new QueryWrapper<ScalpAccountLog>().eq("account_num",order.getOrderId()));
            int M_addMoney=0;   //商户-余额添加，冻结扣除的金额
            for(ScalpAccountLog log:accountLogs){
                if(log.getUserType()==UsedCode.GET_MERCHANT){       //商户
                    M_addMoney=log.getChangeMoney();
                    order.setMerchantEarn(M_addMoney);
                }else if(log.getUserType()==UsedCode.GET_USER){     //码商
                    if(log.getType().equals(UsedCode.BALANCE_REDUCE)){      //减少
                    }else if(log.getType().equals(UsedCode.BALANCE_ADD)){   //添加
                        log.setRemark(UsedCode.getProTxt(allPro));
                    }
                }
                log.setIsShow(1);
                log.setPutType(order.getQrType());
            }
            merchant.setFrozen(merchant.getFrozen()-M_addMoney);
            merchant.setBalance(merchant.getBalance()+M_addMoney);

            order.setUserEarn(allPro);
            user.setVoucherMoney(user.getVoucherMoney() + allPro);
            user.setDayAccount(user.getDayAccount()+allPro);
            users.add(user);
            userStock.setOrderFrozen(userStock.getOrderFrozen() - order.getShopNum());
            userStock.setDayCount(userStock.getDayCount() + 1);
            /** 添加代理记录 */
            subCommission.setProxyMoney(allPro);
            /**数据库修改*/
            orderMapper.updateById(order);
            redisUtil.delete(UsedCode.ORDER_PREFIX+order.getOrderId());
            //修改订单
            accountLogService.updateBatchById(accountLogs);  //修改账目信息
            stockMapper.updateById(userStock);     //修改码商库存
            merchantMapper.updateById(merchant);//修改商户余额
            userService.updateBatchById(users);         //修改码商数据
            subCommissionMapper.insert(subCommission); //添加码商分润信息
            //修改二维码信息
            QrCode qrCode=qrCodeMapper.selectById(order.getQrId());
            qrCode.setSurplusAccount(qrCode.getSurplusAccount()-order.getEarnMoney());
            qrCode.setTodayAccount(qrCode.getTodayAccount()+order.getEarnMoney());
            qrCodeMapper.updateById(qrCode);
            return true;
        } catch (Exception e) {
            log.error("订单审核账目更新失败fixedInto：{}",e);
            return false;
        }
    }

    /**
     * 订单驳回--恢复原数据
     * @param user
     * @param merchant
     * @param order
     * @return
     */
    private boolean refuseInfo(User user,Merchant merchant,ScalpOrder order){
        try {
            ScalpAccountLog accountLog=accountLogService.getOne(new QueryWrapper<ScalpAccountLog>().eq("user_type",UsedCode.GET_MERCHANT).eq("account_num",order.getOrderId()));
            UserStock userStock=stockMapper.selectOne(new QueryWrapper<UserStock>().eq("user_id",user.getId()).eq("m_id",order.getShopId()));
            userStock.setNum(userStock.getNum()+1);
            userStock.setOrderFrozen(userStock.getOrderFrozen() - 1);
            merchant.setFrozen(merchant.getFrozen()-accountLog.getChangeMoney());
            /**----修改 码商库存、商户余额、删除账目信息----*/
            merchantMapper.updateById(merchant);
            stockMapper.updateById(userStock);
            accountLogService.remove(new QueryWrapper<ScalpAccountLog>().eq("account_num",order.getOrderId()));
            return true;
        } catch (Exception e) {
            log.error("订单驳回失败refuseInfo：{}",e);
            return false;
        }
    }

    /**
     * 初始化代理记录信息
     * @param sub
     * @param id
     * @param level
     * @param money
     * @return
     */
    public SubCommission updSubComm(SubCommission sub,String id,int level,int money){
        if(level==4){           //代理等级=4
            sub.setFourUserId(id);
            sub.setFourUserMoney(money);
        }else if(level==3){     //代理等级=3
            sub.setThreeUserId(id);
            sub.setThreeUserMoney(money);
        }else if(level==2){     //代理等级=2
            sub.setTwoUserId(id);
            sub.setTwoUserMoney(money);
        }else if(level==1){     //代理等级=1
            sub.setOneUserId(id);
            sub.setOneUserMoney(money);
        }
        return sub;
    }
}
