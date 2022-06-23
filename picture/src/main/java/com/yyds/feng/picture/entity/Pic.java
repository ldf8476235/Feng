package com.yyds.feng.picture.entity;

/**
 * @Desc
 * @Author Lee
 * @Date 2022/5/23 14:47
 */
public class Pic {
    private String name;
    private Integer state;
    private Integer gid;
    private Integer sid;
    private Integer cid;
    private Integer price;
    private String belong_nickname;

    public String getBelong_nickname() {
        return belong_nickname;
    }

    public void setBelong_nickname(String belong_nickname) {
        this.belong_nickname = belong_nickname;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}
