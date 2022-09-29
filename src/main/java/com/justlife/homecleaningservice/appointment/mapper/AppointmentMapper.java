package com.justlife.homecleaningservice.appointment.mapper;

import com.justlife.homecleaningservice.appointment.dto.AppointmentResponseDTO;
import com.justlife.homecleaningservice.appointment.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CleanerMapper.class})
public interface AppointmentMapper {

    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    @Mappings({
            @Mapping(target = "cleanerResponseDTOList", source = "cleaners")
    })
    AppointmentResponseDTO entityToDto(Appointment appointment);
}
