package com.hrkj.scalp.merchant.service;

import com.hrkj.scalp.merchant.entity.QrCode;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description: farming_qr_code
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
public interface IQrCodeService extends IService<QrCode> {

    void editStatus(Map<String, Object> params);

    void updateBatch(List<String> list, int status);

    List<Map<String,Object>>  listForApp(String userId,int type);

    Integer queryCountOpen(@Param("userId") String userId, @Param("useStatus") int useStatus);

    List<Map<String,Object>> getPaymentCode(Map<String, Object> params);

    void quartzUpdate();

    void quartzUpdateTodayAccount();

}
