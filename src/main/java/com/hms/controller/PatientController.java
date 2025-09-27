package com.hms.controller;

import com.hms.model.Patient;
import com.hms.security.AccessControlUtil;
import com.hms.service.AppointmentService;
import com.hms.service.PatientService;
import com.hms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private AccessControlUtil accessControlUtil;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // List patients
    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("role", "ADMIN"); // TODO: replace with actual logged-in role
        return "patients";
    }

    @GetMapping("/patients")
    public String patientsDashboard(Model model, Principal principal) {
        String username = principal.getName();
        if (!accessControlUtil.hasAccess(username, "PATIENTS")) {
            return "error-403";
        }
        return "patients-dashboard";
    }
    
    // Show Add Patient form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient-form";
    }

    // Save new patient
    @PostMapping("/save")
    public String savePatient(@ModelAttribute Patient patient) {
        patientService.save(patient);
        return "redirect:/patients";
    }

    // Show Edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Patient patient = patientService.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        model.addAttribute("patient", patient);
        return "patient-form";
    }

    // Update patient
    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient) {
        patientService.updatePatient(id, patient);
        return "redirect:/patients";
    }

    // View patient
    @GetMapping("/view/{id}")
    public String viewPatient(@PathVariable Long id, Model model) {
        Patient patient = patientService.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        model.addAttribute("patient", patient);
        return "patient-view";
    }

    // Delete patient
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deleteById(id);
        return "redirect:/patients";
    }
}