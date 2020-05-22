package com.hrkj.scalp.util.rsa;

import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 自定义刷新规则
 * （定时任务）
 * @ClassName: Scheduler
 * @Description 每月15日3时刷新秘钥策略
 */
@Component
public class Scheduler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    // 定义每月15日凌晨更新秘钥
    @Scheduled(cron = "00 00 00 15 * ?") // 依次对应:秒/分/小时/日(每个月第几天)/月/星期/年份
    public void testTasks() throws Exception {
        // 生成秘钥对
        KeyPair keyPair = RSAUtil.getKeyPair();
        // 获取私钥
        String private_key = RSAUtil.getPrivateKey(keyPair);
        // 获取公钥
        String public_key = RSAUtil.getPublicKey(keyPair);
        // 设置私钥
        KeyManager.setPrivate_key(private_key);
        // 设置公钥
        KeyManager.setPublic_key(public_key);
    }
}
