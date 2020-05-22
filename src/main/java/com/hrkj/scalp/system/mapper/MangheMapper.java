package com.hrkj.scalp.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.system.entity.Manghe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 产品库存
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
 @Repository
public interface MangheMapper extends BaseMapper<Manghe> {

 Integer queryShopNum(@Param("list")List<String> list);

 void updateBatch(@Param("list")List<String> list,@Param("status") Integer status);


}
