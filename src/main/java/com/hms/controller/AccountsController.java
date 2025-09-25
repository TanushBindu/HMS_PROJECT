package com.hms.controller;

import com.hms.dto.AccountsMonthlyIncome;
import com.hms.dto.SpecialistMonthlyIncome;
import com.hms.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    @Autowired
    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping
    public String viewAccountsPage(@RequestParam(defaultValue = "2025") int year, Model model) {
        List<AccountsMonthlyIncome> patientTypeIncome = accountsService.getPatientTypeMonthlyIncome(year);
        List<SpecialistMonthlyIncome> specialistIncome = accountsService.getSpecialistMonthlyIncome(year);

        Double yearlyRevenue = accountsService.getYearlyRevenue(year);
        Double monthlyRevenue = accountsService.getMonthlyRevenue(LocalDate.now().getMonthValue(), year);

        model.addAttribute("patientTypeIncome", patientTypeIncome);
        model.addAttribute("specialistIncome", specialistIncome);
        model.addAttribute("yearlyRevenue", yearlyRevenue);
        model.addAttribute("monthlyRevenue", monthlyRevenue);

        return "accounts";
    }
}
