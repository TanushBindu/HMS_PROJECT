package com.hms.controller;

import com.hms.model.Appointment;
import com.hms.service.AppointmentService;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    // Show all appointments page
    @GetMapping
    public String listAppointments(Model model) {
        List<Appointment> appointments = appointmentService.findAll();
        model.addAttribute("appointments", appointments);
        model.addAttribute("patients", patientService.getAllPatients()); // needed for dropdown
        return "appointments";
    }

    // Save (add/update) appointment
    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.save(appointment);
        return "redirect:/appointments";
    }

    // ❌ FIX: Only keep ONE JSON endpoint for edit modal
    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<Appointment> getAppointmentJson(@PathVariable Long id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointment);
    }

    // Delete appointment
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteById(id);
        return "redirect:/appointments";
    }
}
