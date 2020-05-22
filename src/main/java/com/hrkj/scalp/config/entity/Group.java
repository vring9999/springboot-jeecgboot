package com.hrkj.scalp.config.entity;

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
 * @Description: farming_group
 * @Author: jeecg-boot
 * @Date:   2020-03-17
 * @Version: V1.0
 */
@Data
@TableName("farming_group")
public class Group implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private Integer id;
	/**用户组名称*/
	@Excel(name = "用户组名称", width = 15)
    private String groupName;
	/**默认*/
	@Excel(name = "默认", width = 15)
    private Double defaultPer;
	/**支付宝*/
	@Excel(name = "支付宝", width = 15)
    private Double alipay;
	/**微信*/
	@Excel(name = "微信", width = 15)
    private Double wechat;
	/**1  码商用户组   2商户用户组*/
	@Excel(name = "1  码商用户组   2商户用户组", width = 15)
    private Integer type;
	/**等级*/
	@Excel(name = "等级", width = 15)
    private Integer sort;
}
