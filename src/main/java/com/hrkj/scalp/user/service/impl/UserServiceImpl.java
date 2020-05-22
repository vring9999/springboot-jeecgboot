package com.hrkj.scalp.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hrkj.scalp.merchant.entity.MerchantUserVo;
import com.hrkj.scalp.user.entity.User;
import com.hrkj.scalp.user.entity.UserSon;
import com.hrkj.scalp.user.entity.UserVo;
import com.hrkj.scalp.user.mapper.UserMapper;
import com.hrkj.scalp.user.service.IUserService;
import org.jeecg.common.api.vo.Result;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: farming_user
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> selectByMainId(String mainId) {
		return userMapper.selectByMainId(mainId);
	}

	@Override
	public User getUserByIphone(String phone) {
		return userMapper.getUserByIphone(phone);
	}

	@Override
	public Result<JSONObject> checkUserIsEffective(User user) {
		Result<JSONObject> result = new Result<JSONObject>();
		if (user == null) {
			result.error500("该用户不存在，请注册");
			return result;
		}
		return result;
	}

	@Override
	public void editLockStatus(Map<String, Object> params) {
		userMapper.editLockStatus(params);
	}

	@Override
	public UserVo queryParentUser(String userId) {
		return userMapper.queryParentUser(userId);
	}

	@Override
	public List<UserVo> querySonList(String uesrId) {
		return userMapper.querySonList(uesrId);
	}

	@Override
	public UserVo queryUserInfo(String userId) {
		return userMapper.queryUserInfo(userId);
	}

	@Override
	public MerchantUserVo queryBasicInfo(String id) {
		return userMapper.queryBasicInfo(id);
	}

	@Override
	public List<UserSon> queryUserSonList(Map<String, Object> params) {
		return userMapper.queryUserSonList(params);
	}

}
