package com.example.statisticsservice.repository;

import com.example.statisticsservice.domain.CallRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CallRecordRepository extends JpaRepository<CallRecord, Long> {

    @Query("SELECT c FROM Call_Record c WHERE c.callDate BETWEEN :startDate AND :endDate")
    List<CallRecord> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Call_Record c WHERE c.phoneNumber = :phoneNumber AND c.callDate BETWEEN :startDate AND :endDate")
    List<CallRecord> findByPhoneNumberAndDateRange(@Param("phoneNumber") String phoneNumber, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT c.phoneNumber, " +
            "SUM(c.callCount) as totalCount, SUM(c.callTime) as totalTime " +
            "FROM CallRecord c GROUP BY c.phoneNumber ORDER BY totalCount DESC")
    List<Object[]> findTopByCallCount();

    @Query("SELECT c.phoneNumber, " +
            "SUM(c.callCount) as totalCount, SUM(c.callTime) as totalTime " +
            "FROM CallRecord c GROUP BY c.phoneNumber ORDER BY totalTime DESC")
    List<Object[]> findTopByCallTime();
}
