package com.oj.backend.controller.config;

import com.oj.backend.dto.response.config.SyncConfigResponse;
import com.oj.backend.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ConfigController {
    @Autowired
    ConfigService configService;

    // 包装配置文件
//    @GetMapping("/sync-config")
//    public ResponseMessage<SyncConfigResponse> syncConfig(){
//        return ResponseMessage.configSuccess(configService.getConfig());
//    }

    @GetMapping("/sync-config")
    public SyncConfigResponse syncConfig(){
        return configService.getConfig();
    }
}
