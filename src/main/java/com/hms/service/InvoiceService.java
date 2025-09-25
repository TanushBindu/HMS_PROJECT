package com.hms.service;

import com.hms.model.Invoice;
import com.hms.model.Patient;
import com.hms.model.Doctor;
import com.hms.repository.InvoiceRepository;
import com.hms.repository.PatientRepository;
import com.hms.repository.DoctorRepository;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Get all invoices
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    // Get single invoice
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id " + id));
    }

    // Create invoice
    public Invoice createInvoice(Invoice invoice, Long patientId, Long doctorId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + patientId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id " + doctorId));

        invoice.setPatient(patient);
        invoice.setDoctor(doctor);
        invoice.setDate(Date.from(LocalDate.now().atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        invoice.setCustomerName(patient.getName()); // Auto populate
        return invoiceRepository.save(invoice);
    }

    // Update invoice
    public Invoice updateInvoice(Long id, Invoice invoiceDetails, Long patientId, Long doctorId) {
        Invoice invoice = getInvoiceById(id);

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + patientId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id " + doctorId));

        invoice.setPatient(patient);
        invoice.setDoctor(doctor);
        invoice.setCustomerName(patient.getName());
        invoice.setTreatment(invoiceDetails.getTreatment());
        invoice.setAmount(invoiceDetails.getAmount());
        invoice.setPaymentMode(invoiceDetails.getPaymentMode());
        invoice.setPaid(invoiceDetails.isPaid());
        invoice.setStatus(invoiceDetails.getStatus());

        return invoiceRepository.save(invoice);
    }

    // Delete invoice
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    // Search invoices by customer name, treatment, or doctor name
    public List<Invoice> searchInvoices(String keyword) {
        return invoiceRepository
                .findByPatient_NameContainingIgnoreCaseOrDoctor_NameContainingIgnoreCaseOrTreatmentContainingIgnoreCase(
                        keyword, keyword, keyword
                );
    }

    // Generate PDF
    public byte[] generatePdf(Long id) {
        Invoice invoice = getInvoiceById(id);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph title = new Paragraph("Hospital Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Invoice ID: " + invoice.getId()));
            document.add(new Paragraph("Date: " + invoice.getDate()));
            document.add(new Paragraph("Customer Name: " + invoice.getCustomerName()));
            document.add(new Paragraph("Patient: " + invoice.getPatient().getName()));
            document.add(new Paragraph("Doctor: " + invoice.getDoctor().getName()));
            document.add(new Paragraph("Treatment: " + invoice.getTreatment()));
            document.add(new Paragraph("Amount: ₹" + invoice.getAmount()));
            document.add(new Paragraph("Payment Mode: " + invoice.getPaymentMode()));
            document.add(new Paragraph("Status: " + invoice.getStatus()));
            document.add(new Paragraph("Paid: " + (invoice.isPaid() ? "Yes" : "No")));

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating invoice PDF", e);
        }
    }
}
