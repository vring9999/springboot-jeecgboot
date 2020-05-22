package com.hrkj.scalp.user.service;

import com.hrkj.scalp.user.entity.UserCollectionLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 码商更改收款方式记录
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
public interface IUserCollectionLogService extends IService<UserCollectionLog> {

    UserCollectionLog queryCollectionLogByUserId(@Param("userId")String userId);


}
