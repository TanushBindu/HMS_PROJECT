package com.hms.controller;

import com.hms.model.Doctor;
import com.hms.service.AppointmentService;
import com.hms.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors";
    }

    @GetMapping({"/dashboard"})
    public String dashboard(Model model) {
        int totalAppointments = appointmentService.getAllAppointments().size();
        List<Doctor> availableDoctors = doctorService.getAllDoctors();

        model.addAttribute("totalAppointments", totalAppointments);
        model.addAttribute("availableDoctors", availableDoctors);
        return "dashboard"; // maps to dashboard.html
    }

    @PostMapping("/edit/{id}")
    public String updateDoctor(@PathVariable Long id, @ModelAttribute Doctor updatedDoctor) {
        Doctor doctor = doctorService.findById(id);
        if (doctor != null) {
            doctor.setName(updatedDoctor.getName());
            doctor.setDepartment(updatedDoctor.getDepartment());
            doctor.setAvailableToday(updatedDoctor.getAvailableToday());
            doctor.setAbout(updatedDoctor.getAbout()); // ✅
            doctorService.save(doctor);
        }
        return "redirect:/doctors";
    }


    @GetMapping("/search")
    public String searchDoctors(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String department,
                                @RequestParam(required = false) Boolean available,
                                Model model) {
        model.addAttribute("doctors", doctorService.search(name, department, available));
        return "doctors";
    }

    @PostMapping("/add")
    public String addDoctor(Doctor doctor) {
        doctorService.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteById(id);
        return "redirect:/doctors";
    }

    @GetMapping("/edit/{id}")
    public String editDoctor(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.findById(id));
        return "edit-doctor"; // A separate page/modal for editing
    }
}

