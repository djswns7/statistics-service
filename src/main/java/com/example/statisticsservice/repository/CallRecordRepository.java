package com.example.statisticsservice.repository;

import com.example.statisticsservice.domain.CallRecord;
import com.example.statisticsservice.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CallRecordRepository extends JpaRepository<CallRecord, Long> {

    List<CallRecord> findAllByCallDateBetween(LocalDate startDate, LocalDate endDate);

    // call_count 조회용 쿼리

    // 쿼리에서 반환하려는 값(아래의 경우 callDate, callTime)과
    // 함수가 리턴하려는 클래스의 생성자가 매칭이 되어야
    // 자동 매핑이 가능함
//    @Query("SELECT c FROM CallRecord c GROUP BY c.callDate ORDER BY c.callDate")
    @Query(value = """
        WITH aggregated AS (
            SELECT call_date, SUM(call_time) AS total_call_time
            FROM call_record
            GROUP BY call_date
        ),
        representative AS (
            SELECT c.*, aggregated.total_call_time
            FROM call_record c
            JOIN aggregated ON c.call_date = aggregated.call_date
            WHERE c.id = (
                SELECT MIN(sub_c.id)
                FROM call_record sub_c
                WHERE sub_c.call_date = c.call_date
            )
        )
        SELECT
            id,
            call_date,
            phone_number,
            school_name,
            office_name,
            call_count,
            total_call_time AS call_time,
            stt_length,
            input_tokens,
            output_tokens,
            created_at,
            updated_at
        FROM representative
        ORDER BY call_date
        LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}
    """, countQuery = "SELECT COUNT(*) FROM call_record", nativeQuery = true)
    Page<CallRecord> findDailyCallTime(Pageable pageable);

    @Query(value = """
        WITH aggregated AS (
            SELECT YEARWEEK(call_date, 1) AS year_week, SUM(call_time) AS total_call_time
            FROM call_record
            GROUP BY YEARWEEK(call_date, 1)
        ),
        ranked AS (
            SELECT
                c.*, aggregated.total_call_time,
                ROW_NUMBER() OVER (PARTITION BY YEARWEEK(c.call_date, 1) ORDER BY c.id ASC) AS rn
            FROM call_record c
            JOIN aggregated ON YEARWEEK(c.call_date, 1) = aggregated.year_week
        )
        SELECT
            id,
            call_date,
            phone_number,
            school_name,
            office_name,
            call_count,
            total_call_time AS call_time,
            stt_length,
            input_tokens,
            output_tokens,
            created_at,
            updated_at
        FROM ranked
        WHERE rn = 1
        ORDER BY call_date
        LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}
    """, countQuery = "SELECT COUNT(*) FROM call_record", nativeQuery = true)
    Page<CallRecord> findWeeklyCallTime(Pageable pageable);

    @Query(value = """
        WITH aggregated AS (
            SELECT DATE_FORMAT(call_date, '%Y-%m') AS month, SUM(call_time) AS total_call_time
            FROM call_record
            GROUP BY DATE_FORMAT(call_date, '%Y-%m')
        ),
        ranked AS (
            SELECT
                c.*, aggregated.total_call_time,
                ROW_NUMBER() OVER (PARTITION BY DATE_FORMAT(c.call_date, '%Y-%m') ORDER BY c.id ASC) AS rn
            FROM call_record c
            JOIN aggregated ON DATE_FORMAT(c.call_date, '%Y-%m') = aggregated.month
        )
        SELECT
            id,
            call_date,
            phone_number,
            school_name,
            office_name,
            call_count,
            total_call_time AS call_time,
            stt_length,
            input_tokens,
            output_tokens,
            created_at,
            updated_at
        FROM ranked
        WHERE rn = 1
        ORDER BY call_date
        LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}
    """, countQuery = "SELECT COUNT(*) FROM call_record", nativeQuery = true)
    Page<CallRecord> findMonthlyCallTime(Pageable pageable);

    @Query(value = """
        WITH aggregated AS (
            SELECT call_date, SUM(call_time) AS total_call_time
            FROM call_record
            WHERE call_date BETWEEN :startDate AND :endDate
            GROUP BY call_date
        ),
        representative AS (
            SELECT c.*, aggregated.total_call_time
            FROM call_record c
            JOIN aggregated ON c.call_date = aggregated.call_date
            WHERE c.id = (
                SELECT MIN(sub_c.id)
                FROM call_record sub_c
                WHERE sub_c.call_date = c.call_date
            )
        )
        SELECT
            id,
            call_date,
            phone_number,
            school_name,
            office_name,
            call_count,
            total_call_time AS call_time,
            stt_length,
            input_tokens,
            output_tokens,
            created_at,
            updated_at
        FROM representative
        ORDER BY call_date
        LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}
    """, countQuery = "SELECT COUNT(*) FROM call_record WHERE call_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    Page<CallRecord> findPeriodCallTime(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

    // call_count 조회용 쿼리

    @Query(value = """
        WITH aggregated AS (
            SELECT call_date, SUM(call_count) AS total_call_count
            FROM call_record
            GROUP BY call_date
        ),
        representative AS (
            SELECT c.*, aggregated.total_call_count
            FROM call_record c
            JOIN aggregated ON c.call_date = aggregated.call_date
            WHERE c.id = (
                SELECT MIN(sub_c.id)
                FROM call_record sub_c
                WHERE sub_c.call_date = c.call_date
            )
        )
        SELECT
            id,
            call_date,
            phone_number,
            school_name,
            office_name,
            total_call_count AS call_count,
            call_time,
            stt_length,
            input_tokens,
            output_tokens,
            created_at,
            updated_at
        FROM representative
        ORDER BY call_date
    """, nativeQuery = true)
    List<CallRecord> findDailyCallCount();

    @Query(value = """
        WITH aggregated AS (
            SELECT YEARWEEK(call_date, 1) AS year_week, SUM(call_count) AS total_call_count
            FROM call_record
            GROUP BY YEARWEEK(call_date, 1)
        ),
        ranked AS (
            SELECT
                c.*,
                aggregated.total_call_count,
                ROW_NUMBER() OVER (PARTITION BY YEARWEEK(c.call_date, 1) ORDER BY c.id ASC) AS rn
            FROM call_record c
            JOIN aggregated ON YEARWEEK(c.call_date, 1) = aggregated.year_week
        )
        SELECT
            id,
            call_date,
            phone_number,
            school_name,
            office_name,
            total_call_count AS call_count,
            call_time,
            stt_length,
            input_tokens,
            output_tokens,
            created_at,
            updated_at
        FROM ranked
        WHERE rn = 1
        ORDER BY call_date;
    """, nativeQuery = true)
    List<CallRecord> findWeeklyCallCount();

    @Query(value = """
        WITH aggregated AS (
            SELECT DATE_FORMAT(call_date, '%Y-%m') AS month, SUM(call_count) AS total_call_count
            FROM call_record
            GROUP BY DATE_FORMAT(call_date, '%Y-%m')
        ),
        ranked AS (
            SELECT 
                c.*, 
                aggregated.total_call_count, 
                ROW_NUMBER() OVER (PARTITION BY DATE_FORMAT(c.call_date, '%Y-%m') ORDER BY c.id ASC) AS rn
            FROM call_record c
            JOIN aggregated ON DATE_FORMAT(c.call_date, '%Y-%m') = aggregated.month
        )
        SELECT 
            id, 
            call_date, 
            phone_number, 
            school_name, 
            office_name, 
            total_call_count AS call_count,
            call_time,
            stt_length,
            input_tokens,
            output_tokens,
            created_at,
            updated_at
        FROM ranked
        WHERE rn = 1
        ORDER BY call_date;
    """, nativeQuery = true)
    List<CallRecord> findMonthlyCallCount();

    // 리포지토리 일부

    // 학교 이름 필터 쿼리
    @Query(value = """
    WITH aggregated AS (
        SELECT call_date, SUM(call_time) AS total_call_time
        FROM call_record
        WHERE school_name = :schoolName
        GROUP BY call_date
    ),
    representative AS (
        SELECT c.*, aggregated.total_call_time
        FROM call_record c
        JOIN aggregated ON c.call_date = aggregated.call_date
        WHERE c.id = (
            SELECT MIN(sub_c.id)
            FROM call_record sub_c
            WHERE sub_c.call_date = c.call_date AND sub_c.school_name = :schoolName
        )
    )
    SELECT
        id,
        call_date,
        phone_number,
        school_name,
        office_name,
        call_count,
        total_call_time AS call_time,
        stt_length,
        input_tokens,
        output_tokens,
        created_at,
        updated_at
    FROM representative
    ORDER BY call_date;
""", nativeQuery = true)
    List<CallRecord> findDailyCallTimeBySchool(@Param("schoolName") String schoolName);

    @Query(value = """
    WITH aggregated AS (
        SELECT YEARWEEK(call_date, 1) AS year_week, SUM(call_time) AS total_call_time
        FROM call_record
        WHERE school_name = :schoolName
        GROUP BY YEARWEEK(call_date, 1)
    ),
    ranked AS (
        SELECT
            c.*, aggregated.total_call_time,
            ROW_NUMBER() OVER (PARTITION BY YEARWEEK(c.call_date, 1) ORDER BY c.id ASC) AS rn
        FROM call_record c
        JOIN aggregated ON YEARWEEK(c.call_date, 1) = aggregated.year_week
        WHERE c.school_name = :schoolName
    )
    SELECT
        id,
        call_date,
        phone_number,
        school_name,
        office_name,
        call_count,
        total_call_time AS call_time,
        stt_length,
        input_tokens,
        output_tokens,
        created_at,
        updated_at
    FROM ranked
    WHERE rn = 1
    ORDER BY call_date;
""", nativeQuery = true)
    List<CallRecord> findWeeklyCallTimeBySchool(@Param("schoolName") String schoolName);

    @Query(value = """
    WITH aggregated AS (
        SELECT DATE_FORMAT(call_date, '%Y-%m') AS month, SUM(call_time) AS total_call_time
        FROM call_record
        WHERE school_name = :schoolName
        GROUP BY DATE_FORMAT(call_date, '%Y-%m')
    ),
    ranked AS (
        SELECT
            c.*, aggregated.total_call_time,
            ROW_NUMBER() OVER (PARTITION BY DATE_FORMAT(c.call_date, '%Y-%m') ORDER BY c.id ASC) AS rn
        FROM call_record c
        JOIN aggregated ON DATE_FORMAT(c.call_date, '%Y-%m') = aggregated.month
        WHERE c.school_name = :schoolName
    )
    SELECT
        id,
        call_date,
        phone_number,
        school_name,
        office_name,
        call_count,
        total_call_time AS call_time,
        stt_length,
        input_tokens,
        output_tokens,
        created_at,
        updated_at
    FROM ranked
    WHERE rn = 1
    ORDER BY call_date;
""", nativeQuery = true)
    List<CallRecord> findMonthlyCallTimeBySchool(@Param("schoolName") String schoolName);

    @Query(value = """
    WITH aggregated AS (
        SELECT call_date, SUM(call_time) AS total_call_time
        FROM call_record
        WHERE call_date BETWEEN :startDate AND :endDate AND school_name = :schoolName
        GROUP BY call_date
    ),
    representative AS (
        SELECT c.*, aggregated.total_call_time
        FROM call_record c
        JOIN aggregated ON c.call_date = aggregated.call_date
        WHERE c.id = (
            SELECT MIN(sub_c.id)
            FROM call_record sub_c
            WHERE sub_c.call_date = c.call_date AND sub_c.school_name = :schoolName
        )
    )
    SELECT
        id,
        call_date,
        phone_number,
        school_name,
        office_name,
        call_count,
        total_call_time AS call_time,
        stt_length,
        input_tokens,
        output_tokens,
        created_at,
        updated_at
    FROM representative
    ORDER BY call_date;
""", nativeQuery = true)
    List<CallRecord> findPeriodCallTimeBySchool(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("schoolName") String schoolName);


    // 전화번호 필터 쿼리
    @Query(value = """
    WITH aggregated AS (
        SELECT call_date, SUM(call_time) AS total_call_time
        FROM call_record
        WHERE phone_number = :number
        GROUP BY call_date
    ),
    representative AS (
        SELECT c.*, aggregated.total_call_time
        FROM call_record c
        JOIN aggregated ON c.call_date = aggregated.call_date
        WHERE c.id = (
            SELECT MIN(sub_c.id)
            FROM call_record sub_c
            WHERE sub_c.call_date = c.call_date AND sub_c.phone_number = :number
        )
    )
    SELECT
        id,
        call_date,
        phone_number,
        school_name,
        office_name,
        call_count,
        total_call_time AS call_time,
        stt_length,
        input_tokens,
        output_tokens,
        created_at,
        updated_at
    FROM representative
    ORDER BY call_date;
    """, nativeQuery = true)
    List<CallRecord> findDailyCallTimeByPhone(@Param("number") String phoneNumber);


    @Query(value = """
    WITH aggregated AS (
        SELECT YEARWEEK(call_date, 1) AS year_week, SUM(call_time) AS total_call_time
        FROM call_record
        WHERE phone_number = :phoneNumber
        GROUP BY YEARWEEK(call_date, 1)
    ),
    ranked AS (
        SELECT
            c.*, aggregated.total_call_time,
            ROW_NUMBER() OVER (PARTITION BY YEARWEEK(c.call_date, 1) ORDER BY c.id ASC) AS rn
        FROM call_record c
        JOIN aggregated ON YEARWEEK(c.call_date, 1) = aggregated.year_week
        WHERE c.phone_number = :phoneNumber
    )
    SELECT
        id,
        call_date,
        phone_number,
        school_name,
        office_name,
        call_count,
        total_call_time AS call_time,
        stt_length,
        input_tokens,
        output_tokens,
        created_at,
        updated_at
    FROM ranked
    WHERE rn = 1
    ORDER BY call_date;
""", nativeQuery = true)
    List<CallRecord> findWeeklyCallTimeByPhone(@Param("phoneNumber") String phoneNumber);

    @Query(value = """
    WITH aggregated AS (
        SELECT DATE_FORMAT(call_date, '%Y-%m') AS month, SUM(call_time) AS total_call_time
        FROM call_record
        WHERE phone_number = :phoneNumber
        GROUP BY DATE_FORMAT(call_date, '%Y-%m')
    ),
    ranked AS (
        SELECT
            c.*, aggregated.total_call_time,
            ROW_NUMBER() OVER (PARTITION BY DATE_FORMAT(c.call_date, '%Y-%m') ORDER BY c.id ASC) AS rn
        FROM call_record c
        JOIN aggregated ON DATE_FORMAT(c.call_date, '%Y-%m') = aggregated.month
        WHERE c.phone_number = :phoneNumber
    )
    SELECT
        id,
        call_date,
        phone_number,
        school_name,
        office_name,
        call_count,
        total_call_time AS call_time,
        stt_length,
        input_tokens,
        output_tokens,
        created_at,
        updated_at
    FROM ranked
    WHERE rn = 1
    ORDER BY call_date;
""", nativeQuery = true)
    List<CallRecord> findMonthlyCallTimeByPhone(@Param("phoneNumber") String phoneNumber);

    @Query(value = """
    WITH aggregated AS (
        SELECT call_date, SUM(call_time) AS total_call_time
        FROM call_record
        WHERE call_date BETWEEN :startDate AND :endDate AND phone_number = :phoneNumber
        GROUP BY call_date
    ),
    representative AS (
        SELECT c.*, aggregated.total_call_time
        FROM call_record c
        JOIN aggregated ON c.call_date = aggregated.call_date
        WHERE c.id = (
            SELECT MIN(sub_c.id)
            FROM call_record sub_c
            WHERE sub_c.call_date = c.call_date AND sub_c.phone_number = :phoneNumber
        )
    )
    SELECT
        id,
        call_date,
        phone_number,
        school_name,
        office_name,
        call_count,
        total_call_time AS call_time,
        stt_length,
        input_tokens,
        output_tokens,
        created_at,
        updated_at
    FROM representative
    ORDER BY call_date;
""", nativeQuery = true)
    List<CallRecord> findPeriodCallTimeByPhone(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("phoneNumber") String phoneNumber);


    // 일별 집계 쿼리
    @Query("SELECT SUM(c.callCount) as callCount, c.callDate as callDate " +
            "FROM CallRecord c " +
            "WHERE c.schoolName = :schoolName " +
            "GROUP BY c.callDate " +
            "ORDER BY c.callDate")
    List<CallCountPerDateProjection> findDailyCallCountBySchool(@Param("schoolName") String schoolName);

    // 주별 집계 쿼리
    @Query("SELECT SUM(c.callCount) as callCount, FUNCTION('WEEK', c.callDate) as week " +
            "FROM CallRecord c " +
            "WHERE c.schoolName = :schoolName " +
            "GROUP BY FUNCTION('YEAR', c.callDate), FUNCTION('WEEK', c.callDate) " +
            "ORDER BY FUNCTION('YEAR', c.callDate), FUNCTION('WEEK', c.callDate)")
    List<CallCountPerWeekProjection> findWeeklyCallCountBySchool(@Param("schoolName") String schoolName);

    // 월별 집계 쿼리
    @Query("SELECT SUM(c.callCount) as callCount, FUNCTION('MONTH', c.callDate) as month " +
            "FROM CallRecord c " +
            "WHERE c.schoolName = :schoolName " +
            "GROUP BY FUNCTION('YEAR', c.callDate), FUNCTION('MONTH', c.callDate) " +
            "ORDER BY FUNCTION('YEAR', c.callDate), FUNCTION('MONTH', c.callDate)")
    List<CallCountPerMonthProjection> findMonthlyCallCountBySchool(@Param("schoolName") String schoolName);

    // 전화번호 기반 일별 집계 쿼리
    @Query("SELECT SUM(c.callCount) as callCount, c.callDate as callDate " +
            "FROM CallRecord c " +
            "WHERE c.phoneNumber = :phoneNumber " +
            "GROUP BY c.callDate " +
            "ORDER BY c.callDate")
    List<CallCountPerDateProjection> findDailyCallCountByPhone(@Param("phoneNumber") String phoneNumber);

    // 전화번호 기반 주별 집계 쿼리
    @Query("SELECT SUM(c.callCount) as callCount, FUNCTION('WEEK', c.callDate) as week " +
            "FROM CallRecord c " +
            "WHERE c.phoneNumber = :phoneNumber " +
            "GROUP BY FUNCTION('YEAR', c.callDate), FUNCTION('WEEK', c.callDate) " +
            "ORDER BY FUNCTION('YEAR', c.callDate), FUNCTION('WEEK', c.callDate)")
    List<CallCountPerWeekProjection> findWeeklyCallCountByPhone(@Param("phoneNumber") String phoneNumber);



    // 전화번호 기반 월별 집계 쿼리
    @Query("SELECT SUM(c.callCount) as callCount, FUNCTION('MONTH', c.callDate) as month " +
            "FROM CallRecord c " +
            "WHERE c.phoneNumber = :phoneNumber " +
            "GROUP BY FUNCTION('YEAR', c.callDate), FUNCTION('MONTH', c.callDate) " +
            "ORDER BY FUNCTION('YEAR', c.callDate), FUNCTION('MONTH', c.callDate)")
    List<CallCountPerMonthProjection> findMonthlyCallCountByPhone(@Param("phoneNumber") String phoneNumber);

    List<CallRecord> findBySchoolNameAndCallDateBetween(String schoolName, LocalDate startDate, LocalDate endDate);

    List<CallRecord> findByPhoneNumberAndCallDateBetween(String phoneNumber, LocalDate startDate, LocalDate endDate);


    @Query("SELECT SUM(c.callTime) as callTime, c.schoolName as schoolName FROM CallRecord c GROUP BY c.schoolName")
    List<CallTimePerSchoolRes> findCallTimeBySchool(Sort sort);

    @Query("SELECT SUM(c.callCount) as callCount, c.schoolName as schoolName FROM CallRecord c GROUP BY c.schoolName")
    List<CallCountPerSchoolRes> findCallCountBySchool(Sort sort);

    @Query("SELECT SUM(c.callTime) as callTime, c.phoneNumber as phoneNum FROM CallRecord c GROUP BY c.phoneNumber")
    List<CallTimePerPhoneNumRes> findCallTimeByPhoneNum(Sort sort);

    @Query("SELECT SUM(c.callCount) as callCount, c.phoneNumber as phoneNum FROM CallRecord c GROUP BY c.phoneNumber")
    List<CallCountPerPhoneNumRes> findCallCountByPhoneNum(Sort sort);

}

