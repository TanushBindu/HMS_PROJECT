package com.hms.repository;

import com.hms.model.Appointment;
import com.hms.model.Doctor;
import com.hms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Search by name containing keyword
    List<Doctor> findByNameContainingIgnoreCase(String keyword);

    // Search by specialization containing keyword
    List<Doctor> findBySpecializationContainingIgnoreCase(String keyword);

    // Or combine both in a single method using JPQL
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Doctor> searchByKeyword(@Param("keyword") String keyword);
}