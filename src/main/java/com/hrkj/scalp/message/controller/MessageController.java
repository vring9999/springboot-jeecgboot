package com.hrkj.scalp.message.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hrkj.scalp.util.UsedCode;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.message.entity.Message;
import com.hrkj.scalp.message.service.IMessageService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

 /**
 * @Description: 消息公告
 * @Author: jeecg-boot
 * @Date:   2020-03-30
 * @Version: V1.0
 */
@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController extends JeecgController<Message, IMessageService> {
	@Autowired
	private IMessageService messageService;

	 @Autowired
	 private ISysBaseAPI sysBaseAPI;

//	 @Autowired
//	 private SendMassageService sendMassageService;
	
	/**
	 * 分页列表查询
	 *
	 * @param message
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Message message,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Message> queryWrapper = QueryGenerator.initQueryWrapper(message, req.getParameterMap());
		queryWrapper = sysBaseAPI.checkType(queryWrapper,req,3);
		if(null == queryWrapper) return Result.error("token为空");
		//商户或者码商查看个人和自己所属loginType的
		Page<Message> page = new Page<Message>(pageNo, pageSize);
		IPage<Message> pageList = messageService.page(page, queryWrapper);
		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
	}

	 /**
	  * app端分页列表查询
	  *
	  * @param message
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/listForApp")
	 public Result<?> listForApp(Message message,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<Message> queryWrapper = QueryGenerator.initQueryWrapper(message, req.getParameterMap());
		 queryWrapper = sysBaseAPI.checkType(queryWrapper,req,3);
		 if(null == queryWrapper) return Result.error("token失效");
		 //商户或者码商查看个人和自己所属loginType的
		 Page<Message> page = new Page<Message>(pageNo, pageSize);
		 IPage<Message> pageList = messageService.page(page, queryWrapper);
		 return Result.ok(pageList.getRecords());
	 }

	 /**
	  * app端获取消息总条数
	  * @param userId
	  * @return
	  */
	@GetMapping(value = "/count")
	public Result<?> queryPageList(String userId){
		QueryWrapper<Message> queryWrapper = new QueryWrapper<Message>()
				.eq("user_id", userId)
				.or().eq("send_type","user")
				.or().eq("send_type", UsedCode.LOGIN_TYPE_FULL);
		List<Message> list = messageService.list(queryWrapper);
		return Result.okData(list.size());
	}



	/**
	 *   添加
	 *
	 * @param message
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Message message) {
		messageService.save(message);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param message
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Message message) {
		messageService.updateById(message);
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
		messageService.removeById(id);
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
		this.messageService.removeByIds(Arrays.asList(ids.split(",")));
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
		Message message = messageService.getById(id);
		if(message==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(message);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param message
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Message message) {
        return super.exportXls(request, message, Message.class, "消息公告");
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
        return super.importExcel(request, response, Message.class);
    }

}
