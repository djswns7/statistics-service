package com.example.statisticsservice.response;

import com.example.statisticsservice.domain.CallRecord;

public class CallCountPerMonthRes {
    private Integer callCount;
    private Integer month;

    public CallCountPerMonthRes(CallRecord callRecord) {
        this.callCount = callRecord.getCallCount();
        this.month = callRecord.getMonth();
    }

    public Integer getCallCount() {
        return callCount;
    }

    public Integer getMonth() {
        return month;
    }
}
