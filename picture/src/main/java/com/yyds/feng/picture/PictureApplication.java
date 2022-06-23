package com.yyds.feng.picture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Desc
 * @Author Lee
 * @Date 2022/5/27 21:39
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.yyds.feng"})
public class PictureApplication {
    public static void main(String[] args) {
        SpringApplication.run(PictureApplication.class, args);
    }
}
