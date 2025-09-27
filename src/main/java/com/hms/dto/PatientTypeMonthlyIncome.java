package com.hms.dto;

public class PatientTypeMonthlyIncome {
    private String patientType;
    private Double totalAmount;

    public PatientTypeMonthlyIncome(String patientType, Double totalAmount) {
        this.patientType = patientType;
        this.totalAmount = totalAmount;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
