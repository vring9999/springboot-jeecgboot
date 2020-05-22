package com.hrkj.scalp.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.role.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Description: farming_user_role
 * @Author: jeecg-boot
 * @Date:   2020-03-12
 * @Version: V1.0
 */
 @Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

 @Select("select role_code from farming_role where id in (select role_id from farming_user_role where user_id = (select id from farming_system_user where account=#{account}))")
 List<String> getRoleByUserName(@Param("account") String account);

 @Select("select role_id from farming_user_role where user_id = (select id from farming_system_user where account=#{account})")
 List<String> getRoleIdByUserName(@Param("account") String account);

}
