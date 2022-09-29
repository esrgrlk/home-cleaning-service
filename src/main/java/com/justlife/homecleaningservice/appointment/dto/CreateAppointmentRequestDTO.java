package com.justlife.homecleaningservice.appointment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justlife.homecleaningservice.common.validations.IsAfter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequestDTO {

    @ApiModelProperty(example = "2022-09-29", required = true)
    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(example = "10:00:00", required = true)
    @NotNull
    @IsAfter(time = "08:00", message = "Appointment can not be started before 08:00")
    private LocalTime startTime;

    @ApiModelProperty(example = "2", required = true)
    @NotNull
    @Min(2)
    @Max(4)
    private Integer duration;

    @ApiModelProperty(example = "3", required = true)
    @NotNull
    @Min(1)
    @Max(3)
    private Integer cleanerCount;
}
