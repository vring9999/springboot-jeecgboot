package com.hrkj.scalp.config.service.impl;

import com.hrkj.scalp.config.entity.Config;
import com.hrkj.scalp.config.mapper.ConfigMapper;
import com.hrkj.scalp.config.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: instead_common_config
 * @Author: jeecg-boot
 * @Date:   2020-02-28
 * @Version: V1.0
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config>  implements IConfigService {

    @Autowired
    private ConfigMapper ConfigMapper;

    @Override
    public Integer queryByName(String cfgName) {
        return ConfigMapper.queryByName(cfgName);
    }
}
