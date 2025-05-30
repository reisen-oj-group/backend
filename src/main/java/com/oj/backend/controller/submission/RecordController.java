package com.oj.backend.controller.submission;

import com.oj.backend.dto.request.submission.RecordDTO;
import com.oj.backend.dto.request.submission.RecordFilter;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.dto.response.submission.RecordListVO;
import com.oj.backend.dto.response.submission.SubmissionVO;
import com.oj.backend.service.submission.SubmissionService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseMessage<SubmissionVO> returnRecord(
            HttpServletRequest request,
            @RequestBody RecordDTO recordDTO
    ){
        Integer userId = (Integer) request.getAttribute("userId");
        return submissionService.returnRecord(recordDTO, userId);
    }


    @PostMapping("/list")
    public ResponseMessage<RecordListVO> returnRecordList(
            HttpServletRequest request,
            @RequestBody RecordFilter recordFilter
    ){
        Integer userId = (Integer) request.getAttribute("userId");
        return submissionService.returnRecordList(recordFilter, userId);
    }
}
