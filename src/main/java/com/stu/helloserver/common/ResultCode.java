package com.stu.helloserver.common;

public enum ResultCode {
    SUCCESS(200, "操作成功"),

    // 新增的注册登录相关状态码
    USER_HAS_EXISTED(4001, "该用户名已被注册"),
    USER_NOT_EXIST(4002, "该用户不存在"),
    PASSWORD_ERROR(4003, "账号或密码错误"),

    // 原有状态码保持不变
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有权限访问");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}