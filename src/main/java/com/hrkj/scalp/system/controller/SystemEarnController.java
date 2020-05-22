package com.hrkj.scalp.system.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import com.hrkj.scalp.system.entity.SystemEarn;
import com.hrkj.scalp.system.service.ISystemEarnService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: farming_system_earn
 * @Author: jeecg-boot
 * @Date:   2020-03-24
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sys/systemEarn")
@Slf4j
public class SystemEarnController extends JeecgController<SystemEarn, ISystemEarnService> {
	@Autowired
	private ISystemEarnService systemEarnService;
	
	/**
	 * 分页列表查询
	 *
	 * @param systemEarn
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SystemEarn systemEarn,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SystemEarn> queryWrapper = QueryGenerator.initQueryWrapper(systemEarn, req.getParameterMap());
		Page<SystemEarn> page = new Page<SystemEarn>(pageNo, pageSize);
		IPage<SystemEarn> pageList = systemEarnService.page(page, queryWrapper);
		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
	}
	
	/**
	 *   添加
	 *
	 * @param systemEarn
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SystemEarn systemEarn) {
		systemEarnService.save(systemEarn);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param systemEarn
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SystemEarn systemEarn) {
		systemEarnService.updateById(systemEarn);
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
		systemEarnService.removeById(id);
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
		this.systemEarnService.removeByIds(Arrays.asList(ids.split(",")));
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
		SystemEarn systemEarn = systemEarnService.getById(id);
		if(systemEarn==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(systemEarn);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param systemEarn
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SystemEarn systemEarn) {
        return super.exportXls(request, systemEarn, SystemEarn.class, "farming_system_earn");
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
        return super.importExcel(request, response, SystemEarn.class);
    }

}
