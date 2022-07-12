package com.yyds.feng.tgball.component;

import com.yyds.feng.common.util.DateUtils;
import com.yyds.feng.common.util.RedisUtils;
import com.yyds.feng.common.util.WxPush;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class TgBall {

    @Autowired
    RedisUtils redisUtils;

    public void my(){
        Connection.Response res = null;
        try {
            res = Jsoup.connect("https://m1.zvip111.co/my.php")
                    .method(Connection.Method.GET)
                    .timeout(10000)
                    .header("Cookie","popupshow=saw; PHPSESSID=n4ocdm9l19f435q12bf6vk8uh8; say=tg332wyaya223.104.41.120; loginInfo_cookie=eyJhY2NvdW50IjoiQVNXMjA1IiwicHdkIjoiV3NsZGYxMjM0NTYifQ%3D%3D")
                    .header("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Mobile/15E148 Safari/604.1")
                    .header("deviceInfo", "Mozilla/5.0 (iPhone; CPU iPhone OS 15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Mobile/15E148 Safari/604.1")
                    .validateTLSCertificates(false)
                    .execute();
            Document doc = res.parse();
//            System.out.println(doc);
            Element element = doc.getElementsByClass("type1 carousel-item").get(1);
            int num = Integer.parseInt(element.child(1).text());
            if (num == 0) {
                String remind = redisUtils.get("remind");
                Long old = Long.parseLong(remind);
                Long now = System.currentTimeMillis()/1000;
                if (now-old > (60 * 90) &&
                        (8 < DateUtils.getHour() && DateUtils.getHour() < 23)) {
                    redisUtils.set("remind", "" + System.currentTimeMillis() / 1000);
                    WxPush.push("TG", "交易结束", WxPush.DEFAULT_KEY);
                }
            }
            log.info("当前交易中订单->{}",num);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
