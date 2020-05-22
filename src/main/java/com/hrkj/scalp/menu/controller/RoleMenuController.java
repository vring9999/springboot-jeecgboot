package com.hrkj.scalp.menu.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.menu.entity.RoleMenu;
import com.hrkj.scalp.menu.service.IRoleMenuService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

 /**
 * @Description: farming_role_menu
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@RestController
@RequestMapping("/roleMenu")
@Slf4j
public class RoleMenuController extends JeecgController<RoleMenu, IRoleMenuService> {
	@Autowired
	private IRoleMenuService roleMenuService;


	
	/**
	 * 分页列表查询
	 *
	 * @param roleMenu
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(RoleMenu roleMenu,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<RoleMenu> queryWrapper = QueryGenerator.initQueryWrapper(roleMenu, req.getParameterMap());
		Page<RoleMenu> page = new Page<RoleMenu>(pageNo, pageSize);
		IPage<RoleMenu> pageList = roleMenuService.page(page, queryWrapper);
		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
	}


	 /**
	  * 查询角色已分配的菜单id
	  *
	  * @return
	  */
	 @RequestMapping(value = "/queryRolePermission", method = RequestMethod.GET)
	 public Result<List<String>> queryRolePermission(@RequestParam(name = "roleId", required = true) String roleId) {
		 Result<List<String>> result = new Result<>();
		 try {
			 List<RoleMenu> list = roleMenuService.list(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId, roleId));
			 List<String> stringList =new ArrayList<>();
					 //users.stream().map(UserEntity::getUserName).collect(Collectors.toList());
					 //list.stream().map(RoleMenu -> RoleMenu.getMenuId()).collect(Collectors.toList())

			 list.forEach(menu-> stringList.add(menu.getMenuId()));
			 result.setResult(stringList);
//			 result.setResult(list.stream().map(RoleMenu -> RoleMenu.getMenuId()).collect(Collectors.toList()));
			 result.setSuccess(true);
		 } catch (Exception e) {
			 log.error(e.getMessage(), e);
		 }
		 return result;
	 }

	 /**
	  * 给角色分配菜单
	  *
	  * @return
	  */
	 @RequestMapping(value = "/saveRolePermission", method = RequestMethod.POST)
	 public Result<String> saveRolePermission(@RequestBody JSONObject json) {
		 long start = System.currentTimeMillis();
		 Result<String> result = new Result<>();
		 try {
//			 Integer roleId = json.getInteger("roleId");
			 String roleId = json.getString("roleId");
			 String newMenuIds = json.getString("newMenuIds");//新分配的菜单id列表
			 String lastMenuIds = json.getString("lastMenuIds");//旧菜单id列表
			 log.info("saveRolePermission--->newMenuIds");
			 this.roleMenuService.saveRolePermission(roleId, newMenuIds, lastMenuIds);
			 result.success("保存成功！");
			 log.info("======角色授权成功=====耗时:" + (System.currentTimeMillis() - start) + "毫秒");
		 } catch (Exception e) {
			 result.error500("授权失败！");
			 log.error(e.getMessage(), e);
		 }
		 return result;
	 }

	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		roleMenuService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.roleMenuService.removeByIds(Arrays.asList(ids.split(",")));
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
		RoleMenu roleMenu = roleMenuService.getById(id);
		if(roleMenu==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(roleMenu);
	}


}
