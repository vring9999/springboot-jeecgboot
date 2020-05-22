package com.hrkj.scalp.stock.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.stock.entity.UserStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 码商库存
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
 @Repository
public interface UserStockMapper extends BaseMapper<UserStock> {

  List<Map<String,Object>> queryUserStock(@Param("userId")String userId);

  void quartzUpdateStock();

}
