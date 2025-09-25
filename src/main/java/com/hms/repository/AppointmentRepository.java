package com.hms.repository;

import com.hms.dto.AccountsMonthlyIncome;
import com.hms.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    long countByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Appointment a WHERE LOWER(a.patient.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.doctor.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Appointment> search(@Param("keyword") String keyword);

    // Search by patient or doctor name
    @Query("SELECT a FROM Appointment a WHERE LOWER(a.patient.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.doctor.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Appointment> findByPatientNameContainingIgnoreCaseOrDoctorNameContainingIgnoreCase(@Param("keyword") String keyword, @Param("keyword") String keyword2);
    // Specialist-wise monthly income
    @Query("SELECT new com.hms.dto.AccountsMonthlyIncome(p.type, SUM(i.amount), MONTH(i.date))\n" +
            "FROM Invoice i\n" +
            "JOIN i.patient p\n" +
            "WHERE YEAR(i.date) = :year\n" +
            "GROUP BY p.type, MONTH(i.date)")
    List<AccountsMonthlyIncome> getSpecialistMonthlyIncome(int year);

    // OPD/IPD monthly income
    @Query("SELECT new com.hms.dto.AccountsMonthlyIncome(p.type, SUM(i.amount), MONTH(i.date)) " +
            "FROM Invoice i JOIN i.patient p " +
            "WHERE YEAR(i.date) = :year " +
            "GROUP BY p.type, MONTH(i.date)")
    List<AccountsMonthlyIncome> getPatientTypeMonthlyIncome(@Param("year")int year);
}
