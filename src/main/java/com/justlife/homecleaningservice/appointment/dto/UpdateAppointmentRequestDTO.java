package com.justlife.homecleaningservice.appointment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justlife.homecleaningservice.common.validations.IsAfter;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(example = "3", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(example = "2022-09-29", required = true)
    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(example = "14:00:00", required = true)
    @NotNull
    @IsAfter(time = "08:00", message = "Appointment can not be started before 08:00")
    private LocalTime startTime;
}
