package com.example.statisticsservice.response;

import com.example.statisticsservice.domain.CallRecord;

import java.time.LocalDate;

public class CallCountPerWeekRes {
    private Integer callCount;
    private Integer week;

    public CallCountPerWeekRes(CallRecord callRecord) {
        this.callCount = callRecord.getCallCount();
        this.week = callRecord.getWeek();
    }

    public Integer getCallCount() {
        return callCount;
    }

    public Integer getWeek() {
        return week;
    }
}
