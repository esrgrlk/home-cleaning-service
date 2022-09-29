package com.justlife.homecleaningservice.unittest.appointment.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TestAppointmentController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testWrongStartDateFormat() throws Exception {
        this.mockMvc
                .perform(get("/appointments/availability").param("startDate", "20220929"))
                .andExpect(status().is(400))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));
    }

    @Test
    public void testWrongStartTimeFormat() throws Exception {
        this.mockMvc
                .perform(get("/appointments/availability")
                        .param("startDate", "2022-09-29")
                        .param("startTime", "8.00"))
                .andExpect(status().is(400))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));
    }
}