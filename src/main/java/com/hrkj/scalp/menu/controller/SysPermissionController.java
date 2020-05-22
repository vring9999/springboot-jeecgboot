package com.hrkj.scalp.menu.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrkj.scalp.menu.entity.SysPermission;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.menu.service.SysPermissionService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

 /**
 * @Description: farming_menu
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@RestController
@RequestMapping("/menu")
@Slf4j
public class SysPermissionController extends JeecgController<SysPermission, SysPermissionService> {
	@Autowired
	private SysPermissionService sysPermissionService;
	
	 /**
	  * 查询用户拥有的菜单权限和按钮权限（根据TOKEN）
	  *
	  * @return
	  */
	 @GetMapping(value = "/getUserPermissionByToken")
	 public Result<?> getUserPermissionByToken(@RequestParam(name = "token", required = true) String token) {
		 Result<JSONObject> result = new Result<JSONObject>();
		 try {
			 if (oConvertUtils.isEmpty(token)) {
				 return Result.error("TOKEN不允许为空！");
			 }
			 log.info(" ------ 通过令牌获取用户拥有的访问菜单 ---- TOKEN ------ " + token);
			 String username = JwtUtil.getUsername(token);
			 //根据账号获取用户用户的所有菜单
			 List<SysPermission> metaList = sysPermissionService.queryByUser(username);
			 JSONObject json = new JSONObject();
			 JSONArray menujsonArray = new JSONArray();
			 this.getPermissionJsonArray(menujsonArray, metaList, null);
			 //路由菜单
			 json.put("menu", menujsonArray);
			 result.setResult(json);
			 result.success("查询成功");
		 } catch (Exception e) {
			 result.error500("查询失败:" + e.getMessage());
			 log.error(e.getMessage(), e);
		 }
		 return result;
	 }
	 /**
	  *  获取菜单JSON数组
	  * @param jsonArray
	  * @param metaList
	  * @param parentJson
	  */
	 private void getPermissionJsonArray(JSONArray jsonArray, List<SysPermission> metaList, JSONObject parentJson) {
		 for (SysPermission permission : metaList) {
		     String name = permission.getMenuName();
			 if (permission.getMenuType() == null) {
				 continue;
			 }
			 String tempPid = permission.getParentId();
			 JSONObject json = getPermissionJsonObject(permission);
			 if(json==null) {
				 continue;
			 }
			 //如果父json为空 并且 父菜单id为空  即为一级菜单  直接添加到数组中
			 if (parentJson == null && oConvertUtils.isEmpty(tempPid)) {
				 jsonArray.add(json);
//				 if (!permission.isLeaf()) {
					 getPermissionJsonArray(jsonArray, metaList, json);
//				 }
			 } else if (parentJson != null && oConvertUtils.isNotEmpty(tempPid) && tempPid.equals(parentJson.getInteger("id"))) {
				 // 类型( 0：一级菜单 1：子菜单 2：按钮 )
				 if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2)) {
					 JSONObject metaJson = parentJson.getJSONObject("meta");
					 if (metaJson.containsKey("permissionList")) {
						 metaJson.getJSONArray("permissionList").add(json);
					 } else {
						 JSONArray permissionList = new JSONArray();
						 permissionList.add(json);
						 metaJson.put("permissionList", permissionList);
					 }
					 // 类型( 0：一级菜单 1：子菜单 2：按钮 )
				 } else if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_1) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_0)) {
					 if (parentJson.containsKey("children")) {
						 parentJson.getJSONArray("children").add(json);
					 } else {
						 JSONArray children = new JSONArray();
						 children.add(json);
						 parentJson.put("children", children);
					 }
				 }
			 }

		 }
	 }

	 /**
	  * 根据菜单配置生成路由json
	  * @param permission
	  * @return
	  */
	 private JSONObject getPermissionJsonObject(SysPermission permission) {
		 JSONObject json = new JSONObject();
		 // 类型(0：一级菜单 1：子菜单 2：按钮)
		 if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2)) {
			 return null;
		 } else if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_0) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_1)) {
			 json.put("id", permission.getMenuId());
			 //存放menu属性
			 json.put("component", permission.getComponent());
             json.put("icon",permission.getIcon());
             json.put("title", permission.getMenuName());
             json.put("url",permission.getMenuUrl());
             json.put("redirect",permission.getRedirect());
		 }
		 return json;
	 }




	/**
	 *   添加
	 *
	 * @param sysPermission
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(SysPermission sysPermission) {
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("perms",sysPermission.getPerms());
		SysPermission sysPermission1 = sysPermissionService.getOne(queryWrapper);
		if(null == sysPermission1){
			sysPermissionService.save(sysPermission);
			return Result.ok("添加成功！");
		}else{
			return Result.error("已存在该编码的菜单");
		}

	}
	
	/**
	 *  编辑
	 *
	 * @param sysPermission
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SysPermission sysPermission) {
		sysPermissionService.updateById(sysPermission);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		sysPermissionService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.sysPermissionService.deletes(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SysPermission sysPermission = sysPermissionService.getById(id);
		if(sysPermission ==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(sysPermission);
	}


}
