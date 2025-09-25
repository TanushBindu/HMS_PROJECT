package com.hms.service;

import com.hms.model.Appointment;
import com.hms.model.Doctor;
import com.hms.model.Patient;
import com.hms.repository.AppointmentRepository;
import com.hms.repository.DoctorRepository;
import com.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment save(Long patientId, Long doctorId, String reason, String status, String dateTimeStr) {
        Appointment a = new Appointment();
        a.setPatient(patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Patient")));
        a.setDoctor(doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Doctor")));
        a.setReason(reason);
        a.setStatus(Appointment.Status.fromString(status));
        a.setAppointmentDate(LocalDateTime.parse(dateTimeStr));
        return appointmentRepository.save(a);
    }

    public Appointment update(Long id, Long patientId, Long doctorId, String reason, String status, String dateTimeStr) {
        Appointment a = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Appointment ID"));
        a.setPatient(patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Patient")));
        a.setDoctor(doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Doctor")));
        a.setReason(reason);
        a.setStatus(Appointment.Status.fromString(status));
        a.setAppointmentDate(LocalDateTime.parse(dateTimeStr));
        return appointmentRepository.save(a);
    }

    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    public long totalAppointments() {
        return appointmentRepository.count();
    }

    public long todaysAppointments() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);
        return appointmentRepository.countByAppointmentDateBetween(start, end);
    }

    public List<Appointment> search(String keyword) {
        return appointmentRepository.search(keyword);
    }
}

