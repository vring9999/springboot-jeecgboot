package com.hrkj.scalp.merchant.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author vring
 * @ClassName MerchantUserVo.java
 * @Description 存放码商商户基础信息的实体对象
 * @createTime 2020/3/24 9:24
 */
@Data
public class MerchantUserVo {

    private String id;

    private String phone;

    private String email;

    private String qq;

    private String wechat;

    private String secretKey;

    /**注册时间  JsonFormat设置显示到web端的时间格式  DateTimeFormat设置存储到数据库的时间格式*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    private Integer status;

    /**最后登录时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;

    /**最后登录系统*/
    private String lastOs;

    /**最后登录城市*/
    private String lastCity;



}
