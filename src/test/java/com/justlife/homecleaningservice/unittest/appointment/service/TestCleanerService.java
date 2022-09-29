package com.justlife.homecleaningservice.unittest.appointment.service;

import com.justlife.homecleaningservice.appointment.entity.Cleaner;
import com.justlife.homecleaningservice.appointment.repository.CleanerRepository;
import com.justlife.homecleaningservice.appointment.service.CleanerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestCleanerService {

    @InjectMocks
    private CleanerService cleanerService;

    @Mock
    private CleanerRepository cleanerRepository;

    @Test
    public void testGetAvailableCleaners() {
        when(cleanerRepository.findAll(Mockito.<Specification<Cleaner>>any())).thenReturn(Collections.emptyList());

        LocalDate date = LocalDate.of(2022, 9, 1);
        LocalTime time = LocalTime.of(9, 0, 0);

        cleanerService.getAvailableCleaners(date, time, 4);

        verify(cleanerRepository).findAll(Mockito.<Specification<Cleaner>>any());
    }
}
