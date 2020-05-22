package com.hrkj.scalp.config.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.config.entity.Group;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: farming_group
 * @Author: jeecg-boot
 * @Date:   2020-03-17
 * @Version: V1.0
 */
 @Repository
public interface GroupMapper extends BaseMapper<Group> {

  Map<String,Object> KKs(Map<String,Object> parms);

}
