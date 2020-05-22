package com.hrkj.scalp.menu.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: farming_menu
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Data
@TableName("farming_menu")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysPermission implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**菜单ID*/
	@Excel(name = "菜单ID", width = 15)
	@TableId(type = IdType.ID_WORKER_STR)
    private String menuId;
	/**菜单名*/
	@Excel(name = "菜单名", width = 15)
    private String menuName;
	/**菜单路径*/
	@Excel(name = "菜单路径", width = 15)
    private String menuUrl;
	/**前端组件/路由*/
	@Excel(name = "前端组件/路由", width = 15)
	private String component;
	/**类型（0：一级菜单；1：子菜单 ；2：按钮权限）*/
	@Dict(dicCode = "menu_type")
	private Integer menuType;
	/**菜单图标*/
	private String icon;
	/**菜单权限编码*/
	private String perms;
	/**是否路由菜单: 0:不是  1:是（默认值1）*/
	@TableField(value="is_route")
	private boolean route;
	/**父节点ID*/
	@Excel(name = "父节点ID", width = 15)
    private String parentId;
	/**排序*/
	@Excel(name = "排序", width = 15)
    private Integer sort;
	/**保留字段*/
	@Excel(name = "保留字段", width = 15)
    private String reserve;
	/**是否隐藏路由菜单: 0否,1是（默认值0）*/
	private boolean hidden;
	/**一级菜单跳转地址*/
	private String redirect;

}
