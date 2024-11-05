package com.example.statisticsservice.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConverter {

    // String 또는 int를 LocalDate로 변환하는 함수
    // 가능한 input 타입
    //"20241105", "2024-11-05", 20241105
    public static LocalDate convertToDate(Object input) {
        try {
            if (input instanceof String) {
                String dateStr = (String) input;
                if (dateStr.length() == 8) {
                    // 8자리 문자열 (예: "20241105") 처리
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    return LocalDate.parse(dateStr, formatter);
                } else if (dateStr.length() == 10) {
                    // yyyy-MM-dd 형식 (예: "2024-11-05") 처리
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                    return LocalDate.parse(dateStr, formatter);
                }
            } else if (input instanceof Integer) {
                // 8자리 정수 (예: 20241105) 처리
                int dateInt = (Integer) input;
                String dateStr = String.valueOf(dateInt);
                if (dateStr.length() == 8) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    return LocalDate.parse(dateStr, formatter);
                }
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + input);
        }

        // 유효하지 않은 형식일 경우 null 반환
        return null;
    }
}
