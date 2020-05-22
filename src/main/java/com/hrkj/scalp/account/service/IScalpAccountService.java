package com.hrkj.scalp.account.service;

import com.hrkj.scalp.account.entity.ScalpAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Description: 账目表
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
public interface IScalpAccountService extends IService<ScalpAccount> {

    Map<String,Object> queryActualMoney(@Param("userId") String userId);

    Map<String, Integer> queryWithdraw(@Param("userId") String userId, @Param(value = "type")int type);


}
