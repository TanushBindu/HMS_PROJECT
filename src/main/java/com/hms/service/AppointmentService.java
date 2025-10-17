package com.hms.service;

import com.hms.model.Appointment;
import com.hms.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {


    @Autowired
    private AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    public long countAllAppointments() {
        return appointmentRepository.count();
    }

    public long countAppointmentsByDate(LocalDate date) {
        return appointmentRepository.countByAppointmentDateBetween(
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay()
        );
    }

//    public long getTodayAppointmentsCount() {
//        return appointmentRepository.countTodayAppointments();
//    }

    // --- Count today’s appointments ---
    public int getTodayAppointmentsCount() {
        LocalDate today = LocalDate.now();
        return (int) appointmentRepository.findAll()
                .stream()
                .filter(a -> a.getAppointmentDate().toLocalDate().isEqual(today))
                .count();
    }

    // --- Weekly counts (Mon-Sun) ---
    public int[] getWeeklyAppointmentsCount() {
        int[] counts = new int[7]; // Mon=0, Sun=6
        LocalDate now = LocalDate.now();

        appointmentRepository.findAll().forEach(a -> {
            DayOfWeek dow = a.getAppointmentDate().getDayOfWeek();
            counts[dow.getValue() - 1]++; // DayOfWeek.MONDAY=1
        });

        return counts;
    }

    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
