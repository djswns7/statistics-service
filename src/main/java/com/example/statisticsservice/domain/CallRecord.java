package com.example.statisticsservice.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

// 인덱싱, 각 데이터 사이즈에 따라 타입 한계값이 대응 가능한지,
@Entity
@Table(name = "call_record")
public class CallRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)  // VARCHAR(20)
    private String phoneNumber;

    @Column(length = 100)  // VARCHAR(100)
    private String schoolName;

    @Column(length = 100)  // VARCHAR(100)
    private String officeName;

    @Column(nullable = false)
    private Long callCount;

    @Column(nullable = false)
    private Long callTime;

    private Long sttLength;

    private Long inputTokens;

    private Long outputTokens;

    @Column(nullable = false)
    private LocalDate callDate;

    //레코드 데이터와 유저는 상관관계 없음
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

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

