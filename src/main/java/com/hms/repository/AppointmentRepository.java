package com.hms.repository;

import com.hms.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Count appointments today
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.dateTime >= :startOfDay AND a.dateTime <= :endOfDay")
    Long getTodayAppointmentsCount(@Param("startOfDay") LocalDateTime startOfDay,
                                   @Param("endOfDay") LocalDateTime endOfDay);

    // Count all today appointments without parameters
    default Long countTodayAppointments() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.now().toLocalDate().atTime(23,59,59);
        return getTodayAppointmentsCount(startOfDay, endOfDay);
    }

    // Count upcoming appointments for a patient
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.patient.id = :patientId AND a.dateTime > :now")
    Long countUpcomingForPatient(@Param("patientId") Long patientId,
                                 @Param("now") LocalDateTime now);

    // Other repository methods
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByPatientIdAndStatus(Long patientId, String status);
}
