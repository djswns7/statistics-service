package com.example.statisticsservice.response;

import com.example.statisticsservice.domain.CallRecord;

public class CallTimePerMonthRes {
    private Long callTime;
    private Integer month;

    public CallTimePerMonthRes(CallRecord callRecord) {
        this.callTime = callRecord.getCallTime().longValue();
        this.month = callRecord.getMonth();
    }

    public Long getCallTime() {
        return callTime;
    }

    public Integer getMonth() {
        return month;
    }
}