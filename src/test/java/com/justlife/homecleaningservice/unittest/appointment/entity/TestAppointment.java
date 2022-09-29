package com.justlife.homecleaningservice.unittest.appointment.entity;

import com.justlife.homecleaningservice.appointment.entity.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAppointment {

    private Appointment appointment;

    @BeforeEach
    public void beforeTest() {
        appointment = new Appointment();
    }

    @Test
    public void testIsDayOfWeekSuitable() {
        appointment.setStartTime(LocalDateTime.of(2022, 9, 29, 9, 0, 0));

        assertTrue(appointment.isDayOfWeekSuitable());
    }

    @Test
    public void testIsDayOfWeekNotSuitable() {
        appointment.setStartTime(LocalDateTime.of(2022, 9, 30, 9, 0, 0));

        assertFalse(appointment.isDayOfWeekSuitable());
    }

    @Test
    public void testIsEqualStartDate() {
        appointment.setStartTime(LocalDateTime.of(2022, 9, 29, 9, 0, 0));

        assertTrue(appointment.isEqualStartDate(LocalDate.of(2022, 9, 29)));
    }

    @Test
    public void testIsNotEqualStartDate() {
        appointment.setStartTime(LocalDateTime.of(2022, 9, 29, 9, 0, 0));

        assertFalse(appointment.isEqualStartDate(LocalDate.of(2022, 9, 20)));
    }
}
