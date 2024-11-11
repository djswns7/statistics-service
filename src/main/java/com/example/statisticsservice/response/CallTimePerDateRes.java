package com.example.statisticsservice.response;

import java.time.LocalDate;

public interface CallTimePerDateRes {
    Long getCallTime();
    LocalDate getCallDate();
}