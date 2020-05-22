package com.hrkj.scalp.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@Slf4j
public class DeployCode implements ApplicationContextAware {


    private static DeployCode instance;


    public static DeployCode getInstance() {
        return instance;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {


        instance = applicationContext.getBean(DeployCode.class);

    }



}
