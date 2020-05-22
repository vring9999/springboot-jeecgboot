package com.hrkj.scalp.account.service.impl;

import com.hrkj.scalp.account.entity.ScalpAccount;
import com.hrkj.scalp.account.mapper.ScalpAccountMapper;
import com.hrkj.scalp.account.service.IScalpAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;

/**
 * @Description: 账目表
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Service
public class ScalpAccountServiceImpl extends ServiceImpl<ScalpAccountMapper, ScalpAccount> implements IScalpAccountService {
    @Autowired
    private ScalpAccountMapper accountMapper;

    @Override
    public Map<String, Object> queryActualMoney(String userId) {
        return accountMapper.queryActualMoney(userId);
    }

    @Override
    public Map<String, Integer> queryWithdraw(String userId, int type) {
        return accountMapper.queryWithdraw(userId,type);
    }



}
