package com.hrkj.scalp.system.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.system.entity.SystemLog;
import com.hrkj.scalp.system.service.ISystemLogService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

 /**
 * @Description: farming_system_log
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/systemLog")
@Slf4j
public class SystemLogController extends JeecgController<SystemLog, ISystemLogService> {
	@Autowired
	private ISystemLogService systemLogService;

	 @Autowired
	 private ISysBaseAPI sysBaseAPI;
	
	/**
	 * 分页列表查询
	 *
	 * @param systemLog
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SystemLog systemLog,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SystemLog> queryWrapper = QueryGenerator.initQueryWrapper(systemLog, req.getParameterMap());
		//查询数据限制  管理员无限制查询 码商/商户查询个人数据
		queryWrapper = sysBaseAPI.checkType(queryWrapper,req,2);
		if(null == queryWrapper) return Result.error("token失效");
		Page<SystemLog> page = new Page<SystemLog>(pageNo, pageSize);
		IPage<SystemLog> pageList = systemLogService.page(page, queryWrapper);
		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
	}
	
	/**
	 *   添加
	 *
	 * @param systemLog
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SystemLog systemLog) {
		systemLogService.save(systemLog);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param systemLog
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SystemLog systemLog) {
		systemLogService.updateById(systemLog);
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
		systemLogService.removeById(id);
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
		this.systemLogService.removeByIds(Arrays.asList(ids.split(",")));
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
		SystemLog systemLog = systemLogService.getById(id);
		if(systemLog==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(systemLog);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param systemLog
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SystemLog systemLog) {
        return super.exportXls(request, systemLog, SystemLog.class, "farming_system_log");
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
        return super.importExcel(request, response, SystemLog.class);
    }

}
