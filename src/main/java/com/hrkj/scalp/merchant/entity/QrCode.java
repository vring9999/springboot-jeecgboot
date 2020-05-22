package com.hrkj.scalp.merchant.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Objects;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: farming_qr_code
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@Data
@TableName("farming_qr_code")
public class QrCode implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id    当数据库主键有设置为自增的时候需要注释掉下面的tableId注解或者改为@TableId(type=IdType.AUTO)*/
//	@TableId(type = IdType.ID_WORKER_STR)
    private Integer id;
	/**码商id*/
	@Excel(name = "码商id", width = 15)
    private String userId;
	/**收款名*/
	@Excel(name = "收款名", width = 15)
    private String name;
	/**二维码展示地址*/
	@Excel(name = "二维码展示地址", width = 15)
    private String qrUrl;
	/**二维码类型   1支付宝   2微信*/
	@Excel(name = "二维码类型   1支付宝   2微信", width = 15)
    private Integer type;
	/**类型为支付宝时必填*/
	@Excel(name = "支付宝账号", width = 15)
    private String alipay;
	/**最大收款*/
	@Excel(name = "最大收款", width = 15)
    private Integer maxAccount;
	/**今日收款*/
	@Excel(name = "今日收款", width = 15)
    private Integer todayAccount;
	/**今日还可收款*/
	@Excel(name = "今日还可收款", width = 15)
	private Integer surplusAccount;
	/**用户备注*/
	@Excel(name = "用户备注", width = 15)
    private String userRemark;
	/**管理员强制下线   1是  0否*/
	@Excel(name = "管理员强制下线  1是  0否", width = 15)
    private Integer adminStatus;
	/**管理员强制下线说明*/
	@Excel(name = "管理员强制下线说明/驳回说明", width = 15)
    private String adminRemark;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
	/**轮询开关  0关闭   1开启*/
	@Excel(name = "轮询开关  0关闭   1开启", width = 15)
    private Integer pollStatus;
	/**使用状态  0关闭   1开启  2待审核  3驳回*/
	@Excel(name = "使用状态  0关闭   1开启", width = 15)
    private Integer useStatus;
	/**排序*/
	@Excel(name = "排序", width = 15)
    private Integer sort;
	/**备用*/
	@Excel(name = "备用", width = 15)
    private String remark;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		QrCode qrCode = (QrCode) o;
		return maxAccount.equals(qrCode.maxAccount) &&
				userId.equals(qrCode.userId) &&
				useStatus.equals(qrCode.useStatus);
	}

	@Override
	public int hashCode() {
		return Objects.hash(maxAccount, useStatus, userId);
	}

}
