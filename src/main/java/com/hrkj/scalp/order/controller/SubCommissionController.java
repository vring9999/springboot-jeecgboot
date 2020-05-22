package com.hrkj.scalp.order.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hrkj.scalp.user.entity.User;
import com.hrkj.scalp.user.service.IUserService;
import com.hrkj.scalp.util.StringUtil;
import com.hrkj.scalp.util.UsedCode;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import com.hrkj.scalp.order.entity.SubCommission;
import com.hrkj.scalp.order.service.ISubCommissionService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 下级代理收益明细
 * @Author: jeecg-boot
 * @Date: 2020-03-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/subCommission")
@Slf4j
public class SubCommissionController extends JeecgController<SubCommission, ISubCommissionService> {
    @Autowired
    private ISubCommissionService subCommissionService;
    @Autowired
    private IUserService userService;

    @Autowired
    private ISysBaseAPI sysBaseAPI;


    /**
     * 分页列表查询
     *
     * @param subCommission
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<?> queryPageList(SubCommission subCommission,String phoneId, String openTime, String endTime,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<SubCommission> queryWrapper = QueryGenerator.initQueryWrapper(subCommission, req.getParameterMap());
        if(!StringUtil.isEmpty(phoneId)) queryWrapper.eq("order_id",phoneId).or().eq("user_id",phoneId);
        if(!StringUtil.isEmpty(openTime)) queryWrapper.ge("create_time", openTime);//.ge 添加 >= 的条件判断
        if(!StringUtil.isEmpty(endTime)) queryWrapper.le("create_time", endTime);//.le 添加 <= 的条件判断
        //查询数据限制  管理员无限制查询 码商/商户查询个人1数据
        queryWrapper = sysBaseAPI.checkType(queryWrapper, req, 1);
        if (null == queryWrapper) return Result.error("token为空");
        Page<SubCommission> page = new Page<SubCommission>(pageNo, pageSize);
        IPage<SubCommission> pageList = subCommissionService.page(page, queryWrapper);
        return Result.okRowsData(pageList.getTotal(),pageList.getRecords());
    }

    /**
     * APP端获取码商返利列表
     *
     * @return
     */
    @GetMapping(value = "/querySubCommissionList")
    public Result<?> querySubCommissionList(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        User user = userService.getById(userId);
        if(null == user) return  Result.error("未获取用户信息");
        String id = UsedCode.getGroupLevel(user.getGroupName());
        Map<String,Object> params = new HashMap<>();
        params.put(id,userId);
        List<Map<String,Object>> list = subCommissionService.queryComeBackList(params);
        return Result.ok(list);
    }

    /**
     * 添加
     *
     * @param subCommission
     * @return
     */
    @AutoLog(value = "分佣明细---添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody SubCommission subCommission) {
        subCommissionService.save(subCommission);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param subCommission
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody SubCommission subCommission) {
        subCommissionService.updateById(subCommission);
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
        subCommissionService.removeById(id);
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
        this.subCommissionService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        SubCommission subCommission = subCommissionService.getById(id);
        if (subCommission == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(subCommission);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param subCommission
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SubCommission subCommission) {
        return super.exportXls(request, subCommission, SubCommission.class, "分佣明细");
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
        return super.importExcel(request, response, SubCommission.class);
    }

/*    @PostMapping(value = "/get")
    public Result<?> get(String id,int level) {
        try {
            List<User> users = userService.list(new QueryWrapper<User>().eq("parent_id",id));
            level+=1;
            StringBuffer userIds=new StringBuffer();
			Map<String,Object> map=new HashMap<>();
			users.forEach(u->userIds.append("'").append(u.getId()).append("'").append(","));

            String us=userIds.substring(0, userIds.length()-1);
            if(level==2){
                map.put("tow",us);
            }else if(level==3){
                map.put("three",us);
            }else if(level==4){
                map.put("four",us);
            }else if(level==5){
                map.put("five",us);
            }
            list<Map<String,Object>> num=subCommissionService.getSumAccount(map);
            return Result.ok(num);
        } catch (Exception e) {
            e.printStackTrace();
			return Result.error("查询失败！");
        }
    }*/

}
