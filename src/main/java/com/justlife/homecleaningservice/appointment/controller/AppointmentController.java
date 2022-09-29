package com.justlife.homecleaningservice.appointment.controller;

import com.justlife.homecleaningservice.appointment.dto.AppointmentResponseDTO;
import com.justlife.homecleaningservice.appointment.dto.CleanerAvailabilityResponseDTO;
import com.justlife.homecleaningservice.appointment.dto.CreateAppointmentRequestDTO;
import com.justlife.homecleaningservice.appointment.dto.UpdateAppointmentRequestDTO;
import com.justlife.homecleaningservice.appointment.mapper.AppointmentMapper;
import com.justlife.homecleaningservice.appointment.mapper.CreateAppointmentMapper;
import com.justlife.homecleaningservice.appointment.mapper.UpdateAppointmentMapper;
import com.justlife.homecleaningservice.appointment.service.AppointmentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @ApiOperation(value = "Lists all appointments with cleaners")
    @GetMapping
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentService.getAllAppointments().stream().map(AppointmentMapper.INSTANCE::entityToDto).collect(Collectors.toList());
    }

    @ApiOperation(value = "Lists available cleaners and their available times for the given time period",
                notes = "If only start date is provided, returns available cleaners and their available times for the given date. " +
                        "If all parameters are provided, returns available cleaners for the given time period")
    @GetMapping("/availability")
    public List<CleanerAvailabilityResponseDTO> getCleanersAvailability(
            @ApiParam(example = "2022-09-29", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @ApiParam(example = "08:00:00") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @ApiParam(example = "2") @RequestParam(required = false) Integer duration) {
        return appointmentService.getCleanersAvailability(startDate, startTime, duration);
    }

    @ApiOperation(value = "Creates a new appointment")
    @PostMapping
    public void create(@Valid @RequestBody CreateAppointmentRequestDTO createAppointmentRequestDTO) {
        appointmentService.create(CreateAppointmentMapper.INSTANCE.convertToEntity(createAppointmentRequestDTO), createAppointmentRequestDTO.getCleanerCount());
    }

    @ApiOperation(value = "Updates the appointment")
    @PutMapping
    public void update(@Valid @RequestBody UpdateAppointmentRequestDTO updateAppointmentRequestDTO) {
        appointmentService.update(UpdateAppointmentMapper.INSTANCE.convertToEntity(updateAppointmentRequestDTO));
    }
}
