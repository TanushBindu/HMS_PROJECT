package com.hms.dto;

import com.hms.model.Invoice;

public class InvoiceDto {

    private Long id;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
    private String treatment;
    private double amount;
    private String paymentMode;
    private boolean paid;

    public InvoiceDto(Invoice invoice) {
        this.id = invoice.getId();
        this.patientId = invoice.getPatient() != null ? invoice.getPatient().getId() : null;
        this.patientName = invoice.getPatient() != null ? invoice.getPatient().getName() : null;
        this.doctorId = invoice.getDoctor() != null ? invoice.getDoctor().getId() : null;
        this.doctorName = invoice.getDoctor() != null ? invoice.getDoctor().getName() : null;
        this.treatment = invoice.getTreatment();
        this.amount = invoice.getAmount();
        this.paymentMode = invoice.getPaymentMode().name();
        this.paid = invoice.isPaid();
    }


    // Getters and setters
}
