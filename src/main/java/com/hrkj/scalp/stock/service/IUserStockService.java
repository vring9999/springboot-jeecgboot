package com.hrkj.scalp.stock.service;

import com.hrkj.scalp.stock.entity.UserStock;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description: 码商库存
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
public interface IUserStockService extends IService<UserStock> {

    List<Map<String,Object>> queryUserStock(@Param("userId")String userId);

    void quartzUpdateStock();


}
