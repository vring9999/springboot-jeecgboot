package com.hrkj.scalp.merchant.service.impl;

import com.hrkj.scalp.merchant.entity.QrCode;
import com.hrkj.scalp.merchant.mapper.QrCodeMapper;
import com.hrkj.scalp.merchant.service.IQrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: farming_qr_code
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@Service
public class QrCodeServiceImpl extends ServiceImpl<QrCodeMapper, QrCode> implements IQrCodeService {

    @Autowired
    private QrCodeMapper qrCodeMapper;
    @Override
    public void editStatus(Map<String, Object> params) {
        qrCodeMapper.editStatus(params);
    }

    @Override
    public void updateBatch(List<String> list, int status) {
        qrCodeMapper.updateBatch(list,status);
    }

    @Override
    public List<Map<String,Object>>  listForApp(String userId,int type) {
        return qrCodeMapper.listForApp(userId,type);
    }

    @Override
    public Integer queryCountOpen(String userId, int useStatus) {
        return qrCodeMapper.queryCountOpen(userId,useStatus);
    }

    @Override
    public List<Map<String, Object>> getPaymentCode(Map<String, Object> params) {
        return qrCodeMapper.getPaymentCode(params);
    }

    @Override
    public void quartzUpdate() {
        qrCodeMapper.quartzUpdate();
    }

    @Override
    public void quartzUpdateTodayAccount() {
        qrCodeMapper.quartzUpdateTodayAccount();
    }

}
