package com.hrkj.scalp.a_common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *   分布式架构获取唯一订单号（基于redis），如果redis 集群,需设置自增歩长,使用命令设置
 *
 */
@Configuration
public class RedisOrderIDGenerate {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     *  获取唯一Id（20位）
     *
     * @param key   redis-key前缀
     * @param delta 默认初始自增值
     * @return java.lang.String
     * @author ws
     * @mail 1720696548@qq.com
     * @date 2020/2/20 0020 15:59
     */
    public String getOrderId(String key, Long delta) {
        try {
            // delta为空默认值1
            if (null == delta) {
                delta = 1L;
            }
            // 生成14位的时间戳(每秒使用新的时间戳当key)
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            // 获得redis-key
            String newKey = key + ":" + timeStamp;
            // 获取自增值（时间戳+自定义key）
            Long increment = redisTemplate.opsForValue().increment(newKey, delta);
            // 设置时间戳生成的key的有效期为2秒
            redisTemplate.expire(newKey, 2, TimeUnit.SECONDS);
            // 获取订单号，时间戳 + 唯一自增Id( 6位数,不过前方补0)
            return timeStamp + String.format("%06d", increment);
        } catch (Exception e) {
            // redis 宕机时采用时间戳加随机数
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Random random = new Random();
            //14位时间戳到秒 + 4位随机数
            timeStamp += random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
            return timeStamp;
        }
    }
}

