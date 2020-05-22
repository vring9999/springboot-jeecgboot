package com.hrkj.scalp.merchant.controller;

import java.io.FileInputStream;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hrkj.scalp.shiro.vo.DefContants;
import com.hrkj.scalp.util.*;
import jdk.nashorn.internal.objects.annotations.Where;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.merchant.entity.QrCode;
import com.hrkj.scalp.merchant.service.IQrCodeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @Description: farming_qr_code
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/qrCode")
@Slf4j
public class QrCodeController extends JeecgController<QrCode, IQrCodeService> {
	@Autowired
	private IQrCodeService qrCodeService;

	@Autowired
	private ISysBaseAPI sysBaseAPI;
	
	/**
	 * 分页列表查询
	 *
	 * @param qrCode
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(QrCode qrCode,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<QrCode> queryWrapper = QueryGenerator.initQueryWrapper(qrCode, req.getParameterMap());
		log.info("***************开始获取二维码*************");
		log.info("查询的userId为：{}",qrCode.getUserId());
		queryWrapper = sysBaseAPI.checkType(queryWrapper,req,1);
		if(null == queryWrapper) return Result.error("token为空");
		boolean empty = StringUtil.checkObjAllFieldsIsNull(qrCode);
		//获取全部的二维码（不包括样板收款码和待审核的二维码）
		if(empty) {
			queryWrapper.ne("user_id",UsedCode.LOGIN_TYPE_ADMIN).ne("use_status",UsedCode.QR_STATUS_WAIT);
		}
		Page<QrCode> page = new Page<QrCode>(pageNo, pageSize);
		IPage<QrCode> pageList = qrCodeService.page(page, queryWrapper);
		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
	}



     /**
      * 上传文件到七牛云存储
      *
      * @return
      */
     @PostMapping("/upload")
     public Result<?> uploadImgQiniu(HttpServletRequest request) throws Exception {
     	int imgType = Integer.parseInt(request.getParameter("imgType"));
		 MultipartHttpServletRequest rq = (MultipartHttpServletRequest) request;
		 MultipartFile file = rq.getFile("file");
		 if (file == null || file.isEmpty()) {
			 return Result.error("文件为空");
		 }
         String src = "";
		 String name=System.currentTimeMillis()+file.getOriginalFilename();
		 FileInputStream inputStream = (FileInputStream) file.getInputStream();
		 ConstantQiniu constantQiniu=new ConstantQiniu();
		 if(imgType == 1){//1普通图片
			 src = constantQiniu.uploadQNImg(file);
		 }else if(imgType == 2){//2二维码
			 boolean flag = constantQiniu.testQr(inputStream);
			 if(flag)
				 src = constantQiniu.uploadQNImg(file);
			 else
				 return Result.error("解析失败，请检查上传文件是否为二维码");
		 } else
			 return Result.error("未知图片类型");
         return Result.okData(src);
     }


	/**
	 * 上传文件到七牛云存储
	 *
	 * @return
	 */
	@PostMapping("/uploadForApp")
	public Result<?> uploadForApp(HttpServletRequest request) throws Exception {
		String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
		if(StringUtil.isEmpty(token)) return Result.error("token 为空");

		MultipartHttpServletRequest rq = (MultipartHttpServletRequest) request;
		MultipartFile file = rq.getFile("file");
		if (file == null || file.isEmpty()) {
			return Result.error("文件为空");
		}
		FileInputStream inputStream = (FileInputStream) file.getInputStream();
		ConstantQiniu constantQiniu=new ConstantQiniu();
		boolean flag = constantQiniu.testQr(inputStream);
		if(!flag)
			return Result.error("解析失败，请检查上传文件是否为二维码");

		String src = constantQiniu.uploadQNImg(file);

		//获取收款码样板信息
		if(StringUtil.isEmpty(request.getParameter("qrId")))
			return Result.error("参数缺失");
		int qrId = Integer.parseInt(request.getParameter("qrId"));
		QrCode qrCode1 = qrCodeService.getById(qrId);
		if(null == qrCode1)  return Result.error("未知的收款码样本");
//		String userId = "111";
		String userId =  JwtUtil.getId(token);
//			BeanUtils.copyProperties(qrCode1, qrCode);
		QrCode qrCode = new QrCode();
		qrCode.setUserId(userId);
		qrCode.setQrUrl(src);
		qrCode.setMaxAccount(qrCode1.getMaxAccount());
		qrCode.setType(qrCode1.getType());
		qrCode.setUseStatus(UsedCode.QR_STATUS_WAIT);//待审核
		qrCodeService.save(qrCode);
		return Result.okData(src);
	}

	/**
	 * 管理员审核二维码
	 * @param id		二维码id
	 * @param useStatus	状态  0关闭   1开启   3驳回
	 * @return
	 */
	@PostMapping(value = "/editStatusByadmin")
	public Result<?> editStatusByadmin(String id, int useStatus,String remark) {
		try{
			QrCode qrCode = qrCodeService.getById(id);
			//管理员强制下线    驳回
			if(useStatus == UsedCode.QR_STATUS_CLOSE || useStatus == UsedCode.QR_STATUS_CANCEL){
				if(StringUtil.isEmpty(remark)) return  Result.error("请说明理由");
				qrCode.setAdminStatus(UsedCode.BOOLEAN_TRUE);
				qrCode.setAdminRemark(remark);
			}else if(useStatus == UsedCode.QR_STATUS_PASS){
				QueryWrapper<QrCode> queryWrapper = new QueryWrapper<QrCode>()
						.eq("user_id",qrCode.getUserId())
						.eq("type",qrCode.getType())
						.eq("use_status",UsedCode.QR_STATUS_PASS)
						.eq("max_account",qrCode.getMaxAccount());
				QrCode qrCode1 = qrCodeService.getOne(queryWrapper);
				if(null != qrCode1)
					return Result.error("已存在该金额的收款码");
				qrCode.setAdminStatus(UsedCode.BOOLEAN_FALSE);//管理员审核通过
			}
			qrCode.setUseStatus(useStatus);
			UpdateWrapper<QrCode> updateWrapper = new UpdateWrapper<QrCode>().eq("id", id);
			qrCodeService.update(qrCode,updateWrapper);
			return Result.ok("修改成功!");
		} catch (Exception e) {
			log.error("二维码审核：{}",e);
			return Result.error("修改失败，请稍后再试!");
		}
	}
	
	/**
	 *   admin添加样板的收款码
	 *   微信|支付宝 收款码中 每种金额的收款码只能有一个
	 * @param qrCode
	 * @return
	 */
    @AutoLog(value = "收款码--上传")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody QrCode qrCode) {
    	log.info("二维码上传参数:{}",qrCode.getMaxAccount());
        try {
        	if(StringUtil.isEmpty(qrCode.getType()) || qrCode.getMaxAccount() == 0)
				return Result.error("参数缺失");
        	QueryWrapper<QrCode> queryWrapper = new QueryWrapper<QrCode>()
					.eq("max_account",qrCode.getMaxAccount()).eq("type",qrCode.getType());
        	QrCode qrCode1 = qrCodeService.getOne(queryWrapper);
        	if(null != qrCode1)
				return Result.error("已存在当前金额种类的收款码");
			qrCode.setUserId(UsedCode.LOGIN_TYPE_ADMIN);
			if(StringUtil.isEmpty(qrCode.getQrUrl()))
				//使用默认图片展示地址
				qrCode.setQrUrl(UsedCode.SYSTEM_DEFAULT_QRCODE);
			if(qrCode.getMaxAccount() == 0){
				return Result.error("请输入合格的金额");
			}
            qrCodeService.save(qrCode);
            return Result.ok("添加成功！");
        } catch (Exception e) {
            log.error("二维码上传失败：{}",e);
            return Result.error("添加失败，请稍后再试！");
        }
    }


     /**
      * 修改轮询状态
      *
      * @param id     	 二维码id
      * @param pollStatus 是否轮询
      * @return
      */
     @PostMapping(value = "/editPollStatus")
     public Result<?> editPollStatus(String id, Integer pollStatus) {
         try {
             QrCode qrCode = qrCodeService.getById(id);
             qrCode.setPollStatus(pollStatus);
             qrCodeService.updateById(qrCode);
             return Result.ok("修改成功!");
         } catch (Exception e) {
			 log.error("修改轮询状态：{}",e);
             return Result.error("修改失败，请稍后再试!");
         }
     }

	 /**
	  * 修改二维码启用状态（码商/管理员）
	  * @param id
	  * @param userType
	  * @param useStatus
	  * @param remark
	  * @return
	  */
	 @PostMapping(value = "/editUseStatus")
     public Result<?> editUseStatus(String id,Integer userType,Integer useStatus,String remark){
		try {
			QrCode qrCode = qrCodeService.getById(id);
			if(userType==UsedCode.GET_SYSTEM){		//管理员
				if(useStatus==UsedCode.QR_STATUS_CLOSE)return Result.error("管理员关闭需要填写理由！");
				qrCode.setAdminStatus(useStatus);
			}else if(userType==UsedCode.GET_USER){	//码商
			}else{
				return Result.error("对不起，您无权修改！");
			}
			qrCode.setUseStatus(useStatus);
			qrCode.setRemark(remark);
			qrCode.setUpdateTime(new Date());
			qrCodeService.updateById(qrCode);
			return Result.ok("修改成功！");
		} catch (Exception e) {
			log.error("修改启动状态：{}",e);
			return Result.error("修改失败，请稍后再试!");
		}
	 }

     /**
      * 修改码商下所有二维码启用状态
      * @param userId		码商id
      * @param useStatus		状态  0关闭   1开启
      * @return
      */
     @PostMapping(value = "/editStatusAll")
     public Result<?> editStatusAll(String userId, int useStatus) {
         try{
             QrCode qrCode = new QrCode();
			 UpdateWrapper<QrCode> queryWrapper = new UpdateWrapper<QrCode>().eq("user_id", userId);
             //过滤被管理员强制下线 ，未通过审核的二维码
             if(useStatus == UsedCode.QR_STATUS_PASS){
				 queryWrapper.eq("admin_status",0).eq("use_status",0);
			 }
			 qrCode.setUseStatus(useStatus);
             qrCodeService.update(qrCode,queryWrapper);
             return Result.ok("修改成功!");
         } catch (Exception e) {
			 log.error("修改码商下所有二维码启用状态：{}",e);
             return Result.error("修改失败，请稍后再试!");
         }
     }



	/**
	 *  编辑
	 *
	 * @param qrCode
	 * @return
	 */
	@PostMapping(value = "/edit")
	public Result<?> edit(@RequestBody QrCode qrCode) {
		qrCodeService.updateById(qrCode);
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
		qrCodeService.removeById(id);
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
		this.qrCodeService.removeByIds(Arrays.asList(ids.split(",")));
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
		QrCode qrCode = qrCodeService.getById(id);
		if(qrCode==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(qrCode);
	}

//
//	@GetMapping(value = "/listForApp")
//	public Result<?> listForApp(QrCode qrCode,
//								@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//								@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//								HttpServletRequest req) {
//		QueryWrapper<QrCode> queryWrapper = QueryGenerator.initQueryWrapper(qrCode, req.getParameterMap()).select("id","qr_url","max_account");
//		queryWrapper.eq("user_id",UsedCode.LOGIN_TYPE_ADMIN);
//		Page<QrCode> page = new Page<QrCode>(pageNo, pageSize);
//		IPage<QrCode> pageList = qrCodeService.page(page, queryWrapper);
//		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
//	}

	 /**
	  * app端查询二维码列表
	  * @return
	  */
	 @GetMapping(value = "/listForApp")
	 public Result<?> listForApp(HttpServletRequest request) {
	 	 String tempType = request.getParameter("type");
		 String userId =  request.getParameter("userId");
		 if(StringUtil.isEmpty(userId) || StringUtil.isEmpty(tempType)){
			 return Result.error("参数缺失");
		 }
		 int type = Integer.parseInt(tempType);
		 QueryWrapper<QrCode> queryWrapper = new QueryWrapper<>();
		 queryWrapper.select("id","user_id","qr_url","max_account","use_status","admin_remark");
		 queryWrapper.eq("user_id",UsedCode.LOGIN_TYPE_ADMIN).or().eq("user_id",userId).eq("type",type);
		 List<QrCode> adminList = qrCodeService.list(queryWrapper);
		 QueryWrapper<QrCode> queryWrapper1 = new QueryWrapper<>();
		 queryWrapper1.select("id","max_account");
		 queryWrapper1.eq("user_id",userId).eq("type",type);
		 List<QrCode> userList = qrCodeService.list(queryWrapper1);
		 Set<Integer> remove = new HashSet<>();
//		 List<Integer> remove = new ArrayList<>();
		 for(int i = 0; i < adminList.size(); i ++){
			 for(int j = 0; j < userList.size(); j ++){
				if(adminList.get(i).getUserId().equals(UsedCode.LOGIN_TYPE_ADMIN)
						&& adminList.get(i).getMaxAccount() == userList.get(j).getMaxAccount()){
					remove.add(i);
				}
			 }
		 }
		 for(Integer index: remove) adminList.remove(index);

		 HashSet temp = new HashSet();
		 temp.addAll(adminList);
		 adminList.clear();
		 adminList.addAll(temp);

//		 List<Map<String,Object>> list = qrCodeService.listForApp(userId,type);
//		return Result.ok(list);
		 return Result.okData(adminList);
	 }


}
