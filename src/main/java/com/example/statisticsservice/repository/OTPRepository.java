package com.example.statisticsservice.repository;

import com.example.statisticsservice.domain.UserOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<UserOTP, String> {

    // 1. accountName과 secreteKey를 사용해 새로운 UserOTP 엔티티를 생성
    @Modifying
    @Query("INSERT INTO UserOTP(accountName, secreteKey, createdAt, updatedAt) VALUES (:accountName, :secreteKey, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")
    void createUserOTP(@Param("accountName") String accountName, @Param("secreteKey") String secreteKey);

    // 2. accountName에 해당하는 secreteKey 값을 조회
    @Query("SELECT u.secreteKey FROM UserOTP u WHERE u.accountName = :accountName")
    String findSecreteKeyByAccountName(@Param("accountName") String accountName);

    // 3. accountName에 해당하는 UserOTP 엔티티를 삭제
    @Modifying
    @Query("DELETE FROM UserOTP u WHERE u.accountName = :accountName")
    void deleteByAccountName(@Param("accountName") String accountName);
}

