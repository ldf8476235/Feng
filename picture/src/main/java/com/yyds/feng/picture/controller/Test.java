package com.yyds.feng.picture.controller;

import com.yyds.feng.common.util.RedisUtils;
import com.yyds.feng.picture.service.MuyunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc
 * @Author Lee
 * @Date 2022/5/27 21:40
 */

@RestController
public class Test {


    @Autowired
    MuyunService muyunService;

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/test2")
    void test2() {
        redisUtils.set("123","123");
    }

    @GetMapping("/test")
    void test(){
    }
}
