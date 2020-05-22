package com.hrkj.scalp.menu.mapper;

import java.util.List;

import com.hrkj.scalp.menu.entity.SysPermission;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: farming_menu
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
 @Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {


   /**
   *   根据用户查询用户权限
   */
    List<SysPermission> queryByUser(@Param("account") String account);

    void deletes(@Param("list") List<String> list);

}
