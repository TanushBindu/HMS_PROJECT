package com.hms.service;

import com.hms.dto.AccountsMonthlyIncome;
import com.hms.model.BiomedicalWasteIncome;
import com.hms.repository.BiomedicalWasteIncomeRepository;
import com.hms.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountsService {

    private final InvoiceRepository invoiceRepository;
    private final BiomedicalWasteIncomeRepository biomedicalRepo;

    public AccountsService(InvoiceRepository invoiceRepository,
                           BiomedicalWasteIncomeRepository biomedicalRepo) {
        this.invoiceRepository = invoiceRepository;
        this.biomedicalRepo = biomedicalRepo;
    }

    public List<AccountsMonthlyIncome> getSpecialistIncome(int year) {
        return invoiceRepository.getSpecialistMonthlyIncome(year);
    }

    public List<AccountsMonthlyIncome> getPatientTypeIncome(int year) {
        return invoiceRepository.getPatientTypeMonthlyIncome(year);
    }

    public List<BiomedicalWasteIncome> getBiomedicalIncome(LocalDate start, LocalDate end) {
        return biomedicalRepo.findByDateBetween(start, end);
    }

    // Dummy methods for ROI/Budget/Revenue/Expenditure
    public double getTotalRevenue(int year) {
        return invoiceRepository.findAll().stream()
                .filter(i -> i.getDate().getYear() == year)
                .mapToDouble(i -> i.getAmount())
                .sum();
    }

    public double getTotalExpenditure(int year) {
        // Replace with actual expenditure logic
        return 50000; // dummy
    }
}
