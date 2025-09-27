package com.hms.repository;

import com.hms.dto.AccountsMonthlyIncome;
import com.hms.dto.PatientTypeMonthlyIncome;
import com.hms.dto.SpecialistMonthlyIncome;
import com.hms.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByPatient_NameContainingIgnoreCaseOrDoctor_NameContainingIgnoreCaseOrTreatmentContainingIgnoreCase(
            String patientName, String doctorName, String treatment);

    @Query("SELECT new com.hms.dto.PatientTypeMonthlyIncome(p.type, SUM(i.amount)) " +
            "FROM Invoice i JOIN i.patient p WHERE YEAR(i.date) = :year GROUP BY p.type")
    List<PatientTypeMonthlyIncome> getPatientTypeMonthlyIncome(@Param("year") int year);

    @Query("SELECT new com.hms.dto.SpecialistMonthlyIncome(i.doctor.specialization, SUM(i.amount)) " +
            "FROM Invoice i WHERE YEAR(i.date) = :year GROUP BY i.doctor.specialization")
    List<SpecialistMonthlyIncome> getSpecialistMonthlyIncome(@Param("year") int year);

    @Query("SELECT SUM(i.amount) FROM Invoice i WHERE i.patient.type = :type AND MONTH(i.date) = :month AND YEAR(i.date) = :year")
    Double getMonthlyRevenueByPatientType(@Param("type") String type,
                                          @Param("month") int month,
                                          @Param("year") int year);

    @Query("SELECT MONTH(i.date), SUM(i.amount) " +
            "FROM Invoice i WHERE YEAR(i.date) = :year GROUP BY MONTH(i.date)")
    List<Object[]> getMonthlyYearlyExpenditure(@Param("year") int year);

    @Query("SELECT SUM(i.amount) FROM Invoice i WHERE i.category='BIOMEDICAL_WASTE'")
    Double getBiomedicalWasteIncome();

    @Query("SELECT SUM(i.amount) AS budget, SUM(i.amount)*0.12 AS roi FROM Invoice i")
    Map<String, Double> getRoiAndBudget();

    @Query("SELECT MONTH(i.date), SUM(i.amount) " +
            "FROM Invoice i WHERE YEAR(i.date) = :year GROUP BY MONTH(i.date)")
    List<Object[]> getMonthlyYearlyRevenue(@Param("year") int year);

    @Query("SELECT SUM(i.amount) FROM Invoice i WHERE YEAR(i.date) = :year")
    Double getYearlyRevenue(@Param("year") int year);
    @Query("SELECT i FROM Invoice i " +
            "WHERE LOWER(i.patient.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(i.doctor.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(i.treatment) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Invoice> searchInvoices(@Param("keyword") String keyword);

}
