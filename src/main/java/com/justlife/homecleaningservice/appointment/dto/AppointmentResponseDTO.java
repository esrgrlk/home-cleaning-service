package com.justlife.homecleaningservice.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {

    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private List<CleanerResponseDTO> cleanerResponseDTOList;
}
