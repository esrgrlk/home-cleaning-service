package com.justlife.homecleaningservice.appointment.entity;

import com.justlife.homecleaningservice.common.entity.AbstractVersionedAuditableEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@Table(name = "CLEANER")
public class Cleaner extends AbstractVersionedAuditableEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cleaners")
    private Set<Appointment> appointments;

    public Cleaner(String name, String surname, Vehicle vehicle, Set<Appointment> appointments) {
        this.name = name;
        this.surname = surname;
        this.vehicle = vehicle;
        this.appointments = appointments;
    }

    public Cleaner() {

    }
}
