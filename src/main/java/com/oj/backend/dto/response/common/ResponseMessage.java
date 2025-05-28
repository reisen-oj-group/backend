package com.oj.backend.dto.response.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Response message.
 *
 * @param <T> the type parameter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // 只序列化非null字段
public class ResponseMessage<T> {

    private int code;           // 状态码
    private String message;     // 提示信息
    private T data;             // 返回数据

    /**
     * Success response message.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the response message
     */
    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage<T>(200, "成功", data);
    }

    /**
     * Error response message.
     *
     * @param <T>     the type parameter
     * @param message the message
     * @return the response message
     */
    public static <T> ResponseMessage<T> error(String message) {
        return new ResponseMessage<T>(500, message, null);
    }

    /**
     * Register success response message.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the response message
     */
    public static <T> ResponseMessage<T> registerSuccess(T data) {
        return new ResponseMessage<T>(200, "注册成功", data);
    }

    /**
     * Register error response message.
     *
     * @param <T>     the type parameter
     * @param message the message
     * @return the response message
     */
    public static <T> ResponseMessage<T> registerError(String message) {
        return new ResponseMessage<T>(500, message, null);
    }

    /**
     * Problem update success response message.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the response message
     */
    public static <T> ResponseMessage<T> problemUpdateSuccess(T data) {
        return new ResponseMessage<T>(200, "更新成功", data);
    }

    /**
     * Problem update error response message.
     *
     * @param <T>     the type parameter
     * @param message the message
     * @return the response message
     */
    public static <T> ResponseMessage<T> problemUpdateError(String message) {
        return new ResponseMessage<T>(500, message, null);
    }

    /**
     * Problem list get success response message.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the response message
     */
    public static <T> ResponseMessage<T> problemListGetSuccess(T data) {
        return new ResponseMessage<T>(200, "列表获取成功", data);
    }

    /**
     * Login success response message.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the response message
     */
    public static <T> ResponseMessage<T> loginSuccess(T data) {
        return new ResponseMessage<T>(200, "登录成功", data);
    }

    /**
     * Login error response message.
     *
     * @param <T>     the type parameter
     * @param message the message
     * @return the response message
     */
    public static <T> ResponseMessage<T> loginError(String message) {
        return new ResponseMessage<T>(500, message, null);
    }

    /**
     * Config success response message.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the response message
     */
    public static <T> ResponseMessage<T> configSuccess(T data) {
        return new ResponseMessage<T>(200, "配置导入成功", data);
    }

    /**
     * Config error response message.
     *
     * @param <T>     the type parameter
     * @param message the message
     * @return the response message
     */
    public static <T> ResponseMessage<T> configError(String message) {
        return new ResponseMessage<T>(500, message, null);
    }


}
