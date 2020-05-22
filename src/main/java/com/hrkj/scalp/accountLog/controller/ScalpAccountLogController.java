package com.hrkj.scalp.accountLog.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

import com.hrkj.scalp.accountLog.entity.ScalpAccountLog;
import com.hrkj.scalp.util.StringUtil;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.accountLog.service.IScalpAccountLogService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: scalp_account_old
 * @Author: jeecg-boot
 * @Date: 2020-03-09
 * @Version: V1.0
 */
@RestController
@RequestMapping("/accountLog")
@Slf4j
public class ScalpAccountLogController extends JeecgController<ScalpAccountLog, IScalpAccountLogService> {
    @Autowired
    private IScalpAccountLogService scalpAccountLogService;

    @Autowired
    private ISysBaseAPI sysBaseAPI;

    /**
     * 分页列表查询
     *
     * @param ScalpAccountLog
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<?> queryPageList(ScalpAccountLog ScalpAccountLog,String phoneId,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req, String openTime, String endTime) {
        QueryWrapper<ScalpAccountLog> queryWrapper = QueryGenerator.initQueryWrapper(ScalpAccountLog, req.getParameterMap());
        if(!StringUtil.isEmpty(openTime)) queryWrapper.ge("update_time",openTime);	//.ge 添加 >= 的条件判断
        if(!StringUtil.isEmpty(openTime)) queryWrapper.le("update_time",endTime);	//.le 添加 >= 的条件判断
        if(!StringUtil.isEmpty(phoneId)) queryWrapper.eq("accountNum",phoneId).or().eq("userId",phoneId);
        //查询数据限制  管理员无限制查询 码商/商户查询个人数据
        queryWrapper = sysBaseAPI.checkType(queryWrapper,req,1);
        if(null == queryWrapper) return Result.error("token失效");
        queryWrapper.eq("is_show", 1);
        Page<ScalpAccountLog> page = new Page<ScalpAccountLog>(pageNo, pageSize);
        IPage<ScalpAccountLog> pageList = scalpAccountLogService.page(page, queryWrapper);
        return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
    }

    /**
     * 编辑
     *
     * @param ScalpAccountLog
     * @return
     */
    @PostMapping(value = "/edit")
    public Result<?> edit(@RequestBody ScalpAccountLog ScalpAccountLog) {
        scalpAccountLogService.updateById(ScalpAccountLog);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        scalpAccountLogService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.scalpAccountLogService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        ScalpAccountLog ScalpAccountLog = scalpAccountLogService.getById(id);
        if (ScalpAccountLog == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(ScalpAccountLog);
    }

}
