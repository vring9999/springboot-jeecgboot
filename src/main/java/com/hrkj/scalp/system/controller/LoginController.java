package com.hrkj.scalp.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hrkj.scalp.merchant.entity.Merchant;
import com.hrkj.scalp.user.entity.User;
import com.hrkj.scalp.merchant.service.IMerchantService;
import com.hrkj.scalp.user.service.IUserService;
import com.hrkj.scalp.shiro.vo.DefContants;
import com.hrkj.scalp.system.entity.SysLoginModel;
import com.hrkj.scalp.system.entity.SystemLog;
import com.hrkj.scalp.system.entity.SystemUser;
import com.hrkj.scalp.system.mapper.SystemLogMapper;
import com.hrkj.scalp.system.service.ISystemUserService;
import com.hrkj.scalp.util.StringUtil;
import com.hrkj.scalp.util.UsedCode;
import com.hrkj.scalp.util.gsonadapter.GsonUtil;
import com.hrkj.scalp.util.rsa.KeyManager;
import com.hrkj.scalp.util.rsa.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginModel;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.function.Function;

/**
 * @author vring
 * @ClassName LoginController.java
 * @Description TODO
 * @createTime 2020/3/6 17:51
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController{
    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IMerchantService iMerchantService;

    @Autowired
    private ISystemUserService iSystemUserService;

    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private SystemLogMapper systemLogMapper;

    @PostMapping(value = "/getPublicKey")
    public JSONObject getPublicKey() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("publicKey", KeyManager.getPublic_key());
        log.info("获取公钥：{},",KeyManager.getPublic_key());
        log.info("获取私钥：{}",KeyManager.getPrivate_key());
        return jsonObject;
    }

    @PostMapping(value = "/getPublicKeyForApp")
    public Result<?> getPublicKeyForApp() {
        log.info("获取公钥：{}",KeyManager.getPublic_key());
        log.info("------------------------------------------------------");
        log.info("获取私钥：{}",KeyManager.getPrivate_key());
        return Result.okData(KeyManager.getPublic_key());
    }

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public Result<JSONObject> login(@RequestBody SysLoginModel sysLoginModel,HttpServletRequest req) throws Exception {
        log.info("登录参数：account:{},userType:{},safeCode:{}",sysLoginModel.getAccount(),sysLoginModel.getLoginType(),sysLoginModel.getSafeCode());
        HttpSession session = req.getSession();
        Result<JSONObject> result = new Result<JSONObject>();
        boolean empty = StringUtil.checkObjAllFieldsIsNull(sysLoginModel);
        if(empty) return result.error500("登录信息为空");
        String account = sysLoginModel.getAccount();
        String encryptionPassword = sysLoginModel.getPassword();
        JSONObject jsonObject = new JSONObject();
        if(StringUtil.isEmpty(sysLoginModel.getLoginType())) return result.error500("登录参数缺失");
        if(sysLoginModel.getLoginType() .equals(UsedCode.LOGIN_TYPE_ADMIN)){//管理员
            //校验用户是否有效
            SystemUser systemUser = iSystemUserService.querySystemUserByAccount(account);
            result = iSystemUserService.checkUserIsEffective(systemUser);
            if(!result.isSuccess()) {
                return result;
            }
            //密码校验
            result = checkPwd(encryptionPassword,systemUser.getPassword());
            if(!result.isSuccess()) {
                return result;
            }
            //安全口令校验
            String safeCode = sysLoginModel.getSafeCode();
            result = checkPwd(safeCode,systemUser.getSafeCode());
            if(!result.isSuccess()) {
                return result;
            }
            //生成token
//            jsonObject = userInfo(systemUser.getAccount(), systemUser.getPassword(),jsonObject);
            jsonObject = userInfo(systemUser.getAccount(), systemUser.getPassword(),UsedCode.LOGIN_TYPE_ADMIN,String.valueOf(systemUser.getId()),jsonObject);
            jsonObject.put("userInfo", systemUser);
            session.setAttribute("user", systemUser.getAccount());
            //将登陆以后信息存入redis
            redisUtil.set(sysLoginModel.getAccount(), GsonUtil.boToString(systemUser));
        }else if(sysLoginModel.getLoginType() .equals(UsedCode.LOGIN_TYPE_MERCHANT)){//商户
            //校验用户是否有效
            Merchant merchant = iMerchantService.queryMerchantByPhone(account);
            result = iMerchantService.checkUserIsEffective(merchant);
            if(!result.isSuccess()) {
                return result;
            }
            //密码校验
            result = checkPwd(encryptionPassword,merchant.getPassword());
            if(!result.isSuccess()) {
                return result;
            }
            //生成token
//            jsonObject = userInfo(merchant.getPhone(), merchant.getPassword(),jsonObject);
            jsonObject = userInfo(merchant.getPhone(), merchant.getPassword(),UsedCode.LOGIN_TYPE_MERCHANT,merchant.getId(),jsonObject);
            jsonObject.put("userInfo", merchant);
            session.setAttribute("user", merchant.getName());
            redisUtil.set(sysLoginModel.getAccount(), GsonUtil.boToString(merchant));
        }else if(sysLoginModel.getLoginType() .equals(UsedCode.LOGIN_TYPE_USER)){//码商
            //校验用户是否有效
            User user = iUserService.getUserByIphone(account);
            result = iUserService.checkUserIsEffective(user);
            if(!result.isSuccess()) {
                return result;
            }
            //密码校验
            result = checkPwd(encryptionPassword,user.getPassword());
            if(!result.isSuccess()) {
                return result;
            }
            //生成token
//            jsonObject = userInfo(user.getPhone(), user.getPassword(),jsonObject);
            jsonObject = userInfo(user.getPhone(), user.getPassword(),UsedCode.LOGIN_TYPE_USER,user.getId(),jsonObject);
            jsonObject.put("userInfo", user);
            session.setAttribute("user", user.getName());
            redisUtil.set(sysLoginModel.getAccount(), GsonUtil.boToString(user));
        }
        jsonObject.put("loginType",sysLoginModel.getLoginType());
        result.setResult(jsonObject);
        result.success("登录成功");
        updateLast(account,sysLoginModel.getLoginType());
        sysBaseAPI.addLog(sysLoginModel.getLoginType()+": " + account + ",登录成功！", CommonConstant.LOG_TYPE_1, null);
        return result;
    }


    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    public Result<Object> logout(HttpServletRequest request) {
        //用户退出逻辑
        String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
        if(oConvertUtils.isEmpty(token)) {
            return Result.error("退出登录失败！");
        }
        String account = JwtUtil.getUsername(token);
        String loginType = JwtUtil.getLoginType(token);
        LoginModel loginModel = sysBaseAPI.getUserByName(account,loginType);
        if(loginModel != null){
            log.info(" 账号:  "+account+",退出成功！ ");
            //清空用户登录Token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户登录Shiro权限缓存
            redisUtil.del(CommonConstant.PREFIX_USER_SHIRO_CACHE + loginModel.getAccount());
            //清空缓存的用户信息
            redisUtil.del(account);
            //调用shiro的logout
            SecurityUtils.getSubject().logout();
            return Result.ok("退出登录成功！");
        }else {
            return Result.error("Token无效!");
        }
    }


    /**
     * 码商商户修改登入密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @PostMapping(value = "/updUserPwd")
    @AutoLog(value = "码商商户修改登录密码")
    public Result<?> edit(String oldPwd,String newPwd,HttpServletRequest request){
        try {
            String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
            String userId = JwtUtil.getId(token);
            if(oConvertUtils.isEmpty(token)) {
                return Result.error("token 失效");
            }
            String loginType = JwtUtil.getLoginType(token);
            if(loginType.equals(UsedCode.LOGIN_TYPE_USER)) {           //码商
                User user = iUserService.getById(userId);
                return checkPwd(oldPwd, user.getPassword(), Object -> {
                    user.setPassword(newPwd);
                    iUserService.updateById(user);
                    return Result.ok("修改成功");
                });
            }else if(loginType.equals(UsedCode.LOGIN_TYPE_MERCHANT)) {  //商户
                Merchant merchant=iMerchantService.getById(userId);
                return checkPwd(oldPwd, merchant.getPassword(), Object -> {
                    merchant.setPassword(newPwd);
                    iMerchantService.updateById(merchant);
                    return Result.ok("修改成功");
                });
            }else if(loginType.equals(UsedCode.LOGIN_TYPE_ADMIN)) {     //管理员
                SystemUser systemUser=iSystemUserService.getById(userId);
                return checkPwd(oldPwd, systemUser.getPassword(), Object -> {
                    systemUser.setPassword(newPwd);
                    iSystemUserService.updateById(systemUser);
                    return Result.ok("修改成功");
                });
            }else{
                return Result.error("用户类型有误，请重试或联系管理员！");
            }
        } catch (Exception e) {
            log.info("修改登录密码失败：{}",e);
            return Result.error("修改失败，请稍后再试！");
        }
    }

    /**
     * 修改安全密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @PostMapping(value = "/updSafetyPwd")
    public Result<?> updSafetyPwd(String oldPwd,String newPwd,HttpServletRequest request){
        try {
            String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
            String userId = JwtUtil.getId(token);
            if(oConvertUtils.isEmpty(token)) {
                return Result.error("token 失效");
            }
            String loginType = JwtUtil.getLoginType(token);
            if(loginType.equals(UsedCode.LOGIN_TYPE_USER)) {//码商
                User user = iUserService.getById(userId);
                return checkPwd(oldPwd, user.getSafetyPwd(), Object -> {
                    user.setSafetyPwd(newPwd);
                    iUserService.updateById(user);
                    return Result.ok("修改成功");
                });
            }else if(loginType.equals(UsedCode.LOGIN_TYPE_MERCHANT)) {  //商户
                Merchant merchant=iMerchantService.getById(userId);
                return checkPwd(oldPwd, merchant.getSafetyPwd(), Object -> {
                    merchant.setSafetyPwd(newPwd);
                    iMerchantService.updateById(merchant);
                    return Result.ok("修改成功");
                });
            }else{
                return Result.error("用户类型有误，请重试或联系管理员！");
            }
        } catch (Exception e) {
            log.info("修改安全密码失败：{}",e);
            return Result.error("修改失败，请稍后再试！");
        }
    }


    private Result<JSONObject> checkPwd(String loginPwd,String sql_pwd) throws Exception {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            log.info("登录时的密码：{},数据库密码：{}",loginPwd,sql_pwd);
            // 解密后的明文     修改后的密码校验
            String password = MD5Util.getPwd(loginPwd);
            log.info("解密后的登录明文：{}",password);
            //校验密码是否一致
            boolean checkPwd = MD5Util.getSaltverifyMD5(password, sql_pwd);
            if(!checkPwd){
                result.setSuccess(false);
                result.error500("密码错误");
                return result;
            }
        }catch (Exception e){
            result.setSuccess(false);
            log.error("登录密码解析失败：{}",e);
        }
        return result;
    }

    private Result<?> checkPwd(String loginPwd, String sql_pwd, Function<Result<?>,Result<?>> function) throws Exception {
        // 解密后的明文     修改后的密码校验
        String password = MD5Util.getPwd(loginPwd);
        //校验密码是否一致
        boolean checkPwd = MD5Util.getSaltverifyMD5(password, sql_pwd);
        if(!checkPwd){
            return Result.error("原密码错误，请重新输入");
        }
        return function.apply(Result.ok());
    }

    /**
     * 用户信息
     *
     * @param account
     * @param password
     * @return jsonObject
     */
    private JSONObject userInfo(String account,String password,String loginType,String id,JSONObject jsonObject) {
        // 生成token
        String token = JwtUtil.sign(account, password,loginType,id);
        System.out.println("account："+account+"-----token:"+token);
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);//过期时间为一小时
        jsonObject.put("token", token);
        return jsonObject;
    }


    /**
     * 注入上次登录信息
     *
     * @param account
     */
    private void updateLast(String account,String loginType) {
        QueryWrapper<SystemLog> queryWrapper = new QueryWrapper<SystemLog>();
        queryWrapper.eq("account",account).eq("log_type",1).orderByDesc("create_time").last("limit 1");
        SystemLog log = systemLogMapper.selectOne(queryWrapper);
//                systemLogMapper.getOne(queryWrapper);
//                systemLogMapper.selectOne(queryWrapper);
        if(null != log){
            if(loginType.equals(UsedCode.LOGIN_TYPE_MERCHANT)){       //商户
                UpdateWrapper<Merchant> updateWrapper = new UpdateWrapper<>();
                Merchant merchant = new Merchant();
                merchant.setLastIp(log.getIp());
                merchant.setLastTime(log.getCreateTime());
                merchant.setLastOs(log.getOs());
                merchant.setLastCity(log.getCity());
                updateWrapper.eq("phone",account);
                iMerchantService.getBaseMapper().update(merchant,updateWrapper);
            }else if(loginType.equals(UsedCode.LOGIN_TYPE_USER)){ //码商
                UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                User user = new User();
                user.setLastIp(log.getIp());
                user.setLastTime(log.getCreateTime());
                user.setLastOs(log.getOs());
                user.setLastCity(log.getCity());
                updateWrapper.eq("phone",account);
                iUserService.getBaseMapper().update(user,updateWrapper);
            }
        }

    }






}
