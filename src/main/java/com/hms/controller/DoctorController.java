package com.hms.controller;

import com.hms.model.Doctor;
import com.hms.security.AccessControlUtil;
import com.hms.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private AccessControlUtil accessControlUtil;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("totalDoctors", doctorService.getTotalDoctors());
        model.addAttribute("availableToday", doctorService.getAvailableToday());
        model.addAttribute("onLeave", doctorService.getOnLeave());
        model.addAttribute("bySpecialization", doctorService.getDoctorsBySpecialization());
        return "doctors";
    }

    @GetMapping("/doctors")
    public String doctorsDashboard(Model model, Principal principal) {
        String username = principal.getName();
        if (!accessControlUtil.hasAccess(username, "DOCTORS")) {
            return "error-403";
        }
        return "doctors-dashboard";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor-form";
    }

    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        model.addAttribute("doctor", doctor);
        return "doctor-form";
    }

    @GetMapping("/view/{id}")
    public String viewDoctor(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        model.addAttribute("doctor", doctor);
        return "doctor-view";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}
