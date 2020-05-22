package com.hrkj.scalp.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
@Slf4j
public class ConstantQiniu {

//    @Value("${qiniu.accessKey}")
//    private String accessKey;
//
//    @Value("${qiniu.secretKey}")
//    private String secretKey;
//
//    @Value("${qiniu.bucket}")
//    private String bucket;
//
//    @Value("${qiniu.path}")
//    private String path;

    @Autowired
    private static CacheInfo cacheInfo;

    @Autowired
    public ConstantQiniu(CacheInfo cacheInfo) {
        ConstantQiniu.cacheInfo = cacheInfo;
    }

    public String accessKey;
    public String secretKey;
    public String bucket;
    public String path;

    public ConstantQiniu() throws Exception {
        this.accessKey = cacheInfo.getCommonToKey("qiniu.properties+qiniu.accessKey");
        this.secretKey = cacheInfo.getCommonToKey("qiniu.properties+q" +
                "iniu.secretKey");
        this.bucket = cacheInfo.getCommonToKey("qiniu.properties+qiniu.bucket");
        this.path = cacheInfo.getCommonToKey("qiniu.properties+qiniu.path");
    }



    /**
     * 将图片上传到七牛云   MultipartFile multipartFile, String fileName
     */
    public String uploadQNImg(MultipartFile multipartFile) {
        byte[] bytes = getBytesWithMultipartFile(multipartFile);
        String src = "";
        JSONObject json = new JSONObject();
        // 构造一个带指定Zone对象的配置类        zone0(华东)  zone1(华北)  zone2(华南)  zoneNa0(北美)
        Configuration cfg = new Configuration(Zone.zone0());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //图片名
        String timeStamp = DateUtil.getCurrentTime("yyyyMMddHHmmssSSS");
        // 生成上传凭证，然后准备上传
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket,timeStamp,3600,new StringMap().put("insertOnly", 1));
            try {
                //超时响应
                Response response = uploadManager.put(bytes, timeStamp, upToken);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                src = path + "/" + putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                try {
                    log.error("七牛云解析：{}",r.bodyString());
                } catch (QiniuException ex2) {
                    log.error("QiniuException:{}", ex2);
                }
                src = null;
            }
        } catch (Exception e) {
            log.error("{}", e);
            src = null;
        }
        return src;
    }

    public byte[] getBytesWithMultipartFile(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public  boolean testQr(FileInputStream file){
        boolean flag = false;
        BufferedImage image;
        try {
            image = ImageIO.read(file);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
//            JSONObject content = JSONObject.parseObject(result.getText());
            String mes = result.getText();
            log.info("mes:{}",mes);
//            log.info("author:{},zxing: {} ",content.getString("author"),content.getString("zxing"));
            log.info("encode:{} ",result.getBarcodeFormat());
            flag = true;
        } catch (Exception e) {
            log.error("二维码解析失败:{}",e);
        }
        return flag;
    }



}