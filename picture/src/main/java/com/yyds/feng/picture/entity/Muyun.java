package com.yyds.feng.picture.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Desc
 * @Author Lee
 * @Date 2022/5/27 22:36
 */

@Data
public class Muyun {
    private String phone;
    private String pic;
    private String name;
    private String token;
    private String cookies;
    private Integer gid;
    private Integer cid;
    private Integer sid;
    private String detail;
    @Value("1")
    private String mode;
    private JSONObject addressInfo;
}
