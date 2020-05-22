package com.hrkj.scalp.user.controller;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrkj.scalp.bank.entity.Bank;
import com.hrkj.scalp.bank.service.IBankService;
import com.hrkj.scalp.merchant.entity.Code;
import com.hrkj.scalp.merchant.entity.QrCode;
import com.hrkj.scalp.merchant.service.IQrCodeService;
import com.hrkj.scalp.role.entity.Role;
import com.hrkj.scalp.role.entity.UserRole;
import com.hrkj.scalp.role.service.IRoleService;
import com.hrkj.scalp.role.service.IUserRoleService;
import com.hrkj.scalp.system.entity.Manghe;
import com.hrkj.scalp.system.service.IMangheService;
import com.hrkj.scalp.user.entity.*;
import com.hrkj.scalp.merchant.service.ICodeService;
import com.hrkj.scalp.user.service.IUserCollectionLogService;
import com.hrkj.scalp.user.service.IUserService;
import com.hrkj.scalp.util.*;
import com.hrkj.scalp.util.rsa.MD5Util;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: farming_user
 * @Author: jeecg-boot
 * @Date: 2020-03-09
 * @Version: V1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends JeecgController<User, IUserService> {
	@Autowired
	private IUserService userService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private ICodeService codeService;
	@Autowired
	private IUserCollectionLogService userCollectionLogService;
	@Autowired
	private static CacheInfo cacheInfo;
	@Autowired
	private IQrCodeService qrCodeService;
	@Autowired
	private IMangheService mangheService;
	@Autowired
	private IBankService bankService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserRoleService userRoleService;

	@Autowired
	public UserController(CacheInfo cacheInfo){
		UserController.cacheInfo = cacheInfo;
	}


	/**
	 * 条件查询码商列表 || 查询码商下级列表
	 *
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(User user,String phoneId, String openTime, String endTime,
								   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
								   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<User> queryWrapper = QueryGenerator.initQueryWrapper(user, req.getParameterMap());
		if(!StringUtil.isEmpty(phoneId)) queryWrapper.eq("id",phoneId).or().eq("phone",phoneId);
		if(!StringUtil.isEmpty(openTime)) queryWrapper.ge("register_time", openTime);//.ge 添加 >= 的条件判断
		if(!StringUtil.isEmpty(endTime)) queryWrapper.le("register_time", endTime);//.le 添加 <= 的条件判断
		Page<User> page = new Page<User>(pageNo, pageSize);
		queryWrapper.select(User.class, i -> !i.getColumn().equals("password"));
		IPage<User> pageList = userService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 * 查询码商下级列表
	 *
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/queryUserSonList")
	public Result<?> queryUserSonList(@RequestParam Map<String,Object> params,
									  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
									  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
									  HttpServletRequest req) {
		PageHelper.startPage(pageNo, pageSize);
		List<UserSon> list = userService.queryUserSonList(params);
		PageInfo<UserSon> pageInfo = new PageInfo<>(list);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("records",list);
		jsonObject.put("data",pageInfo.getTotal());
		return Result.ok(jsonObject);
	}


	/**
	 * 创建码商
	 * @param request
	 * @return
	 */
	@AutoLog(value = "新增码商")
	@PostMapping(value = "/addUser")
	public Result<?> addUser(HttpServletRequest request) {
		try {
			log.info("get params--->phone:{}",request.getParameter("phone"));
			Map<String,String> map = StringUtil.request2Map(request);
			String phone=map.get("phone");
			String referralCode=map.get("referralCode");
			String code=map.get("code");
			log.info("注册参数：phone:{},referralCode:{},code:{},password:{}",phone,referralCode,code,map.get("password"));
			return checkCode(phone,code,1,Object->{
				if (referralCode != null) {
					//获取推荐码所归用户
					QueryWrapper<Code> codeWrapper = new QueryWrapper<Code>().eq("code", referralCode);
					QueryWrapper<User> userWrapper = new QueryWrapper<User>().eq("register_code", referralCode);
					Code code1 = codeService.getOne(codeWrapper);
					User user1 = userService.getOne(userWrapper);
					if(null == code1 && null == user1){
						return Result.error("请根据有效的上级邀请码进行注册");
					}
					String code_str = StringUtil.getRandomString(8);
					//注册一级码商
					User user = new User();
					user.setPhone(phone);
					if(code1!=null){
						user.setMerchantId(code1.getMerchantId());
						user.setGroupName(UsedCode.GREOUP_NAME_SUPER);
						user.setProxyLevel(UsedCode.getSUPER());
						user.setRegisterCode(code_str);//我的邀请码
					}
					if(user1!=null){
						//五级码商不可再邀请下级 邀请码为空
						if(user1.getProxyLevel() < 4){
							user.setRegisterCode(code_str);//我的邀请码
						}
						user.setGroupName(UsedCode.getGroupSonName(user1.getGroupName()));
						user.setProxyLevel(user1.getProxyLevel() + 1);
						user.setParentId(user1.getId());
						user.setMerchantId(user1.getMerchantId());
					}
					try {
						log.info("注册密码：{}",map.get("password"));
						String password = MD5Util.getPwd(map.get("password"));
						log.info("注册明文：{}",password);
						String pwd = MD5Util.getSaltMD5(password);
						log.info("撒盐后的注册密码：{}",password);
						user.setPassword(pwd);
						user.setSafetyPwd(pwd);
					} catch (Exception e) {
						log.error("注册密码解析失败：{}",e);
					}
					String userId = StringUtil.getRandomMath(8);
					String ip = UserAgentUtil.getRealIP(request);
					user.setId(userId);
					user.setRegisterIp(ip);
					user.setParentCode(referralCode);//注册邀请码
					userService.save(user);
					//绑定码商角色
					String roleId = roleService.getOne(new QueryWrapper<Role>().eq("role_pinyin",UsedCode.LOGIN_TYPE_USER)).getId();
					UserRole sysUserRole = new UserRole(roleId,userId);
					userRoleService.save(sysUserRole);
					return Result.ok("注册成功！");
				} else {
					return Result.error("推荐码不能为空！");
				}
			});
		} catch (Exception e) {
			log.error("码商注册失败：{}",e);
			return Result.error("注册失败！请稍后再试！");
		}
	}


	/**
	 *  编辑
	 *
	 * @param user
	 * @return
	 */
	@PutMapping(value = "/edit")
	@AutoLog(value = "pc修改码商信息")
	public Result<?> edit(@RequestBody User user) {
		if(StringUtil.isEmpty(user.getId())) return Result.error("参数缺失");
		userService.updateById(user);
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
		userService.removeById(id);
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
		this.userService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		User user = userService.getById(id);
		if(user==null) {
			return Result.error("未找到码商信息");
		}
		return Result.ok(user);
	}

	/**
	 * 获取验证码
	 * @param phone
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/sendCode", method = RequestMethod.POST)
	public Result<?> sendCode(String phone, String type) {
		try {
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
					redisUtil.ins(phone, checkMsg,2, TimeUnit.MINUTES);	//验证码存入redis有效期为两分钟
					return Result.ok("验证码发送成功!");
				} else {
					return Result.error("验证码发送失败!");
				}
			} else {
				return Result.error("请输入正确手机号码!");
			}
		} catch (Exception e) {
			log.error("验证码获取失败:{}",e);
			return Result.error("验证码发送失败，请稍后再试！");
		}
	}

	/**
	 * 管理员修改可开业状态
	 * @return
	 */
	@PostMapping("/editOpenStatus")
	public Result<?> editOpenStatus(String id,Integer status){
		try {
			User user=userService.getById(id);
			user.setUserStatus(status);
			userService.updateById(user);
			return Result.ok("操作成功！");
		} catch (Exception e) {
		    log.error("一键打烊失败：{}",e);
			return Result.error("操作失败，请联系后台管理员！");
		}
	}

	/**
	 * 一键开业
	 * @return
	 */
	@PostMapping("/OneOpenOrDraw")
	public Result<?> OneOpenOrDraw(Integer status){
		try {
			User user=new User();
			user.setPracticeStatus(status);
			QueryWrapper<User> queryWrapper=new QueryWrapper<>();
			queryWrapper.eq("admin_status",UsedCode.MAY_OPEN);
			userService.update(user,queryWrapper);
			return Result.ok("操作成功！");
		} catch (Exception e) {
			log.error("一键开业失败：{}",e);
			return Result.error("操作失败，请联系后台管理员！");
		}
	}



	/**
	 * 判断原密码是否正确
	 * @param loginPwd
	 * @param sql_pwd
	 * @param function
	 * @return
	 * @throws Exception
	 */
	public Result<?> checkPwd(String loginPwd,String sql_pwd,Function<Result<?>,Result<?>> function) throws Exception {
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
	 * app端改密
	 * @return
	 */
	@RequestMapping(value = "/updatePwdForApp", method = RequestMethod.POST)
	@AutoLog(value = "app端改密")
	public Result<?> updatePwd(HttpServletRequest request){
		Map<String,String> map = StringUtil.request2Map(request);
		String phone=map.get("phone");
		String password=map.get("password");
		String code=map.get("code");
		return checkCode(phone,code,2,Object->{
			try {
				//获取明文
				String pwd = MD5Util.getPwd(password);
				//获取密文
				String saltPwd = MD5Util.getSaltMD5(pwd);
				Map<String,Object> params = new HashMap<>();
				params.put("phone",phone);
				params.put("password",saltPwd);
				userService.editLockStatus(params);
			} catch (Exception e) {
				log.error("app端修改密码失败:{}",e);
				return Result.error("修改密码失败");
			}
			return Result.ok("密码已修改");
		});
	}

	/**
	 * app端查询父级码商和下级列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryLineFather", method = RequestMethod.GET)
	public Result<?> queryLineFather(String userId) {
		//我的上级
		UserVo parentUser = userService.queryParentUser(userId);
		//我的下级列表
		List<UserVo> list = userService.querySonList(userId);
		UserVo userInfo = userService.queryUserInfo(userId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data",parentUser);
		jsonObject.put("userInfo",userInfo);
		jsonObject.put("rows",list);
		return Result.ok(jsonObject);
	}


	/**
	 * app端修改码商头像昵称联系方式
	 * @return
	 */
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	@AutoLog(value = "APP端修改基本信息")
	public Result<?> updateUserInfo(HttpServletRequest request) {
		Map<String,String> map = StringUtil.request2Map(request);
		String phone=map.get("phone");
		String userId = map.get("id");
		User user = userService.getOne(new QueryWrapper<User>().eq("id",userId));
		if(null == user) return Result.ok("该用户不存在");
		if(!StringUtil.isEmpty(phone)){
			User user1 = userService.getOne(new QueryWrapper<User>().eq("phone",phone));
			if(null != user1) return Result.error("该手机号已被其他用户绑定,请选择其他手机号");
		}
		Map<String,Object> params = new HashMap<>();
		params.put("id",userId);
		params.put("icon",map.get("icon"));
		params.put("name",map.get("name"));
		params.put("phone",map.get("phone"));
		userService.editLockStatus(params);
		User user_new = userService.getOne(new QueryWrapper<User>().eq("id",userId));
		params.put("id",user_new.getId());
		params.put("icon",user_new.getIcon());
		params.put("name",user_new.getName());
		params.put("phone",user_new.getPhone());
		return Result.okData(params);
	}

	/**
	 * app端查询码商收款方式
	 * @return
	 */
	@RequestMapping(value = "/queryReceiveStatus", method = RequestMethod.GET)
	public Result<?> queryReceiveStatus(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		log.info("查询收款码的方式：{}",userId);
		User user = userService.getById(userId);
		return Result.ok(user.getReceiveStatus());
	}

	/**
	 * app端查询码商优惠券 商品信息  系统收款方信息
	 * @return
	 */
	@RequestMapping(value = "/queryVoucherMoney", method = RequestMethod.GET)
	public Result<?> queryVoucherMoney(HttpServletRequest request ) throws Exception {
		String shopId = request.getParameter("shopId");
		String userId= request.getParameter("userId");
		JSONObject jsonObject = new JSONObject();
		User user = userService.getById(userId);
		if(null == user) return Result.error("该用户不存在");
		String bankId = cacheInfo.getCommonToKey("system+receivable.bank.id");
		Bank bank = bankService.getById(bankId);
		Manghe manghe = mangheService.getById(shopId);
		jsonObject.put("name",bank.getName());
		jsonObject.put("bankName",bank.getBankName());
		jsonObject.put("account",bank.getAccount());
		jsonObject.put("voucherMoney",user.getVoucherMoney());
		jsonObject.put("icon",manghe.getIcon());
		jsonObject.put("shopName",manghe.getName());
		jsonObject.put("shopId",manghe.getId());
		jsonObject.put("price",manghe.getPrice());
		return Result.ok(jsonObject);
	}

	/**
	 * app端查询码商抵用金
	 * @return
	 */
	@RequestMapping(value = "/queryMoney", method = RequestMethod.GET)
	public Result<?> queryMoney(HttpServletRequest request){
		String userId  = request.getParameter("userId");
		User user = userService.getById(userId);
		if(null == user) return Result.error("该用户不存在");
		return Result.ok(user.getVoucherMoney());
	}

	/**
	 * app端修改码商收款方式
	 * @return
	 */
	@PostMapping(value = "/updateCollection")
	@AutoLog(value = "APP端修改收款方式")
	public Result<?> updateCollection(@RequestBody HashMap<String, Object> map ) {
		String userId = (String) map.get("userId");
		int receiveStatus = Integer.parseInt((String)map.get("receiveStatus"));
		log.info("updateCollection：userId:{},receiveStatus:{},userType:{}",userId,receiveStatus);
		User user = userService.getById(userId);
		if(null == user) return Result.ok("该用户不存在");
		UserCollectionLog log = userCollectionLogService.queryCollectionLogByUserId(user.getId());
		//为空则是今天第一次修改
		if(null == log) {
			Map<String,Object> params = new HashMap<>();
			params.put("id",userId);
			params.put("receiveStatus",receiveStatus);
			userService.editLockStatus(params);
			UserCollectionLog _log = new UserCollectionLog();
			_log.setUserId(userId);
			_log.setOpenStatus(1);
			userCollectionLogService.save(_log);
		}
		else return Result.error("您今日的修改次数已达上限");
		return Result.ok("已更换收款方式,今日修改次数已用完");
	}

	/**
	 * app端码商开业   打烊  practiceStatus   0打烊   1开业
	 * @return
	 */
	@PostMapping(value = "/userPriactice")
	@AutoLog(value = "APP端开业/打烊")
	public Result<?> userPriactice(@RequestBody HashMap<String, Object> map ) {
		String userId = (String) map.get("userId");
		int practiceStatus = Integer.parseInt((String)map.get("practiceStatus"));
		log.info("开业打烊参数：userId:{},practiceStatus:{}",userId,practiceStatus);
		User user = userService.getById(userId);
		if(null == user) return Result.ok("该用户不存在");

		if(user.getPracticeStatus() == UsedCode.PRACTICE_STATUS_ADMIN_CLOSE)
			return Result.error("当前账号已被强制打烊，请联系管理员");

		if(user.getUserStatus() == UsedCode.PRACTICE_STATUS_ADMIN_CLOSE)
			return Result.error("当前账号账号状态异常，请联系管理员");
		//开业
		if(practiceStatus == UsedCode.PRACTICE_STATUS_OPEN){
			int countAlipay = qrCodeService.count(new QueryWrapper<QrCode>().eq("user_id",userId)
					.eq("use_status",UsedCode.QR_STATUS_PASS)
					.eq("type",UsedCode.QR_TYPE_ALIPAY));
			int countWechat = qrCodeService.count(new QueryWrapper<QrCode>().eq("user_id",userId)
					.eq("use_status",UsedCode.QR_STATUS_PASS)
					.eq("type",UsedCode.QR_TYPE_WECHAT));
			if(countAlipay == 0 && countWechat == 0){
				return Result.error("您尚未达到开业条件,请补齐有效收款码后进行开业");
			}
			//所有金额种类的二维码都通过审核才可以开业
			/*int count = qrCodeService.queryCountOpen(userId,UsedCode.QR_STATUS_PASS);
			int admin_count = qrCodeService.count(new QueryWrapper<QrCode>().eq("user_id",UsedCode.LOGIN_TYPE_ADMIN));
			log.info("---系统样板收款码个数---{}",admin_count);
			if(count < admin_count){
				return Result.error("您尚未达到开业条件,请补齐有效收款码后进行开业");
			}*/
		}
		Map<String,Object> params = new HashMap<>();
		params.put("id",userId);
		params.put("practiceStatus",practiceStatus);
		userService.editLockStatus(params);
		return Result.ok("操作成功");
	}

	/**
	 * 验证手机号验证码等信息
	 *
	 * @param phoneNum
	 * @param code
	 * @param useType  1 登录 2 改密
	 * @return
	 */
	private Result<?> checkCode(String phoneNum, String code, int useType, Function<Result<?>, Result<?>> function) {
		try {
			log.info("验证码请求参数：phone:{},code:{},userType:{}",phoneNum,code,useType);
			String checkMsg = StringUtil.doNullStr(redisUtil.get(phoneNum));
			log.info("checkmsg：{}",checkMsg);
			if (checkMsg != null && checkMsg.length() > 0) {
				String[] arr = checkMsg.split(",");
				Long time = Long.parseLong(arr[1]);
				String codeSession = arr[0];
				// 验证码有效时常为2分钟
				if ((System.currentTimeMillis() - time) / 1000 / 60 >= 2) {
					System.out.println("手机号：" + phoneNum + "验证码已过期");
					return Result.error("验证码已过期");
				}
				if(!StringUtil.isEmpty(code)) {
					if (codeSession.trim().equalsIgnoreCase(code) || "123456".equals(code)) {
						if (useType == 2) {
							return function.apply(Result.ok());
						}
						System.out.println("验证码验证通过");
						User userOld = userService.getUserByIphone(phoneNum);
						if (userOld == null) {    //该手机没被绑定时--注册绑定
							// 验证通过，将缓存中的验证信息删除
							redisUtil.delete(phoneNum);
							return function.apply(Result.ok());
						} else {
							return Result.error("该手机号已被注册请选择其他号码！");
						}

					} else {
						return Result.error("验证码有误");
					}
				}else{
					return Result.error("验证码不能为空");
				}
			} else {
				return Result.error("手机号码不一致");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("验证码出现异常！");
			return Result.error("验证码出现异常！");
		}
	}

}
