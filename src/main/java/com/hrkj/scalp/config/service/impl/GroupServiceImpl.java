package com.hrkj.scalp.config.service.impl;

import com.hrkj.scalp.config.entity.Group;
import com.hrkj.scalp.config.mapper.GroupMapper;
import com.hrkj.scalp.config.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;

/**
 * @Description: farming_group
 * @Author: jeecg-boot
 * @Date:   2020-03-17
 * @Version: V1.0
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Map<String, Object> KK(Map<String, Object> parms) {
        return groupMapper.KKs(parms);
    }
}
