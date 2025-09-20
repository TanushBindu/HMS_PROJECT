package com.hms.controller;

import com.hms.model.Patient;
import com.hms.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping("/patients")
    public String allPatients(Model model) {
        model.addAttribute("activeTab", "all");
        model.addAttribute("patients", service.getAllPatients());
        return "patients";
    }

    @GetMapping("/patients/opd")
    public String opdPatients(Model model) {
        model.addAttribute("patients", service.getOpdPatients());
        model.addAttribute("activeTab", "opd");
        return "patients";
    }

    @GetMapping("/patients/in")
    public String inPatients(Model model) {
        model.addAttribute("patients", service.getInPatients());
        model.addAttribute("activeTab", "in");
        return "patients";
    }

    // Optional JSON endpoint for edit modal
    @GetMapping("/patients/get/{id}")
    @ResponseBody
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
        Patient p = service.getPatientById(id);
        if (p == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    // List patients with optional type filter: /patients?type=OPD
    @GetMapping
    public String listPatients(@RequestParam(value = "type", required = false) String type, Model model) {
        List<Patient> patients = (type == null) ? service.getAllPatients() : service.getPatientsByType(type);
        model.addAttribute("patients", patients);
        return "patients"; // returns src/main/resources/templates/patients.html
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
