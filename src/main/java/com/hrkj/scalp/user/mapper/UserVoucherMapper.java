package com.hrkj.scalp.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hrkj.scalp.user.entity.UserVoucher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 优惠券抵扣记录
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
 @Repository
public interface UserVoucherMapper extends BaseMapper<UserVoucher> {

}
