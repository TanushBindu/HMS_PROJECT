package com.hms.controller;

import com.hms.model.Patient;
import com.hms.service.AppointmentService;
import com.hms.service.DoctorService;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class DashboardController {

    @Autowired
    private PatientService patientService;
    @Autowired private DoctorService doctorService;
    @Autowired private AppointmentService appointmentService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
//        String role = userService.getRole(principal.getName());
//        model.addAttribute("role", role);
//
//        if(role.equals("ADMIN") || role.equals("DOCTOR")) {
//            model.addAttribute("totalPatients", patientService.countAll());
//            model.addAttribute("totalDoctors", doctorService.getAllDoctors().size());
//            model.addAttribute("todayAppointments", appointmentService.getAll());
//            model.addAttribute("pendingInvoices", 5); // example
//        }
//        if(role.equals("PATIENT")) {
//            Optional<Patient> patient = patientService.findByUsername(principal.getName());
//            model.addAttribute("patientName", patient.get().getName());
//            model.addAttribute("totalAppointments", appointmentService.totalAppointments());
//            model.addAttribute("todaysAppointments", appointmentService.todaysAppointments());
//        }

        return "dashboard";
    }
}