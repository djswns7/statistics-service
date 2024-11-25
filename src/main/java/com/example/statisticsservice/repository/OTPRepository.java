package com.example.statisticsservice.repository;

import com.example.statisticsservice.domain.UserOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OTPRepository extends JpaRepository<UserOTP, String> {

    // 1. email과 secreteKey, otpExpiry를 사용해 새로운 UserOTP 엔티티를 생성
    @Modifying
    @Query("INSERT INTO UserOTP(email, secreteKey, otpExpiry, createdAt, updatedAt) " +
            "VALUES (:email, :secreteKey, :otpExpiry, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")
    void createUserOTP(@Param("email") String email,
                       @Param("secreteKey") String secreteKey,
                       @Param("otpExpiry") LocalDateTime otpExpiry);

    // 2. email에 해당하는 secreteKey 값을 조회
    @Query("SELECT u.secreteKey FROM UserOTP u WHERE u.email = :email")
    String findSecreteKeyByEmail(@Param("email") String email);

    // 3. email에 해당하는 UserOTP 엔티티를 삭제
    @Modifying
    @Query("DELETE FROM UserOTP u WHERE u.email = :email")
    void deleteByEmail(@Param("email") String email);

    // 4. email에 해당하는 otpExpiry 값을 조회
    @Query("SELECT u.otpExpiry FROM UserOTP u WHERE u.email = :email")
    LocalDateTime findOtpExpiryByEmail(@Param("email") String email);

    // 5. email에 해당하는 UserOTP 엔티티를 업데이트 (secreteKey 및 otpExpiry)
    @Modifying
    @Query("UPDATE UserOTP u SET u.secreteKey = :secreteKey, u.otpExpiry = :otpExpiry, u.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE u.email = :email")
    void updateUserOTP(@Param("email") String email,
                       @Param("secreteKey") String secreteKey,
                       @Param("otpExpiry") LocalDateTime otpExpiry);
}
