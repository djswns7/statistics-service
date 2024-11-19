package com.example.statisticsservice.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="user_OPT", indexes = {
        @Index(name = "idx_account_name", columnList = "accountName")
})
public class UserOTP {
    // id 자동생성이 아니므로 항상 직접 아이디 입력 필요
    @Id
    @Column(name = "account_name", nullable = false, length = 100)
    private String accountName;

    @Column(name = "secrete_key", nullable = false, length = 500)
    private String secreteKey;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public UserOTP(){}

    // accountName 값을 생성 시 반드시 받아야 하므로 생성자 추가
    public UserOTP(String accountName, String secreteKey) {
        this.accountName = accountName;
        this.secreteKey = secreteKey;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getSecreteKey() {
        return secreteKey;
    }

    public void setSecreteKey(String secreteKey) {
        this.secreteKey = secreteKey;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();;
    }
}
