package com.jack.springboot.handle;

import com.jack.springboot.domain.Result;
import com.jack.springboot.exception.CustomException;
import com.jack.springboot.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof CustomException) {
            CustomException customException = (CustomException) e;
            return ResultUtils.error(customException.getCode(), customException.getMessage());
        }else {
            logger.error("【系统异常】{}", e);
            return ResultUtils.error(-1, "未知错误");
        }
    }
}
