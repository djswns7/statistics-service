package com.example.statisticsservice.controller;

import com.example.statisticsservice.request.PhoneNumReq;
import com.example.statisticsservice.response.*;
import com.example.statisticsservice.response.CallCountPerDateRes;
import com.example.statisticsservice.response.CallCountPerMonthRes;
import com.example.statisticsservice.response.CallCountPerWeekRes;
import com.example.statisticsservice.service.CallRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    // Pageable 클래스 사용시 RequestParameter 에 page, size 값을 넘겨줄 수 있음
    @GetMapping("/call-time/daily")
    public Page<CallTimePerDateRes> getOverallDailyCallTime(Pageable pageable) {
        return callRecordService.getOverallDailyCallTime(pageable);
    }

    // 다음 방식처럼 직접 Pageable 객체를 생성하는 방식도 존재
    @GetMapping("/call-time/weekly")
    public Page<CallTimePerWeekRes> getOverallWeeklyCallTime(
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="size", defaultValue = "10") int size
    ) {
        
        Pageable pageable = PageRequest.of(page, size);
        return callRecordService.getOverallWeeklyCallTime(pageable);
    }

    @GetMapping("/call-time/monthly")
    public Page<CallTimePerMonthRes> getOverallMonthlyCallTime(Pageable pageable) {
        return callRecordService.getOverallMonthlyCallTime(pageable);
    }

    @GetMapping("/call-time/period")
    public Page<CallTimePerDateRes> getOverallPeriodCallTime(
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate,
            Pageable pageable) {
        return callRecordService.getOverallPeriodCallTime(startDate, endDate, pageable);
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

    // swagger ui 테스트시 전체범위설정하면 오류남(부하 때문인듯?)
    // 많은 값 불러올 시에는 postman으로 테스트할것
    @GetMapping("/call-counts/period")
    public List<CallCountPerDateRes> getOverallPeriodCallCount(
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate) {
        return callRecordService.getOverallPeriodCallCount(startDate, endDate);
    }

    // 학교 이름 별 call-time 조회
    @GetMapping("/schools/{school-name}/call-time/daily")
    public List<CallTimePerDateRes> getOverallDailyCallTimeBySchool(
            @PathVariable(name = "school-name") String schoolName
    ) {
        return callRecordService.getOverallDailyCallTimeBySchool(schoolName);
    }

    @GetMapping("/schools/{school-name}/call-time/weekly")
    public List<CallTimePerWeekRes> getOverallWeeklyCallTimeBySchool(
            @PathVariable(name = "school-name") String schoolName
    ) {
        return callRecordService.getOverallWeeklyCallTimeBySchool(schoolName);
    }

    @GetMapping("/schools/{school-name}/call-time/monthly")
    public List<CallTimePerMonthRes> getOverallMonthlyCallTimeBySchool(
            @PathVariable(name = "school-name") String schoolName
    ) {
        return callRecordService.getOverallMonthlyCallTimeBySchool(schoolName);
    }

    @GetMapping("/schools/{school-name}/call-time/period")
    public List<CallTimePerDateRes> getOverallPeriodCallTimeBySchool(
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate,
            @PathVariable(name = "school-name") String schoolName) {
        return callRecordService.getOverallPeriodCallTimeBySchool(startDate, endDate, schoolName);
    }

    // 학교 이름 별 call-count 조회
    @GetMapping("/schools/{school-name}/call-counts/daily")
    public List<CallCountPerDateRes> getDailyCallCountBySchool(
            @PathVariable(name = "school-name") String schoolName
    ) {
        return callRecordService.getDailyCallCountBySchool(schoolName);
    }

    @GetMapping("/schools/{school-name}/call-counts/weekly")
    public List<CallCountPerWeekRes> getWeeklyCallCountBySchool(
            @PathVariable(name = "school-name") String schoolName
    ) {
        return callRecordService.getWeeklyCallCountBySchool(schoolName);
    }

    @GetMapping("/schools/{school-name}/call-counts/monthly")
    public List<CallCountPerMonthRes> getMonthlyCallCountBySchool(
            @PathVariable(name = "school-name") String schoolName
    ) {
        return callRecordService.getMonthlyCallCountBySchool(schoolName);
    }

    @GetMapping("/schools/{school-name}/call-counts/period")
    public List<CallCountPerDateRes> getPeriodCallCountBySchool(
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate,
            @PathVariable(name = "school-name") String schoolName) {
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
            @RequestParam(name = "phoneNumber") String phoneNumber
            ) {
        return callRecordService.getOverallDailyCallTimeByPhone(phoneNumber);
    }

//    @GetMapping("/overall-call-time/phone/daily")
//    public List<CallTimePerDateRes> getOverallDailyCallTimeByPhone(
//            @RequestParam(name = "phoneNumber") String phoneNumber
//    ) {
//        return callRecordService.getOverallDailyCallTimeByPhone(phoneNumber);
//    }

    @GetMapping("/phones/call-time/weekly")
    public List<CallTimePerWeekRes> getOverallWeeklyCallTimeByPhone(
            @RequestParam(name = "phoneNumber") String phoneNumber
    ) {
        return callRecordService.getOverallWeeklyCallTimeByPhone(phoneNumber);
    }

    @GetMapping("/phones/call-time/monthly")
    public List<CallTimePerMonthRes> getOverallMonthlyCallTimeByPhone(
            @RequestParam(name = "phoneNumber") String phoneNumber
    ) {
        return callRecordService.getOverallMonthlyCallTimeByPhone(phoneNumber);
    }

    @GetMapping("/phones/call-time/period")
    public List<CallTimePerDateRes> getOverallPeriodCallTimeByPhone(
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate,
            @RequestParam(name = "phoneNumber") String phoneNumber
    ) {
        return callRecordService.getOverallPeriodCallTimeByPhone(startDate, endDate, phoneNumber);
    }

    // 전화번호 별 call-count 조회
    @GetMapping("/phones/call-counts/daily")
    public List<CallCountPerDateRes> getDailyCallCountByPhone(@RequestParam(name = "phoneNumber") String phoneNumber) {
        return callRecordService.getDailyCallCountByPhone(phoneNumber);
    }

    @GetMapping("/phones/call-counts/weekly")
    public List<CallCountPerWeekRes> getWeeklyCallCountByPhone(@RequestParam(name = "phoneNumber") String phoneNumber) {
        return callRecordService.getWeeklyCallCountByPhone(phoneNumber);
    }

    @GetMapping("/phones/call-counts/monthly")
    public List<CallCountPerMonthRes> getMonthlyCallCountByPhone(@RequestParam(name = "phoneNumber") String phoneNumber) {
        return callRecordService.getMonthlyCallCountByPhone(phoneNumber);
    }

    @GetMapping("/phones/call-counts/period")
    public List<CallCountPerDateRes> getPeriodCallCountByPhone(
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate,
            @RequestParam(name = "phoneNumber") String phoneNumber) {
        return callRecordService.getPeriodCallCountByPhone(startDate, endDate, phoneNumber);
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
