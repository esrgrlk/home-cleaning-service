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
import static com.justlife.homecleaningservice.appointment.repository.specifications.CleanerSpecifications.overlapsTimePeriod;

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

    @Transactional(readOnly = true)
    public List<Cleaner> getAvailableCleanersByDate(LocalDate startDate) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, Cleaner.START_WORKING_HOUR);
        LocalDateTime endDateTime = LocalDateTime.of(startDate, Cleaner.END_WORKING_HOUR);
        return cleanerRepository.findAll(overlapsTimePeriod(startDateTime, endDateTime));
    }

    @Transactional(readOnly = true)
    public List<Cleaner> getAvailableCleaners(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        startDateTime = startDateTime.minusMinutes(Cleaner.MIN_BREAK_DURATION_IN_MINUTES);
        endDateTime = endDateTime.plusMinutes(Cleaner.MIN_BREAK_DURATION_IN_MINUTES);
        return cleanerRepository.findAll(notOverlapsTimePeriod(startDateTime, endDateTime));
    }
}