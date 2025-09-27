package com.hms.controller;

import com.hms.security.AccessControlUtil;
import com.hms.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private AccessControlUtil accessControlUtil;

    @GetMapping("/accounts")
    public String accountsDashboard(Model model, Principal principal) {
        String username = principal.getName();

        if (!accessControlUtil.hasAccess(username, "ACCOUNTS")) {
            return "error-403"; // custom forbidden page
        }
        int currentYear = java.time.LocalDate.now().getYear();

        model.addAttribute("patientTypeIncome", accountsService.getPatientTypeMonthlyIncome(currentYear));
        model.addAttribute("specialistIncome", accountsService.getSpecialistMonthlyIncome(currentYear));
        model.addAttribute("yearlyRevenue", accountsService.getYearlyRevenue(currentYear));

        return "accounts-dashboard"; // Thymeleaf HTML file
    }
}
