package com.hrkj.scalp.system.entity;

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
 * @Description: farming_system_log
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@Data
@TableName("farming_system_log")
public class SystemLog implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**日志类型（1登录日志，2操作日志）*/
	@Excel(name = "日志类型（1登录日志，2操作日志）", width = 15)
    private Integer logType;
	/**日志内容*/
	@Excel(name = "日志内容", width = 15)
    private String logContent;
	/**操作类型 1查询，2添加，3修改，4删除,5导入，6导出）*/
	@Excel(name = "操作类型 1查询，2添加，3修改，4删除,5导入，6导出）", width = 15)
    private Integer operateType;
	/**操作用户账号*/
	@Excel(name = "操作用户账号", width = 15)
    private String account;
	/**当前登录用户账号类型   admin   user merchant*/
	@Excel(name = "当前登录用户账号类型   admin   user merchant", width = 15)
    private String userType;
	/**IP*/
	@Excel(name = "IP", width = 15)
    private String ip;
	/**city*/
	@Excel(name = "city", width = 15)
	private String city;
	/**操作系统os*/
	@Excel(name = "os", width = 15)
	private String os;

	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
}
