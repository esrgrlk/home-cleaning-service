package com.justlife.homecleaningservice.appointment.entity;

import com.justlife.homecleaningservice.common.entity.AbstractVersionedAuditableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "VEHICLE")
public class Vehicle extends AbstractVersionedAuditableEntity {

    private static final int CLEANER_CAPACITY = 5;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    private Set<Cleaner> cleaners;

    @Column(name = "LICENSE_PLATE")
    private String licensePlate;

    @Column(name = "DRIVER_NAME")
    private String driverName;
}
