package com.example.statisticsservice.service;

import com.example.statisticsservice.repository.CallRecordRepository;
import com.example.statisticsservice.response.*;
import com.example.statisticsservice.response.CallCountPerDateRes;
import com.example.statisticsservice.response.CallCountPerMonthRes;
import com.example.statisticsservice.response.CallCountPerWeekRes;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Transactional// 개별 트랜잭션 고민할것
@Service
public class CallRecordService {

    private final CallRecordRepository callRecordRepository;

    public CallRecordService(CallRecordRepository callRecordRepository) {
        this.callRecordRepository = callRecordRepository;
    }

    public List<CallTimePerDateRes> getOverallDailyCallTime() {
        return callRecordRepository.findDailyCallTime().stream()
                .map(CallTimePerDateRes::new)
                .collect(Collectors.toList());
    }

    public List<CallTimePerWeekRes> getOverallWeeklyCallTime() {
        return callRecordRepository.findWeeklyCallTime().stream()
                .map(CallTimePerWeekRes::new)
                .collect(Collectors.toList());
    }

    public List<CallTimePerMonthRes> getOverallMonthlyCallTime() {
        return callRecordRepository.findMonthlyCallTime().stream()
                .map(CallTimePerMonthRes::new)
                .collect(Collectors.toList());
    }

    public List<CallTimePerDateRes> getOverallPeriodCallTime(String startDateString, String endDateString) {

        LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.BASIC_ISO_DATE); //20241112
        LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.BASIC_ISO_DATE);
        return callRecordRepository.findPeriodCallTime(startDate, endDate).stream()
                .map(CallTimePerDateRes::new)
                .collect(Collectors.toList());
    }


        // callCount
    public List<CallCountPerDateRes> getOverallDailyCallCount() {
        return callRecordRepository.findDailyCallCount().stream()
                .map(CallCountPerDateRes::new)
                .collect(Collectors.toList());
    }

    public List<CallCountPerWeekRes> getOverallWeeklyCallCount() {
        return callRecordRepository.findWeeklyCallCount().stream()
                .map(CallCountPerWeekRes::new)
                .collect(Collectors.toList());
    }

    public List<CallCountPerMonthRes> getOverallMonthlyCallCount() {
        return callRecordRepository.findMonthlyCallCount().stream()
                .map(CallCountPerMonthRes::new)
                .collect(Collectors.toList());
    }

    public List<CallCountPerDateRes> getOverallPeriodCallCount(String startDateString, String endDateString) {

        LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.BASIC_ISO_DATE); //20241112
        LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.BASIC_ISO_DATE);
        return callRecordRepository.findAllByCallDateBetween(startDate, endDate).stream()
                .map(CallCountPerDateRes::new)
                .collect(Collectors.toList());
    }

    // 학교 이름 필터
    public List<CallTimePerDateRes> getOverallDailyCallTimeBySchool(String schoolName) {
        return callRecordRepository.findDailyCallTimeBySchool(schoolName).stream()
                .map(CallTimePerDateRes::new)
                .collect(Collectors.toList());
    }

    public List<CallTimePerWeekRes> getOverallWeeklyCallTimeBySchool(String schoolName) {
        return callRecordRepository.findWeeklyCallTimeBySchool(schoolName).stream()
                .map(CallTimePerWeekRes::new)
                .collect(Collectors.toList());
    }

    public List<CallTimePerMonthRes> getOverallMonthlyCallTimeBySchool(String schoolName) {
        return callRecordRepository.findMonthlyCallTimeBySchool(schoolName).stream()
                .map(CallTimePerMonthRes::new)
                .collect(Collectors.toList());
    }

    public List<CallTimePerDateRes> getOverallPeriodCallTimeBySchool(String startDateString, String endDateString, String schoolName) {
        LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.BASIC_ISO_DATE);
        return callRecordRepository.findPeriodCallTimeBySchool(startDate, endDate, schoolName).stream()
                .map(CallTimePerDateRes::new)
                .collect(Collectors.toList());
    }

    public List<CallCountPerDateRes> getDailyCallCountBySchool(String schoolName) {
        return callRecordRepository.findDailyCallCountBySchool(schoolName)
                .stream()
                .map(projection -> new CallCountPerDateRes(projection.getCallCount(), projection.getCallDate()))
                .collect(Collectors.toList());
    }

    public List<CallCountPerWeekRes> getWeeklyCallCountBySchool(String schoolName) {
        return callRecordRepository.findWeeklyCallCountBySchool(schoolName)
                .stream()
                .map(projection -> new CallCountPerWeekRes(projection.getCallCount(), projection.getWeek()))
                .collect(Collectors.toList());
    }

    public List<CallCountPerMonthRes> getMonthlyCallCountBySchool(String schoolName) {
        return callRecordRepository.findMonthlyCallCountBySchool(schoolName)
                .stream()
                .map(projection -> new CallCountPerMonthRes(projection.getCallCount(), projection.getMonth()))
                .collect(Collectors.toList());
    }

    public List<CallCountPerDateRes> getPeriodCallCountBySchool(String startDate, String endDate, String schoolName) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return callRecordRepository.findBySchoolNameAndCallDateBetween(schoolName, start, end)
                .stream()
                .map(callRecord -> new CallCountPerDateRes(callRecord.getCallCount(), callRecord.getCallDate()))
                .collect(Collectors.toList());
    }

    // 전화번호 필터
    public List<CallTimePerDateRes> getOverallDailyCallTimeByPhone(String phoneNumber) {
        System.out.println(phoneNumber);
        return callRecordRepository.findDailyCallTimeByPhone(phoneNumber).stream()
                .map(CallTimePerDateRes::new)
                .collect(Collectors.toList());
    }

    public List<CallTimePerWeekRes> getOverallWeeklyCallTimeByPhone(String phoneNumber) {
        return callRecordRepository.findWeeklyCallTimeByPhone(phoneNumber).stream()
                .map(CallTimePerWeekRes::new)
                .collect(Collectors.toList());
    }

    public List<CallTimePerMonthRes> getOverallMonthlyCallTimeByPhone(String phoneNumber) {
        return callRecordRepository.findMonthlyCallTimeByPhone(phoneNumber).stream()
                .map(CallTimePerMonthRes::new)
                .collect(Collectors.toList());
    }

    public List<CallTimePerDateRes> getOverallPeriodCallTimeByPhone(String startDateString, String endDateString, String phoneNumber) {
        LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.BASIC_ISO_DATE);
        return callRecordRepository.findPeriodCallTimeByPhone(startDate, endDate, phoneNumber).stream()
                .map(CallTimePerDateRes::new)
                .collect(Collectors.toList());
    }

    // PhoneNumber 기반 메서드도 동일하게 적용
    public List<CallCountPerDateRes> getDailyCallCountByPhone(String phoneNumber) {
        return callRecordRepository.findDailyCallCountByPhone(phoneNumber)
                .stream()
                .map(projection -> new CallCountPerDateRes(projection.getCallCount(), projection.getCallDate()))
                .collect(Collectors.toList());
    }

    public List<CallCountPerWeekRes> getWeeklyCallCountByPhone(String phoneNumber) {
        return callRecordRepository.findWeeklyCallCountByPhone(phoneNumber)
                .stream()
                .map(projection -> new CallCountPerWeekRes(projection.getCallCount(), projection.getWeek()))
                .collect(Collectors.toList());
    }

    public List<CallCountPerMonthRes> getMonthlyCallCountByPhone(String phoneNumber) {
        return callRecordRepository.findMonthlyCallCountByPhone(phoneNumber)
                .stream()
                .map(projection -> new CallCountPerMonthRes(projection.getCallCount(), projection.getMonth()))
                .collect(Collectors.toList());
    }

    public List<CallCountPerDateRes> getPeriodCallCountByPhone(String startDate, String endDate, String phoneNumber) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return callRecordRepository.findByPhoneNumberAndCallDateBetween(phoneNumber, start, end)
                .stream()
                .map(callRecord -> new CallCountPerDateRes(callRecord.getCallCount(), callRecord.getCallDate()))
                .collect(Collectors.toList());
    }


    public List<CallTimePerSchoolRes> getCallTimeBySchool(String order) {
        Sort sort = Sort.by(order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "callTime");
        return callRecordRepository.findCallTimeBySchool(sort);
    }

    public List<CallCountPerSchoolRes> getCallCountBySchool(String order) {
        Sort sort = Sort.by(order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "callCount");
        return callRecordRepository.findCallCountBySchool(sort);
    }

    public List<CallTimePerPhoneNumRes> getCallTimeByPhoneNum(String order) {
        Sort sort = Sort.by(order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "callTime");
        return callRecordRepository.findCallTimeByPhoneNum(sort);
    }

    public List<CallCountPerPhoneNumRes> getCallCountByPhoneNum(String order) {
        Sort sort = Sort.by(order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "callCount");
        return callRecordRepository.findCallCountByPhoneNum(sort);
    }








/*
    public void createRecord(RecordReq req){
        CallRecord callRecord = new CallRecord(createdAt=req.createdTime, callTime = req.callDuration);
        callRecordRepository.save(callRecord);
    }

    public List<CallRecord> getAllrecords() {
        return callRecordRepository.findAll();
    }
    */
}
