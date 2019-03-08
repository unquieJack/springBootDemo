package com.jack.springboot.exception;


import com.jack.springboot.enums.ResultEnum;

public class CustomException extends RuntimeException{
    private Integer code;

    public CustomException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
