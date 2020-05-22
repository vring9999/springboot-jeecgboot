package com.hrkj.scalp.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author vring
 * @ClassName UserSon.java
 * @Description 码商下级代理实体对象
 * @createTime 2020/3/24 11:06
 */
@Data
public class UserSon {

    private String id;

    private String phone;

    private String wechat;

    private Integer status;

    private Integer proxyLevel;

    private Integer receiptStatus;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
}
