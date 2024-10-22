package com.example.statisticsservice.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "APILog")
public class APILog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(nullable = false)
    private String apiEndpoint;

    @Lob
    private String requestData;

    @Lob
    private String responseData;

    @Column(length = 50)
    private String result;

}

