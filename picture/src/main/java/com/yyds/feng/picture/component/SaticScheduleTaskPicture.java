package com.yyds.feng.picture.component;


import com.yyds.feng.common.util.BarkPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.UnsupportedEncodingException;

@Configuration
@EnableScheduling
public class SaticScheduleTaskPicture {

    @Scheduled(cron = "0 28 10 ? * MON-FRI")
    void remind(){
        BarkPush.push("Muyun","入场");
    }
}
