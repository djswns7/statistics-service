package com.example.statisticsservice.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성되는 PK
    private Long id;

    @Column(nullable = false, unique = true, length = 100) // 유저 이름, 중복 불가
    private String username;

    @Column(nullable = false, unique = true) // 이메일, 중복 불가
    private String email;

    @Column(nullable = false) // 암호 저장
    private String password;

    @Column(nullable = false, length = 50) // 기본값으로 'USER' 역할 부여
    private String role = "USER";

    @Column(nullable = false, updatable = false) // 생성 시간, 업데이트 불가
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false) // 수정 시간
    private LocalDateTime updatedAt = LocalDateTime.now();

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
