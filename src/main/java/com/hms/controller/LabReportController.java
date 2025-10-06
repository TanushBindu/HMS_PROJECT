package com.hms.controller;

import com.hms.model.LabReport;
import com.hms.model.Patient;
import com.hms.model.Doctor;
import com.hms.model.Appointment;
import com.hms.service.LabReportService;
import com.hms.service.PatientService;
import com.hms.service.DoctorService;
import com.hms.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/report")
public class LabReportController {

    @Autowired
    private LabReportService labReportService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    // Show list page
    @GetMapping
    public String reportList(Model model) {
        List<LabReport> reports = labReportService.getAllReports();
        model.addAttribute("reports", reports);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.findAll());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("newReport", new LabReport());
        return "report/list"; // Thymeleaf HTML page
    }

    // Save new report
    @PostMapping("/save")
    public String saveReport(@ModelAttribute LabReport report) {
        labReportService.saveReport(report);
        return "redirect:/report";
    }

    // Delete report
    @GetMapping("/delete/{id}")
    public String deleteReport(@PathVariable Long id) {
        labReportService.deleteReport(id);
        return "redirect:/report";
    }

    // Get report as JSON for edit modal
    @GetMapping("/{id}")
    @ResponseBody
    public LabReport getReport(@PathVariable Long id) {
        return labReportService.getReportById(id);
    }

    // Update report
    @PostMapping("/update")
    public String updateReport(@ModelAttribute LabReport report) {
        labReportService.saveReport(report);
        return "redirect:/report";
    }
}
