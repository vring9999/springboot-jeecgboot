package com.hrkj.scalp.menu.service;

import com.hrkj.scalp.menu.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: farming_role_menu
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     * 保存授权/先删后增
     * @param roleId
     * @param permissionIds
     */
    public void saveRolePermission(String roleId,String permissionIds);

    /**
     * 保存授权 将上次的权限和这次作比较 差异处理提高效率
     * @param roleId
     * @param menuIds
     * @param lastMenuIds
     */
    public void saveRolePermission(String roleId,String menuIds,String lastMenuIds);

}
