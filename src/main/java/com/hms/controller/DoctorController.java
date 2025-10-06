package com.hms.controller;

import com.hms.model.Doctor;
import com.hms.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.findAll());
        return "doctors"; // doctors.html
    }

    @PostMapping("/add")
    public String addDoctor(@ModelAttribute Doctor doctor) {
        doctorService.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/doctors/search")
    public String searchDoctors(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String specialization,
                                @RequestParam(required = false) Boolean available,
                                Model model) {
        List<Doctor> doctors = doctorService.searchDoctors(name, specialization, available);
        model.addAttribute("doctors", doctors);
        return "doctors"; // your doctors.html page
    }


    @GetMapping("/edit/{id}")
    public String editDoctor(@PathVariable Long  id, Model model) {
        model.addAttribute("doctor", doctorService.findById(id));
        return "edit-doctor"; // separate edit page
    }

    @PostMapping("/update")
    public String updateDoctor(@ModelAttribute Doctor doctor) {
        doctorService.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long  id) {
        doctorService.deleteById(id);
        return "redirect:/doctors";
    }
}
