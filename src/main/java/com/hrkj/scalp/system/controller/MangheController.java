package com.hrkj.scalp.system.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.hrkj.scalp.util.UsedCode;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.system.entity.Manghe;
import com.hrkj.scalp.system.service.IMangheService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

 /**
 * @Description: 活动产品
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
@RestController
@RequestMapping("/manghe")
@Slf4j
public class MangheController extends JeecgController<Manghe, IMangheService> {
	@Autowired
	private IMangheService mangheService;

	@Autowired
	private ISysBaseAPI sysBaseAPI;


	/**
	 * 分页列表查询
	 *
	 * @param manghe
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Manghe manghe,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Manghe> queryWrapper = QueryGenerator.initQueryWrapper(manghe, req.getParameterMap());
		queryWrapper = sysBaseAPI.checkType(queryWrapper,req,4);
		if(queryWrapper == null) return Result.error("token为空");
		Page<Manghe> page = new Page<Manghe>(pageNo, pageSize);
		IPage<Manghe> pageList = mangheService.page(page, queryWrapper);
		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
	}

	 /**
	  * 分页列表查询
	  *
	  * @param manghe
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/listForApp")
	 public Result<?> listForApp(Manghe manghe,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<Manghe> queryWrapper = QueryGenerator.initQueryWrapper(manghe, req.getParameterMap());
		 queryWrapper.eq("status",1);
		 Page<Manghe> page = new Page<Manghe>(pageNo, pageSize);
		 IPage<Manghe> pageList = mangheService.page(page, queryWrapper);
		 return Result.ok(pageList.getRecords());
	 }


	 /**
	  * 批量或者单个商品上下架
	  * @param ids
	  * @param status
	  * @return
	  */
	@PostMapping(value = "lowerShop")
	public Result<?> lowerShop(String ids,Integer status) {
		List<String> list = Arrays.asList(ids.split(","));
		//下架
		if(UsedCode.BOOLEAN_FALSE == status){
			int count = mangheService.queryShopNum(list);
			if(count > 0){
				return Result.error("该商品还有"+count+"个库存尚未清空，无法下架");
			}
		}
		mangheService.updateBatch(list,status);
		return Result.ok("操作成功");
	}


	/**
	 *   添加
	 *
	 * @param manghe
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Manghe manghe) {
		mangheService.save(manghe);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param manghe
	 * @return
	 */
	@PostMapping(value = "/edit")
	public Result<?> edit(@RequestBody Manghe manghe) {
		mangheService.updateById(manghe);
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
		mangheService.removeById(id);
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
		this.mangheService.removeByIds(Arrays.asList(ids.split(",")));
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
		Manghe manghe = mangheService.getById(id);
		if(manghe==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(manghe);
	}


}
