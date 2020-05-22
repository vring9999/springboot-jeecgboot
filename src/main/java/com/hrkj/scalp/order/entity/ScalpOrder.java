package com.hrkj.scalp.order.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: scalp_order
 * @Author: jeecg-boot
 * @Date:   2020-03-09
 * @Version: V1.0
 */
@Data
@TableName("scalp_order")
public class ScalpOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**订单id*/
	@Excel(name = "订单id", width = 15)
	@TableId(type=IdType.UUID)
    private String orderId;
	/**外部订单*/
	@Excel(name = "外部订单", width = 15)
    private String outId;
	/**码商id*/
	@Excel(name = "码商id", width = 15)
    private String userId;
	/**商户id*/
	@Excel(name = "商户id", width = 15)
    private String merchantId;
	/**收款金额*/
	@Excel(name = "收款金额", width = 15)
    private Integer earnMoney;
	/**码商收益*/
	@Excel(name = "码商收益", width = 15)
    private Integer userEarn;
	/**商户收益*/
	@Excel(name = "商户收益", width = 15)
    private Integer merchantEarn;
	/**二维码id*/
	@Excel(name = "二维码id", width = 15)
    private Integer qrId;
	/**二维码类型*/
	@Excel(name = "二维码类型", width = 15)
    private Integer qrType;
	/**码商确认方式   0：手动确认   1：后台确认*/
	@Excel(name = "码商确认方式   0：手动确认   1：后台确认", width = 15)
    private Integer userPassType;
	/**付款方式  0：未操作  1：确认支付  */
	@Excel(name = "付款方式  0：未操作  1：确认支付  ", width = 15)
    private Integer payType;
	/**订单状态  0：未支付   1：已支付*/
	@Excel(name = "订单状态  0：未支付   1：已支付", width = 15)
    private Integer orderStatus;
	/**是否正常回调   0：未回调   1：已回调*/
	@Excel(name = "是否正常回调   0：未回调   1：已回调", width = 15)
    private Integer payStatus;
	/**是否异常单      0：否    1：是*/
	@Excel(name = "是否异常单      0：否    1：是", width = 15)
    private Integer isUnusual;
	/**平台利润比例*/
	@Excel(name = "平台利润比例", width = 15)
    private Double platRatio;
	/**代理分润*/
	@Excel(name = "代理分润", width = 15)
    private Double agencyRatio;
	/**商品id*/
	@Excel(name = "商品id", width = 15)
	private String shopId;
	/**商品数量*/
	@Excel(name = "商品数量", width = 15)
	private Integer shopNum;
	/**回调次数*/
	@Excel(name = "回调次数", width = 15)
    private Integer backNum;
	/**订单时间*/
	@Excel(name = "订单时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Long orderTime;
	/**打款时间*/
	@Excel(name = "打款时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Long remitTime;
	/**回调时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Long backTime;
	/**过期时间*/
	@Excel(name = "过期时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Long pastTime;
	/**审核管理员*/
	@Excel(name = "审核管理员", width = 15)
    private String systemId;
	/**备注*/
	@Excel(name = "备注", width = 15)
    private String remark;
	/**预留1*/
	@Excel(name = "预留1", width = 15)
    private String reserved1;
	/**预留2*/
	@Excel(name = "预留2", width = 15)
    private String reserved2;
	/**预留3*/
	@Excel(name = "预留3", width = 15)
    private String reserved3;
}
