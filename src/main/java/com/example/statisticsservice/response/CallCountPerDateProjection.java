package com.example.statisticsservice.response;

import java.time.LocalDate;

public interface CallCountPerDateProjection {
    Integer getCallCount();
    LocalDate getCallDate();
}
