package com.justlife.homecleaningservice.appointment.entity;

import com.justlife.homecleaningservice.common.entity.AbstractVersionedAuditableEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLEANER")
public class Cleaner extends AbstractVersionedAuditableEntity {

    public static final int MIN_BREAK_DURATION_IN_MINUTES = 30;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cleaners")
    private Set<Appointment> appointments;

}
