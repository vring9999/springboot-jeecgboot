package com.hrkj.scalp.account.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 账目表
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Data
@TableName("scalp_account")
public class ScalpAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.UUID)
    private String id;
	/**账目号*/
	@Excel(name = "账目号", width = 15)
    private String accountNum;
	/**商户号*/
	@Excel(name = "商户号", width = 15)
    private String userId;
	/**0:商户  1：码商*/
	@Excel(name = "0:商户  1：码商", width = 15)
	private Integer userType;
	/**1：充值   2：提现*/
	@Excel(name = "1：充值   2：提现", width = 15)
    private Integer type;
	/**操作金额*/
	@Excel(name = "操作金额", width = 15)
    private Integer operationMoney;
	/**实际金额*/
	@Excel(name = "实际金额", width = 15)
    private Integer actualMoney;
	/**手续费*/
	@Excel(name = "手续费", width = 15)
    private Integer serviceMoney;
	/**商品id*/
	@Excel(name = "商品id", width = 15)
	private String shopId;
	/**商品数量*/
	@Excel(name = "商品数量", width = 15)
	private Integer shopNum;
	/**收款银行*/
	@Excel(name = "收款银行", width = 15)
    private String receiptBank;
	/**收款人*/
	@Excel(name = "收款人", width = 15)
    private String receiptName;
	/**银行卡号*/
	@Excel(name = "银行卡号", width = 15)
    private String bankNumber;
	/**银行流水号*/
	@Excel(name = "银行流水号", width = 15)
    private String bankNum;
	/**1:待处理  2：驳回   3：通过*/
	@Excel(name = "1:待处理  2：驳回   3：通过", width = 15)
    private Integer status;
	/**备注*/
	@Excel(name = "备注", width = 15)
    private String remark;
	/**创建时间*/
	@Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	/**修改时间*/
	@Excel(name = "修改时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
	/**审核管理员id*/
	@Excel(name = "审核管理员id", width = 15)
    private String systemId;
	/**是否使用优惠券  1是 0否*/
	@TableField(value="is_sub")
	private Integer isSub;
	/**抵消佣金*/
	@Excel(name = "优惠券抵扣金额", width = 15)
	private Integer subMoney;
	/**预留字段1*/
	@Excel(name = "预留字段1", width = 15)
    private String reserved1;
	/**预留字段2*/
	@Excel(name = "预留字段2", width = 15)
    private String reserved2;
	/**预留字段3*/
	@Excel(name = "预留字段3", width = 15)
    private String reserved3;

	public ScalpAccount() {
	}

	public ScalpAccount(String accountNum, String userId, Integer userType, Integer type, Integer operationMoney, Integer actualMoney, Integer serviceMoney, String receiptBank, String receiptName, String bankNum, Integer status) {
		this.accountNum = accountNum;
		this.userId = userId;
		this.userType = userType;
		this.type = type;
		this.operationMoney = operationMoney;
		this.actualMoney = actualMoney;
		this.serviceMoney = serviceMoney;
		this.receiptBank = receiptBank;
		this.receiptName = receiptName;
		this.bankNum = bankNum;
		this.status = status;
	}
}
