package com.example.statisticsservice.service;

import com.example.statisticsservice.domain.CallRecord;
import com.example.statisticsservice.repository.CallRecordRepository;
import com.example.statisticsservice.response.CallTimePerDateRes;
import com.example.statisticsservice.response.CallTimePerMonthRes;
import com.example.statisticsservice.response.CallTimePerWeekRes;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Transactional// 개별 트랜잭션 고민할것
@Service
public class CallRecordService {

    private final CallRecordRepository callRecordRepository;

    public CallRecordService(CallRecordRepository callRecordRepository) {
        this.callRecordRepository = callRecordRepository;
    }

    public List<CallTimePerDateRes> getOverallDailyStatistics() {
        // 일 별 통계 데이터를 계산하여 반환
        return callRecordRepository.findDailyStatistics();
    }

    public List<CallTimePerWeekRes> getOverallWeeklyStatistics() {
        // 주 별 통계 데이터를 계산하여 반환
        return callRecordRepository.findWeeklyStatistics();
    }

    public List<CallTimePerMonthRes> getOverallMonthlyStatistics() {
        // 월 별 통계 데이터를 계산하여 반환
        return callRecordRepository.findMonthlyStatistics();
    }

    public List<CallTimePerDateRes> getOverallPeriodStatistics(LocalDate startDate, LocalDate endDate) {
        // 특정 기간 통계 데이터를 계산하여 반환
        return callRecordRepository.findByDateRange(startDate, endDate);
    }



    public List<Object[]> getTopRecordsByCallCount() {
        return callRecordRepository.findTopByCallCount();
    }

    public List<Object[]> getTopRecordsByCallDuration() {
        return callRecordRepository.findTopByCallTime();
    }

    public Optional<CallRecord> getRecordById(Long id) {

        return callRecordRepository.findById(id);
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
