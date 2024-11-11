package com.example.statisticsservice.controller;

import com.example.statisticsservice.domain.CallRecord;
import com.example.statisticsservice.response.CallTimePerDateRes;
import com.example.statisticsservice.response.CallTimePerMonthRes;
import com.example.statisticsservice.response.CallTimePerWeekRes;
import com.example.statisticsservice.service.CallRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@RestController
@RequestMapping("/call-records")
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
    // 전체 통계 데이터 조회
    @GetMapping("/overall/daily")
    public List<CallTimePerDateRes> getOverallDailyStatistics() {
        return callRecordService.getOverallDailyStatistics();
    }

    @GetMapping("/overall/weekly")
    public List<CallTimePerWeekRes> getOverallWeeklyStatistics() {
        return callRecordService.getOverallWeeklyStatistics();
    }

    @GetMapping("/overall/monthly")
    public List<CallTimePerMonthRes> getOverallMonthlyStatistics() {
        return callRecordService.getOverallMonthlyStatistics();
    }

    @GetMapping("/overall/period")
    public List<CallTimePerDateRes> getOverallPeriodStatistics(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return callRecordService.getOverallPeriodStatistics(startDate, endDate);
    }

//    // 기간 별 통화량/건수
//    @GetMapping("/statistics/daily")
//    public List<CallStatisticsResponse> getDailyStatistics() {
//        return callRecordService.getDailyStatistics();
//    }

//    @GetMapping("/statistics/weekly")
//    public List<CallStatisticsResponse> getWeeklyStatistics() {
//        return callRecordService.getWeeklyStatistics();
//    }

//    @GetMapping("/statistics/monthly")
//    public List<CallStatisticsResponse> getMonthlyStatistics() {
//        return callRecordService.getMonthlyStatistics();
//    }

//    @GetMapping("/statistics/period")
//    public List<CallStatisticsResponse> getPeriodStatistics(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
//        return callRecordService.getPeriodStatistics(startDate, endDate);
//    }


//    // 전화번호 별 통계 조회
//    @GetMapping("/statistics/phone/{phoneNumber}/daily")
//    public List<CallStatisticsResponse> getPhoneDailyStatistics(@PathVariable String phoneNumber) {
//        return callRecordService.getPhoneDailyStatistics(phoneNumber);
//    }
//
//    @GetMapping("/statistics/phone/{phoneNumber}/weekly")
//    public List<CallStatisticsResponse> getPhoneWeeklyStatistics(@PathVariable String phoneNumber) {
//        return callRecordService.getPhoneWeeklyStatistics(phoneNumber);
//    }
//
//    @GetMapping("/statistics/phone/{phoneNumber}/monthly")
//    public List<CallStatisticsResponse> getPhoneMonthlyStatistics(@PathVariable String phoneNumber) {
//        return callRecordService.getPhoneMonthlyStatistics(phoneNumber);
//    }
//
//    @GetMapping("/statistics/phone/{phoneNumber}/period")
//    public List<CallStatisticsResponse> getPhonePeriodStatistics(@PathVariable String phoneNumber, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
//        return callRecordService.getPhonePeriodStatistics(phoneNumber, startDate, endDate);
//    }
//
//    @GetMapping("/statistics/phone/top")
//    public List<PhoneStatisticsResponse> getTopPhoneStatistics(@RequestParam(defaultValue = "callVolume") String sortBy,
//                                                               @RequestParam(defaultValue = "0") int page,
//                                                               @RequestParam(defaultValue = "10") int size) {
//        return callRecordService.getTopPhoneStatistics(sortBy, page, size);
//    }


    /*
//    @PutMapping("records/{index}")
//    public String update(@PathVariable String index) {
//
//    }
//
//    @DeleteMapping("records/{index}")
//    public String delete(@PathVariable String index) {
//
//    }
*/


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
