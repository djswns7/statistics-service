package com.example.statisticsservice.controller;

import com.example.statisticsservice.request.PhoneNumReq;
import com.example.statisticsservice.response.*;
import com.example.statisticsservice.response.CallCountPerDateRes;
import com.example.statisticsservice.response.CallCountPerMonthRes;
import com.example.statisticsservice.response.CallCountPerWeekRes;
import com.example.statisticsservice.service.CallRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.statisticsservice.utility.ParameterValidator.validateOrderParameter;

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

    // 전체 통계 데이터 call-time 조회
    @GetMapping("/overall-call-time/daily")
    public List<CallTimePerDateRes> getOverallDailyCallTime() {
        return callRecordService.getOverallDailyCallTime();
    }

    @GetMapping("/overall-call-time/weekly")
    public List<CallTimePerWeekRes> getOverallWeeklyCallTime() {
        return callRecordService.getOverallWeeklyCallTime();
    }

    @GetMapping("/overall-call-time/monthly")
    public List<CallTimePerMonthRes> getOverallMonthlyCallTime() {
        return callRecordService.getOverallMonthlyCallTime();
    }

    @GetMapping("/overall-call-time/period")
    public List<CallTimePerDateRes> getOverallPeriodCallTime(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) {
        return callRecordService.getOverallPeriodCallTime(startDate, endDate);
    }

    // 전체 통계 데이터 call-count 조회
    @GetMapping("/overall-call-counts/daily")
    public List<CallCountPerDateRes> getOverallDailyCallCount() {
        return callRecordService.getOverallDailyCallCount();
    }

    @GetMapping("/overall-call-counts/weekly")
    public List<CallCountPerWeekRes> getOverallWeeklyCallCount() {
        return callRecordService.getOverallWeeklyCallCount();
    }

    @GetMapping("/overall-call-counts/monthly")
    public List<CallCountPerMonthRes> getOverallMonthlyCallCount() {
        return callRecordService.getOverallMonthlyCallCount();
    }

    @GetMapping("/overall-call-counts/period")
    public List<CallCountPerDateRes> getOverallPeriodCallCount(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) {
        return callRecordService.getOverallPeriodCallCount(startDate, endDate);
    }

    // 학교 이름 별 call-time 조회
    @GetMapping("/overall-call-time/school/daily")
    public List<CallTimePerDateRes> getOverallDailyCallTimeBySchool(
            @RequestParam(value = "schoolName")String schoolName
    ) {
        return callRecordService.getOverallDailyCallTimeBySchool(schoolName);
    }

    @GetMapping("/overall-call-time/school/weekly")
    public List<CallTimePerWeekRes> getOverallWeeklyCallTimeBySchool(
            @RequestParam(value = "schoolName") String schoolName
    ) {
        return callRecordService.getOverallWeeklyCallTimeBySchool(schoolName);
    }

    @GetMapping("/overall-call-time/school/monthly")
    public List<CallTimePerMonthRes> getOverallMonthlyCallTimeBySchool(
            @RequestParam(value = "schoolName") String schoolName
    ) {
        return callRecordService.getOverallMonthlyCallTimeBySchool(schoolName);
    }

    @GetMapping("/overall-call-time/school/period")
    public List<CallTimePerDateRes> getOverallPeriodCallTimeBySchool(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "schoolName") String schoolName) {
        return callRecordService.getOverallPeriodCallTimeBySchool(startDate, endDate, schoolName);
    }

    // 학교 이름 별 call-count 조회
    @GetMapping("/school/daily")
    public List<CallCountPerDateRes> getDailyCallCountBySchool(@RequestParam(value = "schoolName") String schoolName) {
        return callRecordService.getDailyCallCountBySchool(schoolName);
    }

    @GetMapping("/school/weekly")
    public List<CallCountPerWeekRes> getWeeklyCallCountBySchool(@RequestParam(value = "schoolName") String schoolName) {
        return callRecordService.getWeeklyCallCountBySchool(schoolName);
    }

    @GetMapping("/school/monthly")
    public List<CallCountPerMonthRes> getMonthlyCallCountBySchool(@RequestParam(value = "schoolName") String schoolName) {
        return callRecordService.getMonthlyCallCountBySchool(schoolName);
    }

    @GetMapping("/school/period")
    public List<CallCountPerDateRes> getPeriodCallCountBySchool(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "schoolName") String schoolName) {
        return callRecordService.getPeriodCallCountBySchool(startDate, endDate, schoolName);
    }



    // 전화번호 별 call-time 조회
    @GetMapping("/overall-call-time/phone/daily")
    public List<CallTimePerDateRes> getOverallDailyCallTimeByPhone(
            @RequestBody PhoneNumReq phoneNumReq
            ) {
        return callRecordService.getOverallDailyCallTimeByPhone(phoneNumReq.getPhoneNumber().trim());
    }

//    @GetMapping("/overall-call-time/phone/daily")
//    public List<CallTimePerDateRes> getOverallDailyCallTimeByPhone(
//            @RequestParam(value = "phoneNumber") String phoneNumber
//    ) {
//        return callRecordService.getOverallDailyCallTimeByPhone(phoneNumber);
//    }

    @GetMapping("/overall-call-time/phone/weekly")
    public List<CallTimePerWeekRes> getOverallWeeklyCallTimeByPhone(
            @RequestBody PhoneNumReq phoneNumReq
    ) {
        return callRecordService.getOverallWeeklyCallTimeByPhone(phoneNumReq.getPhoneNumber());
    }

    @GetMapping("/overall-call-time/phone/monthly")
    public List<CallTimePerMonthRes> getOverallMonthlyCallTimeByPhone(
            @RequestBody PhoneNumReq phoneNumReq
    ) {
        return callRecordService.getOverallMonthlyCallTimeByPhone(phoneNumReq.getPhoneNumber());
    }

    @GetMapping("/overall-call-time/phone/period")
    public List<CallTimePerDateRes> getOverallPeriodCallTimeByPhone(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestBody PhoneNumReq phoneNumReq
    ) {
        return callRecordService.getOverallPeriodCallTimeByPhone(startDate, endDate, phoneNumReq.getPhoneNumber());
    }

    // 전화번호 별 call-count 조회
    @GetMapping("/phone/daily")
    public List<CallCountPerDateRes> getDailyCallCountByPhone(@RequestBody PhoneNumReq phoneNumReq) {
        return callRecordService.getDailyCallCountByPhone(phoneNumReq.getPhoneNumber().trim());
    }

    @GetMapping("/phone/weekly")
    public List<CallCountPerWeekRes> getWeeklyCallCountByPhone(@RequestBody PhoneNumReq phoneNumReq) {
        return callRecordService.getWeeklyCallCountByPhone(phoneNumReq.getPhoneNumber());
    }

    @GetMapping("/phone/monthly")
    public List<CallCountPerMonthRes> getMonthlyCallCountByPhone(@RequestBody PhoneNumReq phoneNumReq) {
        return callRecordService.getMonthlyCallCountByPhone(phoneNumReq.getPhoneNumber());
    }

    @GetMapping("/phone/period")
    public List<CallCountPerDateRes> getPeriodCallCountByPhone(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestBody PhoneNumReq phoneNumReq) {
        return callRecordService.getPeriodCallCountByPhone(startDate, endDate, phoneNumReq.getPhoneNumber());
    }

    // 학교 또는 번호 별 가장 많은 통화량 또는 통화 횟수 별 정렬 값 반환
    // order 값은 asc 또는 desc(기본값)
    @GetMapping("/school/call-time")
    public List<CallTimePerSchoolRes> getCallTimeBySchool(@RequestParam(name = "order", defaultValue = "desc") String order) {
        validateOrderParameter(order);
        return callRecordService.getCallTimeBySchool(order);
    }

    @GetMapping("/school/call-count")
    public List<CallCountPerSchoolRes> getCallCountBySchool(@RequestParam(name = "order", defaultValue = "desc") String order) {
        validateOrderParameter(order);
        return callRecordService.getCallCountBySchool(order);
    }

    @GetMapping("/phone/call-time")
    public List<CallTimePerPhoneNumRes> getCallTimeByPhoneNum(@RequestParam(name = "order", defaultValue = "desc") String order) {
        validateOrderParameter(order);
        return callRecordService.getCallTimeByPhoneNum(order);
    }

    @GetMapping("/phone/call-count")
    public List<CallCountPerPhoneNumRes> getCallCountByPhoneNum(@RequestParam(name = "order", defaultValue = "desc") String order) {
        validateOrderParameter(order);
        return callRecordService.getCallCountByPhoneNum(order);
    }



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


        //추가한 데이터 제거


        return "test fin!";
    }

}
