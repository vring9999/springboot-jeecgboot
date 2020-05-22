package com.hrkj.scalp.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.menu.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: farming_role_menu
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
 @Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

 /**
  * 保存授权/先删后增
  * @param roleId
  * @param permissionIds
  */
 public void saveRolePermission(String roleId,String permissionIds);

 /**
  * 保存授权 将上次的权限和这次作比较 差异处理提高效率
  * @param roleId
  * @param permissionIds
  * @param lastPermissionIds
  */
 public void saveRolePermission(String roleId,String permissionIds,String lastPermissionIds);

}
