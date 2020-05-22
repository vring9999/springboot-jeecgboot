package com.hrkj.scalp.order.service;

import com.hrkj.scalp.order.entity.SubCommission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 分佣明细
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
public interface ISubCommissionService extends IService<SubCommission> {

    List<Map<String,Object>> queryComeBackList(Map<String,Object> params);

}
