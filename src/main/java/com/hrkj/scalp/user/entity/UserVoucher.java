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
 * @Description: 优惠券抵扣记录
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
@Data
@TableName("farming_user_voucher")
public class UserVoucher implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**抵扣记录id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private Integer id;
	/**userId*/
	@Excel(name = "userId", width = 15)
    private String userId;
	/**充值订单id*/
	@Excel(name = "充值订单id", width = 15)
    private String accountId;
	/**本次抵扣金额*/
	@Excel(name = "本次抵扣金额", width = 15)
    private Integer voucherMoney;
	/**剩余抵扣券*/
	@Excel(name = "剩余抵扣券", width = 15)
    private Integer afterMoney;
	/**createTime*/
	@Excel(name = "createTime", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
	/**remark*/
	@Excel(name = "remark", width = 15)
    private String remark;
}
