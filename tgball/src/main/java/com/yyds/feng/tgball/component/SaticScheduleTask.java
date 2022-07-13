package com.yyds.feng.tgball.component;


import com.yyds.feng.tgball.controller.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class SaticScheduleTask {

    @Autowired
    TgBall tgBall;

    @Scheduled(fixedRate=600000 * 2)
    private void configureTasks() {
        try {
            tgBall.login();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        tgBall.my();
    }
}
