package com.hrkj.scalp.bank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.bank.entity.Bank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 码商银行卡
 * @Author: jeecg-boot
 * @Date:   2020-03-19
 * @Version: V1.0
 */
 @Repository
public interface BankMapper extends BaseMapper<Bank> {

}
