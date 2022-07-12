package com.yyds.feng.vx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Desc
 * @Author Lee
 * @Date 2022/7/12 10:59
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.yyds.feng"})
public class WxApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxApplication.class, args);
    }
}
