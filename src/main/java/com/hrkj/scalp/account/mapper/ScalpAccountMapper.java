package com.hrkj.scalp.account.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.account.entity.ScalpAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 账目表
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Repository
public interface ScalpAccountMapper extends BaseMapper<ScalpAccount> {

    Map<String,Object> queryActualMoney(@Param("userId") String userId);

    Map<String,Integer> queryWithdraw(@Param("userId") String userId, @Param(value = "type")int type);

}
