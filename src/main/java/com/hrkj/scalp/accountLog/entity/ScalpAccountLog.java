package com.hrkj.scalp.accountLog.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: scalp_account_old
 * @Author: jeecg-boot
 * @Date:   2020-03-09
 * @Version: V1.0
 */
@Data
@TableName("scalp_account_log")
public class ScalpAccountLog implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**变动记录id*/
	@Excel(name = "变动记录id", width = 15)
	@TableId(type= IdType.UUID)
    private String recordId;
	/**账目号*/
	@Excel(name = "账目号", width = 15)
	private String accountNum;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    private String userId;
	/**用户类型   0：商户   1：码商*/
	@Excel(name = "用户类型   0：商户   1：码商", width = 15)
    private Integer userType;
	/**是否显示  0：不显示  1：显示*/
	@Excel(name = "是否显示  0：不显示  1：显示", width = 15)
    private Integer isShow;
	/**操作前金额*/
	@Excel(name = "操作前金额", width = 15)
    private Integer oldMoney;
	/**当前金额*/
	@Excel(name = "当前金额", width = 15)
    private Integer nowMoney;
	/**变动金额*/
	@Excel(name = "变动金额", width = 15)
    private Integer changeMoney;
	/**商品id*/
	@Excel(name = "商品id", width = 15)
	private String shopId;
	/**商品操作前数量*/
	@Excel(name = "商品操作前数量", width = 15)
	private Integer shopOldNum;
	/**商品当前数量*/
	@Excel(name = "商品当前数量", width = 15)
	private Integer shopNowNum;
	/**商品数量*/
	@Excel(name = "商品数量", width = 15)
	private Integer shopNum;
	/**出入账   0：出账   1：入账*/
	@Excel(name = "出入账 ", width = 15)
    private String type;
	/**订单类型*/
	@Excel(name = "订单类型", width = 15)
    private Integer recordType;
	/**收款方式  1：支付宝   2：微信*/
	@Excel(name = "收款方式  1：支付宝   2：微信", width = 15)
    private Integer putType;
	/**费率*/
	@Excel(name = "费率", width = 15)
    private Double rateRatio;
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
	/**预留1*/
	@Excel(name = "预留1", width = 15)
    private String reserved1;
	/**预留2*/
	@Excel(name = "预留2", width = 15)
    private String reserved2;
	/**预留3*/
	@Excel(name = "预留3", width = 15)
    private String reserved3;

	public ScalpAccountLog() {
	}

	public ScalpAccountLog(String accountNum, String userId, Integer userType, Integer oldMoney, Integer nowMoney, Integer changeMoney, String type, Integer recordType, Double rateRatio, String remark,Integer isShow) {
		this.accountNum = accountNum;
		this.userId = userId;
		this.userType = userType;
		this.oldMoney = oldMoney;
		this.nowMoney = nowMoney;
		this.changeMoney = changeMoney;
		this.type = type;
		this.recordType = recordType;
		this.rateRatio = rateRatio;
		this.remark = remark;
		this.isShow = isShow;
	}

	public ScalpAccountLog(String accountNum, String userId, Integer userType, String shopId, Integer shopOldNum, Integer shopNowNum, Integer shopNum, String type, Integer recordType, Double rateRatio, String remark,Integer isShow) {
		this.accountNum = accountNum;
		this.userId = userId;
		this.userType = userType;
		this.shopId = shopId;
		this.shopOldNum = shopOldNum;
		this.shopNowNum = shopNowNum;
		this.shopNum = shopNum;
		this.type = type;
		this.recordType = recordType;
		this.rateRatio = rateRatio;
		this.remark = remark;
		this.isShow = isShow;
	}

	public ScalpAccountLog(String accountNum, String userId, Integer userType, String shopId, Integer shopNum, String type, Integer recordType, Double rateRatio, String remark,Integer isShow) {
		this.accountNum = accountNum;
		this.userId = userId;
		this.userType = userType;
		this.shopId = shopId;
		this.shopNum = shopNum;
		this.type = type;
		this.recordType = recordType;
		this.rateRatio = rateRatio;
		this.remark = remark;
		this.isShow = isShow;
	}

}
