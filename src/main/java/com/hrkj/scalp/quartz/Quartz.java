package com.hrkj.scalp.quartz;

import com.hrkj.scalp.merchant.service.IQrCodeService;
import com.hrkj.scalp.stock.service.IUserStockService;
import com.hrkj.scalp.user.entity.User;
import com.hrkj.scalp.user.service.IUserService;
import com.hrkj.scalp.util.UsedCode;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author vring
 * @ClassName Quartz.java
 * @Description 定时任务类
 * @createTime 2020/3/25 11:01
 */
@Component(value = "quartz")
@Slf4j
public class Quartz {

    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private IQrCodeService qrCodeService;

    @Autowired
    private IUserStockService userStockService;

    @Autowired
    private IUserService userService;

    /**
     * @Title: quartzUpdateQrCode
     * @Description: 每日0点--开启审核通过但是被管理员强制下线的收款码
     */
    @Scheduled(cron = "0 0 0 * * ?") //依次对应:秒/分/小时/日(每个月第几天)/月/星期/年份
    public void quartzUpdateQrCodeForAdmin(){
        qrCodeService.quartzUpdate();
//        sysBaseAPI.addLog("quartzUpdateQrCode is over", UsedCode.LOG_TYPE_3,3);
        log.info("开启二维码定时任务执行完毕");
    }

    /**
     * @Title: quartzUpdateQrCodeForDayAccount
     * @Description: 每日0点--清空码商收款码的今日收款,重新计算今日还可收款
     */
    @Scheduled(cron = "0 0 0 * * ?") //依次对应:秒/分/小时/日(每个月第几天)/月/星期/年份
    public void quartzUpdateQrCodeForDayAccount(){
        qrCodeService.quartzUpdateTodayAccount();
//        sysBaseAPI.addLog("quartzUpdateQrCodeForDayAccount is over", UsedCode.LOG_TYPE_3,3);
        log.info("二维码收款金额设置的定时任务执行完毕");
    }

    /**
     * @Title: quartzUpdateStock
     * @Description: 每日0点--清空码商库存的今日已售
     */
    @Scheduled(cron = "0 0 0 * * ?") //依次对应:秒/分/小时/日(每个月第几天)/月/星期/年份
    public void quartzUpdateStock(){
        userStockService.quartzUpdateStock();
//        sysBaseAPI.addLog("quartzUpdateStock is over", UsedCode.LOG_TYPE_3,3);
        log.info("清空码商库存的今日已售");
    }

    /**
     * @Title: InitDayAccount
     * @Description: 每日0点--清空用户今日收益
     */
    @Scheduled(cron = "0 0 0 * * ?") //依次对应:秒/分/小时/日(每个月第几天)/月/星期/年份
    public void InitDayAccount(){
        User user=new User();
        user.setDayAccount(0);
        userService.update(user,null);
        log.info("初始化用户今日收益");

    }


}
