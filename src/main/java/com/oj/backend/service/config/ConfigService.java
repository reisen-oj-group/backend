package com.oj.backend.service.config;

import com.oj.backend.response.ResponseMessage;
import com.oj.backend.response.SyncConfigResponse;

public interface ConfigService {
    SyncConfigResponse getConfig();
}
