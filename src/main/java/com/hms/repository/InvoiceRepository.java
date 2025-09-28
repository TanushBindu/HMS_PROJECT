package com.hms.repository;

import com.hms.model.Invoice;
import com.hms.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
