package com.example.statisticsservice.response;

import com.example.statisticsservice.domain.CallRecord;

import java.time.LocalDate;

public class CallTimePerDateRes {
    private Long callTime;
    private LocalDate callDate;

    public CallTimePerDateRes(CallRecord callRecord) {
        this.callTime = callRecord.getCallTime().longValue();
        this.callDate = callRecord.getCallDate();
    }

    public Long getCallTime() {
        return callTime;
    }

    public LocalDate getCallDate() {
        return callDate;
    }
}