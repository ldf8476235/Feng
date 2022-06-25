package com.yyds.feng.common.enums;

import lombok.Getter;

/**
 * @Desc
 * @Author Lee
 * @Date 2022/6/23 13:59
 */
@Getter
public enum ResultCode{

    SUCCESS(200, "success"),
    FAILED(400, "error"),
    VALIDATE_ERROR(1002, "参数校验失败"),
    RESPONSE_PACK_ERROR(1003, "response返回包装失败");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
