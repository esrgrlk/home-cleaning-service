package com.justlife.homecleaningservice.appointment.service;

import com.justlife.homecleaningservice.appointment.dto.AvailableTimePeriodResponseDTO;
import com.justlife.homecleaningservice.appointment.dto.CleanerAvailabilityResponseDTO;
import com.justlife.homecleaningservice.appointment.entity.Appointment;
import com.justlife.homecleaningservice.appointment.entity.Cleaner;
import com.justlife.homecleaningservice.appointment.mapper.CleanerAvailabilityMapper;
import com.justlife.homecleaningservice.appointment.repository.AppointmentRepository;
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

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final CleanerService cleanerService;

    @Transactional(readOnly = true)
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CleanerAvailabilityResponseDTO> getCleanersAvailability(LocalDate startDate, LocalTime startTime, Integer duration) {
        if (startDate.getDayOfWeek() == Appointment.OFF_DAY) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, AppointmentMessages.ERROR_APPOINTMENT_OFF_DAY);
        }

        if (startTime == null || duration == null) {
            return getAvailableCleanersSchedule(startDate);
        }

        List<Cleaner> availableCleaners = cleanerService.getAvailableCleaners(startDate, startTime, duration);
        return availableCleaners.stream().map(CleanerAvailabilityMapper.INSTANCE::entityToDto).collect(Collectors.toList());
    }

    public List<CleanerAvailabilityResponseDTO> getAvailableCleanersSchedule(LocalDate date) {
        List<Cleaner> availableCleaners = cleanerService.getAvailableCleanersByDate(date);
        return availableCleaners.stream()
                .map(cleaner -> new CleanerAvailabilityResponseDTO(cleaner.getId(), cleaner.getName(), cleaner.getSurname(), getAvailableSchedule(cleaner)))
                .collect(Collectors.toList());
    }

    private List<AvailableTimePeriodResponseDTO> getAvailableSchedule(Cleaner cleaner) {
        LocalTime timeStart = Cleaner.START_WORKING_HOUR;
        LocalTime timeEnd = Cleaner.END_WORKING_HOUR;

        if (cleaner.getAppointments() == null || cleaner.getAppointments().isEmpty()) {
            return List.of(new AvailableTimePeriodResponseDTO(timeStart, timeEnd));
        }
        List<Appointment> appointments = cleaner.getAppointments().stream().sorted(Comparator.comparing(Appointment::getStartTime)).toList();

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
        if (!appointment.isDayOfWeekSuitable()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, AppointmentMessages.ERROR_APPOINTMENT_OFF_DAY);
        }

        List<Cleaner> availableCleaners = getAvailableCleanersByCleanerCount(appointment, cleanerCount);
        appointment.getCleaners().addAll(availableCleaners);
        appointmentRepository.save(appointment);
    }

    private List<Cleaner> getAvailableCleanersByCleanerCount(Appointment appointment, Integer cleanerCount) {
        List<Cleaner> availableCleaners = cleanerService.getAvailableCleaners(appointment.getStartTime(), appointment.getEndTime());

        List<Cleaner> cleaners = new ArrayList<>();
        if (cleanerCount == 1) {
            cleaners.add(availableCleaners.get(0));
        } else {
            Map<Long, List<Cleaner>> cleanersPerVehicle = availableCleaners.stream().collect(Collectors.groupingBy(cleaner -> cleaner.getVehicle().getId()));

            for (Map.Entry<Long, List<Cleaner>> entry : cleanersPerVehicle.entrySet()) {
                if (entry.getValue().size() >= cleanerCount) {
                    cleaners = entry.getValue().subList(0, cleanerCount);
                    break;
                }
            }
        }
        return cleaners;
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
