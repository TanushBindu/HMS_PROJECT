package com.hms.service;

import com.hms.dto.AccountsMonthlyIncome;
import com.hms.dto.SpecialistMonthlyIncome;
import com.hms.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public AccountsService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<AccountsMonthlyIncome> getPatientTypeMonthlyIncome(int year) {
        return invoiceRepository.getPatientTypeMonthlyIncome(year);
    }

    public List<SpecialistMonthlyIncome> getSpecialistMonthlyIncome(int year) {
        return invoiceRepository.getSpecialistMonthlyIncome(year);
    }

    public Double getYearlyRevenue(int year) {
        return invoiceRepository.getYearlyRevenue(year);
    }

    public Double getMonthlyRevenue(int month, int year) {
        return invoiceRepository.getMonthlyRevenue(month, year);
    }
}
