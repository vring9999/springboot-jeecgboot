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
 * @Description: farming_system_user
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Data
@TableName("farming_system_user")
public class SystemUser implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
//	@TableId(type = IdType.ID_WORKER_STR)
	@TableId(type=IdType.AUTO)//数据库设置主键自增的话 就用这个注解或者什么注解都不加
    private Integer id;
	/**登录账号*/
	@Excel(name = "登录账号", width = 15)
    private String account;
	/**登录密码*/
	@Excel(name = "登录密码", width = 15)
    private String password;
	/**安全口令*/
	@Excel(name = "安全口令", width = 15)
    private String safeCode;
	/**是否为超管  1是  2否*/
	@Excel(name = "是否为超管  1是  2否", width = 15)
    private Integer isSuperAdmin;
	/**锁定状态  0锁定 1正常*/
	@Excel(name = "锁定状态  0锁定 1正常", width = 15)
    private Integer lockStatus;
	/**createTime*/
	@Excel(name = "createTime", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
	/**updateTime*/
	@Excel(name = "updateTime", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateTime;
	/**remark*/
	@Excel(name = "remark", width = 15)
    private String remark;
}
