package com.hrkj.scalp.a_common.listener;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hrkj.scalp.order.entity.ScalpOrder;
import com.hrkj.scalp.order.service.IScalpOrderService;
import com.hrkj.scalp.util.UsedCode;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @title
 * @description 订单过期监听：接收过期的redis消息,获取到key（订单号）然后去更新对应订单状态
 * @author vring
 * @param:
 * @throws
 */
@Transactional
@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private IScalpOrderService scalpOrderService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 在Redis配置文件中开启订阅键过期模式
     *监听失效的key
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        //  获取失效的key
        String expiredKey = message.toString();
        String prefix = "";
        if(expiredKey.indexOf("_") != -1){
            prefix = expiredKey.substring(0,expiredKey.indexOf("_"));
        }
        //根据key的前缀判断是不是属于订单过期
        if(UsedCode.ORDER_PREFIX.equals(prefix)){
            String orderId = expiredKey.substring(expiredKey.indexOf("_") + 1);
            //业务逻辑处理   查询这笔未确认收款但是没有取消支付的订单是否还存在
            QueryWrapper queryWrapper = new QueryWrapper<ScalpOrder>().eq("user_pass_type",UsedCode.ORDER_NOT_PASS)
                    .eq("order_id",orderId).ne("pay_type",UsedCode.ORDER_CANCEL);
            ScalpOrder order = scalpOrderService.getOne(queryWrapper);
            if(null != order){
                order.setOrderStatus(UsedCode.ORDER_PAST_DUE);
                scalpOrderService.updateById(order);
                scalpOrderService.putFreezeMoney(order,UsedCode.ORDER_PAST_DUE,Object->{
                    log.info("订单号:{}已超时" ,expiredKey);
                    return Result.ok();
                });
            }

        }
    }
}

