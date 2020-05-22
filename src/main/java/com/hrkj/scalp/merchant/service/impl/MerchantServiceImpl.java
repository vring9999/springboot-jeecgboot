package com.hrkj.scalp.merchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hrkj.scalp.merchant.entity.Merchant;
import com.hrkj.scalp.merchant.entity.MerchantUserVo;
import com.hrkj.scalp.user.entity.User;
import com.hrkj.scalp.user.mapper.UserMapper;
import com.hrkj.scalp.merchant.mapper.MerchantMapper;
import com.hrkj.scalp.merchant.service.IMerchantService;
import org.jeecg.common.api.vo.Result;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;
import java.util.Map;

/**
 * @Description: farming_merchant
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements IMerchantService {

	@Autowired
	private MerchantMapper merchantMapper;
	@Autowired
	private UserMapper userMapper;
	
	@Override
	@Transactional
	public void saveMain(Merchant merchant, List<User> userList) {
		merchantMapper.insert(merchant);
		if(userList!=null && userList.size()>0) {
			for(User entity:userList) {
				//外键设置
				entity.setMerchantId(merchant.getId());
				userMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(Merchant merchant,List<User> userList) {
		merchantMapper.updateById(merchant);
		
		//1.先删除子表数据
		userMapper.deleteByMainId(merchant.getId());
		
		//2.子表数据重新插入
		if(userList!=null && userList.size()>0) {
			for(User entity:userList) {
				//外键设置
				entity.setMerchantId(merchant.getId());
				userMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		userMapper.deleteByMainId(id);
		merchantMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			userMapper.deleteByMainId(id.toString());
			merchantMapper.deleteById(id);
		}
	}

	@Override
	public Merchant queryMerchantByPhone(String phone) {
		return merchantMapper.queryMerchantByPhone(phone);
	}

	public Result<JSONObject> checkUserIsEffective(Merchant merchant) {
		Result<JSONObject> result = new Result<JSONObject>();
		if (merchant == null) {
			result.error500("该用户不存在，请注册");
			return result;
		}
		return result;
	}

	@Override
	public void editLockStatus(Map<String, Object> params) {
		merchantMapper.editLockStatus(params);
	}

	@Override
	public MerchantUserVo queryBasicInfo(String id) {
		return merchantMapper.queryBasicInfo(id);
	}

}
