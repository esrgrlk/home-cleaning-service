package com.justlife.homecleaningservice.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CleanerResponseDTO {

    private Long id;

    private String name;

    private String surname;

    private Long vehicleId;
}
