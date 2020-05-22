package com.hrkj.scalp.system.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hrkj.scalp.util.UsedCode;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.system.entity.SystemUser;
import com.hrkj.scalp.system.service.ISystemUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

 /**
 * @Description: farming_system_user
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@RestController
@RequestMapping("/system")
@Slf4j
public class SystemUserController extends JeecgController<SystemUser, ISystemUserService> {
	@Autowired
	private ISystemUserService systemUserService;
	
	/**
	 * 系统用户列表查询
	 *
	 * @param systemUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SystemUser systemUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SystemUser> queryWrapper = QueryGenerator.initQueryWrapper(systemUser, req.getParameterMap());
		Page<SystemUser> page = new Page<SystemUser>(pageNo, pageSize);
		queryWrapper.select(SystemUser.class, i -> !i.getColumn().equals("password")&& !i.getColumn().equals("safe_code"));
		IPage<SystemUser> pageList = systemUserService.page(page, queryWrapper);
		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
	}
	
	/**
	 *   添加
	 *
	 * @param systemUser
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SystemUser systemUser) {
		systemUser.setPassword(UsedCode.SALT_MD5_PWD);
		systemUser.setSafeCode(UsedCode.SALT_MD5_PWD);
		systemUserService.save(systemUser);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param systemUser
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SystemUser systemUser) {
		systemUserService.updateById(systemUser);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@PostMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		systemUserService.removeById(id);
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
		this.systemUserService.removeByIds(Arrays.asList(ids.split(",")));
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
		SystemUser systemUser = systemUserService.getById(id);
		if(systemUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(systemUser);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param systemUser
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SystemUser systemUser) {
        return super.exportXls(request, systemUser, SystemUser.class, "farming_system_user");
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
        return super.importExcel(request, response, SystemUser.class);
    }

}
