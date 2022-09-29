package com.justlife.homecleaningservice.unittest.appointment.service;

import com.justlife.homecleaningservice.appointment.dto.AvailableTimePeriodResponseDTO;
import com.justlife.homecleaningservice.appointment.dto.CleanerAvailabilityResponseDTO;
import com.justlife.homecleaningservice.appointment.entity.Appointment;
import com.justlife.homecleaningservice.appointment.entity.Cleaner;
import com.justlife.homecleaningservice.appointment.service.AppointmentService;
import com.justlife.homecleaningservice.appointment.service.CleanerService;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class TestAppointmentService {

    @Mock
    private CleanerService cleanerService;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    public void testAllCleanersSchedule() {
        // when
        Appointment app1 = Appointment.builder()
                .startTime(LocalDateTime.of(2022, 9, 28, 8, 0, 0))
                .endTime(LocalDateTime.of(2022, 9, 28, 12, 0, 0))
                .build();
        Appointment app2 = Appointment.builder()
                .startTime(LocalDateTime.of(2022, 9, 28, 13, 0, 0))
                .endTime(LocalDateTime.of(2022, 9, 28, 15, 0, 0))
                .build();
        Appointment app3 = Appointment.builder()
                .startTime(LocalDateTime.of(2022, 9, 28, 17, 0, 0))
                .endTime(LocalDateTime.of(2022, 9, 28, 19, 0, 0))
                .build();
        val c1 = Cleaner.builder().name("name1").surname("sur1").appointments(Set.of(app1, app3)).build();
        c1.setId(1L);
        val c2 = Cleaner.builder().name("name2").surname("sur2").appointments(Set.of(app2)).build();
        c2.setId(2L);
        val c3 = Cleaner.builder().name("name3").surname("sur3").build();
        c3.setId(3L);

        Mockito.when(cleanerService.getAvailableCleanersByDate(Mockito.any())).thenReturn(List.of(c1, c2, c3));

        // do
        LocalDate queryDate = LocalDate.of(2022, 9, 28);
        val actual = appointmentService.getAvailableCleanersSchedule(queryDate);

        // then
        Assertions.assertThat(actual).hasSize(3);
        val response1 = actual.get(0);
        Assertions.assertThat(response1).isEqualTo(new CleanerAvailabilityResponseDTO(c1.getId(), c1.getName(), c1.getSurname(),
                List.of(
                        new AvailableTimePeriodResponseDTO(LocalTime.of(12, 30, 0), LocalTime.of(16, 30, 0)),
                        new AvailableTimePeriodResponseDTO(LocalTime.of(19, 30, 0), LocalTime.of(22, 0, 0))
                )));
        val response2 = actual.get(1);
        Assertions.assertThat(response2).isEqualTo(new CleanerAvailabilityResponseDTO(c2.getId(), c2.getName(), c2.getSurname(),
                List.of(
                        new AvailableTimePeriodResponseDTO(LocalTime.of(8, 0, 0), LocalTime.of(12, 30, 0)),
                        new AvailableTimePeriodResponseDTO(LocalTime.of(15, 30, 0), LocalTime.of(22, 0, 0))
                )));
        val response3 = actual.get(2);
        Assertions.assertThat(response3).isEqualTo(new CleanerAvailabilityResponseDTO(c3.getId(), c3.getName(), c3.getSurname(),
                List.of(new AvailableTimePeriodResponseDTO(LocalTime.of(8, 0, 0), LocalTime.of(22, 0, 0)))));
    }

}