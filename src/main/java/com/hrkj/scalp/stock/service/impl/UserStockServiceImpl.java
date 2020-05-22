package com.hrkj.scalp.stock.service.impl;

import com.hrkj.scalp.stock.entity.UserStock;
import com.hrkj.scalp.stock.mapper.UserStockMapper;
import com.hrkj.scalp.stock.service.IUserStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 码商库存
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
@Service
public class UserStockServiceImpl extends ServiceImpl<UserStockMapper, UserStock> implements IUserStockService {

    @Autowired
    private UserStockMapper userStockMapper;
    @Override
    public List<Map<String, Object>> queryUserStock(String userId) {
        return userStockMapper.queryUserStock(userId);
    }

    @Override
    public void quartzUpdateStock() {
        userStockMapper.quartzUpdateStock();
    }
}
