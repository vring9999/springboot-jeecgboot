package com.hrkj.scalp.user.entity;

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
 * @Description: 保留
 * @Author: jeecg-boot
 * @Date:   2020-03-17
 * @Version: V1.0
 */
@Data
@TableName("farming_user_per")
public class UserPer implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
	/**userId*/
	@Excel(name = "userId", width = 15)
    private String userId;
	/**groupName*/
	@Excel(name = "groupName", width = 15)
    private String groupName;
	/**分配给下级的利润比例*/
	@Excel(name = "分配给下级的利润比例", width = 15)
    private Double sonPer;
	/**码商可截留的利润比例*/
	@Excel(name = "码商可截留的利润比例", width = 15)
    private Double userPer;
	/**码商总的返利比例*/
	@Excel(name = "码商总返利比例", width = 15)
    private Double countPer;
	/**邀请码*/
	@Excel(name = "邀请码", width = 15)
    private String code;
	/**二维码展示路径*/
	@Excel(name = "二维码展示路径", width = 15)
    private String url;

	public UserPer() {
	}

	public UserPer(String id, String userId, String groupName, Double sonPer, Double userPer, Double countPer, String code, String url) {
		this.id = id;
		this.userId = userId;
		this.groupName = groupName;
		this.sonPer = sonPer;
		this.userPer = userPer;
		this.countPer = countPer;
		this.code = code;
		this.url = url;
	}
}
