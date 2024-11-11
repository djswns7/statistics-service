//package com.example.statisticsservice.response;
//
//// week는 0~52의 정수형태 단위(Ex. 2월 첫째 주 의 경우 5 로 표기)
//
//public class CallTimePerWeekRes {
//    private Long callTime;
//    private Integer week;
//
//    // 매개변수가 있는 생성자 (Hibernate가 사용)
//    public CallTimePerWeekRes(Long callTime, Integer week) {
//        this.callTime = callTime;
//        this.week = week;
//    }
//
//    // Getters
//    public Long getCallTime() {
//        return callTime;
//    }
//
//    public Integer getWeek() {
//        return week;
//    }
//}
package com.example.statisticsservice.response;

// week는 0~52의 정수형태 단위(Ex. 2월 첫째 주 의 경우 5 로 표기)
// 인터페이스 기반 DTO 프로젝션
public interface CallTimePerWeekRes {
    Long getCallTime();
    Integer getWeek();
}
