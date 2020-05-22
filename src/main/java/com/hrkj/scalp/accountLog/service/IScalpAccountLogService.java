package com.hrkj.scalp.accountLog.service;

import com.hrkj.scalp.accountLog.entity.ScalpAccountLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: scalp_account_old
 * @Author: jeecg-boot
 * @Date:   2020-03-09
 * @Version: V1.0
 */
public interface IScalpAccountLogService extends IService<ScalpAccountLog> {

    Integer queryChangeMoneyForOrder(@Param("userId")String userId, @Param("recordType")int recordType);


}
