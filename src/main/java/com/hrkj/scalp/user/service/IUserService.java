package com.hrkj.scalp.user.service;

import com.alibaba.fastjson.JSONObject;
import com.hrkj.scalp.merchant.entity.MerchantUserVo;
import com.hrkj.scalp.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hrkj.scalp.user.entity.UserSon;
import com.hrkj.scalp.user.entity.UserVo;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;

import java.util.List;
import java.util.Map;

/**
 * @Description: farming_user
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
public interface IUserService extends IService<User> {

	public List<User> selectByMainId(String mainId);

	User getUserByIphone(String phone);

	Result<JSONObject> checkUserIsEffective(User user);

	void editLockStatus(Map<String, Object> params);

	UserVo queryParentUser(String userId);

	List<UserVo> querySonList(String uesrId);
//	List<UserVo> querySonList(Map<String, Object> params);
	/**
	 * 查询登录用户的头像，姓名，今日收益，累积收益
	 * @param userId
	 * @return
	 */
	UserVo queryUserInfo(@Param("userId") String userId);

	MerchantUserVo queryBasicInfo(@Param("id") String id);

	List<UserSon> queryUserSonList(Map<String, Object> params);

}
