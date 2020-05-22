package com.hrkj.scalp.system.entity;


import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 登录表单
 *
 * @Author scott
 * @since  2019-01-18
 */
@Data
@Component
public class SysLoginModel {
    private String account;
    private String password;
    private String safeCode;
    private String loginType;

}