package com.oj.backend.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // 只序列化非null字段
public class ResponseMessage<T> {

    private int code;           // 状态码
    private String message;     // 提示信息
    private T data;             // 返回数据

    public static <T> ResponseMessage<T> registerSuccess(T data) {
        return new ResponseMessage<T>(200, "注册成功", data);
    }

    public static <T> ResponseMessage<T> registerError(String message) {
        return new ResponseMessage<T>(500, message, null);
    }

    public static <T> ResponseMessage<T> problemUpdateSuccess(T data){
        return new ResponseMessage<T>(200, "更新成功", data);
    }

    public static <T> ResponseMessage<T> problemUpdateError(String message) {
        return new ResponseMessage<T>(500, message, null);
    }

    public static <T> ResponseMessage<T> problemListGetSuccess(T data){
        return new ResponseMessage<T>(200, "列表获取成功", data);
    }

    public static <T> ResponseMessage<T> configSuccess(T data) {
        return new ResponseMessage<T>(200, "配置导入成功", data);
    }

    public static <T> ResponseMessage<T> configError(String message) {
        return new ResponseMessage<T>(500, message, null);
    }
}
