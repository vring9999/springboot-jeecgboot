package com.hrkj.scalp.config.service;

import com.hrkj.scalp.config.entity.Config;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: instead_common_config
 * @Author: jeecg-boot
 * @Date:   2020-02-28
 * @Version: V1.0
 */
public interface IConfigService extends IService<Config> {

    Integer queryByName(String cfgName);

}
