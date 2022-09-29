package com.justlife.homecleaningservice.appointment.repository.specifications;

import com.justlife.homecleaningservice.appointment.entity.Appointment;
import com.justlife.homecleaningservice.appointment.entity.Appointment_;
import com.justlife.homecleaningservice.appointment.entity.Cleaner;
import com.justlife.homecleaningservice.appointment.entity.Cleaner_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.time.LocalDateTime;

public class CleanerSpecifications {

    public static Specification<Cleaner> notOverlapsTimePeriod(LocalDateTime startTime, LocalDateTime endTime) {
        return (root, query, cb) -> {
            Join<Cleaner, Appointment> join = root.join(Cleaner_.appointments, JoinType.LEFT);
            query.distinct(true);
            return cb.or(cb.isNull(join.get(Appointment_.id)),
                    cb.or(
                            cb.greaterThanOrEqualTo(join.get(Appointment_.startTime), endTime),
                            cb.lessThanOrEqualTo(join.get(Appointment_.endTime), startTime)
                    ));
        };
    }

    public static Specification<Cleaner> overlapsTimePeriod(LocalDateTime startTime, LocalDateTime endTime) {
        return (root, query, cb) -> {
            Join<Cleaner, Appointment> join = root.join(Cleaner_.appointments, JoinType.LEFT);
            return cb.or(cb.isNull(join.get(Appointment_.id)),
                    cb.and(
                            cb.greaterThanOrEqualTo(join.get(Appointment_.startTime), startTime),
                            cb.lessThanOrEqualTo(join.get(Appointment_.endTime), endTime)
                    ));
        };
    }
}
