package com.hrkj.scalp.config.service;

import com.hrkj.scalp.config.entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Description: farming_group
 * @Author: jeecg-boot
 * @Date:   2020-03-17
 * @Version: V1.0
 */
public interface IGroupService extends IService<Group> {

    Map<String,Object> KK(Map<String,Object> parms);


}
