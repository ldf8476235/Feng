package com.yyds.feng.tgball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yyds.feng"})
public class TgballApplication {
    public static void main(String[] args) {
        SpringApplication.run(TgballApplication.class, args);
    }
}
