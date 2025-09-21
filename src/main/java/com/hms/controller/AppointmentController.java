package com.hms.controller;

import com.hms.model.Appointment;
import com.hms.model.Doctor;
import com.hms.model.Patient;
import com.hms.service.AppointmentService;
import com.hms.service.DoctorService;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // --- Display all appointments ---
//    @GetMapping
//    public String getAllAppointments(Model model) {
//        List<Appointment> appointments = appointmentService.getAllAppointments();
//        List<String> reasons = List.of("Consultation","Follow-up","Emergency","Checkup");
//        List<String> statuses = List.of("Scheduled","Completed","Cancelled","Rescheduled");
//
//        model.addAttribute("appointments", appointments);
//        model.addAttribute("appointment", new Appointment());
//        model.addAttribute("reasons", reasons);
//        model.addAttribute("statuses", statuses);
//
//        // Today's appointments count
//        long todayCount = appointmentService.getTodayAppointmentsCount();
//        model.addAttribute("todayCount", todayCount);
//
//        return "appointments"; // Thymeleaf template name
//    }

    public AppointmentController(AppointmentService appointmentService,
                                 PatientService patientService,
                                 DoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

        // --- Fetch appointment data by ID for Edit Modal ---
        @GetMapping("/appointments/{id}")
        @ResponseBody
        public Appointment getAppointmentById(@PathVariable Long id) {
            return appointmentService.getAppointmentById(id)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
        }

    @GetMapping
    public String showAppointmentsPage(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("appointment", new Appointment());

        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());

        List<String> reasons = List.of("Consultation","Follow-up","Emergency","Checkup");
        List<String> statuses = List.of("Scheduled","Completed","Cancelled","Rescheduled");
        model.addAttribute("reasons", reasons);
        model.addAttribute("statuses", statuses);

        model.addAttribute("todayCount", appointmentService.getTodayAppointmentsCount());
        return "appointments"; // Thymeleaf template
    }

    @GetMapping("/appointments")
    public String getAllAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();

        model.addAttribute("appointments", appointments);
        model.addAttribute("todayCount", appointmentService.getTodayAppointmentsCount());
        model.addAttribute("totalCount", appointments.size());

        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());

        return "appointments";
    }



    // --- Save new appointment ---
    @PostMapping("/appointments/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        // set patient & doctor
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
    @PostMapping("/appointments/update")
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


    @GetMapping("/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
}
