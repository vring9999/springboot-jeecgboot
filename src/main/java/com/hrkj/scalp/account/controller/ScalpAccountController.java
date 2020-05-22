package com.hrkj.scalp.account.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hrkj.scalp.accountLog.entity.ScalpAccountLog;
import com.hrkj.scalp.accountLog.service.IScalpAccountLogService;
import com.hrkj.scalp.merchant.entity.Merchant;
import com.hrkj.scalp.stock.entity.UserStock;
import com.hrkj.scalp.stock.service.IUserStockService;
import com.hrkj.scalp.user.entity.User;
import com.hrkj.scalp.merchant.service.IMerchantService;
import com.hrkj.scalp.util.CacheInfo;
import com.hrkj.scalp.user.service.IUserService;
import com.hrkj.scalp.util.StringUtil;
import com.hrkj.scalp.util.UsedCode;
import com.hrkj.scalp.util.gsonadapter.GsonUtil;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.account.entity.ScalpAccount;
import com.hrkj.scalp.account.service.IScalpAccountService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 账目表
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class ScalpAccountController extends JeecgController<ScalpAccount, IScalpAccountService> {
	@Autowired
	private IScalpAccountService scalpAccountService;
	@Autowired
	private IScalpAccountLogService scalpAccountLogService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IMerchantService merchantService;
	@Autowired
	private static CacheInfo cacheInfo;
	@Autowired
	private IUserStockService userStockService;

	/**
	 * @param cacheInfo
	 * @Title:SendMsgUtil
	 * @Description:项目初始化时注入cacheUtil
	 */
	@Autowired
	public ScalpAccountController(CacheInfo cacheInfo) {
		ScalpAccountController.cacheInfo = cacheInfo;
	}

	@Autowired
	private ISysBaseAPI sysBaseAPI;

	/**
	 * 分页列表查询
	 *
	 * @param scalpAccount
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ScalpAccount scalpAccount,String phoneId,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req, String openTime, String endTime) {
		QueryWrapper<ScalpAccount> queryWrapper = QueryGenerator.initQueryWrapper(scalpAccount, req.getParameterMap());
		if(!StringUtil.isEmpty(openTime)) queryWrapper.ge("update_time",openTime);	//.ge 添加 >= 的条件判断
		if(!StringUtil.isEmpty(openTime)) queryWrapper.le("update_time",endTime);	//.le 添加 >= 的条件判断
		if(!StringUtil.isEmpty(phoneId)) queryWrapper.eq("accountNum",phoneId).or().eq("userId",phoneId);
		//查询数据限制  管理员无限制查询 码商/商户查询个人数据
		queryWrapper = sysBaseAPI.checkType(queryWrapper,req,1);
		if(null == queryWrapper) return Result.error("token失效");
		Page<ScalpAccount> page = new Page<ScalpAccount>(pageNo, pageSize);
		IPage<ScalpAccount> pageList = scalpAccountService.page(page, queryWrapper);
		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
	}


	/**
	 * 获取商户提现信息
	 * @param id
	 * @return
	 */
	@GetMapping(value = "queryWithdraw")
	public Result<?> queryWithdraw(String id){
		if(StringUtil.isEmpty(id) ) return Result.error("参数缺失");
		Map<String,Integer> map = scalpAccountService.queryWithdraw(id,UsedCode.RECORD_TYPE_OUT);
		return Result.ok(map);
	}

	/**
	 *   添加
	 *
	 * @param scalpAccount
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ScalpAccount scalpAccount) throws Exception {
		scalpAccount.setAccountNum(StringUtil.getRandomCode(10).toString());
		if(scalpAccount.getUserType()==UsedCode.GET_USER) {    //码商

		}
		Double depRatio = Double.parseDouble(cacheInfo.getCommonToKey("scalp.account+deposit.money"));
		int serviceMoney=new BigDecimal(depRatio*scalpAccount.getOperationMoney()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		scalpAccount.setServiceMoney(serviceMoney);
		scalpAccount.setActualMoney(scalpAccount.getOperationMoney()-serviceMoney);
		scalpAccountService.save(scalpAccount);
		return Result.ok("添加成功！");
	}

	/**
	 * 管理员审核账目
	 * @param id
	 * @return
	 */
	@PostMapping(value = "/audit")
	public Result<?> audit(String id,Integer status,String remark,HttpServletRequest req){
		HttpSession session = req.getSession();
		// test
//		String systemId = "testId";
		try {
			ScalpAccount account=scalpAccountService.getById(id);
			if(account.getStatus()!= UsedCode.ACCOUNT_STATUS_WAIT){
				return Result.error("审核失败，当前状态不为-待审核");
			}
			account.setStatus(status);
			account.setSystemId((String)session.getAttribute("user"));
//            account.setSystemId(systemId);
			if(status==UsedCode.ACCOUNT_STATUS_CANCEL){
				if(StringUtil.isEmpty(remark)) return Result.error("审核失败，驳回时需要写理由！");
				account.setRemark(remark);
				cancelTop(account);
				scalpAccountService.updateById(account);
			}else{
				addOld(account);
			}
			return Result.ok("审核成功!");
		} catch (Exception e) {
			log.error("账目审核失败：{}",e);
			return Result.error("审核失败，请稍后再试！");
		}
	}


	/**
	 *  编辑
	 *
	 * @param scalpAccount
	 * @return
	 */
	@PostMapping(value = "/edit")
	public Result<?> edit(@RequestBody ScalpAccount scalpAccount) {
		scalpAccountService.updateById(scalpAccount);
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
		scalpAccountService.removeById(id);
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
		this.scalpAccountService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@PostMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ScalpAccount scalpAccount = scalpAccountService.getById(id);
		if(scalpAccount==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(scalpAccount);
	}


	/**
	 * 添加账目记录
	 * @param scalpAccount
	 * @return
	 * @throws Exception
	 */
	private void addOld(ScalpAccount scalpAccount) throws Exception{
		ScalpAccountLog accountLog=new ScalpAccountLog();
		if(scalpAccount.getUserType()==UsedCode.GET_USER){	//码商
			User user=userService.getById(scalpAccount.getUserId());
			String type="";
			int old_num = 0;
			int new_num = 0;
			if(scalpAccount.getType()==UsedCode.RECORD_TYPE_IN){	//充值
				type=UsedCode.BALANCE_ADD;
				//是否使用佣金抵消
				if(UsedCode.BOOLEAN_TRUE == scalpAccount.getIsSub()){
					//更改冻结的抵用券金额
					int surplus = user.getVoucherFrozen() - scalpAccount.getSubMoney();
					user.setVoucherFrozen(surplus);
					//更新库存
					UserStock stock = userStockService.getOne(new QueryWrapper<UserStock>()
							.eq("m_id",scalpAccount.getShopId())
							.eq("user_id",scalpAccount.getUserId()));
					old_num = stock.getNum();
					stock.setNumFrozen(stock.getNumFrozen() - scalpAccount.getShopNum());
					stock.setNum(stock.getNum() + scalpAccount.getShopNum());
					new_num = stock.getNum();
					userStockService.updateById(stock);
				}
			}
			accountLog=new ScalpAccountLog(scalpAccount.getAccountNum(),
					scalpAccount.getUserId(),
					scalpAccount.getUserType(),
					scalpAccount.getShopId(),
					scalpAccount.getShopNum(),
					type,
					scalpAccount.getType(),
					0.0,
					scalpAccount.getRemark(),1);
			accountLog.setShopNowNum(new_num);
			accountLog.setOldMoney(old_num);
			userService.updateById(user);

		}else if(scalpAccount.getUserType()==UsedCode.GET_MERCHANT){	//商户
			Merchant merchant=merchantService.getById(scalpAccount.getUserId());
			merchant.setBalance(merchant.getBalance()-scalpAccount.getOperationMoney());	//修改余额
			accountLog=new ScalpAccountLog(
					scalpAccount.getAccountNum(),
					scalpAccount.getUserId(),
					scalpAccount.getUserType(),
					merchant.getBalance(),
					merchant.getBalance()+scalpAccount.getOperationMoney(),
					scalpAccount.getOperationMoney(),
					UsedCode.BALANCE_ADD,
					scalpAccount.getType(),
					0.0,
					scalpAccount.getRemark(),
					1);
			merchantService.updateById(merchant);
		}
		scalpAccountLogService.save(accountLog);
	}

	/**
	 * 驳回申请
	 * @param scalpAccount
	 */
	public void cancelTop(ScalpAccount scalpAccount) {
		if(scalpAccount.getUserType()==UsedCode.GET_USER){	//码商
			User user=userService.getById(scalpAccount.getUserId());
			if(scalpAccount.getType()== UsedCode.RECORD_TYPE_IN){	//充值
				//是否使用佣金抵消
				if(UsedCode.BOOLEAN_TRUE == scalpAccount.getIsSub()){
					//更改冻结的抵用券金额
					int surplus = user.getVoucherFrozen() - scalpAccount.getSubMoney();
					user.setVoucherFrozen(surplus);
					user.setVoucherMoney(user.getVoucherMoney() + scalpAccount.getSubMoney());
					//更新冻结库存
					UserStock stock = userStockService.getOne(new QueryWrapper<UserStock>()
							.eq("m_id",scalpAccount.getShopId())
							.eq("user_id",scalpAccount.getUserId()));
					stock.setNumFrozen(stock.getNumFrozen() - scalpAccount.getShopNum());
					userStockService.updateById(stock);
				}
			}
			userService.updateById(user);
		}else if(scalpAccount.getUserType()==UsedCode.GET_MERCHANT){	//商户
			Merchant merchant=merchantService.getById(scalpAccount.getUserId());
			merchant.setBalance(merchant.getBalance()-scalpAccount.getOperationMoney());	//修改余额
			merchantService.updateById(merchant);
		}
	}


	/**
	 * app端充值库存
	 * @return
	 */
	@PostMapping(value = "/recharge")
	@AutoLog("码商库存充值")
	public Result<?> recharge(@RequestBody ScalpAccount account){
		log.info("码商库存充值：{}", GsonUtil.boToString(account));
		String bankName = account.getReceiptName();
		String userId = account.getUserId();
		if(StringUtil.isEmpty(userId) || StringUtil.isEmpty(bankName))
			return Result.error("参数缺失");
		//是否使用优惠券
		User user = userService.getById(userId);
		if(UsedCode.BOOLEAN_TRUE == account.getIsSub()){
			int voucherMoney = user.getVoucherMoney();//优惠券金额
			//优惠券大于充值金额--》免费充值  扣除抵用券
			if(voucherMoney >= account.getOperationMoney()){
				account.setActualMoney(0);
				//充值订单抵扣金额
				account.setSubMoney(account.getOperationMoney());
				// 剩余抵用券
				int surplus = voucherMoney - account.getOperationMoney();
				user.setVoucherMoney(surplus);
				//冻结的抵用券金额
				user.setVoucherFrozen(user.getVoucherFrozen()+account.getOperationMoney());
			}else{
				//实际金额
				int actualMoney = account.getOperationMoney() - voucherMoney;
				account.setActualMoney(actualMoney);
				user.setVoucherMoney(0);
				//冻结的抵用券金额
				user.setVoucherFrozen(user.getVoucherFrozen()+voucherMoney);
			}
		}
		UserStock stock = userStockService.getOne(new QueryWrapper<UserStock>()
				.eq("m_id",account.getShopId())
				.eq("user_id",account.getUserId()));
		if(null == stock){
			stock = new UserStock(account.getUserId(),account.getShopId(),account.getShopNum());
			userStockService.save(stock);
		}else{
			stock.setNumFrozen(stock.getNumFrozen() + account.getShopNum());
			userStockService.updateById(stock);
		}
		//完善充值订单信息
		account.setAccountNum(StringUtil.getRandomNum());//账目号
		account.setUserType(UsedCode.GET_USER);//码商类型
		account.setStatus(UsedCode.ACCOUNT_STATUS_WAIT);//待审核
		account.setType(UsedCode.RECORD_TYPE_IN);//充值
		scalpAccountService.save(account);
		userService.updateById(user);
		return Result.ok("充值订单审核中");
	}

}
