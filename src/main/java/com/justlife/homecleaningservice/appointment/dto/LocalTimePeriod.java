package com.justlife.homecleaningservice.appointment.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LocalTimePeriod {

    private LocalTime start;

    private LocalTime end;


}
