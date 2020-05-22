package com.hrkj.scalp.config.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hrkj.scalp.config.service.IConfigService;
import com.hrkj.scalp.util.CacheInfo;
import com.hrkj.scalp.util.StringUtil;
import com.hrkj.scalp.util.UsedCode;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.config.entity.Config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: instead_common_config
 * @Author: jeecg-boot   D:\comm-jeecg\hrkj
 * @Date:   2020-02-28
 * @Version: V1.0
 */
@RestController
@RequestMapping("/config/config")
@Slf4j
public class ConfigController extends JeecgController<Config, IConfigService> {
	@Autowired
	private IConfigService configService;

	@Autowired
	private static CacheInfo cacheInfo;

	@Autowired
	public ConfigController(CacheInfo cacheInfo){
		ConfigController.cacheInfo = cacheInfo;
	}

	 /**
	  * 根据配置名进行查询
	  *
	  * @param cfgName
	  * @return
	  */
	 @GetMapping(value = "/queryByName")
	 public Result<?> queryByName(String cfgName) {
		 int count = configService.queryByName(cfgName);
		 return Result.ok(count);
	 }
	
	/**
	 * 分页列表查询
	 *
	 * @param config
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Config config,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Config> queryWrapper = QueryGenerator.initQueryWrapper(config, req.getParameterMap());
		Page<Config> page = new Page<Config>(pageNo, pageSize);
		IPage<Config> pageList = configService.page(page, queryWrapper);
		return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
	}
	
	/**
	 *   添加
	 *
	 * @param config
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Config config) {
		configService.save(config);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param config
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Config config) {
		configService.updateById(config);
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
		Config commonConfig=configService.getById(id);
		String key=commonConfig.getCfgName()+"+"+commonConfig.getCfgKey();
		CacheInfo.commonInfo.remove(key);
		configService.removeById(id);
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
		this.configService.removeByIds(Arrays.asList(ids.split(",")));
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
		Config config = configService.getById(id);
		if(config==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(config);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param config
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Config config) {
        return super.exportXls(request, config, Config.class, "instead_common_config");
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
        return super.importExcel(request, response, Config.class);
    }

	/**
	 * 查询配置信息
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryCommon", method = RequestMethod.POST)
	@ResponseBody
	public Result<?> queryCommon(Config config, Integer page, Integer limit,HttpServletRequest req){
		try {
			QueryWrapper<Config> queryWrapper = QueryGenerator.initQueryWrapper(config, req.getParameterMap());
			Page<Config> pages = new Page<Config>(page, limit);
			IPage<Config> pageList = configService.page(pages, queryWrapper);

			for(Config com:pageList.getRecords()){
				if(com.getCfgType()==UsedCode.SECRET_KEY){  //如果是秘钥--解密
					com.setCfgValue(UsedCode.GET_SECRET);
				}
			}
			return Result.ok(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("{}",e);
			return Result.error("查询失败，请稍后再试！");
		}
	}

	/**
	 * 添加配置信息
	 * @param commonConfig
	 * @return
	 */
	@PostMapping(value = "/addCommon")
	@ResponseBody
	public Result<?> addCommon(Config commonConfig){
		try {
			if(commonConfig.getCfgType()== UsedCode.SECRET_KEY){  //如果是秘钥--解密
				//commonConfig.setCfgValue(MD5Util.getPwd(commonConfig.getCfgValue()));
			}
			commonConfig.setId(StringUtil.getUuid());
			configService.save(commonConfig);
//            updCacheToComm();
			CacheInfo.commonInfo.put(commonConfig.getCfgName()+"+"+commonConfig.getCfgKey(),commonConfig.getCfgValue());
			return Result.ok("添加成功！");
		} catch (Exception e) {
			log.error("{}",e);
			return Result.error("添加失败，请稍后再试！");
		}
	}


	/**
	 * 修改配置信息
	 * @param commonConfig
	 * @return
	 */
	@PostMapping(value = "/updCommon")
	@ResponseBody
	public Result<?> updCommon(Config commonConfig){
		try {
			if(commonConfig.getCfgType()==UsedCode.SECRET_KEY&&!commonConfig.getCfgValue().equals(UsedCode.GET_SECRET)){  //如果是秘钥--解密
				//commonConfig.setCfgValue(MD5Util.getPwd(commonConfig.getCfgValue()));
			}else if(commonConfig.getCfgType()!=UsedCode.SECRET_KEY&&commonConfig.getCfgValue().equals(UsedCode.GET_SECRET)){  //如果不是秘钥并且返回的值是“**********”
				Config comm=configService.getById(commonConfig.getId());
				commonConfig.setCfgValue(comm.getCfgValue());
			}
			configService.updateById(commonConfig);
			updCacheToComm();
			return Result.ok("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("{}",e);
			return Result.error("修改失败，请稍后确认参数值！");
		}
	}





	/**
	 * 更新配置信息，配置信息更改后缓存信息也要相应修改
	 * @throws Exception
	 */
	private void updCacheToComm() throws Exception {
		log.info("更新配置信息！");
		cacheInfo.commonInfo.clear();   //清空原数据
		List<Config> commons = configService.list();
		commons.forEach(comm -> CacheInfo.commonInfo.put(comm.getCfgName()+"+"+comm.getCfgKey(),comm.getCfgValue()));
		log.info("当前配置信息为：{}",CacheInfo.commonInfo);
	}

}
