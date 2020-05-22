package com.hrkj.scalp.bank.service.impl;

import com.hrkj.scalp.bank.entity.Bank;
import com.hrkj.scalp.bank.mapper.BankMapper;
import com.hrkj.scalp.bank.service.IBankService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 码商银行卡
 * @Author: jeecg-boot
 * @Date:   2020-03-19
 * @Version: V1.0
 */
@Service
public class BankServiceImpl extends ServiceImpl<BankMapper, Bank> implements IBankService {

}
