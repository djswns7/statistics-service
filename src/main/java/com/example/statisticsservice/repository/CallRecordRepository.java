package com.example.statisticsservice.repository;

import com.example.statisticsservice.domain.CallRecord;
import com.example.statisticsservice.response.CallTimePerDateRes;
import com.example.statisticsservice.response.CallTimePerMonthRes;
import com.example.statisticsservice.response.CallTimePerWeekRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CallRecordRepository extends JpaRepository<CallRecord, Long> {

    List<CallRecord> findAllByCallDateBetween(LocalDate startDate, LocalDate endDate);

    // 쿼리에서 반환하려는 값(아래의 경우 callDate, callTime)과
    // 함수가 리턴하려는 클래스의 생성자가 매칭이 되어야
    // 자동 매핑이 가능함
//    @Query("SELECT c FROM CallRecord c GROUP BY c.callDate ORDER BY c.callDate")
    @Query("SELECT c FROM CallRecord c WHERE c.id IN (SELECT MIN(sub_c.id) FROM CallRecord sub_c GROUP BY sub_c.callDate) ORDER BY c.callDate")
    List<CallRecord> findDailyStatistics();

//    @Query("SELECT c FROM CallRecord c GROUP BY FUNCTION('WEEK', c.callDate) ORDER BY FUNCTION('WEEK', c.callDate)")
    @Query("SELECT c FROM CallRecord c WHERE c.id IN (SELECT MIN(sub_c.id) FROM CallRecord sub_c GROUP BY FUNCTION('WEEK', sub_c.callDate)) ORDER BY FUNCTION('WEEK', c.callDate)")
    List<CallRecord> findWeeklyStatistics();

//    @Query("SELECT c FROM CallRecord c GROUP BY FUNCTION('MONTH', c.callDate) ORDER BY FUNCTION('MONTH', c.callDate)")
    @Query("SELECT c FROM CallRecord c WHERE c.id IN (SELECT MIN(sub_c.id) FROM CallRecord sub_c GROUP BY FUNCTION('MONTH', sub_c.callDate)) ORDER BY FUNCTION('MONTH', c.callDate)")
    List<CallRecord> findMonthlyStatistics();

    @Query("SELECT c FROM CallRecord c WHERE c.phoneNumber = :phoneNumber AND c.callDate BETWEEN :startDate AND :endDate")
    List<CallRecord> findByPhoneNumberAndDateRange(@Param("phoneNumber") String phoneNumber, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT c.phoneNumber, " +
            "SUM(c.callCount) as totalCount, SUM(c.callTime) as totalTime " +
            "FROM CallRecord c GROUP BY c.phoneNumber ORDER BY totalCount DESC")
    List<CallRecord> findTopByCallCount();

    @Query("SELECT c.phoneNumber, " +
            "SUM(c.callCount) as totalCount, SUM(c.callTime) as totalTime " +
            "FROM CallRecord c GROUP BY c.phoneNumber ORDER BY totalTime DESC")
    List<CallRecord> findTopByCallTime();
}

