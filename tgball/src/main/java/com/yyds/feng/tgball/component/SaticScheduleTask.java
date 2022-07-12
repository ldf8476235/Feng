package com.yyds.feng.tgball.component;


import com.yyds.feng.tgball.controller.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.UnsupportedEncodingException;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class SaticScheduleTask {
//    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒 5000
    @Autowired
    TgBall tgBall;

    @Scheduled(fixedRate=600000)
    private void configureTasks() {
//        try {
//            Test.login();
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//        Test.my();
        tgBall.my();
    }
}
