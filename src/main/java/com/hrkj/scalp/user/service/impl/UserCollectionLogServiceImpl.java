package com.hrkj.scalp.user.service.impl;

import com.hrkj.scalp.user.entity.UserCollectionLog;
import com.hrkj.scalp.user.mapper.UserCollectionLogMapper;
import com.hrkj.scalp.user.service.IUserCollectionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 码商更改收款方式记录
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
@Service
public class UserCollectionLogServiceImpl extends ServiceImpl<UserCollectionLogMapper, UserCollectionLog> implements IUserCollectionLogService {

    @Autowired
    private UserCollectionLogMapper userCollectionLogMapper;
    @Override
    public UserCollectionLog queryCollectionLogByUserId(String userId) {
        return userCollectionLogMapper.queryCollectionLogByUserId(userId);
    }
}
