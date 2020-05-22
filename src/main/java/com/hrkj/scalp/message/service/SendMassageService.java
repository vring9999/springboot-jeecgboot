package com.hrkj.scalp.message.service;

public interface SendMassageService {

    void sendMsg(String queueName, String msg);

    void sendMq();

    void sendMqRabbit();

    void sendMqExchange();
}
