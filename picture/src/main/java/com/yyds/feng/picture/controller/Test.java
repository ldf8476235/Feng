package com.yyds.feng.picture.controller;

import com.alibaba.fastjson.JSONArray;
import com.yyds.feng.common.util.R;
import com.yyds.feng.common.util.RedisUtils;
import com.yyds.feng.picture.entity.User;
import com.yyds.feng.picture.service.MuyunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc
 * @Author Lee
 * @Date 2022/5/27 21:40
 */

@RestController
@Api(tags = "测试")
public class Test {

    @Autowired
    MuyunService muyunService;

    @Autowired
    RedisUtils redisUtils;

    @PostMapping("/test2")
    @ApiOperation("测试")
    public R test2(@RequestBody User user) {
        redisUtils.set("123","123");
        return R.ok();
    }

    @GetMapping("/test")
    void test(){
    }

    public static void main(String[] args) {
        String str =  "[\"{\\\"textData\\\":\\\"京德高速\\\",\\\"fontColor\\\":\\\"255255000000\\\",\\\"fontSize\\\":\\\"\\\"}\",\"{\\\"textData\\\":\\\"长条屏信息\\\\n第二行\\\",\\\"fontColor\\\":\\\"255255000000\\\",\\\"fontSize\\\":\\\"\\\"}\"]";
        System.out.println(JSONArray.parseObject(str));
    }
}
