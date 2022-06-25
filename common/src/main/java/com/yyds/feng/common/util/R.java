package com.yyds.feng.common.util;

import com.yyds.feng.common.enums.ResultCode;
import java.util.HashMap;

public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
    }

    public R(int code, String msg) {
        put("code", code);
        put("msg", msg);
    }

    public static R ok() {
        return new R(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg());
    }

    public static R ok(Object o) {
        R r = new R(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg());
        r.put("data",o);
        return r;
    }

    public static R error() {
        return new R(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg());
    }

    public static R error(String msg) {
        return new R(ResultCode.FAILED.getCode(), msg);
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
