package com.example.statisticsservice.service;

import com.example.statisticsservice.domain.CallRecord;
import com.example.statisticsservice.repository.CallRecordRepository;
import com.example.statisticsservice.response.CallTimePerDateRes;
import com.example.statisticsservice.response.CallTimePerMonthRes;
import com.example.statisticsservice.response.CallTimePerWeekRes;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional// 개별 트랜잭션 고민할것
@Service
public class CallRecordService {

    private final CallRecordRepository callRecordRepository;

    public CallRecordService(CallRecordRepository callRecordRepository) {
        this.callRecordRepository = callRecordRepository;
    }

    public List<CallTimePerDateRes> getOverallDailyStatistics() {
        return callRecordRepository.findDailyStatistics().stream()
                .map(CallTimePerDateRes::new)
                .collect(Collectors.toList());
    }

    public List<CallTimePerWeekRes> getOverallWeeklyStatistics() {
        return callRecordRepository.findWeeklyStatistics().stream()
                .map(CallTimePerWeekRes::new)
                .collect(Collectors.toList());
    }

    public List<CallTimePerMonthRes> getOverallMonthlyStatistics() {
        return callRecordRepository.findMonthlyStatistics().stream()
                .map(CallTimePerMonthRes::new)
                .collect(Collectors.toList());
    }

    public List<CallTimePerDateRes> getOverallPeriodStatistics(LocalDate startDate, LocalDate endDate) {
        return callRecordRepository.findAllByCallDateBetween(startDate, endDate).stream()
                .map(CallTimePerDateRes::new)
                .collect(Collectors.toList());
    }



    public List<CallRecord> getTopRecordsByCallCount() {
        return callRecordRepository.findTopByCallCount();
    }

    public List<CallRecord> getTopRecordsByCallDuration() {
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
