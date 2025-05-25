package com.oj.backend.service.config;

import com.oj.backend.dto.response.config.SyncConfigResponse;

public interface ConfigService {
    SyncConfigResponse getConfig();
}
