package com.hrkj.scalp.message.service.impl;

import com.hrkj.scalp.message.entity.Message;
import com.hrkj.scalp.message.mapper.MessageMapper;
import com.hrkj.scalp.message.service.IMessageService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 消息公告
 * @Author: jeecg-boot
 * @Date:   2020-03-30
 * @Version: V1.0
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
