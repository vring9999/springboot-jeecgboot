package com.hrkj.scalp.message.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author vring
 * @ClassName LogMqListener.java
 * @Description 生产者服务类
 * @createTime 2020/3/25 16:45
 */
@Service
public class ProdServer {

	private static final Logger log = LoggerFactory.getLogger(ProdServer.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private Environment env;
	
	/**
	 * 生产消息 加入MQ
	 * @param obj
	 * @throws JsonProcessingException
	 */
	public void convertAndSend(Object obj) throws JsonProcessingException {
		
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		rabbitTemplate.setExchange(env.getProperty("log.user.exchange.name"));
		rabbitTemplate.setRoutingKey(env.getProperty("log.user.routing.key.name"));
		
		Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(obj))
				.setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
		message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,
				MessageProperties.CONTENT_TYPE_JSON);
		
		rabbitTemplate.convertAndSend(message);
		log.info("--------------------消息传入MQ成功-----------------");
	}
}
