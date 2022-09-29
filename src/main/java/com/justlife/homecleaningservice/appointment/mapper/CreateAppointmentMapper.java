package com.justlife.homecleaningservice.appointment.mapper;

import com.justlife.homecleaningservice.appointment.dto.CreateAppointmentRequestDTO;
import com.justlife.homecleaningservice.appointment.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.HashSet;

@Mapper
public interface CreateAppointmentMapper {

    CreateAppointmentMapper INSTANCE = Mappers.getMapper(CreateAppointmentMapper.class);

    default Appointment convertToEntity(CreateAppointmentRequestDTO createAppointmentRequestDTO) {
        LocalDateTime startDateTime = LocalDateTime.of(createAppointmentRequestDTO.getStartDate(), createAppointmentRequestDTO.getStartTime());
        LocalDateTime endDateTime = startDateTime.plusHours(createAppointmentRequestDTO.getDuration());
        Appointment appointment = Appointment.builder()
                                            .startTime(startDateTime)
                                            .endTime(endDateTime)
                                            .cleaners(new HashSet<>())
                                            .build();
        appointment.setCreatedDate(LocalDateTime.now());
        return appointment;
    }
}
