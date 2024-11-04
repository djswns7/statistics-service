package com.example.statisticsservice.repository;

import com.example.statisticsservice.domain.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
}
