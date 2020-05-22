package com.hrkj.scalp.util.rsa;

import java.security.KeyPair;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0) // 执行顺序，越小越先执行
public class MyCommandLineRunner1 implements CommandLineRunner {
    // run方法调用的是核心逻辑，参数是系统启动时传入的参数，也就是main方法传入的参数，可在启动类上右击Run As-->Run
    // Configurations-->Arguments中加入
    @Override
    public void run(String... args) throws Exception {
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