package com.hrkj.scalp.message.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.message.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 消息公告
 * @Author: jeecg-boot
 * @Date:   2020-03-30
 * @Version: V1.0
 */
 @Repository
public interface MessageMapper extends BaseMapper<Message> {

}
