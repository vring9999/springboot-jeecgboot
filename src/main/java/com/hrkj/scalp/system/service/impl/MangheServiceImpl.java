package com.hrkj.scalp.system.service.impl;

import com.hrkj.scalp.system.entity.Manghe;
import com.hrkj.scalp.system.mapper.MangheMapper;
import com.hrkj.scalp.system.service.IMangheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 产品库存
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
@Service
public class MangheServiceImpl extends ServiceImpl<MangheMapper, Manghe> implements IMangheService {

    @Autowired
    private MangheMapper mangheMapper;

    @Override
    public Integer queryShopNum(List<String> list) {
        return mangheMapper.queryShopNum(list);
    }

    @Override
    public void updateBatch(List<String> list,Integer status) {
        mangheMapper.updateBatch(list,status);
    }
}
