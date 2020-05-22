package com.hrkj.scalp.message.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 消费者服务类
 * @author vring
 * @ClassName LogMqListener.java
 * @Description 监听用户log队列消息
 * @createTime 2020/3/25 16:45
 */
@Component
@Slf4j
public class LogMqListener {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 监听消费用户日志
     * @param message
     */
//    @RabbitListener(queues = "${basic.info.mq.queue.name}",containerFactory = "singleListenerContainer")
//    public void consumeUserLogQueue(@Payload byte[] message){
//        try {
//            String userLog=objectMapper.readValue(message, String.class);
//            log.info("监听消费用户日志 监听到消息： {} ",userLog);
//            //userLogMapper.insertSelective(userLog);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
