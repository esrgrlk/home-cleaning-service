package com.justlife.homecleaningservice.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AbstractVersionedAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Version
    @Column(name = "VERSION")
    private int version = 0;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Column(insertable = true, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false, updatable = true)
    private LocalDateTime lastModifiedDate;
}
