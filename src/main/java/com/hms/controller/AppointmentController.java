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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;


    // --- Fetch appointment JSON for edit ---

    // --- Page ---
    @GetMapping
    public String showAppointmentsPage(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();

        model.addAttribute("appointments", appointments);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.findAll());
        model.addAttribute("appointment", new Appointment());

        model.addAttribute("totalCount", appointments.size());
        model.addAttribute("todayCount", appointmentService.getTodayAppointmentsCount());

        int[] weeklyCounts = appointmentService.getWeeklyAppointmentsCount();
        model.addAttribute("weeklyCounts", weeklyCounts);

        return "appointments";
    }

    // --- Fetch appointment JSON for edit ---
    @GetMapping("/{id}")
    @ResponseBody
    public Map<String, Object> getAppointment(@PathVariable Long id) {
        Appointment appt = appointmentService.getAppointmentById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Create lightweight response map to avoid lazy loading/circular issues
        Map<String, Object> dto = new HashMap<>();
        dto.put("id", appt.getId());
        dto.put("appointmentDate", appt.getAppointmentDate());
        dto.put("reason", appt.getReason());
        dto.put("status", appt.getStatus());

        Map<String, Object> patient = new HashMap<>();
        patient.put("id", appt.getPatient().getId());
        dto.put("patient", patient);

        Map<String, Object> doctor = new HashMap<>();
        doctor.put("id", appt.getDoctor().getId());
        dto.put("doctor", doctor);

        return dto;
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

    // --- Update appointment ---
    @PostMapping("/update")  // ✅ FIXED PATH
    public String updateAppointment(@ModelAttribute Appointment appointment) {
        Appointment existing = appointmentService.getAppointmentById(appointment.getId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        existing.setAppointmentDate(appointment.getAppointmentDate());
        existing.setReason(appointment.getReason());
        existing.setStatus(appointment.getStatus());

        Patient patient = patientService.getPatientById(appointment.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorService.getDoctorById(appointment.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        existing.setPatient(patient);
        existing.setDoctor(doctor);

        appointmentService.saveAppointment(existing);
        return "redirect:/appointments";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
}
