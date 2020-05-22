package com.hrkj.scalp.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.user.entity.UserCollectionLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 码商更改收款方式记录
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
 @Repository
public interface UserCollectionLogMapper extends BaseMapper<UserCollectionLog> {

  UserCollectionLog queryCollectionLogByUserId(@Param("userId")String userId);

}
