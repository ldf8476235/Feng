package com.yyds.feng.picture.component;


import com.yyds.feng.common.util.BarkPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class SaticScheduleTaskPicture {

    @Scheduled(cron = "0 28 10 ? * MON-FRI")
    void remindMorning(){
        BarkPush.push("Muyun","入场");
    }

    @Scheduled(cron = "0 58 13 ? * MON-FRI")
    void remindAfter(){
        BarkPush.push("Muyun","入场");
    }
}
