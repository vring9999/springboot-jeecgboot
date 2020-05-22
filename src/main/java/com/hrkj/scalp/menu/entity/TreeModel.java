package com.hrkj.scalp.menu.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 树形列表用到
 */
public class TreeModel implements Serializable {

	private static final long serialVersionUID = 4013193970046502756L;

	private String key;

	private String title;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private List<TreeModel> children;

	public List<TreeModel> getChildren() {
		return children;
	}

	public void setChildren(List<TreeModel> children) {
		this.children = children;
	}

	public TreeModel() {

	}

	public TreeModel(SysPermission permission) {
		this.key = permission.getMenuId();
		this.parentId = permission.getParentId();
		this.title = permission.getMenuName();
		this.value = permission.getMenuId();
		this.children = new ArrayList<TreeModel>();
	}

	public TreeModel(String key,String parentId,String title) {
		this.key = key;
		this.parentId = parentId;
		this.title =  title;
		this.value = key;
		this.children = new ArrayList<TreeModel>();
	}

	private String parentId;

	private String value;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}


}
