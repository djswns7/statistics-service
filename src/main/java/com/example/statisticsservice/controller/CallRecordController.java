package com.example.statisticsservice.controller;

import com.example.statisticsservice.domain.CallRecord;
import com.example.statisticsservice.response.CallTimePerDateRes;
import com.example.statisticsservice.service.CallRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/records")
public class CallRecordController {

    private final CallRecordService callRecordService;

    @Autowired
    public CallRecordController(CallRecordService callRecordService) {
        this.callRecordService = callRecordService;
    }


    public static class RecordReq {
        private LocalDateTime createdTime;
        private Long callDuration; // 초 단위

    }

/*  ToDo
    @GetMapping("/{phoneNumber}")
    public CallTimePerDateRes getCallTimePerDate(@PathVariable String phoneNumber) {
        callRecordService.getAllrecords();

        CallTimePerDateRes res = CallTimePerDateRes();
        return res;
    }

    @GetMapping("records/{index}")
    public String read(@PathVariable String index) {

    }
*/

//    @PutMapping("records/{index}")
//    public String update(@PathVariable String index) {
//
//    }
//
//    @DeleteMapping("records/{index}")
//    public String delete(@PathVariable String index) {
//
//    }



    @GetMapping("/t")
    public String wj_test() {
        // data 추가
        
        // 추가한 데이터 로드
        Optional<CallRecord> c = callRecordService.getRecordById(333L);
        System.out.println("로드한 데이터: "+c.toString());
        System.out.println(Arrays.toString(callRecordService.getTopRecordsByCallDuration().getFirst()));
        //추가한 데이터 제거


        return "test complete!";
    }

}
