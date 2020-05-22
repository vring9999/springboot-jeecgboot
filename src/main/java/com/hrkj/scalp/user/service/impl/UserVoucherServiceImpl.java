package com.hrkj.scalp.user.service.impl;

import com.hrkj.scalp.user.entity.UserVoucher;
import com.hrkj.scalp.user.mapper.UserVoucherMapper;
import com.hrkj.scalp.user.service.IUserVoucherService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 优惠券抵扣记录
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
@Service
public class UserVoucherServiceImpl extends ServiceImpl<UserVoucherMapper, UserVoucher> implements IUserVoucherService {

}
