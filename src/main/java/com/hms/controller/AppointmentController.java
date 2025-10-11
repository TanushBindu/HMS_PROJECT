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

    // --- Page ---
    @GetMapping
    public String showAppointmentsPage(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();

        model.addAttribute("appointments", appointments);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.findAll());
        model.addAttribute("appointment", new Appointment());

        // Counts
        model.addAttribute("totalCount", appointments.size());
        model.addAttribute("todayCount", appointmentService.getTodayAppointmentsCount());

        // Example chart data: appointments per day (Mon-Sun)
        int[] weeklyCounts = appointmentService.getWeeklyAppointmentsCount(); // implement in service
        model.addAttribute("weeklyCounts", weeklyCounts);

        return "appointments";
    }


    // --- Fetch appointment JSON for edit ---
    @GetMapping("/{id}")
    @ResponseBody
    public Appointment getAppointment(@PathVariable Long id) {
        Optional<Appointment> optional = appointmentService.getAppointmentById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Appointment not found");
        }

        Appointment appt = optional.get();

        // Ensure patient and doctor are loaded (if using LAZY fetch)
        appt.getPatient().getId();
        appt.getDoctor().getId();

        return appt;
    }

    // --- Save new appointment ---
    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        // fetch patient and doctor
        Patient patient = patientService.getPatientById(appointment.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorService.getDoctorById(appointment.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        appointmentService.saveAppointment(appointment);

        // redirect back to appointments page
        return "redirect:/appointments";
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
