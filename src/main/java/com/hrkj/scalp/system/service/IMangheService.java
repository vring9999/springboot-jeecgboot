package com.hrkj.scalp.system.service;

import com.hrkj.scalp.system.entity.Manghe;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 产品库存
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
public interface IMangheService extends IService<Manghe> {

    Integer queryShopNum(@Param("list") List<String> list);

    void updateBatch(@Param("list")List<String> list,Integer status);


}
