package com.justlife.homecleaningservice.appointment.repository;

import com.justlife.homecleaningservice.appointment.entity.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner, Long>, JpaSpecificationExecutor<Cleaner> {
}
