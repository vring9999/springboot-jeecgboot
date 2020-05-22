package com.hrkj.scalp.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.order.entity.SubCommission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 分佣明细
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
 @Repository
public interface SubCommissionMapper extends BaseMapper<SubCommission> {

    List<Map<String,Object>> getSumAccount(Map<String,Object> params);

  List<Map<String,Object>> queryComeBackList(Map<String,Object> params);
}
