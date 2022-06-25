package com.yyds.feng.picture.entity;

import lombok.Data;

/**
 * @Desc
 * @Author Lee
 * @Date 2022/5/23 14:47
 */
@Data
public class Pic {
    private String name;
    private Integer state;
    private Integer gid;
    private Integer sid;
    private Integer cid;
    private Integer price;
    private String belong_nickname;
}
