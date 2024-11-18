package com.example.statisticsservice.response;

import com.example.statisticsservice.domain.CallRecord;

public class CallCountPerWeekRes {
    private Integer callCount;
    private Integer week;

    public CallCountPerWeekRes(CallRecord callRecord) {
        this.callCount = callRecord.getCallCount();
        this.week = callRecord.getWeek();
    }

    public CallCountPerWeekRes(Integer callCount, Integer week) {
        this.callCount = callCount;
        this.week = week;
    }

    public Integer getCallCount() {
        return callCount;
    }

    public Integer getWeek() {
        return week;
    }
}
