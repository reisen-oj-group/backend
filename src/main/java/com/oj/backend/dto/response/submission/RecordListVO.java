package com.oj.backend.dto.response.submission;

import com.oj.backend.pojo.submission.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordListVO {
    private Integer total;
    private List<Record> records;
}
