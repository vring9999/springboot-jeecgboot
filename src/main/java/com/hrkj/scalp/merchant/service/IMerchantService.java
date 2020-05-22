package com.hrkj.scalp.merchant.service;

import com.alibaba.fastjson.JSONObject;
import com.hrkj.scalp.merchant.entity.MerchantUserVo;
import com.hrkj.scalp.user.entity.User;
import com.hrkj.scalp.merchant.entity.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: farming_merchant
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
public interface IMerchantService extends IService<Merchant> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(Merchant merchant, List<User> userList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(Merchant merchant, List<User> userList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);

	Merchant queryMerchantByPhone(String phone);

	Result<JSONObject> checkUserIsEffective(Merchant merchant);

	void editLockStatus(Map<String, Object> params);

	MerchantUserVo queryBasicInfo(@Param("id") String id);




}
