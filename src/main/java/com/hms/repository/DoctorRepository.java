package com.hms.repository;

import com.hms.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT d FROM Doctor d " +
            "WHERE (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:specialization IS NULL OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :specialization, '%'))) " +
            "AND (:available IS NULL OR d.availableToday = :available) " +
            "AND d.isActive = true")
    List<Doctor> searchDoctors(@Param("name") String name,
                               @Param("specialization") String specialization,
                               @Param("available") Boolean available);

    List<Doctor> findByIsActiveTrue();
}
