package com.justlife.homecleaningservice.common.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeValidator implements ConstraintValidator<IsAfter, LocalTime> {

    private IsAfter isAfter;

    @Override
    public void initialize(IsAfter constraintAnnotation) {
        this.isAfter = constraintAnnotation;
    }

    @Override
    public boolean isValid(LocalTime localTime, ConstraintValidatorContext constraintValidatorContext) {
        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(isAfter.time(), parseFormat);
        return localTime.isAfter(time) || localTime.equals(time);
    }
}
