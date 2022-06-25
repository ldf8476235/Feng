package com.yyds.feng.common.util.exception;

import com.yyds.feng.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Desc
 * @Author Lee
 * @Date 2022/6/23 16:23
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e){
        log.error(e.getMessage(), e);
        return R.error();
    }

}
