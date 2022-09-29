package com.justlife.homecleaningservice.appointment.controller;

import com.justlife.homecleaningservice.appointment.dto.AppointmentResponseDTO;
import com.justlife.homecleaningservice.appointment.dto.CleanerAvailabilityResponseDTO;
import com.justlife.homecleaningservice.appointment.dto.CreateAppointmentRequestDTO;
import com.justlife.homecleaningservice.appointment.dto.UpdateAppointmentRequestDTO;
import com.justlife.homecleaningservice.appointment.mapper.AppointmentMapper;
import com.justlife.homecleaningservice.appointment.mapper.CreateAppointmentMapper;
import com.justlife.homecleaningservice.appointment.mapper.UpdateAppointmentMapper;
import com.justlife.homecleaningservice.appointment.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@ResponseStatus(HttpStatus.OK)
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentService.getAllAppointments().stream().map(AppointmentMapper.INSTANCE::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/availability")
    public List<CleanerAvailabilityResponseDTO> getCleanersAvailability(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam(required = false) Integer duration) {

        if (startDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fridays are holy");
        }
        return appointmentService.getCleanersAvailability(startDate, startTime, duration);
    }

    @PostMapping
    public void create(@Valid @RequestBody CreateAppointmentRequestDTO createAppointmentRequestDTO) {
        appointmentService.create(CreateAppointmentMapper.INSTANCE.convertToEntity(createAppointmentRequestDTO), createAppointmentRequestDTO.getCleanerCount());
    }

    @PutMapping
    public void update(@Valid @RequestBody UpdateAppointmentRequestDTO updateAppointmentRequestDTO) {
        appointmentService.update(UpdateAppointmentMapper.INSTANCE.convertToEntity(updateAppointmentRequestDTO));
    }
}
