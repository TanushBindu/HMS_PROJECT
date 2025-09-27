package com.hms.dto;

public class AccountsMonthlyIncome {
    private String patientType;
    private Double totalIncome;
    private Integer month;

    public AccountsMonthlyIncome(String patientType, Double totalIncome, Integer month) {
        this.patientType = patientType;
        this.totalIncome = totalIncome;
        this.month = month;
    }

    public String getPatientType() { return patientType; }
    public void setPatientType(String patientType) { this.patientType = patientType; }

    public Double getTotalIncome() { return totalIncome; }
    public void setTotalIncome(Double totalIncome) { this.totalIncome = totalIncome; }

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }
}
