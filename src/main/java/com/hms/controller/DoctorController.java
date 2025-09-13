package com.hms.controller;

import com.hms.model.Doctor;
import com.hms.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String doctorsDashboard(Model model) {
        model.addAttribute("doctors", doctorService.findAll());
        model.addAttribute("doctor", new Doctor());
        return "doctors";
    }

    // Add doctor
    @PostMapping("/add")
    public String addDoctor(@ModelAttribute Doctor doctor) {
        doctorService.save(doctor);
        return "redirect:/doctors";
    }

    // Edit doctor
    @PostMapping("/edit")
    public String editDoctor(@ModelAttribute Doctor doctor) {
        doctorService.save(doctor);  // JPA detects existing id → UPDATE
        return "redirect:/doctors";
    }

    // Delete doctor
    @PostMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteById(id);
        return "redirect:/doctors";
    }
}
