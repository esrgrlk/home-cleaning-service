package com.justlife.homecleaningservice.appointment.mapper;

import com.justlife.homecleaningservice.appointment.dto.UpdateAppointmentRequestDTO;
import com.justlife.homecleaningservice.appointment.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.HashSet;

@Mapper
public interface UpdateAppointmentMapper {

    UpdateAppointmentMapper INSTANCE = Mappers.getMapper(UpdateAppointmentMapper.class);

    default Appointment convertToEntity(UpdateAppointmentRequestDTO updateAppointmentRequestDTO) {
        LocalDateTime startDateTime = LocalDateTime.of(updateAppointmentRequestDTO.getStartDate(), updateAppointmentRequestDTO.getStartTime());
        Appointment appointment = Appointment.builder()
                                            .startTime(startDateTime)
                                            .cleaners(new HashSet<>())
                                            .build();
        appointment.setId(updateAppointmentRequestDTO.getId());
        return appointment;
    }
}
