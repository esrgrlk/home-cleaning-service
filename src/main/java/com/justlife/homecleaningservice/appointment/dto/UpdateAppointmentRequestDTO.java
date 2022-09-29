package com.justlife.homecleaningservice.appointment.dto;

import com.justlife.homecleaningservice.common.validations.IsAfter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentRequestDTO {

    @NotNull
    private Long id;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    @IsAfter(time = "08:00", message = "Appointment can not be started before 08:00")
    private LocalTime startTime;
}
