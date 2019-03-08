package com.jack.springboot.enums;


/*
* 异常信息统一维护
* */
public enum ResultEnum {
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    USERISEXIST(-1,"用户不存在"),
    USEREXIST(-1,"用户已经存在"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
