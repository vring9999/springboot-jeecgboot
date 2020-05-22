package com.hrkj.scalp.user.mapper;

import java.util.List;
import java.util.Map;

import com.hrkj.scalp.merchant.entity.MerchantUserVo;
import com.hrkj.scalp.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hrkj.scalp.user.entity.UserSon;
import com.hrkj.scalp.user.entity.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description: farming_user
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<User> selectByMainId(@Param("mainId") String mainId);

	User getUserByIphone(String phone);

	void editLockStatus(Map<String, Object> params);

	/**
	 * 查询登录用户的上级码商
	 * @param userId
	 * @return
	 */
	UserVo queryParentUser(String userId);

	/**
	 * 查询登录用户的下级码商列表
	 * @param userId
	 * @return
	 */
	List<UserVo> querySonList(@Param("userId") String userId);
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



