package com.hms.repository;

import com.hms.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);

    @Query("SELECT COUNT(d) FROM Doctor d")
    Long countTotalDoctors();

    @Query("SELECT COUNT(d) FROM Doctor d WHERE d.availableToday = true")
    Long countAvailableToday();

    @Query("SELECT COUNT(d) FROM Doctor d WHERE d.onLeave = true")
    Long countOnLeave();

    @Query("SELECT d.specialization, COUNT(d) FROM Doctor d GROUP BY d.specialization")
    List<Object[]> countBySpecialization();
}
