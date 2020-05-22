package com.hrkj.scalp.order.entity;

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
 * @Description: farming_sub_commission
 * @Author: jeecg-boot
 * @Date:   2020-03-24
 * @Version: V1.0
 */
@Data
@TableName("farming_sub_commission")
public class SubCommission implements Serializable {
	private static final long serialVersionUID = 1L;

	/**代理收益明细id*/
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	/**关联订单*/
	@Excel(name = "关联订单", width = 15)
	private java.lang.String orderId;
	/**码商id*/
	@Excel(name = "码商id", width = 15)
	private java.lang.String userId;
	/**一级码商id*/
	@Excel(name = "一级码商id", width = 15)
	private java.lang.String oneUserId;
	/**二级码商id*/
	@Excel(name = "二级码商id", width = 15)
	private java.lang.String twoUserId;
	/**三级码商id*/
	@Excel(name = "三级码商id", width = 15)
	private java.lang.String threeUserId;
	/**四级码商id*/
	@Excel(name = "四级码商id", width = 15)
	private java.lang.String fourUserId;
	/**一级码商收益*/
	@Excel(name = "一级码商收益", width = 15)
	private java.lang.Integer oneUserMoney;
	/**二级码商 收益*/
	@Excel(name = "二级码商 收益", width = 15)
	private java.lang.Integer twoUserMoney;
	/**三级码商收益*/
	@Excel(name = "三级码商收益", width = 15)
	private java.lang.Integer threeUserMoney;
	/**四级码商收益*/
	@Excel(name = "四级码商收益", width = 15)
	private java.lang.Integer fourUserMoney;
	/**金额*/
	@Excel(name = "金额", width = 15)
	private java.lang.Integer proxyMoney;
	/**createTime*/
	@Excel(name = "createTime", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**updateTime*/
	@Excel(name = "updateTime", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String remark;
	/**remark1*/
	@Excel(name = "remark1", width = 15)
	private String remark1;

	public SubCommission() {
	}

	public SubCommission(String orderId, String userId) {
		this.orderId = orderId;
		this.userId = userId;
	}
}