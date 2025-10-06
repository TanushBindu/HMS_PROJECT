package com.hms.controller;

import com.hms.model.Appointment;
import com.hms.model.Doctor;
import com.hms.model.Patient;
import com.hms.service.AppointmentService;
import com.hms.service.DoctorService;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DoctorService doctorService;

    // --- Display all appointments ---
    @GetMapping
    public String showAppointmentsPage(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();

        model.addAttribute("appointments", appointments);
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.findAll());

        List<String> reasons = List.of("Consultation", "Follow-up", "Emergency", "Checkup");
        List<String> statuses = List.of("Scheduled", "Completed", "Cancelled", "Rescheduled");
        model.addAttribute("reasons", reasons);
        model.addAttribute("statuses", statuses);

        model.addAttribute("todayCount", appointmentService.getTodayAppointmentsCount());
        return "appointments";
    }

    // --- Save new appointment ---
    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        Patient patient = patientService.getPatientById(appointment.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorService.getDoctorById(appointment.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments";
    }

    // --- Edit appointment (popup data) ---
    @GetMapping("/edit/{id}")
    @ResponseBody
    public Appointment editAppointment(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    // --- Update appointment ---
    @PostMapping("/update")
    public String updateAppointment(@ModelAttribute Appointment appointment) {
        Appointment existing = appointmentService.getAppointmentById(appointment.getId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        existing.setReason(appointment.getReason());
        existing.setStatus(appointment.getStatus());
        existing.setAppointmentDate(appointment.getAppointmentDate());

        Patient patient = patientService.getPatientById(appointment.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorService.getDoctorById(appointment.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        existing.setPatient(patient);
        existing.setDoctor(doctor);

        appointmentService.saveAppointment(existing);
        return "redirect:/appointments";
    }

    // --- Delete appointment ---
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
}
