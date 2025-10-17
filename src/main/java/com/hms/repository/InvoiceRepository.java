package com.hms.repository;

import com.hms.model.Invoice;
import com.hms.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByPatientNameContainingIgnoreCaseOrDoctorNameContainingIgnoreCaseOrTreatmentContainingIgnoreCase(
            String patient, String doctor, String treatment);
    // ✅ Correct: search by patient name
    List<Invoice> findByPatientNameContainingIgnoreCase(String patientName);

    // ✅ Correct: search by doctor name
    List<Invoice> findByDoctorNameContainingIgnoreCase(String doctorName);

    // ✅ Correct: search by status
    List<Invoice> findByStatus(String status);

    @Query("SELECT SUM(i.amount) FROM Invoice i WHERE i.status = 'Paid'")
    Double getOverallIncome();

    @Query("SELECT COUNT(i) FROM Invoice i")
    Long countInvoices();

    @Query("SELECT SUM(i.amount) FROM Invoice i WHERE MONTH(i.date) = MONTH(CURRENT_DATE) AND YEAR(i.date) = YEAR(CURRENT_DATE) AND i.status = 'Paid'")
    Double getMonthlyRevenue();

    @Query("SELECT SUM(i.amount) FROM Invoice i WHERE YEAR(i.date) = YEAR(CURRENT_DATE) AND i.status = 'Paid'")
    Double getYearlyRevenue();

    @Query("SELECT SUM(i.amount) FROM Invoice i WHERE i.status = 'Pending'")
    Double getPendingAmount();

    List<Invoice> findAll();
}
