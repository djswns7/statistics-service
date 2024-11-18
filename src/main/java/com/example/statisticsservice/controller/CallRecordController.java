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
    @GetMapping("/call-time/daily")
    public List<CallTimePerDateRes> getOverallDailyCallTime() {
        return callRecordService.getOverallDailyCallTime();
    }

    @GetMapping("/call-time/weekly")
    public List<CallTimePerWeekRes> getOverallWeeklyCallTime() {
        return callRecordService.getOverallWeeklyCallTime();
    }

    @GetMapping("/call-time/monthly")
    public List<CallTimePerMonthRes> getOverallMonthlyCallTime() {
        return callRecordService.getOverallMonthlyCallTime();
    }

    @GetMapping("/call-time/period")
    public List<CallTimePerDateRes> getOverallPeriodCallTime(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) {
        return callRecordService.getOverallPeriodCallTime(startDate, endDate);
    }

    // 전체 통계 데이터 call-count 조회
    @GetMapping("/call-counts/daily")
    public List<CallCountPerDateRes> getOverallDailyCallCount() {
        return callRecordService.getOverallDailyCallCount();
    }

    @GetMapping("/call-counts/weekly")
    public List<CallCountPerWeekRes> getOverallWeeklyCallCount() {
        return callRecordService.getOverallWeeklyCallCount();
    }

    @GetMapping("/call-counts/monthly")
    public List<CallCountPerMonthRes> getOverallMonthlyCallCount() {
        return callRecordService.getOverallMonthlyCallCount();
    }

    @GetMapping("/call-counts/period")
    public List<CallCountPerDateRes> getOverallPeriodCallCount(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) {
        return callRecordService.getOverallPeriodCallCount(startDate, endDate);
    }

    // 학교 이름 별 call-time 조회
    @GetMapping("/schools/{school-name}/call-time/daily")
    public List<CallTimePerDateRes> getOverallDailyCallTimeBySchool(
            @PathVariable(value = "school-name") String schoolName
    ) {
        return callRecordService.getOverallDailyCallTimeBySchool(schoolName);
    }

    @GetMapping("/schools/{school-name}/call-time/weekly")
    public List<CallTimePerWeekRes> getOverallWeeklyCallTimeBySchool(
            @PathVariable(value = "school-name") String schoolName
    ) {
        return callRecordService.getOverallWeeklyCallTimeBySchool(schoolName);
    }

    @GetMapping("/schools/{school-name}/call-time/monthly")
    public List<CallTimePerMonthRes> getOverallMonthlyCallTimeBySchool(
            @PathVariable(value = "school-name") String schoolName
    ) {
        return callRecordService.getOverallMonthlyCallTimeBySchool(schoolName);
    }

    @GetMapping("/schools/{school-name}/call-time/period")
    public List<CallTimePerDateRes> getOverallPeriodCallTimeBySchool(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @PathVariable(value = "school-name") String schoolName) {
        return callRecordService.getOverallPeriodCallTimeBySchool(startDate, endDate, schoolName);
    }

    // 학교 이름 별 call-count 조회
    @GetMapping("/schools/{school-name}/call-counts/daily")
    public List<CallCountPerDateRes> getDailyCallCountBySchool(
            @PathVariable(value = "school-name") String schoolName
    ) {
        return callRecordService.getDailyCallCountBySchool(schoolName);
    }

    @GetMapping("/schools/{school-name}/call-counts/weekly")
    public List<CallCountPerWeekRes> getWeeklyCallCountBySchool(
            @PathVariable(value = "school-name") String schoolName
    ) {
        return callRecordService.getWeeklyCallCountBySchool(schoolName);
    }

    @GetMapping("/schools/{school-name}/call-counts/monthly")
    public List<CallCountPerMonthRes> getMonthlyCallCountBySchool(
            @PathVariable(value = "school-name") String schoolName
    ) {
        return callRecordService.getMonthlyCallCountBySchool(schoolName);
    }

    @GetMapping("/schools/{school-name}/call-counts/period")
    public List<CallCountPerDateRes> getPeriodCallCountBySchool(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @PathVariable(value = "school-name") String schoolName) {
        return callRecordService.getPeriodCallCountBySchool(startDate, endDate, schoolName);
    }

    // 학교 별 가장 많은 통화량 또는 통화 횟수 별 정렬 값 반환
    // order 값은 asc 또는 desc(기본값)
    @GetMapping("/schools/call-time")
    public List<CallTimePerSchoolRes> getCallTimeBySchool(
            @RequestParam(name = "order", defaultValue = "desc"
            ) String order) {
        validateOrderParameter(order);
        return callRecordService.getCallTimeBySchool(order);
    }

    @GetMapping("/schools/call-count")
    public List<CallCountPerSchoolRes> getCallCountBySchool(
            @RequestParam(name = "order", defaultValue = "desc"
            ) String order) {
        validateOrderParameter(order);
        return callRecordService.getCallCountBySchool(order);
    }


    // 전화번호 별 call-time 조회
    @GetMapping("/phones/call-time/daily")
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

    @GetMapping("/phones/call-time/weekly")
    public List<CallTimePerWeekRes> getOverallWeeklyCallTimeByPhone(
            @RequestBody PhoneNumReq phoneNumReq
    ) {
        return callRecordService.getOverallWeeklyCallTimeByPhone(phoneNumReq.getPhoneNumber());
    }

    @GetMapping("/phones/call-time/monthly")
    public List<CallTimePerMonthRes> getOverallMonthlyCallTimeByPhone(
            @RequestBody PhoneNumReq phoneNumReq
    ) {
        return callRecordService.getOverallMonthlyCallTimeByPhone(phoneNumReq.getPhoneNumber());
    }

    @GetMapping("/phones/call-time/period")
    public List<CallTimePerDateRes> getOverallPeriodCallTimeByPhone(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestBody PhoneNumReq phoneNumReq
    ) {
        return callRecordService.getOverallPeriodCallTimeByPhone(startDate, endDate, phoneNumReq.getPhoneNumber());
    }

    // 전화번호 별 call-count 조회
    @GetMapping("/phones/call-counts/daily")
    public List<CallCountPerDateRes> getDailyCallCountByPhone(@RequestBody PhoneNumReq phoneNumReq) {
        return callRecordService.getDailyCallCountByPhone(phoneNumReq.getPhoneNumber().trim());
    }

    @GetMapping("/phones/call-counts/weekly")
    public List<CallCountPerWeekRes> getWeeklyCallCountByPhone(@RequestBody PhoneNumReq phoneNumReq) {
        return callRecordService.getWeeklyCallCountByPhone(phoneNumReq.getPhoneNumber());
    }

    @GetMapping("/phones/call-counts/monthly")
    public List<CallCountPerMonthRes> getMonthlyCallCountByPhone(@RequestBody PhoneNumReq phoneNumReq) {
        return callRecordService.getMonthlyCallCountByPhone(phoneNumReq.getPhoneNumber());
    }

    @GetMapping("/phones/call-counts/period")
    public List<CallCountPerDateRes> getPeriodCallCountByPhone(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestBody PhoneNumReq phoneNumReq) {
        return callRecordService.getPeriodCallCountByPhone(startDate, endDate, phoneNumReq.getPhoneNumber());
    }

    // 번호 별 가장 많은 통화량 또는 통화 횟수 별 정렬 값 반환
    // order 값은 asc 또는 desc(기본값)
    @GetMapping("/phones/call-time")
    public List<CallTimePerPhoneNumRes> getCallTimeByPhoneNum(
            @RequestParam(name = "order", defaultValue = "desc") String order
    ) {
        validateOrderParameter(order);
        return callRecordService.getCallTimeByPhoneNum(order);
    }

    @GetMapping("/phones/call-count")
    public List<CallCountPerPhoneNumRes> getCallCountByPhoneNum(
            @RequestParam(name = "order", defaultValue = "desc") String order
    ) {
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
