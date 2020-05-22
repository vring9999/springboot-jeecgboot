package com.hrkj.scalp.bank.entity;

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
 * @Description: 码商银行卡
 * @Author: jeecg-boot
 * @Date:   2020-03-19
 * @Version: V1.0
 */
@Data
@TableName("farming_bank")
public class Bank implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
    private Integer id;
	/**归属码商*/
	@Excel(name = "归属码商", width = 15)
    private String userId;
	/**持卡人姓名*/
	@Excel(name = "持卡人姓名", width = 15)
    private String name;
	/**银行名*/
	@Excel(name = "银行名", width = 15)
    private String bankName;
	/**银行卡号*/
	@Excel(name = "银行卡号", width = 15)
    private String account;
	/**开户地址*/
	@Excel(name = "开户地址", width = 15)
	private String bankAddress;
	/**备注信息*/
	@Excel(name = "备注信息", width = 15)
    private String remark;
	/**1 可用（true）0不可用（false）*/
	@Excel(name = "启用状态", width = 15)
    private Integer status;
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
	/**remark1*/
	@Excel(name = "remark1", width = 15)
    private String remark1;
}
