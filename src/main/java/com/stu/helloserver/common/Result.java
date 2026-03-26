package com.stu.helloserver.common;

/**
 * @param <T> 响应数据类型
 */
public class Result<T> {

    // 私有字段
    private Integer code;   // 状态码
    private String msg;     // 提示信息
    private T data;         // 泛型数据载荷

    // 1. 无参构造器
    public Result() {
    }

    // 2. 有参构造器
    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 3. 【核心】Getter 和 Setter 方法
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 4. 【核心】静态工厂方法：成功回调
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = ResultCode.SUCCESS.getCode();  // 从ResultCode枚举获取成功码
        result.msg = ResultCode.SUCCESS.getMsg();    // 获取成功信息
        result.data = data;                          // 设置业务数据
        return result;
    }

    // 5. 【核心】静态工厂方法：失败回调
    public static <T> Result<T> error(ResultCode resultCode) {
        Result<T> result = new Result<>();
        result.code = resultCode.getCode();  // 从ResultCode枚举获取错误码
        result.msg = resultCode.getMsg();    // 获取错误信息
        result.data = null;                  // 失败时数据为null
        return result;
    }
}