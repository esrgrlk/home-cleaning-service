package com.justlife.homecleaningservice.appointment.repository.specifications;

import com.justlife.homecleaningservice.appointment.entity.Appointment;
import com.justlife.homecleaningservice.appointment.entity.Cleaner;


import com.justlife.homecleaningservice.appointment.entity.Appointment_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class AppointmentSpecifications {

    public static Specification<Appointment> byStartTime(LocalDateTime startTime, LocalDateTime endTime) {
        return (root, query, cb) ->
                cb.or(
                        cb.greaterThanOrEqualTo(root.get(Appointment_.startTime), endTime),
                        cb.lessThanOrEqualTo(root.get(Appointment_.endTime), startTime)
                );
    }

    public static Specification<Appointment> nullAppointment() {
        return (root, query, cb) -> {
            Join<Appointment, Cleaner> join = root.join(Appointment_.cleaners, JoinType.RIGHT);
            return cb.isNull(join.get(Appointment_.id));
        };
    }
}
