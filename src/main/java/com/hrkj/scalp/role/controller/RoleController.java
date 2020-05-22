package com.hrkj.scalp.role.controller;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hrkj.scalp.menu.entity.SysPermission;
import com.hrkj.scalp.menu.entity.TreeModel;
import com.hrkj.scalp.menu.service.SysPermissionService;
import com.hrkj.scalp.role.entity.UserRole;
import com.hrkj.scalp.role.service.IUserRoleService;
import com.hrkj.scalp.util.StringUtil;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.role.entity.Role;
import com.hrkj.scalp.role.service.IRoleService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;

import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

 /**
 * @Description: farming_role
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController extends JeecgController<Role, IRoleService> {
	@Autowired
	private IRoleService roleService;
	 @Autowired
	 private IUserRoleService userRoleService;
	 @Autowired
	 private SysPermissionService sysPermissionService;
	
	/**
	 * 分页列表查询
	 *
	 * @param role
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Role role,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Role> queryWrapper = QueryGenerator.initQueryWrapper(role, req.getParameterMap());
		Page<Role> page = new Page<Role>(pageNo, pageSize);
		IPage<Role> pageList = roleService.page(page, queryWrapper);
		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
	}

	/**
	 *   添加
	 *
	 * @param role
	 * @return
	 */
	@AutoLog(value = "权限角色---添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Role role) {
		if(StringUtil.isEmpty(role.getRoleName())){
			return Result.error("参数缺失");
		}
		roleService.save(role);
		return Result.ok("添加成功！");
	}

	 /**
	  * 给用户绑定角色
	  *
	  * @param
	  * @return
	  */
	 @AutoLog(value = "用户绑定角色")
	 @RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
	 public Result<?> addSysUserRole(String roleId,String  userId) {
		 try {
			 UserRole sysUserRole = new UserRole(roleId,userId);
			 QueryWrapper<UserRole> queryWrapper = new QueryWrapper<UserRole>();
			 queryWrapper.eq("role_id", roleId).eq("user_id",userId);
			 UserRole one = userRoleService.getOne(queryWrapper);
			 if(one==null){
				 userRoleService.save(sysUserRole);
			 }else return Result.error("该用户已拥有当前选择角色");
			 return Result.ok("绑定成功！");
		 }catch(Exception e) {
			 log.error(e.getMessage(), e);
			 return Result.ok("操作失败");
		 }
	 }


	/**
	  * 给用户绑定角色   可批量设置用户
	  *
	  * @param
	  * @return
	  */
/*	 @RequestMapping(value = "/addSysUserRole", method = RequestMethod.POST)
	 public Result<String> addSysUserRole(Integer roleId,String  userIds) {
		 Result<String> result = new Result<String>();
		 try {
			 List<String> list = Arrays.asList(userIds.split(","));
			 for(String userId:list) {
				 UserRole sysUserRole = new UserRole(roleId,userId);
				 QueryWrapper<UserRole> queryWrapper = new QueryWrapper<UserRole>();
				 queryWrapper.eq("role_id", roleId).eq("user_id",userId);
				 UserRole one = userRoleService.getOne(queryWrapper);
				 if(one==null){
					 userRoleService.save(sysUserRole);
				 }
			 }
			 result.setMessage("添加成功!");
			 result.setSuccess(true);
			 return result;
		 }catch(Exception e) {
			 log.error(e.getMessage(), e);
			 result.setSuccess(false);
			 result.setMessage("出错了: " + e.getMessage());
			 return result;
		 }
	 }*/




	 /**
	  * 获取全部的菜单树
	  * @param
	  * @return
	  */
	 @RequestMapping(value = "/queryTreeList", method = RequestMethod.GET)
	 public Result<Map<String,Object>> queryTreeList() {
		 Result<Map<String,Object>> result = new Result<>();
		 //全部权限ids
		 List<String> ids = new ArrayList<>();
		 try {
			 LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<SysPermission>();
			 query.orderByAsc(SysPermission::getSort);
			 List<SysPermission> list = sysPermissionService.list(query);
			 for(SysPermission sysPer : list) {
				 ids.add(sysPer.getMenuId());
			 }
			 List<TreeModel> treeList = new ArrayList<>();
			 getTreeModelList(treeList, list, null);
			 Map<String,Object> resMap = new HashMap<String,Object>();
			 resMap.put("treeList", treeList); //全部树节点数据
			 resMap.put("ids", ids);//全部树ids
			 result.setResult(resMap);
			 result.setSuccess(true);
		 } catch (Exception e) {
			 log.error(e.getMessage(), e);
		 }
		 return result;
	 }

	 /**
	  * 处理菜单树
	  * @param treeList 菜单树对象
	  * @param metaList  所有菜单
	  * @param temp   菜单临时父对象
	  */
	 private void getTreeModelList(List<TreeModel> treeList,List<SysPermission> metaList,TreeModel temp) {
		 for (SysPermission permission : metaList) {
			 String tempPid = permission.getParentId();
			 TreeModel tree = new TreeModel(permission.getMenuId(), tempPid, permission.getMenuName());
			 if(temp==null && oConvertUtils.isEmpty(tempPid)) {
				 treeList.add(tree);
//				 if(!tree.getIsLeaf()) {
					 getTreeModelList(treeList, metaList, tree);
//				 }
			 }else if(temp!=null && oConvertUtils.isNotEmpty(tempPid) && tempPid.equals(temp.getKey())){
				 temp.getChildren().add(tree);
//				 if(!tree.getIsLeaf()) {
					 getTreeModelList(treeList, metaList, tree);
//				 }
			 }
		 }
	 }

	
	/**
	 *  编辑
	 *
	 * @param role
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Role role) {
		roleService.updateById(role);
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
		roleService.removeById(id);
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
		this.roleService.removeByIds(Arrays.asList(ids.split(",")));
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
		Role role = roleService.getById(id);
		if(role==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(role);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param role
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Role role) {
        return super.exportXls(request, role, Role.class, "farming_role");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Role.class);
    }

}
