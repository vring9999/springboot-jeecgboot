package com.hrkj.scalp.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.role.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: farming_role
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

}
