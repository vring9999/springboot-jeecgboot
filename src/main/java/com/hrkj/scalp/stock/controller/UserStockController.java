package com.hrkj.scalp.stock.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hrkj.scalp.user.service.IUserService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.stock.entity.UserStock;
import com.hrkj.scalp.stock.service.IUserStockService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

 /**
 * @Description: 码商库存
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
@RestController
@RequestMapping("/stock")
@Slf4j
public class UserStockController extends JeecgController<UserStock, IUserStockService> {
	@Autowired
	private IUserStockService userStockService;

	/**
	 * 分页列表查询
	 *
	 * @param userStock
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(UserStock userStock,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<UserStock> queryWrapper = QueryGenerator.initQueryWrapper(userStock, req.getParameterMap());
		Page<UserStock> page = new Page<UserStock>(pageNo, pageSize);
		IPage<UserStock> pageList = userStockService.page(page, queryWrapper);
		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
	}

	/**
     * app端获取码商库存信息
     * @param userId
     * @return
     */
    @GetMapping(value = "queryUserStock")
    public Result<?> queryUserStock(String userId){
		List<Map<String,Object>> list = userStockService.queryUserStock(userId);
        return Result.ok(list);
    }

	
	/**
	 *   添加
	 *
	 * @param userStock
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody UserStock userStock) {
		userStockService.save(userStock);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param userStock
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody UserStock userStock) {
		userStockService.updateById(userStock);
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
		userStockService.removeById(id);
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
		this.userStockService.removeByIds(Arrays.asList(ids.split(",")));
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
		UserStock userStock = userStockService.getById(id);
		if(userStock==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(userStock);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param userStock
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserStock userStock) {
        return super.exportXls(request, userStock, UserStock.class, "码商库存");
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
        return super.importExcel(request, response, UserStock.class);
    }

}
