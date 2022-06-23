package com.yyds.feng.picture.controller;

import com.yyds.feng.common.util.R;
import com.yyds.feng.picture.entity.User;
import com.yyds.feng.picture.service.MuyunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc
 * @Author Lee
 * @Date 2022/5/27 22:06
 */

@RestController
@RequestMapping("/muyun")
public class MuyunController {

    @Autowired
    MuyunService muyunService;

    @PostMapping("/repair")
    public void repair(@RequestBody User user){
        muyunService.repair(user);
    }

    @PostMapping("/seckill")
    public R seckill(@RequestBody User user){
        muyunService.seckillPic(user);
        return R.ok();
    }
}
