package com.justlife.homecleaningservice.integrationtest.appointment;

import com.justlife.homecleaningservice.appointment.entity.Appointment;
import com.justlife.homecleaningservice.appointment.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TestAppointmentRepository {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    public void testFindById() {
        Optional<Appointment> appointment = appointmentRepository.findById(1L);
        assertTrue(appointment.isPresent());
    }
}