package com.yyds.feng.common.util;

import okhttp3.*;

public class BarkPush {

    public static String DEFAULT_KEY = "MpaeArEPB9Ev952rkbGRrS";

    private static OkHttpClient client = new OkHttpClient();

    private static String url = "https://bark.ioiox.com/";

    public static void push(String title,String content){
        RequestBody formBody = new FormBody.Builder()
                //需要的参数(key,value的格式可以一直add)
                .add("title", title)
                .add("desp", content)
                .build();
        //组装请求头
        Request request = new Request.Builder()
                .url(url+DEFAULT_KEY + "/" + title
                + "/" + content
                + "/?icon=http://43.142.157.247:8080/images/ldf.jpg")
                .get()
                //设置请求cookie(如果当前调用的是登录请求去获取cookie就不需要这个)
                .build();
        //该方法容易触发IOException异常
        try {
            //获取返回值
            Response response = client.newCall(request).execute();
        } catch (Exception e){

        }
    }
}
