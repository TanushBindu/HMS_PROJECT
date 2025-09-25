package com.hms.controller;

import com.hms.dto.AccountsMonthlyIncome;
import com.hms.model.BiomedicalWasteIncome;
import com.hms.service.AccountsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/accounts")
    public String accountsPage(Model model) {
        int year = LocalDate.now().getYear();

        List<AccountsMonthlyIncome> specialistIncome = accountsService.getSpecialistIncome(year);
        List<AccountsMonthlyIncome> patientIncome = accountsService.getPatientTypeIncome(year);
        List<BiomedicalWasteIncome> bioIncome = accountsService.getBiomedicalIncome(
                LocalDate.of(year, 1, 1),
                LocalDate.now()
        );

        double totalRevenue = accountsService.getTotalRevenue(year);
        double totalExpenditure = accountsService.getTotalExpenditure(year);
        double roi = totalRevenue - totalExpenditure;

        model.addAttribute("specialistIncome", specialistIncome);
        model.addAttribute("patientIncome", patientIncome);
        model.addAttribute("bioIncome", bioIncome);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalExpenditure", totalExpenditure);
        model.addAttribute("roi", roi);
        model.addAttribute("year", year);

        return "accounts";
    }
}
