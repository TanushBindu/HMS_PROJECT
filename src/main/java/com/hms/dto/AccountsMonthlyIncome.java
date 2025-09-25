package com.hms.dto;

public class AccountsMonthlyIncome {
    private String type; // e.g., specialization or patientType
    private double amount;
    private int month;

    public AccountsMonthlyIncome(String type, double amount, int month) {
        this.type = type;
        this.amount = amount;
        this.month = month;
    }

    // Getters and setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }
}
