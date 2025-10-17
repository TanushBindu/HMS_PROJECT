package com.hms.service;

import com.hms.model.Invoice;
import com.hms.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }


    public Long countInvoices() {
        return invoiceRepository.countInvoices();
    }

    public Double getMonthlyRevenue() {
        return invoiceRepository.getMonthlyRevenue() != null ? invoiceRepository.getMonthlyRevenue() : 0.0;
    }

    public Double getYearlyRevenue() {
        return invoiceRepository.getYearlyRevenue() != null ? invoiceRepository.getYearlyRevenue() : 0.0;
    }

    public Double getPendingAmount() {
        return invoiceRepository.getPendingAmount() != null ? invoiceRepository.getPendingAmount() : 0.0;
    }

    public Double getOverallIncome() {
        return invoiceRepository.getOverallIncome() != null ? invoiceRepository.getOverallIncome() : 0.0;
    }

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    // ===== Income Stats =====
    public Map<String, Object> getIncomeStats() {
        List<Invoice> invoices = invoiceRepository.findAll();

        double totalRevenue = invoices.stream()
                .mapToDouble(i -> i.getAmount() != null ? i.getAmount() : 0)
                .sum();

        double pendingAmount = invoices.stream()
                .filter(i -> "Pending".equalsIgnoreCase(i.getStatus()))
                .mapToDouble(i -> i.getAmount() != null ? i.getAmount() : 0)
                .sum();

        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        double monthlyRevenue = invoices.stream()
                .filter(i -> i.getDate() != null
                        && i.getDate().getYear() == currentYear
                        && i.getDate().getMonthValue() == currentMonth
                        && "Paid".equalsIgnoreCase(i.getStatus()))
                .mapToDouble(Invoice::getAmount)
                .sum();

        double yearlyRevenue = invoices.stream()
                .filter(i -> i.getDate() != null
                        && i.getDate().getYear() == currentYear
                        && "Paid".equalsIgnoreCase(i.getStatus()))
                .mapToDouble(Invoice::getAmount)
                .sum();

        long totalInvoices = invoices.size();

        // --- Monthly breakdown (for chart) ---
        Map<String, Double> monthlyMap = new LinkedHashMap<>();
        for (int m = 1; m <= 12; m++) {
            final int monthValue = m; // ✅ make final copy
            double sum = invoices.stream()
                    .filter(i -> i.getDate() != null
                            && i.getDate().getYear() == currentYear
                            && i.getDate().getMonthValue() == monthValue
                            && "Paid".equalsIgnoreCase(i.getStatus()))
                    .mapToDouble(Invoice::getAmount)
                    .sum();
            monthlyMap.put(YearMonth.of(currentYear, monthValue).getMonth().name(), sum);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("totalInvoices", totalInvoices);
        result.put("monthlyRevenue", monthlyRevenue);
        result.put("yearlyRevenue", yearlyRevenue);
        result.put("pendingAmount", pendingAmount);
        result.put("overallIncome", totalRevenue);
        result.put("monthlyChart", monthlyMap);
        return result;
    }

    public Invoice save(Invoice invoice) {
        if (invoice.getDate() == null)
            invoice.setDate(LocalDateTime.now());
        return invoiceRepository.save(invoice);
    }

    public Invoice findById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }
}
