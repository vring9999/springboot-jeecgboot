package com.hrkj.scalp.message.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hrkj.scalp.util.UsedCode;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 消息公告
 * @Author: jeecg-boot
 * @Date:   2020-03-30
 * @Version: V1.0
 */
@Data
@TableName("farming_message")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private Integer id;
	/**消息标题*/
	@Excel(name = "消息标题", width = 15)
    private String title;
	/**消息内容*/
	@Excel(name = "消息内容", width = 15)
    private String content;
	/**接收人类型 ：码商   商户   全平台*/
	@Excel(name = "接收人类型 ：码商user   商户merchant   全平台full", width = 15)
    private String sendType;
	/**消息类型 1订单  2充值  3提现  4系统公告*/
	@Excel(name = "消息类型 0提现 1充值  2订单 3系统公告", width = 15)
    private Integer messageType;
	/**码商或者商户id*/
	@Excel(name = "码商或者商户id", width = 15)
    private String userId;
	/**发送时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
	/**推送状态   0未推送   1成功  2失败*/
	@Excel(name = "推送状态   0未推送   1成功  2失败", width = 15)
    private Integer sendStatus;
	/**失败原因*/
	@Excel(name = "失败原因", width = 15)
    private String failedReason;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

	public Message() {
	}

	//订单通知类实体构造器
	public Message(String userId) {
		this.title = "订单通知";
		this.content = "您有一笔新的订单，请注意查收";
		this.sendType = UsedCode.LOGIN_TYPE_USER;
		this.messageType = UsedCode.RECORD_TYPE_ORD;
		this.userId = userId;
		this.createTime = new Date();
	}
}
