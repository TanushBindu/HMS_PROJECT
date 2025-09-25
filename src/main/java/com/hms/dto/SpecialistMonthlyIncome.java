package com.hms.dto;

public class SpecialistMonthlyIncome {
    private String specialization;
    private Double totalIncome;
    private Integer month;

    public SpecialistMonthlyIncome(String specialization, Double totalIncome, Integer month) {
        this.specialization = specialization;
        this.totalIncome = totalIncome;
        this.month = month;
    }

    // Getters & Setters
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public Double getTotalIncome() { return totalIncome; }
    public void setTotalIncome(Double totalIncome) { this.totalIncome = totalIncome; }

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }
}
