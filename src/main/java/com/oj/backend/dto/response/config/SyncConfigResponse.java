package com.oj.backend.dto.response.config;

import com.oj.backend.config.enums.CodeLang;
import com.oj.backend.config.enums.Difficulty;
import com.oj.backend.config.enums.UserLang;
import com.oj.backend.config.enums.Verdict;
import com.oj.backend.pojo.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * The type Sync config response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncConfigResponse {
    private Map<Integer, Tag> tags;
    private Map<String, UserLang> userLangs;
    private Map<String, CodeLang> codeLangs;
    private Map<String, Verdict> verdicts;
    private List<Difficulty> difficulties;
}
