package com.hrkj.scalp.order.service.impl;

import com.hrkj.scalp.order.entity.SubCommission;
import com.hrkj.scalp.order.mapper.SubCommissionMapper;
import com.hrkj.scalp.order.service.ISubCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 分佣明细
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@Service
public class SubCommissionServiceImpl extends ServiceImpl<SubCommissionMapper, SubCommission> implements ISubCommissionService {

    @Autowired
    private SubCommissionMapper subCommissionMapper;

    @Override
    public List<Map<String, Object>> queryComeBackList(Map<String, Object> params) {
        return subCommissionMapper.queryComeBackList(params);
    }
}
