package com.hms.controller;

import com.hms.model.Appointment;
import com.hms.model.Appointment.Status;
import com.hms.repository.DoctorRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.AppointmentService;
import com.hms.service.DoctorService;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Show all appointments
    @GetMapping
    public String listAppointments(@RequestParam(required = false) String search, Model model) {
        List<Appointment> appointments = (search == null || search.isEmpty())
                ? service.getAll()
                : service.search(search);

        model.addAttribute("appointments", appointments);
        model.addAttribute("totalAppointments", service.totalAppointments());
        model.addAttribute("todaysAppointments", service.todaysAppointments());
        model.addAttribute("search", search);
        return "appointments";
    }

    // Show Add Appointment form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        return "add-appointment"; // Create this template
    }

    @PostMapping("/add")
    public String addAppointment(@RequestParam Long patientId,
                                 @RequestParam Long doctorId,
                                 @RequestParam String reason,
                                 @RequestParam String status,
                                 @RequestParam String dateTime) {
        service.save(patientId, doctorId, reason, status, dateTime);
        return "redirect:/appointments";
    }

    // Show Edit Appointment form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Appointment appointment = service.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Appointment ID"));
        model.addAttribute("appointment", appointment);
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository);
        return "edit-appointment"; // Create this template
    }

    @PostMapping("/edit/{id}")
    public String updateAppointment(@PathVariable Long id,
                                    @RequestParam Long patientId,
                                    @RequestParam Long doctorId,
                                    @RequestParam String reason,
                                    @RequestParam String status,
                                    @RequestParam String dateTime) {
        service.update(id, patientId, doctorId, reason, status, dateTime);
        return "redirect:/appointments";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/appointments";
    }
}

