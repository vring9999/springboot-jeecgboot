package com.hrkj.scalp.merchant.mapper;

import java.util.List;
import java.util.Map;

import net.sf.saxon.expr.Component;
import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.merchant.entity.QrCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Description: farming_qr_code
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
 @Repository
public interface QrCodeMapper extends BaseMapper<QrCode> {

 void editStatus(Map<String, Object> params);

 void updateBatch(@Param("list") List<String> list, @Param("status") int status);

 List<Map<String,Object>> listForApp(@Param("userId") String userId, @Param("type") int type);

 Integer queryCountOpen(@Param("userId") String userId,@Param("useStatus") int useStatus);

 List<Map<String,Object>> getPaymentCode(Map<String, Object> params);

 void quartzUpdate();

 void quartzUpdateTodayAccount();

}
