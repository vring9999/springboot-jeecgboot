package com.hrkj.scalp.menu.service.impl;

import com.hrkj.scalp.menu.entity.SysPermission;
import com.hrkj.scalp.menu.mapper.SysPermissionMapper;
import com.hrkj.scalp.menu.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: farming_menu
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Service

public class MenuServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Override
    public List<SysPermission> queryByUser(String account) {
        return sysPermissionMapper.queryByUser(account);
    }

    @Override
    public void deletes(List<String> list) {
        sysPermissionMapper.deletes(list);
    }
}
