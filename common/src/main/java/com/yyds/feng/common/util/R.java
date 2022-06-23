package com.yyds.feng.common.util;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("status", 1);
        put("message", "success");
    }

    public static R error() {
        return error(2, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(0, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("status", code);
        r.put("message", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("message", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}