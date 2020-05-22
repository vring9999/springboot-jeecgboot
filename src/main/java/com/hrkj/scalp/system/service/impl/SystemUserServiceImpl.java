package com.hrkj.scalp.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrkj.scalp.menu.entity.SysPermission;
import com.hrkj.scalp.menu.mapper.SysPermissionMapper;
import com.hrkj.scalp.role.mapper.UserRoleMapper;
import com.hrkj.scalp.system.entity.SystemUser;
import com.hrkj.scalp.system.mapper.SystemUserMapper;
import com.hrkj.scalp.system.service.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: farming_system_user
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Service
@Slf4j
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Override
    public SystemUser querySystemUserByAccount(String account) {
        return systemUserMapper.querySystemUserByAccount(account);
    }

    public Result<JSONObject> checkUserIsEffective(SystemUser systemUser) {
        Result<JSONObject> result = new Result<JSONObject>();
        if (systemUser == null) {
            result.error500("该用户不存在，请注册");
            return result;
        }
        return result;
    }

    @Override
    public List<String> getRole(String account) {
        return userRoleMapper.getRoleByUserName(account);
    }

    @Override
    public IPage<SystemUser> getUserByRoleId(Page<SystemUser> page, String roleId, String account) {
        return systemUserMapper.getUserByRoleId(page,roleId,account);
    }

    /**
     * 通过用户名获取用户角色集合
     * @param account 用户名
     * @return 角色集合
     */
    @Override
    public Set<String> getUserRolesSet(String account) {
        // 查询用户拥有的角色集合
        List<String> roles = userRoleMapper.getRoleByUserName(account);
        log.info("-------通过数据库读取用户拥有的角色Rules------username： " + account + ",Roles size: " + (roles == null ? 0 : roles.size()));
        return new HashSet<>(roles);
    }

    /**
     * 通过用户名获取用户权限集合
     *
     * @param account 用户名
     * @return 权限集合
     */
    @Override
    public Set<String> getUserPermissionsSet(String account) {
        Set<String> permissionSet = new HashSet<>();
        List<SysPermission> permissionList = sysPermissionMapper.queryByUser(account);
        for (SysPermission po : permissionList) {
//            if (oConvertUtils.isNotEmpty(po.getPerms())) {
//                permissionSet.add(po.getPerms());
//            }
        }
        log.info("-------通过数据库读取用户拥有的权限Perms------account： "+ account+",Perms size: "+ (permissionSet==null?0:permissionSet.size()) );
        return permissionSet;
    }
}
