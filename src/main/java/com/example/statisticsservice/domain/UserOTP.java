package com.example.statisticsservice.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_OTP")
public class UserOTP {

    // User 테이블의 email을 외래 키로 참조
    @Id
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "secrete_key", nullable = false, length = 500) // OTP 시크릿 키 저장
    private String secreteKey;

    @Column(name = "otp_expiry", nullable = false) // OTP 만료 시간
    private LocalDateTime otpExpiry;

    @Column(name = "created_at", nullable = false, updatable = false) // 생성 시간
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false) // 수정 시간
    private LocalDateTime updatedAt = LocalDateTime.now();

    @MapsId // email은 User 테이블의 email을 참조
    @OneToOne(cascade = CascadeType.ALL) // Cascade 설정 추가
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_email"))
    private User user;

    public UserOTP() {}

    // 생성자: email과 secretKey, otpExpiry 필수
    public UserOTP(User user, String secreteKey, LocalDateTime otpExpiry) {
        this.email = user.getEmail();
        this.user = user;
        this.secreteKey = secreteKey;
        this.otpExpiry = otpExpiry;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecreteKey() {
        return secreteKey;
    }

    public void setSecreteKey(String secreteKey) {
        this.secreteKey = secreteKey;
    }

    public LocalDateTime getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(LocalDateTime otpExpiry) {
        this.otpExpiry = otpExpiry;
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
