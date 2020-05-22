package com.hrkj.scalp.system.entity;

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
 * @Description: farming_system_earn
 * @Author: jeecg-boot
 * @Date:   2020-03-24
 * @Version: V1.0
 */
@Data
@TableName("farming_system_earn")
public class SystemEarn implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.UUID)
    private String id;
	/**商户id*/
	@Excel(name = "商户id", width = 15)
    private String merchantId;
	/**审核管理员id*/
	@Excel(name = "审核管理员id", width = 15)
    private String auditSysId;
	/**金额*/
	@Excel(name = "金额", width = 15)
    private Integer money;
	/**时间*/
	@Excel(name = "时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date time;
	/**备用1*/
	@Excel(name = "备用1", width = 15)
    private String remark1;
	/**备用2*/
	@Excel(name = "备用2", width = 15)
    private String remark2;

	public SystemEarn() {
	}

	public SystemEarn(String merchantId, String auditSysId, Integer money, Date time) {
		this.merchantId = merchantId;
		this.auditSysId = auditSysId;
		this.money = money;
		this.time = time;
	}
}
