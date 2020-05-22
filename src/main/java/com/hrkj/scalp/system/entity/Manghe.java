package com.hrkj.scalp.system.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 产品库存
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
@Data
@TableName("farming_manghe")
public class Manghe implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
    private String id;
	/**name*/
	@Excel(name = "name", width = 15)
    private String name;
	/**缩略图*/
	@Excel(name = "缩略图", width = 15)
    private String icon;
	/**大图*/
	@Excel(name = "大图", width = 15)
    private String bigIcon;
	/**商品描述*/
	@Excel(name = "商品描述", width = 15)
    private String shopDescribe;
	/**上架状态   1上架  0下架*/
	@Excel(name = "上架状态   1上架  0下架", width = 15)
    private Integer status;
	/**可赚金额*/
	private Integer profit;
	/**售价*/
	@Excel(name = "售价", width = 15)
    private Integer price;
	/**特供价*/
	@Excel(name = "特供价", width = 15)
    private Integer specialPrice;
	/**remark*/
	@Excel(name = "remark", width = 15)
    private String remark;
	/**remark1*/
	@Excel(name = "remark1", width = 15)
    private String remark1;
}
