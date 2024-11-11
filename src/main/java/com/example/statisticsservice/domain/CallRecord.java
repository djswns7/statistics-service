package com.example.statisticsservice.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

// 인덱싱, 각 데이터 사이즈에 따라 타입 한계값이 대응 가능한지,
@Entity
@Table(name = "call_record", indexes = {
        @Index(name = "idx_phone_number", columnList = "phoneNumber"),
        @Index(name = "idx_school_name", columnList = "schoolName"),
        @Index(name = "idx_call_date", columnList = "callDate"),
        @Index(name = "idx_phone_school", columnList = "phoneNumber, schoolName"),
        @Index(name = "idx_call_count", columnList = "callCount"),
        @Index(name = "idx_call_time", columnList = "callTime")
})
public class CallRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "school_name", length = 100)
    private String schoolName;


    @Column(length = 100)  // VARCHAR(100)
    private String officeName;

    @Column(nullable = false)
    private Integer callCount;

    @Column(nullable = false)
    private Integer callTime;


    private Long sttLength;

    private Long inputTokens;

    private Long outputTokens;

    @Column(nullable = false)
    private LocalDate callDate;

    //레코드 데이터와 유저는 상관관계 없음
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    // ChronoField.ALIGNED_WEEK_OF_YEAR는
    // Java의 java.time.temporal.ChronoField에서 제공하는 상수 중 하나로,
    // 날짜(LocalDate)에서 특정한 주(Week of Year)를 추출할 때 사용
    public int getWeek() {
        return callDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    }

    public int getMonth() {
        return callDate.getMonthValue();
    }

    public Integer getCallTime() {
        return callTime;
    }

    public void setCallTime(Integer callTime) {
        this.callTime = callTime;
    }

    public Long getSttLength() {
        return sttLength;
    }

    public void setSttLength(Long sttLength) {
        this.sttLength = sttLength;
    }

    public Long getInputTokens() {
        return inputTokens;
    }

    public void setInputTokens(Long inputTokens) {
        this.inputTokens = inputTokens;
    }

    public Long getOutputTokens() {
        return outputTokens;
    }

    public void setOutputTokens(Long outputTokens) {
        this.outputTokens = outputTokens;
    }

    public LocalDate getCallDate() {
        return callDate;
    }

    public void setCallDate(LocalDate callDate) {
        this.callDate = callDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CallRecord{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", officeName='" + officeName + '\'' +
                ", callCount=" + callCount +
                ", callTime=" + callTime +
                ", sttLength=" + sttLength +
                ", inputTokens=" + inputTokens +
                ", outputTokens=" + outputTokens +
                ", callDate=" + callDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

