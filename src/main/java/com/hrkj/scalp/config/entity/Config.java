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
 * @Description: instead_common_config
 * @Author: jeecg-boot
 * @Date:   2020-02-28
 * @Version: V1.0
 */
@Data
@TableName("common_config")
public class Config implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**配置名称*/
	@Excel(name = "配置名称", width = 15)
    private String cfgName;
	/**属性名*/
	@Excel(name = "属性名", width = 15)
    private String cfgKey;
	/**属性值*/
	@Excel(name = "属性值", width = 15)
    private String cfgValue;
	/**备注*/
	@Excel(name = "备注", width = 15)
    private String cfgRemark;
	/**1:普通配置   2：秘钥配置*/
	@Excel(name = "1:普通配置   2：秘钥配置", width = 15)
    private Integer cfgType;
	/**配置级别*/
	@Excel(name = "配置级别", width = 15)
    private Integer cfgLevel;
}
