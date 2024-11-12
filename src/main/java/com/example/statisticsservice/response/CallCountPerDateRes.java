package com.example.statisticsservice.response;

import com.example.statisticsservice.domain.CallRecord;

import java.time.LocalDate;

public class CallCountPerDateRes {

    private Integer callCount;
    private LocalDate callDate;

    public CallCountPerDateRes(CallRecord callRecord) {
        this.callCount = callRecord.getCallCount();
        this.callDate = callRecord.getCallDate();
    }

    public Integer getCallCount() {
        return callCount;
    }

    public LocalDate getCallDate() {
        return callDate;
    }
}
