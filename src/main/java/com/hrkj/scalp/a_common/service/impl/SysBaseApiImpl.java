package com.hrkj.scalp.a_common.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hrkj.scalp.merchant.entity.Merchant;
import com.hrkj.scalp.message.entity.Message;
import com.hrkj.scalp.shiro.vo.DefContants;
import com.hrkj.scalp.user.entity.User;
import com.hrkj.scalp.merchant.mapper.MerchantMapper;
import com.hrkj.scalp.user.mapper.UserMapper;
import com.hrkj.scalp.role.mapper.UserRoleMapper;
import com.hrkj.scalp.system.entity.SysLoginModel;
import com.hrkj.scalp.system.entity.SystemLog;
import com.hrkj.scalp.system.entity.SystemUser;
import com.hrkj.scalp.system.mapper.SystemLogMapper;
import com.hrkj.scalp.system.mapper.SystemUserMapper;
import com.hrkj.scalp.util.StringUtil;
import com.hrkj.scalp.util.UsedCode;
import com.hrkj.scalp.util.UserAgentUtil;
import com.hrkj.scalp.util.gsonadapter.GsonUtil;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginModel;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 底层共通业务API，提供其他独立模块调用
 * @Author: scott
 * @Date:2019-4-20 
 * @Version:V1.0
 */
@Slf4j
@Service
public class SysBaseApiImpl implements ISysBaseAPI {

	@Resource
	private SystemLogMapper systemLogMapper;
	@Autowired
	private SystemUserMapper systemUserMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private MerchantMapper merchantMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public void addLog(String LogContent, Integer logType, Integer operatetype) {
		SystemLog sysLog = new SystemLog();
		//注解上的描述,操作日志内容
		sysLog.setLogContent(LogContent);
		sysLog.setLogType(logType);
		sysLog.setOperateType(operatetype);
		try {
			//获取request
			HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
			String ip = UserAgentUtil.getRealIP(request);
			ip = ip.equals("127.0.0.1")?"120.36.215.15":ip;
			log.info("------------------ip:"+ip);
			String os = UserAgentUtil.getOBI(request);
			log.info("------------------os:"+os);
			String location = UserAgentUtil.getIpInfo(ip);
			log.info("------------------location:"+location);
			sysLog.setIp(ip);
			sysLog.setCity(location);
			sysLog.setOs(os);
		} catch (Exception e) {
			sysLog.setIp("127.0.0.1");
		}

		//获取登录用户信息
		SysLoginModel loginUser = (SysLoginModel) SecurityUtils.getSubject().getPrincipal();
		log.info("注入日志时获取的登录用户信息：{}", GsonUtil.boToString(loginUser));
		if (loginUser != null) {
			sysLog.setAccount(loginUser.getAccount());
			sysLog.setUserType(loginUser.getLoginType());
		}
		//保存系统日志
		systemLogMapper.insert(sysLog);

	}

	@Override
	public LoginModel getUserByName(String username,String loginType) {
		if (oConvertUtils.isEmpty(username)) {
			return null;
		}
		LoginModel loginUser = new LoginModel();
		if(loginType.equals(UsedCode.LOGIN_TYPE_USER)){
			User user = userMapper.getUserByIphone(username);
			if (user == null) {
				return null;
			}
			loginUser.setAccount(user.getPhone());
			loginUser.setPassword(user.getPassword());
//			BeanUtils.copyProperties(user, loginUser);
		}else if(loginType.equals(UsedCode.LOGIN_TYPE_MERCHANT)){
			Merchant merchant = merchantMapper.queryMerchantByPhone(username);
			if (merchant == null) {
				return null;
			}
			loginUser.setAccount(merchant.getPhone());
			loginUser.setPassword(merchant.getPassword());
//			BeanUtils.copyProperties(merchant, loginUser);

		}else if(loginType.equals(UsedCode.LOGIN_TYPE_ADMIN)){
			SystemUser sysUser = systemUserMapper.querySystemUserByAccount(username);
			if (sysUser == null) {
				return null;
			}
			BeanUtils.copyProperties(sysUser, loginUser);
			loginUser.setSafeCode(sysUser.getSafeCode());
		}
		loginUser.setLoginType(loginType);
		return loginUser;
	}

	@Override
	public LoginModel getUserById(String id,String loginType) {
		if (oConvertUtils.isEmpty(id)) {
			return null;
		}
		LoginModel loginUser = new LoginModel();
		SystemUser sysUser = systemUserMapper.selectById(id);
		if (sysUser == null) {
			return null;
		}
		BeanUtils.copyProperties(sysUser, loginUser);
		return loginUser;
	}

	@Override
	public List<String> getRolesByUsername(String username,String loginType) {
		return userRoleMapper.getRoleByUserName(username);
	}

	@Override
	public List<String> getRoleIdsByUsername(String username,String loginType) {
		return userRoleMapper.getRoleIdByUserName(username);
	}

	//     查询数据限制
	@Override
	public String getDatabaseType() {
		DataSource dataSource = SpringContextUtils.getApplicationContext().getBean(DataSource.class);
//		return getDatabaseTypeByDataSource(dataSource);
		return "";
	}

	/**
	 * 查询数据权限校验
	 * @param queryWrapper
	 * @param req
	 * @param flag  1账目变动记录|二维码  2日志   3消息推送   4活动产品  5银行卡
	 * @return
	 */
	@Override
	public QueryWrapper<T> checkType(QueryWrapper queryWrapper, HttpServletRequest req, int flag) {
		String token = req.getHeader(DefContants.X_ACCESS_TOKEN);
		String loginType =  JwtUtil.getLoginType(token);
		String id =  JwtUtil.getId(token);
		if(StringUtil.isEmpty(loginType)) return null;
		if(loginType.equals(UsedCode.LOGIN_TYPE_MERCHANT) || loginType.equals(UsedCode.LOGIN_TYPE_USER)){
			if(flag == 1 || flag == 5) {
				queryWrapper.eq("user_id", id);
			}else if(flag == 2) {
				String account =  JwtUtil.getUsername(token);
				queryWrapper.eq("account", account);
			}
			else if(flag == 3)
				queryWrapper = new QueryWrapper<Message>().eq("user_id", id).or().eq("send_type",loginType).or().eq("send_type",UsedCode.LOGIN_TYPE_FULL).orderByDesc("create_time");
			else if(flag == 4)
				queryWrapper.eq("status", 1);
		}else if(loginType.equals(UsedCode.LOGIN_TYPE_ADMIN)){
			if(flag == 5) queryWrapper.eq("user_id", UsedCode.LOGIN_TYPE_ADMIN);
		}
		return queryWrapper;
	}

	public static void main(String[] args) {
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJtZXJjaGFudCIsImlkIjoiOTc0OTgyNjk1IiwiZXhwIjoxNTg3MTE4NzkzLCJ1c2VybmFtZSI6IjE4NjQ5NjQ5MjE1In0.7VMVt_LK3TnDnhsgeKkGgulrF9LH8Es_11WH_e9rUQQ";
		String loginType =  JwtUtil.getLoginType(token);
		String id =  JwtUtil.getId(token);
		System.out.println("logintype:"+loginType+"---id:"+id+"---account"+JwtUtil.getUsername(token));
	}

}
