package com.jack.springboot.enums;


/*
* 异常信息统一维护
* */
public enum ResultEnum {
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    USERISEXIST(-1,"用户不存在或已被删除"),
    USEREXIST(-1,"用户名已经存在"),
    ROLEISEXIT(-1,"该角色已经存在"),
    ROLEEXIT(-1,"该角色已经不存在或已被删除"),
    PERMISSIONISEXIST(-1,"权限不存在或已被删除")
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
