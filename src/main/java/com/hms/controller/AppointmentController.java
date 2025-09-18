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
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    // ✅ List all appointments
    @GetMapping
    public String listAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());

        model.addAttribute("totalAppointments", appointments.size());
        model.addAttribute("todaysAppointments", appointmentService.getTodaysAppointments().size());

        return "appointments";
    }

    // ✅ List only today’s appointments
    @GetMapping("/today")
    public String listTodaysAppointments(Model model) {
        List<Appointment> todays = appointmentService.getTodaysAppointments();
        model.addAttribute("appointments", todays);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());

        model.addAttribute("totalAppointments", todays.size());
        model.addAttribute("todaysAppointments", todays.size());

        return "appointments";
    }

    // ✅ Save appointment (create)
    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments";
    }

    // ✅ Fetch single appointment for edit popup
    @GetMapping("/get/{id}")
    @ResponseBody
    public Appointment getAppointment(@PathVariable Long id) {
        return appointmentService.getAppointment(id);
    }

    @PostMapping("/update")
    public String updateAppointment(@ModelAttribute Appointment updatedAppt) {
        Appointment existing = appointmentService.getAppointment(updatedAppt.getId());
        if (existing != null) {
            existing.setAppointmentDate(updatedAppt.getAppointmentDate());
            existing.setStatus(updatedAppt.getStatus());
            existing.setReason(updatedAppt.getReason());
            existing.setDoctor(updatedAppt.getDoctor());
            existing.setPatient(updatedAppt.getPatient());
            appointmentService.saveAppointment(existing);
        }
        return "redirect:/appointments";
    }



    // ✅ Delete appointment
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);  // only delete
        return "redirect:/appointments";
    }

}
