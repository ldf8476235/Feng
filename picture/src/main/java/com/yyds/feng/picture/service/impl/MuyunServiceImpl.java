package com.yyds.feng.picture.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yyds.feng.common.util.DateUtils;
import com.yyds.feng.common.util.RedisUtils;
import com.yyds.feng.picture.entity.Muyun;
import com.yyds.feng.picture.entity.Pic;
import com.yyds.feng.picture.entity.User;
import com.yyds.feng.picture.service.MuyunService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Desc
 * @Author Lee
 * @Date 2022/5/27 22:35
 */
@Slf4j
@Service
public class MuyunServiceImpl implements MuyunService {


    @Autowired
    RedisUtils redisUtils;

    private static MediaType mediaType = MediaType.parse("application/json");

    private static int count = 0;

    private static String cerBaseUrl = "http://longtengmuyun.bjyjyc.com/";

    //用于查询的token
    private static String comToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjE1MTk0LCJleHAiOjE2NTM3NTE2MDkxNzd9.jTlSByZX_3qQdEQvM-rM20hUJNp4D-TFV4MbX9oTM5U";

    private static int sid = 4;
    //抢购时间戳
    private static Long time;


    @Override
    public void repair(User user) {
        sid = DateUtils.getSid();
        Muyun muyun = new Muyun();
        initMuyun(muyun,user);
        if (null == muyun.getCookies()) {
            return;
        }
        Boolean isBuy = false;
        //WxPush.push("捡漏通知",muyun.getName()+":===>开始捡漏！！！",RedisUtils.get(user.getPhone()));
        log.info("{}->开始捡漏",muyun.getName());

        JSONObject params = new JSONObject();
        params.put("page_index", 0);
        params.put("page_size", 1);
        params.put("sid", sid);
        params.put("token", comToken);
        RequestBody requestBody = RequestBody.create(mediaType, params.toString() + "");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(cerBaseUrl + "web/rush/getRushGoods")
                .post(requestBody)
                .addHeader("Content-type", "application/json;charset=UTF-8")
                .build();
        a:while (true) {
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    JSONObject result = JSON.parseObject(response.body().string());
                    response.close();
                    String JSONStr = JSON.toJSONString(result.getJSONObject("data").getJSONArray("list"));
                    List<Pic> picList = JSON.parseObject(JSONStr, new TypeReference<List<Pic>>() {
                    });

                    Pic pic = picList.get(0);
                    if (pic.getState() == 1) {
                        log.info("监测到漏画-> {}->价格:{}",pic.getName(),pic.getPrice());
                        if (user.getSta() * 100000 < pic.getPrice() && pic.getPrice() < user.getEnd() * 100000) {
                            muyun.setGid(pic.getGid());
                            muyun.setCid(pic.getCid());
                            muyun.setSid(pic.getSid());
                            muyun.setDetail(""+pic.getName().trim() + "\n" +" ==>价格:"+pic.getPrice() / 100 +"  ==>利润:" + pic.getPrice() / 100 * 0.02);
                            isBuy = true;
                            break a;
                        }
                    }
                }
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }

        if (isBuy) {
            seckill(muyun);
        }
    }

    @Override
    @Async
    public void seckillPic(User user) {
        sid = DateUtils.getSid();
        time = DateUtils.getSeckillTime();
        Muyun muyun = new Muyun();
        initMuyun(muyun,user);
        if (null == muyun.getCookies()) {
            return;
        }
        //WxPush.push("抢购通知",muyun.getName()+":===>开始抢购！！！",RedisUtils.get(user.getPhone()));
        log.info("{}->开始抢购",muyun.getName());
        JSONObject params = new JSONObject();
        params.put("page_index", 0);
        params.put("page_size", 222);
        params.put("sid", sid);
        params.put("token", comToken);
        RequestBody requestBody = RequestBody.create(mediaType, params.toString() + "");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(cerBaseUrl + "web/rush/getRushGoods")
                .post(requestBody)
                .addHeader("Content-type", "application/json;charset=UTF-8")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject result = JSON.parseObject(response.body().string());
                response.close();
                String JSONStr = JSON.toJSONString(result.getJSONObject("data").getJSONArray("list"));
                List<Pic> picList = JSON.parseObject(JSONStr, new TypeReference<List<Pic>>() {
                });
                a:for (int i = 0; i < picList.size(); i++) {
                    Pic pic = picList.get(i);
                    if (user.getSta() * 100000 < pic.getPrice() && pic.getPrice() < user.getEnd() * 100000
                    && !redisUtils.hasKey(pic.getGid().toString())
                            && !pic.getBelong_nickname().equals(muyun.getName())
                    ) {
                        muyun.setGid(pic.getGid());
                        muyun.setCid(pic.getCid());
                        muyun.setSid(pic.getSid());
                        muyun.setDetail(""+pic.getName().trim() +" =>价格:"+ pic.getPrice() / 100 +"  =>利润:" + pic.getPrice() / 100 * 0.02);
                        //RedisUtils.set(pic.getGid().toString(),user.getPhone());
                        break a;
                    }
                }
                if (null != muyun.getGid()) {
                    b:
                    while (true) {
                        if (time - System.currentTimeMillis() < 100) {
                            seckill(muyun);
                            break b;
                        }
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void seckill(Muyun muyun){
        log.info("{}->抢购请求->{}",muyun.getName(),"进入抢购组装参数");
        JSONObject params = new JSONObject();
        params.put("gid",muyun.getGid());
        params.put("cid",muyun.getCid());
        params.put("sid",muyun.getSid());
        params.put("mode","1");
        params.put("token",muyun.getToken());
        params.put("address_info",muyun.getAddressInfo());

        RequestBody requestBody = RequestBody.create(mediaType, params.toString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(cerBaseUrl + "web/rush/shoot")
                .post(requestBody)
                .addHeader("Content-type", "application/json;charset=UTF-8")
                .addHeader("Host", "longtengmuyun.bjyjyc.com")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "zh-CN,zh-Hans;q=0.9")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Origin", "https://longtengmuyun.bjyjyc.com")
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/8.0.20(0x18001441) NetType/WIFI Language/zh_CN")
                .addHeader("Connection", "keep-alive")
                .addHeader("tid", muyun.getToken())
                .addHeader("Cookie", muyun.getCookies())
                .build();
        try {
            log.info("{}->抢购请求->{}",muyun.getName(),params.toJSONString());
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject result = JSON.parseObject(response.body().string());
                response.close();
                log.info("{}->抢购结果->{}",muyun.getName(),result.toJSONString());
                if (result.getInteger("res_code") == 1){
                    //WxPush.push("通知","成功！！！" + muyun.getDetail(),RedisUtils.get(muyun.getPhone()));
                    log.info("{}->抢购成功->{}",muyun.getName(),muyun.getDetail());
                    count ++;
                } else {
                    //WxPush.push("通知",muyun.getDetail()+"失败！！！",RedisUtils.get(muyun.getPhone()));
                    log.info("{}->抢购失败->{}",muyun.getName(),muyun.getDetail());
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void initMuyun(Muyun muyun,User user){
        OkHttpClient client = new OkHttpClient();
        JSONObject params = new JSONObject();
        params.put("phone", user.getPhone());
        params.put("pwd", user.getPwd());
        log.info("{}->请求登录",user.getPhone() + user.getPwd());
        RequestBody requestBody = RequestBody.create(mediaType, params.toString() + "");
        Request request = new Request.Builder()
                .url(cerBaseUrl + "web/user/login")
                .post(requestBody)
                .addHeader("Content-type", "application/json;charset=UTF-8")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject result = JSON.parseObject(response.body().string());
                response.close();
                JSONObject data = result.getJSONObject("data");
                String token = data.getString("token");
                muyun.setName(data.getString("nickname"));
                muyun.setToken(token);
                muyun.setPhone(user.getPhone());
                muyun.setSid(sid);
                muyun.setAddressInfo(data.getJSONArray("address_list").getJSONObject(0));
                String cookies ="tid=" +token +"; userinfo={"+URLEncoder.encode(data.toJSONString(),"UTF-8")+"}";
                muyun.setCookies(cookies);
                log.info("{}->登录成功",data.getString("nickname"));
                setData(muyun);
            }
        }catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    public void setData(Muyun muyun){
        JSONObject params = new JSONObject();
        params.put("type","进入场次");
        params.put("sid",muyun.getSid());
        params.put("token",muyun.getToken());
        RequestBody requestBody = RequestBody.create(mediaType, params.toString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(cerBaseUrl + "web/common/setData")
                .post(requestBody)
                .addHeader("Content-type", "application/json;charset=UTF-8")
                .addHeader("Host", "longtengmuyun.bjyjyc.com")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "zh-CN,zh-Hans;q=0.9")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Origin", "https://longtengmuyun.bjyjyc.com")
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/8.0.20(0x18001441) NetType/WIFI Language/zh_CN")
                .addHeader("Connection", "keep-alive")
                .addHeader("tid", muyun.getToken())
                .addHeader("Cookie", muyun.getCookies())
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                log.info("{}->进入场次",muyun.getName());
            }
            response.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

}
