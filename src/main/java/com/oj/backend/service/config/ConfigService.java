package com.oj.backend.service.config;

import com.oj.backend.dto.response.config.SyncConfigResponse;

/**
 * The interface Config service.
 */
public interface ConfigService {
    /**
     * Gets config.
     *
     * @return the config
     */
    SyncConfigResponse getConfig();
}
