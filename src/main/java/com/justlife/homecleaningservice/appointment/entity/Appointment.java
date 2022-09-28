package com.justlife.homecleaningservice.appointment.entity;

import com.justlife.homecleaningservice.common.entity.AbstractVersionedAuditableEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@Table(name = "APPOINTMENT")
public class Appointment extends AbstractVersionedAuditableEntity {

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "APPOINTMENT_CLEANER",
            joinColumns = @JoinColumn(name = "APPOINTMENT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CLEANER_ID", referencedColumnName = "ID"))
    private Set<Cleaner> cleaners;

    public Appointment() {
    }

    public Appointment(LocalDateTime startTime, LocalDateTime endTime, Set<Cleaner> cleaners) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.cleaners = cleaners;
    }
}
