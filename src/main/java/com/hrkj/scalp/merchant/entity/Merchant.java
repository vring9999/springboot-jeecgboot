package com.hrkj.scalp.merchant.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: farming_merchant
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Data
@TableName("farming_merchant")
public class Merchant implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
	/**商户名*/
    private String name;
	/**phone*/
    private String phone;
	/**登入密码*/
    private String password;
	/**安全密码*/
    private String safetyPwd;
	/**账户余额*/
    private Integer balance;
	/**冻结金额*/
    private Integer frozen;
	/**冻结金额*/
    private Integer withFrozen;
	/**注册时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date registerTime;
	/**注册ip*/
    private String registerIp;
	/**最后登录时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
	/**最后登录ip*/
    private String lastIp;
    /**最后登录系统*/
    private String lastOs;
    /**最后登录城市*/
    private String lastCity;
	/**qq账号*/
    private String qq;
	/**微信账号*/
    private String wechat;
	/**订单匹配  0关闭  1开启*/
    private Integer matchState;
	/**归属用户组*/
    private String groupName;
	/**永久锁定 0关闭状态 1打开状态*/
    private Integer permanentLock;
	/**临时锁定  0关闭状态  1打开状态  */
    private Integer tempLock;
	/**商户余额变动备注信息*/
    private String remark1;
	/**remark2*/
    private String remark2;
	/**remark3*/
    private String remark3;
	/**remark4*/
    private String remark4;
}
