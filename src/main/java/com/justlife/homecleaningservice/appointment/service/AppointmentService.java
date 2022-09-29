package com.justlife.homecleaningservice.appointment.service;

import com.justlife.homecleaningservice.appointment.dto.CleanerAvailabilityResponseDTO;
import com.justlife.homecleaningservice.appointment.dto.LocalTimePeriod;
import com.justlife.homecleaningservice.appointment.entity.Appointment;
import com.justlife.homecleaningservice.appointment.entity.Cleaner;
import com.justlife.homecleaningservice.appointment.repository.AppointmentRepository;
import com.justlife.homecleaningservice.appointment.repository.CleanerRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.justlife.homecleaningservice.appointment.repository.specifications.CleanerSpecifications.notOverlaps;
import static com.justlife.homecleaningservice.appointment.repository.specifications.CleanerSpecifications.overlaps;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final CleanerRepository cleanerRepository;

    @Transactional(readOnly = true)
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CleanerAvailabilityResponseDTO> getCleanersAvailability(LocalDate startDate, LocalTime startTime, Integer duration) {
        if (startTime == null || duration == null) {
            return getAllCleanersSchedule(startDate);
        }

        LocalDateTime startDateTime = startTime.atDate(startDate);
        val availableCleaners = cleanerRepository.findAll(notOverlaps(startDateTime, startDateTime.plusHours(duration)));

        return availableCleaners
                .stream()
                .map(it -> new CleanerAvailabilityResponseDTO(it.getId(), it.getName(), it.getSurname(), Collections.emptyList()))
                .collect(Collectors.toList());
    }

    public List<CleanerAvailabilityResponseDTO> getAllCleanersSchedule(LocalDate date) {
        val startTime = LocalTime.of(8, 0, 0);
        val endTime = LocalTime.of(22, 0, 0);
        List<Cleaner> cleaners = cleanerRepository.findAll(overlaps(startTime.atDate(date), endTime.atDate(date)));

        return cleaners
                .stream()
                .map(it -> new CleanerAvailabilityResponseDTO(it.getId(), it.getName(), it.getSurname(), getAvailableSchedule(it)))
                .collect(Collectors.toList());
    }

    private List<LocalTimePeriod> getAvailableSchedule(Cleaner c) {
        var timeStart = LocalTime.of(8, 0, 0);
        var timeEnd = LocalTime.of(22, 0, 0);

        if (c.getAppointments() == null || c.getAppointments().isEmpty()) {
            return List.of(new LocalTimePeriod(timeStart, timeEnd));
        }
        val appointments = c.getAppointments().stream().sorted(Comparator.comparing(Appointment::getStartTime)).toList();

        val result = new ArrayList<LocalTimePeriod>();
        for (final Appointment a : appointments) {
            LocalTime appStartTime = a.getStartTime().toLocalTime();
            if (appStartTime.isAfter(timeStart)) {
                result.add(new LocalTimePeriod(timeStart, appStartTime.minusMinutes(30)));
            }
            timeStart = a.getEndTime().toLocalTime().plusMinutes(30);
        }
        if (timeStart.isBefore(timeEnd)) {
            result.add(new LocalTimePeriod(timeStart, timeEnd));
        }
        return result;

    }

    @Transactional
    public void create(Appointment appointment, Integer cleanerCount) {
        List<Cleaner> availableCleaners = cleanerRepository.findAll(notOverlaps(appointment.getStartTime(), appointment.getEndTime()));
        appointment.getCleaners().add(availableCleaners.get(0));
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void update(Appointment appointment) {
        Appointment existingAppointment = retrieveByIdOrElseThrow(appointment.getId());
      //  existingAppointment.update(appointment);
    }

    private Appointment retrieveByIdOrElseThrow(Long id) {
        return appointmentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppointmentMessages.ERROR_APPOINTMENT_NOT_FOUND));
    }
}
