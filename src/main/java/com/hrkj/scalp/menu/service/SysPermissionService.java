package com.hrkj.scalp.menu.service;

import com.hrkj.scalp.menu.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: farming_menu
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
public interface SysPermissionService extends IService<SysPermission> {

    List<SysPermission> queryByUser(String account);

    void deletes(@Param("list") List<String> list);

}
