package com.hms.service;

import com.hms.dto.AccountsMonthlyIncome;
import com.hms.dto.PatientTypeMonthlyIncome;
import com.hms.dto.SpecialistMonthlyIncome;
import com.hms.model.Invoice;
import com.hms.repository.AppointmentRepository;
import com.hms.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountsService {

    private final InvoiceRepository invoiceRepository;
    private final AppointmentRepository appointmentRepository;

    public AccountsService(InvoiceRepository invoiceRepository, AppointmentRepository appointmentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public Map<String, Object> getAccountsDashboardData(int year) {
        Map<String, Object> model = new HashMap<>();

        // Specialist Wise Monthly Income
        List<SpecialistMonthlyIncome> specialistData = invoiceRepository.getSpecialistMonthlyIncome(year);
        List<String> specialistLabels = new ArrayList<>();
        List<Double> specialistValues = new ArrayList<>();
        for (SpecialistMonthlyIncome row : specialistData) {
            specialistLabels.add(row.getSpecialization());
            specialistValues.add(row.getTotalAmount());
        }
        model.put("specialistIncomeLabels", specialistLabels);
        model.put("specialistIncomeValues", specialistValues);

        // Patient Type Income (In-Patient vs OPD)
        List<PatientTypeMonthlyIncome> patientTypeData = invoiceRepository.getPatientTypeMonthlyIncome(year);
        List<String> patientLabels = new ArrayList<>();
        List<Double> patientValues = new ArrayList<>();

        for (PatientTypeMonthlyIncome row : patientTypeData) {
            patientLabels.add(row.getPatientType());
            patientValues.add(row.getTotalAmount());
        }

        model.put("patientTypeLabels", patientLabels);
        model.put("patientTypeValues", patientValues);

        // Expenditure & Revenue Charts
        List<Object[]> expenditureData = invoiceRepository.getMonthlyYearlyExpenditure(year);
        List<String> expenditureLabels = new ArrayList<>();
        List<Double> expenditureValues = new ArrayList<>();
        for (Object[] row : expenditureData) {
            expenditureLabels.add((String) row[0]);
            expenditureValues.add(((Number) row[1]).doubleValue());
        }
        model.put("expenditureLabels", expenditureLabels);
        model.put("expenditureValues", expenditureValues);

        List<Object[]> revenueData = invoiceRepository.getMonthlyYearlyRevenue(year);
        List<String> revenueLabels = new ArrayList<>();
        List<Double> revenueValues = new ArrayList<>();
        for (Object[] row : revenueData) {
            revenueLabels.add((String) row[0]);
            revenueValues.add(((Number) row[1]).doubleValue());
        }
        model.put("revenueLabels", revenueLabels);
        model.put("revenueValues", revenueValues);

        // Other metrics
        model.put("roi", invoiceRepository.getRoiAndBudget().get("roi"));
        model.put("budget", invoiceRepository.getRoiAndBudget().get("budget"));
        model.put("biomedicalIncome", invoiceRepository.getBiomedicalWasteIncome());

        // All invoices
        model.put("invoices", invoiceRepository.findAll());

        return model;
    }

    public List<PatientTypeMonthlyIncome> getPatientTypeMonthlyIncome(int year) {
        return invoiceRepository.getPatientTypeMonthlyIncome(year);
    }

    public List<SpecialistMonthlyIncome> getSpecialistMonthlyIncome(int year) {
        return invoiceRepository.getSpecialistMonthlyIncome(year);
    }

    public Double getMonthlyRevenueByPatientType(String type, int month, int year) {
        return invoiceRepository.getMonthlyRevenueByPatientType(type, month, year);
    }

    public Double getYearlyRevenue(int year) {
        return invoiceRepository.getYearlyRevenue(year);
    }
}
