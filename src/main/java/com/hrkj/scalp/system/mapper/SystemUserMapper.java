package com.hrkj.scalp.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.system.entity.SystemUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: farming_system_user
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
 @Repository
public interface SystemUserMapper extends BaseMapper<SystemUser> {

  SystemUser querySystemUserByAccount(String account);

    public List<String> getRole(String account);

    /**
     * 根据角色Id查询用户信息
     * @param page
     * @param
     * @return
     */
    IPage<SystemUser> getUserByRoleId(Page page, @Param("roleId") String roleId, @Param("account") String account);

}
