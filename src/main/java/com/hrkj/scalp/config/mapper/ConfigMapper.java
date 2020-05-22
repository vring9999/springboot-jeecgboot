package com.hrkj.scalp.config.mapper;

import com.hrkj.scalp.config.entity.Config;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Description: instead_common_config
 * @Author: jeecg-boot
 * @Date:   2020-02-28
 * @Version: V1.0
 */
@Mapper
@Component(value = "configMapper")
public interface ConfigMapper extends BaseMapper<Config> {

    Integer queryByName(String cfgName);

}
