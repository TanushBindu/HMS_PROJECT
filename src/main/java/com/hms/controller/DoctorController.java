package com.hms.controller;

import com.hms.model.Doctor;
import com.hms.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // Show doctor list page
    @GetMapping
    public String listDoctors(Model model) {
        List<Doctor> doctors = doctorService.findAll();
        model.addAttribute("doctors", doctors);
        model.addAttribute("doctor", new Doctor());
        return "doctors"; // HTML template
    }

    // Save new or updated doctor
//    @PostMapping("/save")
//    public String saveDoctor(@ModelAttribute Doctor doctor) {
//        doctorService.saveDoctor(doctor);
//        return "redirect:/doctors";
//    }

    // Get doctor data for edit modal (AJAX)
    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    // Soft delete (set isActive = false)
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.softDeleteDoctor(id);
        return "redirect:/doctors";
    }

    @PostMapping("/update/{id}")
    public String updateDoctor(@PathVariable Long id, @ModelAttribute Doctor doctor) {
        Doctor existingDoctor = doctorService.getDoctorById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        existingDoctor.setName(doctor.getName());
        existingDoctor.setSpecialization(doctor.getSpecialization());
        existingDoctor.setPhone(doctor.getPhone());
        existingDoctor.setEmail(doctor.getEmail());
        existingDoctor.setAddress(doctor.getAddress());
        existingDoctor.setAvailableToday(doctor.isAvailableToday());

        doctorService.saveDoctor(existingDoctor);
        return "redirect:/doctors";
    }

    // GET mapping to show form
    @GetMapping("/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor_form"; // name of your HTML/Thymeleaf template
    }

    // POST mapping to save doctor
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return "redirect:/doctors"; // or wherever you want
    }
}
