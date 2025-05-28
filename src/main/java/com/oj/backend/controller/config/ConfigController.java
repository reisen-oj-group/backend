package com.oj.backend.controller.config;

import com.oj.backend.dto.response.config.SyncConfigResponse;
import com.oj.backend.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置管理API控制器.
 *
 * <p><strong>功能说明：</strong>
 * <ul>
 *   <li>提供配置同步接口</li>
 *   <li>自动注入 {@link ConfigService} 服务</li>
 *   <li>默认启用跨域支持</li>
 * </ul>
 *
 * <p><strong>接口路径：</strong>
 * 基础路径 {@code /api} 下提供配置操作接口
 *
 * @see RestController Spring REST控制器
 * @see CrossOrigin 跨域支持注解
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ConfigController {
    /**
     * 配置服务依赖注入.
     *
     * <p>通过Spring自动注入 {@link ConfigService} 实例
     */
    @Autowired
    ConfigService configService;

    // 历史实现（保留注释作为演进记录）
    //    @GetMapping("/sync-config")
    //    public ResponseMessage<SyncConfigResponse> syncConfig(){
    //        return ResponseMessage.configSuccess(configService.getConfig());
    //    }

    /**
     * 获取同步配置.
     *
     * <p><strong>接口说明：</strong>
     * <ul>
     *   <li>当前版本直接返回 {@link SyncConfigResponse} 原始数据结构</li>
     *   <li>历史版本曾使用 {@code ResponseMessage} 包装响应（见注释代码）</li>
     * </ul>
     *
     * @return 完整的同步配置对象
     * @see GetMapping GET请求映射
     */
    @GetMapping("/sync-config")
    public SyncConfigResponse syncConfig(){
        return configService.getConfig();
    }
}
