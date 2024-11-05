package com.example.statisticsservice.service;

import com.example.statisticsservice.domain.CallRecord;
import com.example.statisticsservice.repository.CallRecordRepository;
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

    public List<CallRecord> getRecordsByDateRange(LocalDate startDate, LocalDate endDate) {
        return callRecordRepository.findByDateRange(startDate, endDate);
    }

    public List<CallRecord> getRecordsByPhoneNumberAndDateRange(String phoneNumber, LocalDate startDate, LocalDate endDate) {
        return callRecordRepository.findByPhoneNumberAndDateRange(phoneNumber, startDate, endDate);
    }

    public List<CallRecord> getDailyRecords(LocalDate date) {
        return callRecordRepository.findByDateRange(date, date);
    }

    public List<CallRecord> getWeeklyRecords(LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6);
        return callRecordRepository.findByDateRange(startDate, endDate);
    }

    public List<CallRecord> getMonthlyRecords(LocalDate month) {
        LocalDate startDate = month.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = month.with(TemporalAdjusters.lastDayOfMonth());
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
