package com.hrkj.scalp.accountLog.mapper;


import com.hrkj.scalp.accountLog.entity.ScalpAccountLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: scalp_account_old
 * @Author: jeecg-boot
 * @Date:   2020-03-09
 * @Version: V1.0
 */
@Repository
public interface ScalpAccountLogMapper extends BaseMapper<ScalpAccountLog> {


    Integer queryChangeMoneyForOrder(@Param("userId")String userId,@Param("recordType")int recordType);


}
