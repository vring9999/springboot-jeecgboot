package com.hrkj.scalp.stock.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 码商库存
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
@Data
@TableName("farming_user_stock")
public class UserStock implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**码商id*/
	@Excel(name = "码商id", width = 15)
    private String userId;
	/**商品id*/
	@Excel(name = "商品id", width = 15)
    private String mId;
	/**库存*/
	@Excel(name = "总库存", width = 15)
    private Integer num;
	/**订单库存冻结*/
	@Excel(name = "订单库存冻结", width = 15)
    private Integer orderFrozen;
	/**充值库存冻结*/
	@Excel(name = "充值库存冻结", width = 15)
    private Integer numFrozen;
	/**今日已售*/
	@Excel(name = "今日已售", width = 15)
	private Integer dayCount;
	/**备用*/
	@Excel(name = "备用", width = 15)
    private String remark;

	public UserStock(String userId, String mId, Integer numFrozen) {
		this.userId = userId;
		this.mId = mId;
		this.numFrozen = numFrozen;
	}
}
