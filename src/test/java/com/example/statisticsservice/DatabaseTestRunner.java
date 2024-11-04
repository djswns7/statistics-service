package com.example.statisticsservice;

import com.example.statisticsservice.domain.TestEntity;
import com.example.statisticsservice.repository.TestEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DatabaseTestRunner implements CommandLineRunner {

    @Autowired
    private TestEntityRepository testEntityRepository;


    @Override
    public void run(String... args) throws Exception {
        // 데이터 저장
        TestEntity entity = new TestEntity();
        entity.setName("Test Name");
        testEntityRepository.save(entity);

        // 데이터 조회
        testEntityRepository.findAll().forEach(e -> {
            System.out.println("Entity found: " + e.getName());
        });
    }
}