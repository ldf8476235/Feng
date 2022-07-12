package com.yyds.feng.picture.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yyds.feng.common.util.DateUtils;
import com.yyds.feng.common.util.WxPush;
import com.yyds.feng.picture.entity.Pic;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

/**
 * @Desc
 * @Author Lee
 * @Date 2022/6/7 17:49
 */
public class Muyun {

    private static String cerBaseUrl = "http://wx.bjzlke.com/";
    private static MediaType mediaType = MediaType.parse("application/json");

    private static String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjIxNzg5MCwiZXhwIjoxNjU2NzMxMTU5NzA5fQ.YUG-OgaEhLoF1hyjRH4zQ1cjP4bwHoclBIltQyCI95M";
    private static String tid = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjIxNzg5MCwiZXhwIjoxNjU2NzMxMTU5NzA5fQ.YUG-OgaEhLoF1hyjRH4zQ1cjP4bwHoclBIltQyCI95M";
    private static String cookie = "tid=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjIxNzg5MCwiZXhwIjoxNjU2NzMxMTU5NzA5fQ.YUG-OgaEhLoF1hyjRH4zQ1cjP4bwHoclBIltQyCI95M; userinfo={%22uid%22:217890%2C%22phone%22:%2217801442962%22%2C%22nickname%22:%22%E5%B0%8F%E6%9D%B0%22%2C%22avatar%22:%22%22%2C%22gender%22:0%2C%22email%22:%22%22%2C%22token%22:%22eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjIxNzg5MCwiZXhwIjoxNjU2NzMxMTU5NzA5fQ.YUG-OgaEhLoF1hyjRH4zQ1cjP4bwHoclBIltQyCI95M%22%2C%22payee_name%22:%22%22%2C%22payee_bankno%22:%22%22%2C%22payee_bankname%22:%22%22%2C%22recommender_path%22:%22[]%22%2C%22level1_recommender%22:0%2C%22level2_recommender%22:0%2C%22qrcode%22:%22https://xiahuixingchen-eshop.oss-cn-beijing.aliyuncs.com/qr-code/217890_qr_1656644745113.jpg%22%2C%22sign%22:%22https://xiahuixingchen-eshop.oss-cn-beijing.aliyuncs.com/user-sign/217890.png%22%2C%22roles%22:[]%2C%22state%22:1%2C%22create_time%22:%222022-07-01%2011:05:45%22%2C%22last_login_time%22:%222022-07-01%2011:05:59%22%2C%22update_time%22:%222022-07-01%2011:08:58%22%2C%22edit_bank_access%22:1%2C%22edit_nickname_access%22:1%2C%22edit_avatar_access%22:1%2C%22has_read_protocol%22:1%2C%22admin_token%22:%22%22%2C%22bad%22:0%2C%22first_enter%22:0%2C%22bucket_id%22:0%2C%22open_id%22:%22opTHp511ow_Hy3_VThU3VBLkHjwU%22%2C%22alipay_img%22:null%2C%22wxpay_img%22:null%2C%22address_list%22:[]%2C%22has_sign%22:true}";

    public static void main(String[] args) {
        com.yyds.feng.picture.entity.Muyun muyun = new com.yyds.feng.picture.entity.Muyun();
        muyun.setCookies(cookie);
        muyun.setToken(token);
        int sid = DateUtils.getSid();
        Boolean isBuy = false;
        JSONObject params = new JSONObject();
        params.put("page_index", 0);
        params.put("page_size", 10);
        params.put("sid", sid);
        params.put("token", token);
        RequestBody requestBody = RequestBody.create(mediaType, params.toString() + "");
        OkHttpClient client = new OkHttpClient();
        String referer = "";
//        if (sid == 3){
//            referer = "https://wx.bjzlke.com/onsalelist?schedule=%7B%22id%22%3A3,%22name%22%3A%22%E7%8B%82%E6%AC%A2%E6%8A%A2%E8%B4%AD%E4%B8%8A%E5%8D%88%E5%9C%BA%22,%22startStr%22%3A%2210%3A30%22,%22starttime%22%3A1654569000069,%22endStr%22%3A%2211%3A35%22,%22endtime%22%3A1654572900069,%22state%22%3A1,%22stateStr%22%3A%22%E6%8A%A2%E8%B4%AD%E4%B8%AD...%22,%22gtime%22%3A0,%22schedule_state%22%3A1,%22tip%22%3A%22%22%7D";
//        } else {
//            referer =  "https://wx.bjzlke.com/onsalelist?schedule=%7B%22id%22%3A3,%22name%22%3A%22%E7%8B%82%E6%AC%A2%E6%8A%A2%E8%B4%AD%E4%B8%8A%E5%8D%88%E5%9C%BA%22,%22startStr%22%3A%2210%3A30%22,%22starttime%22%3A1654569000069,%22endStr%22%3A%2211%3A35%22,%22endtime%22%3A1654572900069,%22state%22%3A1,%22stateStr%22%3A%22%E6%8A%A2%E8%B4%AD%E4%B8%AD...%22,%22gtime%22%3A0,%22schedule_state%22%3A1,%22tip%22%3A%22%22%7D";
//        }
        Request request = new Request.Builder()
                .url(cerBaseUrl + "web/rush/getRushGoods")
                .post(requestBody)
                .addHeader("Host", "wx.bjzlke.com")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "zh-CN,zh-Hans;q=0.9")
//                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Content-Type", "application/json")
                .addHeader("Origin", "https://wx.bjzlke.com")
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/8.0.20(0x18001441) NetType/WIFI Language/zh_CN")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", referer)
                .addHeader("Content-Length", "175")
                .addHeader("tid", tid)
                .addHeader("Cookie", cookie)
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
                        System.out.println("监测到漏画->" + pic.getName() + "->价格:" + pic.getPrice());
                        if (10 * 100000 < pic.getPrice() && pic.getPrice() < 20 * 100000) {
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
                    Thread.sleep(200+(int)(Math.random()*100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }

        if (isBuy) {
            //seckill(muyun);
        }
    }

    public void seckill(com.yyds.feng.picture.entity.Muyun muyun){
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
                .addHeader("Host", "wx.bjzlke.com")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "zh-CN,zh-Hans;q=0.9")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Origin", "https://wx.bjzlke.com")
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 15_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/8.0.20(0x18001441) NetType/WIFI Language/zh_CN")
                .addHeader("Connection", "keep-alive")
                .addHeader("tid", muyun.getToken())
                .addHeader("Cookie", muyun.getCookies())
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject result = JSON.parseObject(response.body().string());
                response.close();
                if (result.getInteger("res_code") == 1){

                } else {

                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
