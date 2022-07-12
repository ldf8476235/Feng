package com.yyds.feng.tgball.component;

import com.yyds.feng.common.util.DateUtils;
import com.yyds.feng.common.util.RedisUtils;
import com.yyds.feng.common.util.SSLSocketClientUtil;
import com.yyds.feng.common.util.WxPush;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
                    .header("Cookie","popupshow=saw; say=tg332wyaya103.95.71.132; PHPSESSID=8ib33oh7jjjqalps78lgurkgr2; loginInfo_cookie=eyJhY2NvdW50IjoiQVNXMjA1IiwicHdkIjoiV3NsZGYxMjM0NTYifQ%3D%3D")
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

    public static void login() throws UnsupportedEncodingException {
        String url="https://m1.zvip111.co/ac_login.php";
        X509TrustManager manager = SSLSocketClientUtil.getX509TrustManager();
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .sslSocketFactory(SSLSocketClientUtil.getSocketFactory(manager), manager)
                .hostnameVerifier(SSLSocketClientUtil.getHostnameVerifier())
                .followRedirects(false)
                .followSslRedirects(false)
                .build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "lastFour=2984&account=ASW205&pwd=Wsldf123456&deviceInfo=Mozilla%2F5.0+(Linux%3B+Android+7.1.2%3B+SM-G977N+Build%2FLMY48Z%3B+wv)+AppleWebKit%2F537.36+(KHTML%2C+like+Gecko)+Version%2F4.0+Chrome%2F92.0.4515.131+Mobile+Safari%2F537.36&vga=Adreno+(TM)+640");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Host", "m1.zvip111.co")
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "zh-CN,zh-Hans;q=0.9")
                //.addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Origin", "https://m1.zvip111.co")
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Mobile/15E148 Safari/604.1")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", "https://m1.zvip111.co/login.php")
                .addHeader("deviceInfo", "Mozilla/5.0 (iPhone; CPU iPhone OS 15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.5 Mobile/15E148 Safari/604.1")
                .addHeader("Cookie", "loginInfo_cookie=eyJhY2NvdW50IjoiQVNXMjA1IiwicHdkIjoiV3NsZGYxMjM0NTYifQ%3D%3D; say=tg332wyaya103.95.71.132; PHPSESSID=8ib33oh7jjjqalps78lgurkgr2")
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String result = response.body().string();//得到数据
            log.info(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
