package com.hrkj.scalp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//Spring Boot 使用事务非常简单，首先使用注解 @EnableTransactionManagement 开启事务支持后，
// 然后在访问数据库的Service方法上添加注解 @Transactional 便可
//@EnableTransactionManagement
//SpringBootApplication 上使用@ServletComponentScan 注解后
//Servlet可以直接通过@WebServlet注解自动注册
//Filter可以直接通过@WebFilter注解自动注册
//Listener可以直接通过@WebListener 注解自动注册
//@ServletComponentScan
@EnableScheduling //开启定时任务
//@EnableRabbit  //开启rabbitmq
@MapperScan("com.hrkj.scalp.*.mapper")
@ComponentScan(basePackages={"org.jeecg.common","com.hrkj.scalp"})
//@EnableAutoConfiguration
public class HrkjApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrkjApplication.class, args);
    }

}
