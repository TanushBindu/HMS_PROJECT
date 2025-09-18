package com.hms.controller;

import com.hms.model.Appointment;
import com.hms.model.Doctor;
import com.hms.model.Patient;
import com.hms.service.AppointmentService;
import com.hms.service.DoctorService;
import com.hms.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public AppointmentController(AppointmentService appointmentService,
                                 DoctorService doctorService,
                                 PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    // Show appointments page
    @GetMapping
    public String listAppointments(Model model) {
        List<Appointment> appointments = appointmentService.findAll();
        List<Doctor> doctors = doctorService.getAllDoctors();
        List<Patient> patients = patientService.getAllPatients();

        long todayCount = appointments.stream()
                .filter(a -> a.getAppointmentDate() != null &&
                        a.getAppointmentDate().toLocalDate().equals(LocalDate.now()))
                .count();


        model.addAttribute("appointments", appointments);
        model.addAttribute("doctors", doctors);
        model.addAttribute("patients", patients);
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("todayCount", todayCount);

        return "appointments";
    }

    // Save new appointment
    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.save(appointment);
        return "redirect:/appointments";
    }

    // Update appointment
    @PostMapping("/update")
    public String updateAppointment(@ModelAttribute Appointment formAppointment) {
        Appointment existing = appointmentService.findById(formAppointment.getId());
        if (existing != null) {
            existing.setReason(formAppointment.getReason());
            existing.setStatus(formAppointment.getStatus());
            // keep appointmentDate, doctor, patient unchanged
            appointmentService.save(existing);
        }
        return "redirect:/appointments";
    }



    // Delete appointment
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteById(id);
        return "redirect:/appointments";
    }

    // Fetch single appointment (JSON) for edit popup
    @GetMapping("/{id}")
    @ResponseBody
    public Appointment getAppointment(@PathVariable Long id) {
        return appointmentService.findById(id);
    }
}
