package com.oj.backend.controller.submission;

import com.oj.backend.dto.request.submission.RecordDTO;
import com.oj.backend.dto.request.submission.RecordFilter;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.dto.response.submission.RecordListVO;
import com.oj.backend.dto.response.submission.SubmissionVO;
import com.oj.backend.service.submission.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Record controller.
 */
@RestController
@RequestMapping("/api/record")
@CrossOrigin
public class RecordController {
    /**
     * The Submission service.
     */
    @Autowired
    SubmissionService submissionService;

    @PostMapping("")
    public ResponseMessage<SubmissionVO> returnRecord(@RequestBody RecordDTO recordDTO){
        return submissionService.returnRecord(recordDTO);
    }


    @PostMapping("/list")
    public ResponseMessage<RecordListVO> returnRecordList(@RequestBody RecordFilter recordFilter){
        return submissionService.returnRecordList(recordFilter);
    }
}
