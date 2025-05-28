package com.oj.backend.dto.response.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oj.backend.controller.config.ConfigController;
import com.oj.backend.pojo.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一API响应消息封装类.
 * <p>所有Controller层接口必须使用本类作为返回值类型</p>
 *
 * @param <T> 响应数据泛型类型
 * @see com.oj.backend.dto.response.common 所在包路径
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage<T> {
    /**
     * HTTP状态码
     */
    private int code;

    /**
     * 业务提示信息
     */
    private String message;

    /**
     * 响应业务数据（泛型）
     * <p>当请求失败时应返回null</p>
     */
    private T data;

    // ===================== 基础响应方法 =====================
    /**
     * 创建成功响应（通用）
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 标准成功响应(200)
     */
    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage<>(200, "成功", data);
    }

    /**
     * 创建错误响应（通用）
     * @param message 错误描述
     * @param <T> 数据类型
     * @return 标准错误响应(500)
     */
    public static <T> ResponseMessage<T> error(String message) {
        return new ResponseMessage<>(500, message, null);
    }

    // ===================== 注册相关响应 =====================
    /**
     * 注册成功专用响应
     * @param data 用户数据
     * @param <T> 数据类型
     * @return 注册成功响应(200)
     * @see com.oj.backend.controller.user.UserController#insert(User)
     */
    public static <T> ResponseMessage<T> registerSuccess(T data) {
        return new ResponseMessage<>(200, "注册成功", data);
    }

    /**
     * 注册失败专用响应
     * @param message 失败原因
     * @param <T> 数据类型
     * @return 注册失败响应(500)
     */
    public static <T> ResponseMessage<T> registerError(String message) {
        return new ResponseMessage<>(500, message, null);
    }

    // ===================== 题目相关响应 =====================
    /**
     * 题目更新成功响应
     * @param data 更新后的题目数据
     * @param <T> 数据类型
     * @return 更新成功响应(200)
     * @see com.oj.backend.controller.problem.ProblemController#updateProblem
     */
    public static <T> ResponseMessage<T> problemUpdateSuccess(T data) {
        return new ResponseMessage<>(200, "更新成功", data);
    }

    /**
     * 题目更新失败响应
     * @param message 失败原因
     * @param <T> 数据类型
     * @return 更新失败响应(500)
     */
    public static <T> ResponseMessage<T> problemUpdateError(String message) {
        // return new ResponseMessage<T>(500, message, null);警告显式类型实参 T 可被替换为 <>，使用钻石操作符，下面同理
        return new ResponseMessage<>(500, message, null);
    }

    /**
     * 题目列表获取成功响应
     * @param data 题目列表数据
     * @param <T> 数据类型
     * @return 获取成功响应(200)
     * @see com.oj.backend.controller.problem.ProblemController#getProblemList
     */
    public static <T> ResponseMessage<T> problemListGetSuccess(T data) {
        return new ResponseMessage<>(200, "列表获取成功", data);
    }

    // ===================== 登录相关响应 =====================
    /**
     * 登录成功响应
     * @param data 用户凭证数据
     * @param <T> 数据类型
     * @return 登录成功响应(200)
     * @see com.oj.backend.controller.user.UserController#login
     */
    public static <T> ResponseMessage<T> loginSuccess(T data) {
        return new ResponseMessage<>(200, "登录成功", data);
    }

    /**
     * 登录失败响应
     * @param message 失败原因
     * @param <T> 数据类型
     * @return 登录失败响应(500)
     */
    public static <T> ResponseMessage<T> loginError(String message) {
        return new ResponseMessage<>(500, message, null);
    }

    // ===================== 配置相关响应 =====================
    /**
     * 配置导入成功响应
     * @param data 导入结果数据
     * @param <T> 数据类型
     * @return 导入成功响应(200)
     * @see ConfigController#syncConfig()
     */
    public static <T> ResponseMessage<T> configSuccess(T data) {
        return new ResponseMessage<>(200, "配置导入成功", data);
    }

    /**
     * 配置导入失败响应
     * @param message 失败原因
     * @param <T> 数据类型
     * @return 导入失败响应(500)
     */
    public static <T> ResponseMessage<T> configError(String message) {
        return new ResponseMessage<>(500, message, null);
    }
}

