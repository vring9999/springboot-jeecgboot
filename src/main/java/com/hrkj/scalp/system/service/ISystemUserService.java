package com.hrkj.scalp.system.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrkj.scalp.role.mapper.UserRoleMapper;
import com.hrkj.scalp.system.entity.SystemUser;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: farming_system_user
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
public interface ISystemUserService extends IService<SystemUser> {

    SystemUser querySystemUserByAccount(String account);

    Result<JSONObject> checkUserIsEffective(SystemUser systemUser);

    public List<String> getRole(String account);

    /**
     * 根据角色Id查询
     * @param
     * @return
     */
    public IPage<SystemUser> getUserByRoleId(Page<SystemUser> page, String roleId, String account);

    /**
     * 通过用户名获取用户角色集合
     *
     * @param account 账号
     * @return 角色集合
     */
    Set<String> getUserRolesSet(String account);

    /**
     * 通过用户名获取用户权限集合
     *
     * @param account 用户名
     * @return 权限集合
     */
    Set<String> getUserPermissionsSet(String account);



}
