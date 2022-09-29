package com.justlife.homecleaningservice.appointment.service;

import com.justlife.homecleaningservice.appointment.entity.Cleaner;
import com.justlife.homecleaningservice.appointment.repository.CleanerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.justlife.homecleaningservice.appointment.repository.specifications.CleanerSpecifications.notOverlapsTimePeriod;

@Service
@RequiredArgsConstructor
public class CleanerService {

    private final CleanerRepository cleanerRepository;

    @Transactional(readOnly = true)
    public List<Cleaner> getAvailableCleaners(LocalDate startDate, LocalTime startTime, Integer duration) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime).minusMinutes(Cleaner.MIN_BREAK_DURATION_IN_MINUTES);
        LocalDateTime endDateTime = LocalDateTime.of(startDate, startTime).plusHours(duration).plusMinutes(Cleaner.MIN_BREAK_DURATION_IN_MINUTES);
        return cleanerRepository.findAll(notOverlapsTimePeriod(startDateTime, endDateTime));
    }
}
