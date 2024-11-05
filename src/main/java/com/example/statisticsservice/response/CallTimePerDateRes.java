package com.example.statisticsservice.response;

import java.time.LocalDate;

public record CallTimePerDateRes(Long callTime, LocalDate date) {
}
