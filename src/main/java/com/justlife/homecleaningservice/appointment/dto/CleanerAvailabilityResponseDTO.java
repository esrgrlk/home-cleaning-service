package com.justlife.homecleaningservice.appointment.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CleanerAvailabilityResponseDTO {

    private Long id;

    private String name;

    private String surname;

    private List<LocalTimePeriod> availableTimePeriodDTOList;
}
