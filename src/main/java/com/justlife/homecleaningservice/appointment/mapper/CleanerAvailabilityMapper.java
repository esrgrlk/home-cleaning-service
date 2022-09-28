package com.justlife.homecleaningservice.appointment.mapper;

import com.justlife.homecleaningservice.appointment.dto.CleanerAvailabilityResponseDTO;
import com.justlife.homecleaningservice.appointment.entity.Appointment;
import com.justlife.homecleaningservice.appointment.entity.Cleaner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CleanerAvailabilityMapper {

    CleanerAvailabilityMapper INSTANCE = Mappers.getMapper(CleanerAvailabilityMapper.class);

    @Mapping(target = "availableTimePeriodDTOList", ignore = true)
    CleanerAvailabilityResponseDTO entityToDto(Cleaner cleaner);
}
