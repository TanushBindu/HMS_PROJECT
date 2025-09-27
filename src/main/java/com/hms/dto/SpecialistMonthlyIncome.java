package com.hms.dto;

public class SpecialistMonthlyIncome {
    private String specialization;
    private Double totalAmount;

    public SpecialistMonthlyIncome(String specialization, Double totalAmount) {
        this.specialization = specialization;
        this.totalAmount = totalAmount;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
