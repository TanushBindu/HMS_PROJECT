package com.hms.repository;

import com.hms.dto.AccountsMonthlyIncome;
import com.hms.dto.SpecialistMonthlyIncome;
import com.hms.model.Invoice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT new com.hms.dto.AccountsMonthlyIncome(p.type, SUM(i.amount), MONTH(i.date)) " +
            "FROM Invoice i JOIN i.patient p " +
            "WHERE YEAR(i.date) = :year " +
            "GROUP BY p.type, MONTH(i.date)")
    List<AccountsMonthlyIncome> getPatientTypeMonthlyIncome(@Param("year") int year);

    @Query("SELECT new com.hms.dto.SpecialistMonthlyIncome(d.specialization, SUM(i.amount), MONTH(i.date)) " +
            "FROM Invoice i JOIN i.doctor d " +
            "WHERE YEAR(i.date) = :year " +
            "GROUP BY d.specialization, MONTH(i.date)")
    List<SpecialistMonthlyIncome> getSpecialistMonthlyIncome(@Param("year") int year);

    @Query("SELECT SUM(i.amount) FROM Invoice i WHERE YEAR(i.date) = :year")
    Double getYearlyRevenue(@Param("year") int year);

    @Query("SELECT SUM(i.amount) FROM Invoice i WHERE MONTH(i.date) = :month AND YEAR(i.date) = :year")
    Double getMonthlyRevenue(@Param("month") int month, @Param("year") int year);

    List<Invoice> findByPatient_NameContainingIgnoreCaseOrDoctor_NameContainingIgnoreCaseOrTreatmentContainingIgnoreCase(
            String patientName, String doctorName, String keyword);

}
