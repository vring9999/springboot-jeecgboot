package com.hrkj.scalp.merchant.mapper;

import java.util.List;
import java.util.Map;

import com.hrkj.scalp.merchant.entity.MerchantUserVo;
import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.merchant.entity.Merchant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: farming_merchant
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Repository
public interface MerchantMapper extends BaseMapper<Merchant> {

    Merchant queryMerchantByPhone(String phone);

    void editLockStatus(Map<String, Object> params);

    MerchantUserVo queryBasicInfo(@Param("id") String id);

}
