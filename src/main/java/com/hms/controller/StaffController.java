package com.hms.controller;

import com.hms.model.AccessControl;
import com.hms.model.Staff;
import com.hms.service.AccessControlService;
import com.hms.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) { this.staffService = staffService; }

    // Show staff list
    @GetMapping
    public String getAllStaff(Model model) {
        List<Staff> staffList = staffService.findAll();
        System.out.println("Staff list in controller: " + staffList.size());
        model.addAttribute("staffList", staffList);
        return "staff";
    }

    // Create staff form
    @GetMapping("/add")
    public String addStaff(Model model) {
        model.addAttribute("staff", new Staff());
        return "staff-form"; // staff-form.html
    }

    // Save staff
    @PostMapping("/save")
    public String saveStaff(@ModelAttribute Staff staff) {
        staffService.save(staff);
        return "redirect:/staff";
    }

    // Edit staff
    @GetMapping("/edit/{id}")
    public String editStaff(@PathVariable Long id, Model model) {
        Staff staff = staffService.findById(id);
        model.addAttribute("staff", staff);
        return "staff-form";
    }

    // Delete staff
    @GetMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffService.deleteById(id);
        return "redirect:/staff";
    }
}


