package com.hrkj.scalp.config.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.config.entity.Group;
import com.hrkj.scalp.config.service.IGroupService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

 /**
 * @Description: farming_group
 * @Author: jeecg-boot
 * @Date:   2020-03-17
 * @Version: V1.0
 */
@RestController
@RequestMapping("/group")
@Slf4j
public class GroupController extends JeecgController<Group, IGroupService> {
	@Autowired
	private IGroupService groupService;
	
	/**
	 * 分页列表查询
	 *
	 * @param group
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/list")
	public Result<?> queryPageList(Group group,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Group> queryWrapper = QueryGenerator.initQueryWrapper(group, req.getParameterMap());
		Page<Group> page = new Page<Group>(pageNo, pageSize);
		IPage<Group> pageList = groupService.page(page, queryWrapper);
		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
	}
	
	/**
	 *   添加
	 *
	 * @param group
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(Group group) {
		groupService.save(group);
		return Result.ok("添加成功！");
	}

	 /**
	  * 批量修改用户组
	  * @return
	  */
	 @PostMapping("editBatch")
	public Result<?> editBatch(String listStr){
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Group> groups=mapper.readValue(listStr,new TypeReference<List<Group>>() {});
			groupService.updateBatchById(groups);
			return Result.ok("修改成功！");
		} catch (Exception e) {
		    e.printStackTrace();
		    return Result.error("修改失败，请稍后再试！");
		}
	}

	
	/**
	 *  编辑
	 *
	 * @param group
	 * @return
	 */
	@PostMapping(value = "/edit")
	public Result<?> edit(Group group) {
		groupService.updateById(group);
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
		groupService.removeById(id);
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
		this.groupService.removeByIds(Arrays.asList(ids.split(",")));
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
		Group group = groupService.getById(id);
		if(group==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(group);
	}


}
