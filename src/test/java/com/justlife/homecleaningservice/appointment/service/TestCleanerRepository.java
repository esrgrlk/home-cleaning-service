package com.justlife.homecleaningservice.appointment.service;

import com.justlife.homecleaningservice.appointment.entity.Cleaner;
import com.justlife.homecleaningservice.appointment.repository.CleanerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.justlife.homecleaningservice.appointment.repository.specifications.CleanerSpecifications.notOverlapsTimePeriod;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TestCleanerRepository {

    @Autowired
    private CleanerRepository cleanerRepository;

    @Test
    public void test() {
        LocalDateTime startTime = LocalDateTime.of(2022, Month.SEPTEMBER, 28, 8, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2022, Month.SEPTEMBER, 28, 10, 0, 0);
        List<Cleaner> cleanerList = cleanerRepository.findAll(notOverlapsTimePeriod(startTime, endTime));
        assertEquals(cleanerList.size(), 24);
    }
}
