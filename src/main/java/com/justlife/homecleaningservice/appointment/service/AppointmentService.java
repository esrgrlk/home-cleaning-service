package com.justlife.homecleaningservice.appointment.service;

import com.justlife.homecleaningservice.appointment.dto.AvailableTimePeriodResponseDTO;
import com.justlife.homecleaningservice.appointment.dto.CleanerAvailabilityResponseDTO;
import com.justlife.homecleaningservice.appointment.entity.Appointment;
import com.justlife.homecleaningservice.appointment.entity.Cleaner;
import com.justlife.homecleaningservice.appointment.mapper.CleanerAvailabilityMapper;
import com.justlife.homecleaningservice.appointment.repository.AppointmentRepository;
import com.justlife.homecleaningservice.appointment.repository.CleanerRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.justlife.homecleaningservice.appointment.repository.specifications.CleanerSpecifications.notOverlapsTimePeriod;
import static com.justlife.homecleaningservice.appointment.repository.specifications.CleanerSpecifications.overlapsTimePeriod;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final CleanerRepository cleanerRepository;

    private final CleanerService cleanerService;

    @Transactional(readOnly = true)
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CleanerAvailabilityResponseDTO> getCleanersAvailability(LocalDate startDate, LocalTime startTime, Integer duration) {
        if (startTime == null || duration == null) {
            return getAllCleanersSchedule(startDate);
        }

        List<Cleaner> availableCleaners = cleanerService.getAvailableCleaners(startDate, startTime, duration);

        return availableCleaners.stream().map(CleanerAvailabilityMapper.INSTANCE::entityToDto).collect(Collectors.toList());
    }

    public List<CleanerAvailabilityResponseDTO> getAllCleanersSchedule(LocalDate date) {
        val startTime = LocalTime.of(8, 0, 0);
        val endTime = LocalTime.of(22, 0, 0);
        List<Cleaner> cleaners = cleanerRepository.findAll(overlapsTimePeriod(startTime.atDate(date), endTime.atDate(date)));

        return cleaners
                .stream()
                .map(it -> new CleanerAvailabilityResponseDTO(it.getId(), it.getName(), it.getSurname(), getAvailableSchedule(it)))
                .collect(Collectors.toList());
    }

    private List<AvailableTimePeriodResponseDTO> getAvailableSchedule(Cleaner c) {
        var timeStart = LocalTime.of(8, 0, 0);
        var timeEnd = LocalTime.of(22, 0, 0);

        if (c.getAppointments() == null || c.getAppointments().isEmpty()) {
            return List.of(new AvailableTimePeriodResponseDTO(timeStart, timeEnd));
        }
        val appointments = c.getAppointments().stream().sorted(Comparator.comparing(Appointment::getStartTime)).toList();

        val result = new ArrayList<AvailableTimePeriodResponseDTO>();
        for (final Appointment a : appointments) {
            LocalTime appStartTime = a.getStartTime().toLocalTime();
            if (appStartTime.isAfter(timeStart)) {
                result.add(new AvailableTimePeriodResponseDTO(timeStart, appStartTime.minusMinutes(30)));
            }
            timeStart = a.getEndTime().toLocalTime().plusMinutes(30);
        }
        if (timeStart.isBefore(timeEnd)) {
            result.add(new AvailableTimePeriodResponseDTO(timeStart, timeEnd));
        }
        return result;

    }

    @Transactional
    public void create(Appointment appointment, Integer cleanerCount) {
        List<Cleaner> availableCleaners = cleanerRepository.findAll(notOverlapsTimePeriod(appointment.getStartTime().minusMinutes(30), appointment.getEndTime().plusMinutes(30)));

        Map<Long, List<Cleaner>> cleanersPerVehicle = availableCleaners.stream().collect(Collectors.groupingBy(cleaner -> cleaner.getVehicle().getId()));

        List<Cleaner> cleaners = new ArrayList<>();
        for (Map.Entry<Long, List<Cleaner>> entry : cleanersPerVehicle.entrySet()) {
            if (entry.getValue().size() >= cleanerCount) {
                cleaners = entry.getValue().subList(0, cleanerCount);
                break;
            }
        }
        appointment.getCleaners().addAll(cleaners);
       // appointment.getCleaners().add(availableCleaners.get(0));
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
