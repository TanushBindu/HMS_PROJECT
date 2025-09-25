package com.hms.repository;

import com.hms.dto.AccountsMonthlyIncome;
import com.hms.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT new com.hms.dto.AccountsMonthlyIncome(d.specialization, SUM(i.amount), MONTH(i.date)) " +
            "FROM Invoice i JOIN i.doctor d " +
            "WHERE YEAR(i.date) = :year " +
            "GROUP BY d.specialization, MONTH(i.date)")
    List<AccountsMonthlyIncome> getSpecialistMonthlyIncome(int year);

    @Query("SELECT new com.hms.dto.AccountsMonthlyIncome(p.type, SUM(i.amount), MONTH(i.date)) " +
            "FROM Invoice i JOIN i.patient p " +
            "WHERE YEAR(i.date) = :year " +
            "GROUP BY p.patientType, MONTH(i.date)")
    List<AccountsMonthlyIncome> getPatientTypeMonthlyIncome(int year);

    List<Invoice> findByCustomerNameContainingIgnoreCaseOrTreatmentContainingIgnoreCase(String customerName, String treatment);
}
