package com.hrkj.scalp.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.system.entity.SystemLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Description: farming_system_log
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
 @Repository
public interface SystemLogMapper extends BaseMapper<SystemLog> {

 /**
  * 使用自己的mapper 使用wrapper的查询条件
  * @param wrapper
  * @return
  */
 @Select("SELECT * FROM farming_system_log ${ew.customSqlSegment}")
  SystemLog getOne(@Param(Constants.WRAPPER) QueryWrapper<SystemLog> wrapper);

}
