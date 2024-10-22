package com.example.statisticsservice.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CallRecord")
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
    private int callCount;

    @Column(nullable = false)
    private int callDuration;

    private int sttLength;

    private int inputTokens;

    private int outputTokens;

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
}

