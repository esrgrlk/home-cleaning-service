package com.justlife.homecleaningservice.appointment.entity;

import com.justlife.homecleaningservice.common.entity.AbstractVersionedAuditableEntity;
import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "APPOINTMENT")
public class Appointment extends AbstractVersionedAuditableEntity {

    public static final DayOfWeek OFF_DAY = DayOfWeek.FRIDAY;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "APPOINTMENT_CLEANER",
            joinColumns = @JoinColumn(name = "APPOINTMENT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CLEANER_ID", referencedColumnName = "ID"))
    private Set<Cleaner> cleaners;

    public void update(Appointment appointment) {
        this.startTime = appointment.getStartTime();
        this.endTime = appointment.getEndTime();
    }

    public long getDuration() {
        return Duration.between(startTime, endTime).toHours();
    }

    public boolean isDayOfWeekSuitable() {
        return startTime.getDayOfWeek() != OFF_DAY;
    }

    public boolean isEqualStartDate(LocalDate date) {
        return startTime.toLocalDate().isEqual(date);
    }
}
