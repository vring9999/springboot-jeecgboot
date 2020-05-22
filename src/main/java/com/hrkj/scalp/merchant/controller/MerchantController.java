package com.hrkj.scalp.merchant.controller;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.hrkj.scalp.account.entity.ScalpAccount;
import com.hrkj.scalp.account.service.IScalpAccountService;
import com.hrkj.scalp.accountLog.entity.ScalpAccountLog;
import com.hrkj.scalp.accountLog.service.IScalpAccountLogService;
import com.hrkj.scalp.merchant.entity.Code;
import com.hrkj.scalp.merchant.entity.MerchantUserVo;
import com.hrkj.scalp.merchant.service.ICodeService;
import com.hrkj.scalp.role.entity.Role;
import com.hrkj.scalp.role.entity.UserRole;
import com.hrkj.scalp.role.service.IRoleService;
import com.hrkj.scalp.role.service.IUserRoleService;
import com.hrkj.scalp.shiro.vo.DefContants;
import com.hrkj.scalp.util.*;
import com.hrkj.scalp.util.rsa.MD5Util;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.user.entity.User;
import com.hrkj.scalp.merchant.entity.Merchant;
import com.hrkj.scalp.merchant.service.IMerchantService;
import com.hrkj.scalp.user.service.IUserService;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: farming_merchant
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@RestController
@RequestMapping("/merchant")
@Slf4j
public class MerchantController {
    @Autowired
    private IMerchantService merchantService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICodeService codeService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IScalpAccountService scalpAccountService;
    @Autowired
    private IScalpAccountLogService scalpAccountLogService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private static CacheInfo cacheInfo;

    /**
     * @param cacheInfo
     * @Title:SendMsgUtil
     * @Description:项目初始化时注入cacheUtil
     */
    @Autowired
    public MerchantController(CacheInfo cacheInfo) {
        MerchantController.cacheInfo = cacheInfo;
    }

    /**
     * 分页列表查询商户
     *
     * @param merchant
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<?> queryPageList(Merchant merchant,String phoneId, String openTime, String endTime,
                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<Merchant> queryWrapper = QueryGenerator.initQueryWrapper(merchant, req.getParameterMap());
        if(!StringUtil.isEmpty(phoneId)) queryWrapper.eq("id",phoneId).or().eq("phone",phoneId);
        if(!StringUtil.isEmpty(openTime)) queryWrapper.ge("register_time", openTime);//.ge 添加 >= 的条件判断
        if(!StringUtil.isEmpty(endTime)) queryWrapper.le("register_time", endTime);//.le 添加 <= 的条件判断
        queryWrapper.select(Merchant.class,i -> !i.getColumn().equals("password"));
        Page<Merchant> page = new Page<Merchant>(pageNo, pageSize);
        IPage<Merchant> pageList = merchantService.page(page, queryWrapper);
        return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
    }


    /**
     * pc端获取码商||商户的基础信息
     * @return
     */
    @GetMapping("/queryBasicInfo")
    public Result<?>  queryBasicInfo(HttpServletRequest request){
        String id = JwtUtil.getId(request.getHeader(DefContants.X_ACCESS_TOKEN));
        String loginType = JwtUtil.getLoginType(request.getHeader(DefContants.X_ACCESS_TOKEN));
        if(StringUtil.isEmpty(loginType)) return Result.error("token 失效");
        if(StringUtil.isEmpty(id)) return Result.error("参数缺失");
        MerchantUserVo vo = new MerchantUserVo();
        if(loginType.equals(UsedCode.LOGIN_TYPE_MERCHANT)){
            vo = merchantService.queryBasicInfo(id);
        }else if(loginType.equals(UsedCode.LOGIN_TYPE_USER)){
            vo = userService.queryBasicInfo(id);
        }else return Result.error("未知的额数据类型");
        return Result.ok(vo);
    }

    /**
     * 分页列表查询邀请码
     *
     * @param code
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/codelist")
    public Result<?> queryPageList(Code code,
                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<Code> queryWrapper = QueryGenerator.initQueryWrapper(code, req.getParameterMap());
        Page<Code> page = new Page<Code>(pageNo, pageSize);
        IPage<Code> pageList = codeService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     *   添加
     *
     * @param merchant
     * @return
     */
    @AutoLog(value = "商户---添加")
    @PostMapping(value = "/add")
    public Result<?> add(Merchant merchant,HttpServletRequest request) {
        String merchantId = StringUtil.getRandomMath(9);
        Merchant old=merchantService.getOne(new QueryWrapper<Merchant>().eq("phone",merchant.getPhone()));
        if(old!=null) return Result.error("手机号已被注册！");
        merchant.setId(merchantId);
        String ip = UserAgentUtil.getRealIP(request);
        merchant.setRegisterIp(ip);
        merchant.setPassword(UsedCode.SALT_MD5_PWD);
        merchantService.save(merchant);
        //绑定商户角色
        String roleId = roleService.getOne(new QueryWrapper<Role>().eq("role_pinyin",UsedCode.LOGIN_TYPE_MERCHANT)).getId();
        UserRole sysUserRole = new UserRole(roleId,merchantId);
        userRoleService.save(sysUserRole);
        return Result.ok("添加成功！");
    }

    /**
     *   批量生成邀请码
     *
     * @param merchantId
     * @return
     */
    @PostMapping(value = "/createBatchCode")
    public Result<?> createBatchCode(String merchantId) {
        Merchant merchant = merchantService.getById(merchantId);
        List<Code> list = new ArrayList<>();
        if(null == merchant) merchantId = "---";
        for(int i = 0 ; i < 10 ; i++){
            Code code = new Code();
            code.setMerchantId(merchantId);
            String code_str = StringUtil.getRandomString(10);
            code.setCode(code_str);
            list.add(code);
        }
        codeService.saveBatch(list);
        List<Code> codeList = codeService.getBaseMapper().selectList(new QueryWrapper<Code>().eq("merchant_id", merchantId));
        return Result.ok(codeList);
    }
    /**
     *  修改商户 余额
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/editBalance")
    @AutoLog(value = "PC端修改商户余额")
    public Result<?> editBalance(@RequestBody Map<String,Object> params) {
        String id = (String) params.get("id");
        String operator_type = (String) params.get("operator_type");
        int value = Integer.parseInt((String) params.get("value"));
        String remark = (String) params.get("remark");
        if(StringUtil.isEmpty(id) ) {
            return Result.error("参数缺失");
        }
        Merchant merchant = merchantService.getBaseMapper().selectOne(new QueryWrapper<Merchant>().eq("id", id));
        if(null == merchant) return Result.error("商户不存在");
        int balance = merchant.getBalance();
        if(operator_type.equals(UsedCode.BALANCE_ADD))
            balance += value;
        else if(operator_type.equals(UsedCode.BALANCE_REDUCE)) {
            balance -= value;
            if (balance < 0) return Result.error("余额不足");
        }else return Result.error("未知的操作类型");
        Map<String,Object> param = new HashMap<>();
        param.put("balance",balance);
        param.put("remark1",remark);
        param.put("merchantId",id);
        merchantService.editLockStatus(param);
        return Result.ok("编辑成功!");
    }


    /**
     *  管理员操作开业 强制打烊
     * @return
     */
    @PostMapping(value = "/editLockStatus")
    public Result<?> editLockStatus(@RequestBody Map<String,Object> params) {
        try {
            Integer type=Integer.parseInt(params.get("type").toString());
            String id = (String)params.get("id");
            if(StringUtil.isEmpty(id) || type==null) return Result.error("参数缺失");
            if(type==UsedCode.GET_MERCHANT){        //商户
                merchantService.editLockStatus(params);
            }else if(type==UsedCode.GET_USER){      //码商
                userService.editLockStatus(params);
            }
            return Result.ok("编辑成功!");
        } catch (Exception e) {
            log.error("{}",e);
            return Result.error("操作失败！");
        }
    }

    /**
     *  管理员操作--->商户 || 用户改密
     *
     * @param newPwd
     * @return
     */
    @PostMapping(value = "/updatePwd")
    @AutoLog("pc端改密")
    public Result<?> editBalance(String newPwd,String id) throws Exception {
        if(StringUtil.isEmpty(newPwd) || StringUtil.isEmpty(id))
            return Result.error("参数缺失");

        newPwd = MD5Util.getPwd(newPwd);
        String saltMD5pwd = MD5Util.getSaltMD5(newPwd);
        Map<String,Object> params = new HashMap<>();
        params.put("password",saltMD5pwd);
        params.put("id",id);
        Merchant merchant = merchantService.getById(id);
        if(null == merchant){
            userService.editLockStatus(params);
        }else {
            merchantService.editLockStatus(params);
        }
        return Result.ok("编辑成功!");
    }

    /**
     * 商户提现申请
     * @return
     */
    @PostMapping(value = "/withBalance")
    public Result<?> withBalance(String id,Integer withMoney,String receiptBank,String receiptName,String bankNumber){
        log.info("商户提现参数-->id:{},withMoney:{},receiptBank：{},receiptName:{},bankNumber:{}");
        try {
            Merchant merchant=merchantService.getById(id);  //获取商户
            //判断余额
            int oldMoney=merchant.getBalance();
            int newMoney=merchant.getBalance()-withMoney;
            if(newMoney<0) return Result.error("提现失败，当前金额不足！");
            //计算手续费，实际金额
            double withProfit=Double.parseDouble(cacheInfo.getCommonToKey("scalp.merchant+with.balance"));
            int serviceMoney=new BigDecimal(withProfit*withMoney).setScale(0,   BigDecimal.ROUND_HALF_UP).intValue();
            int actualMoney=withMoney-serviceMoney;
            String accNum=StringUtil.getRandomMath(10);
            merchant.setBalance(newMoney);
            merchant.setWithFrozen(merchant.getWithFrozen()+withMoney);
            //添加账目审核信息
            ScalpAccount account=new ScalpAccount(accNum,id,UsedCode.GET_MERCHANT,UsedCode.RECORD_TYPE_OUT,
                                                withMoney,actualMoney,serviceMoney,receiptBank,receiptName,bankNumber,
                                                UsedCode.ACCOUNT_STATUS_WAIT);
            //添加记录信息
            ScalpAccountLog scalpAccountLog=new ScalpAccountLog(accNum,id,UsedCode.GET_MERCHANT,oldMoney,newMoney,withMoney,
                    UsedCode.BALANCE_REDUCE,UsedCode.RECORD_TYPE_OUT,withProfit,UsedCode.getWithTxt(withMoney),
                    0);

            scalpAccountService.save(account);
            scalpAccountLogService.save(scalpAccountLog);
            merchantService.updateById(merchant);
            return Result.ok("提现申请成功-->待审核！");
        } catch (Exception e) {
            log.error("商户提现：{}",e);
            return Result.error("提现申请失败，请稍后再试！");
        }
    }


    /**
     *  编辑
     *
     * @param merchant
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<?> edit(Merchant merchant) {
        merchantService.updateById(merchant);
        return Result.ok("编辑成功!");
    }



    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        merchantService.delMain(id);
        return Result.ok("删除成功!");
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.merchantService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }

    /**
     *  批量删除邀请码
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatchCode")
    public Result<?> deleteBatchCode(@RequestParam(name="ids",required=true) String ids) {
        this.codeService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        Merchant merchant = merchantService.getById(id);
        if(merchant==null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(merchant);

    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryUserByMainId")
    public Result<?> queryUserListByMainId(@RequestParam(name="id",required=true) String id) {
        List<User> userList = userService.selectByMainId(id);
        return Result.ok(userList);
    }




    /**
     * 获取验证码
     * @return
     */
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    public Result<?> sendCode(HttpServletRequest request) {
        try {
            Map<String, String> map = StringUtil.request2Map(request);
            String phone=map.get("phone");
            String type=map.get("type");
            phone = InputInjectFilter.encodeInputString(phone);
            type = InputInjectFilter.encodeInputString(type);
            // 检查手机号码正确性
            if (SendMsgUtil.checkPhoneNum(phone)) {
                // 获取随机数
                StringBuffer code = StringUtil.getRandomCode(6);
                String templateParam = "{\"code\":\"" + code + "\"}";
                String templateId = "aliyun.properties+sms.temp." + type;
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("phoneNum", phone);
                params.put("templateParam", templateParam);
                params.put("templateCode", CacheInfo.getCommonToKey(templateId));
                SendMsgUtil sendMsgUtil = new SendMsgUtil();
                JSONObject json = new JSONObject();
                try {
                    json = sendMsgUtil.sendSms(params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ("OK".equals(json.get("code"))) {
                    // 将手机号码，验证码以及当前时间放入缓存；
                    String checkMsg = code.toString() + "," + System.currentTimeMillis();
                    redisUtil.ins(phone, checkMsg,120);	//验证码存入redis有效期为两分钟
                    log.info("--------------------------已将手机号和验证码存入redis--------------------");
                    return Result.ok("验证码发送成功!");
                } else {
                    return Result.error("验证码发送失败!");
                }
            } else {
                return Result.error("请输入正确手机号码!");
            }
        } catch (Exception e) {
            log.error("验证码发送失败:{}",e);
            return Result.error("验证码发送失败，请稍后再试！");
        }
    }


}
