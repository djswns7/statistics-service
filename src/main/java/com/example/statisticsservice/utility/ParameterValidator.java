package com.example.statisticsservice.utility;

public class ParameterValidator {
    public static void validateOrderParameter(String order) {
        if (!"asc".equalsIgnoreCase(order) && !"desc".equalsIgnoreCase(order)) {
            throw new IllegalArgumentException("유효하지 않은 파라미터 타입: only 'asc' or 'desc' are allowed");
        }
    }
}
