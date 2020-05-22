package com.hrkj.scalp.order.service;

import com.hrkj.scalp.order.entity.ScalpOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @Description: scalp_order
 * @Author: jeecg-boot
 * @Date:   2020-03-09
 * @Version: V1.0
 */
public interface IScalpOrderService extends IService<ScalpOrder> {

    List<Map<String,Object>> queryEarnMoneyForApp(String userId);

    Integer queryUserEarnForApp(@Param("userId") String userId);

    List<Map<String,Object>> queryOrderList(Map<String, Object> params);

    Map<String,Object> queryOrderInfo(@Param("userId") String userId,@Param("merchantId") String merchantId,@Param("flag")int flag);

    Result<?> putFreezeMoney(ScalpOrder order, int status, Function<Result<?>, Result<?>> function);

    Result<?> planOrder(ScalpOrder scalpOrder, Function<Result<?>, Result<?>> function);


    List<Map<String,Object>> allRanking();      //所有单排行

    List<Map<String,Object>> smallRanking(String money);    //小金单排行

    List<Map<String,Object>> inviteRanking();   //推广量排行

    Map<String,Object> allUnit(String userId);

    Map<String,Object> smallUnit(String userId,String money);

    Map<String,Object> inviteUnit(String userId);

}
