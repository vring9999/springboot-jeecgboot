package com.hrkj.scalp.accountLog.service.impl;

import com.hrkj.scalp.accountLog.entity.ScalpAccountLog;
import com.hrkj.scalp.accountLog.mapper.ScalpAccountLogMapper;
import com.hrkj.scalp.accountLog.service.IScalpAccountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * @Description: scalp_account_old
 * @Author: jeecg-boot
 * @Date:   2020-03-09
 * @Version: V1.0
 */
@Service
public class ScalpAccountLogServiceImpl extends ServiceImpl<ScalpAccountLogMapper, ScalpAccountLog> implements IScalpAccountLogService {

    @Autowired
    private ScalpAccountLogMapper accountLogMapper;

    @Override
    public Integer queryChangeMoneyForOrder(String userId, int recordType) {
        return accountLogMapper.queryChangeMoneyForOrder(userId,recordType);
    }


}
