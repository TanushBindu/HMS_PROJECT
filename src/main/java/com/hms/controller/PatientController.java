package com.hms.controller;

import com.hms.model.Patient;
import com.hms.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    // List patients with optional type filter: /patients?type=OPD
    @GetMapping
    public String listPatients(@RequestParam(value = "type", required = false) String type, Model model) {
        List<Patient> patients = (type == null) ? service.getAllPatients() : service.getPatientsByType(type);
        model.addAttribute("patients", patients);
        return "patients"; // returns src/main/resources/templates/patients.html
    }

    // ✅ Explicit GET by ID for edit
    @GetMapping("/get/{id}")
    @ResponseBody
    public Patient getPatient(@PathVariable Long id) {
        return service.getPatientById(id);
    }

    // Save create or update
    @PostMapping("/save")
    public String savePatient(Patient patient) {
        service.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Patient getPatientDetails(@PathVariable Long id) {
        return service.getPatientById(id);
    }


    // Delete
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/patients";
    }
}
