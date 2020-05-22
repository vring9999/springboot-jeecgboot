package com.hrkj.scalp;

import com.hrkj.scalp.config.entity.Config;
import com.hrkj.scalp.config.service.IConfigService;
import com.hrkj.scalp.util.CacheInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动运行代码
 */
@Component
@Slf4j
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    private IConfigService commonConfigService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("初始化配置信息");
        queryCommon();
    }

    /**
     * 初始化配置信息
     */
    private void queryCommon() {
        List<Config> commons = commonConfigService.list();
        commons.forEach(comm -> CacheInfo.commonInfo.put(comm.getCfgName()+"+"+comm.getCfgKey(),comm.getCfgValue()));
    }
}