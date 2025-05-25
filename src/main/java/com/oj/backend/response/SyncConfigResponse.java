package com.oj.backend.response;

import com.oj.backend.config.CodeLang;
import com.oj.backend.config.Difficulty;
import com.oj.backend.config.UserLang;
import com.oj.backend.config.Verdict;
import com.oj.backend.pojo.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

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
