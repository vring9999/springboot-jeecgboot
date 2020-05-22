package com.hrkj.scalp.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.order.entity.ScalpOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: scalp_order
 * @Author: jeecg-boot
 * @Date:   2020-03-09
 * @Version: V1.0
 */
@Repository
public interface ScalpOrderMapper extends BaseMapper<ScalpOrder> {

    /*
     * 获取码商微信支付宝今日收款 历史收款
     */
    List<Map<String,Object>> queryEarnMoneyForApp(@Param("userId") String userId);

    /*
     * 获取今日码商个人返利
     */
    Integer queryUserEarnForApp(@Param("userId") String userId);
    /*
     * APP 获取订单列表
     */
    List<Map<String,Object>> queryOrderList(Map<String,Object> params);

    Map<String,Object> queryOrderInfo(@Param("userId") String userId,@Param("merchantId") String merchantId,@Param("flag")int flag);

    List<Map<String,Object>> allRanking();      //所有单排行

    List<Map<String,Object>> smallRanking(String money);    //小金单排行

    List<Map<String,Object>> inviteRanking();   //推广量排行

    Map<String,Object> allUnit(String userId);

    Map<String,Object> smallUnit(@Param("userId") String userId,@Param("money")String money);

    Map<String,Object> inviteUnit(String userId);
}
