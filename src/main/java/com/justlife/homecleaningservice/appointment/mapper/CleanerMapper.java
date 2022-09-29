package com.justlife.homecleaningservice.appointment.mapper;

import com.justlife.homecleaningservice.appointment.dto.CleanerResponseDTO;
import com.justlife.homecleaningservice.appointment.entity.Cleaner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CleanerMapper {

    CleanerMapper INSTANCE = Mappers.getMapper(CleanerMapper.class);

    @Mappings({
            @Mapping(target = "vehicleId", source = "vehicle.id")
    })
    CleanerResponseDTO entityToDto(Cleaner cleaner);
}
