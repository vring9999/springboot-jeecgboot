package com.hrkj.scalp.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 码商实体对象
 * @Author: jeecg-boot
 * @Date:   2020-03-23
 * @Version: V1.0
 */
@Data
@TableName("farming_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	/**码商id*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**父级码商id*/
	@Excel(name = "父级码商id", width = 15)
	private String parentId;
	/**商户id*/
	@Excel(name = "商户id", width = 15)
	private String merchantId;
	/**码商名*/
	@Excel(name = "码商名", width = 15)
	private String name;
	/**真实名*/
	@Excel(name = "真实名", width = 15)
	private String realName;
	/**头像*/
	@Excel(name = "头像", width = 15)
	private String icon;
	/**身份证*/
	@Excel(name = "身份证", width = 15)
	private String idCard;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
	private String phone;
	/**密码*/
	@Excel(name = "密码", width = 15)
	private String password;
	/**安全密码*/
	@Excel(name = "安全密码", width = 15)
	private String safetyPwd;
	/**抵用券*/
	@Excel(name = "抵用券", width = 15)
	private Integer voucherMoney;
	/**抵用券冻结*/
	private Integer voucherFrozen;
	/**注册时间*/
	@Excel(name = "注册时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date registerTime;
	/**注册ip*/
	@Excel(name = "注册ip", width = 15)
	private String registerIp;
	/**上次登录时间*/
	@Excel(name = "上次登录时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastTime;
	/**上次登录城市*/
	@Excel(name = "上次登录城市", width = 15)
	private String lastCity;
	/**上次操作系统*/
	@Excel(name = "上次操作系统", width = 15)
	private String lastOs;
	/**上次登录ip*/
	@Excel(name = "上次登录ip", width = 15)
	private String lastIp;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
	private String email;
	/**qq账号*/
	@Excel(name = "qq账号", width = 15)
	private String qq;
	/**微信账号*/
	@Excel(name = "微信账号", width = 15)
	private String wechat;
	/**归属用户组*/
	@Excel(name = "归属用户组", width = 15)
	private String groupName;
	/**注册邀请码*/
	@Excel(name = "注册邀请码", width = 15)
	private String parentCode;
	/**我的邀请码*/
	@Excel(name = "我的邀请码", width = 15)
	private String registerCode;
	/**收款码开关1开启支付宝二维码收款   2开启微信二维码收款*/
	@Excel(name = "收款码开关1开启支付宝二维码收款   2开启微信二维码收款", width = 15)
	private Integer receiveStatus;
	/**开业状态   0打烊   1开业    2管理员强制打烊*/
	@Excel(name = "开业状态   0打烊   1开业    2管理员强制打烊", width = 15)
	private Integer practiceStatus;
	/**0打烊   1开业    */
	@Excel(name = "0打烊   1开业    ", width = 15)
	private Integer adminStatus;
	/**账号状态   0异常   1正常*/
	@Excel(name = "账号状态   0异常   1正常", width = 15)
	private Integer userStatus;
	/**接单状态   0关闭   1开启*/
	@Excel(name = "接单状态   0关闭   1开启", width = 15)
	private Integer receiptStatus;
	/**谷歌密钥*/
	@Excel(name = "谷歌密钥", width = 15)
	private String chromeKey;
	/**代理等级*/
	@Excel(name = "代理等级", width = 15)
	private Integer proxyLevel;
	/**代理收益*/
	@Excel(name = "代理收益", width = 15)
	private Integer proxyMoney;
	/**今日收益*/
	private Integer dayAccount;
	/**remark1*/
	@Excel(name = "remark1", width = 15)
	private String remark1;
	/**remark2*/
	@Excel(name = "remark2", width = 15)
	private String remark2;
	/**remark3*/
	@Excel(name = "remark3", width = 15)
	private String remark3;
	/**订单匹配   0关闭   1开启*/
//	@Excel(name = "订单匹配   0关闭   1开启", width = 15)
//	private Integer matchStatus;
	/**接单锁定   0关闭   1开启*/
//	@Excel(name = "接单锁定   0关闭   1开启", width = 15)
//	private Integer receiptLock;
//	/**永久锁定   0关闭   1开启*/
//	@Excel(name = "永久锁定   0关闭   1开启", width = 15)
//	private Integer permanentLock;
//	/**临时锁定   0关闭   1开启*/
//	@Excel(name = "临时锁定   0关闭   1开启", width = 15)
//	private Integer tempLock;
}
