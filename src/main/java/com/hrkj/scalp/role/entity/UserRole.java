package com.hrkj.scalp.role.entity;

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
 * @Description: farming_user_role
 * @Author: jeecg-boot
 * @Date:   2020-03-12
 * @Version: V1.0
 */
@Data
@TableName("farming_user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**roleId*/
//	@Excel(name = "roleId", width = 15)
    private String roleId;
	/**userId*/
	@Excel(name = "userId", width = 15)
    private String userId;

	public UserRole(String roleId, String userId) {
		this.roleId = roleId;
		this.userId = userId;
	}
}
