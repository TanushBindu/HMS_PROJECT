package com.hms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    private String reason;

    private LocalDateTime appointmentDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false) // Ensure DB can store all enum values
    private Status status;

    public enum Status {
        SCHEDULED,
        UPCOMING,
        COMPLETED,
        CANCELLED;

        // Convert string to enum safely
        public static Status fromString(String str) {
            for (Status s : Status.values()) {
                if (s.name().equalsIgnoreCase(str)) {
                    return s;
                }
            }
            throw new IllegalArgumentException("Invalid status: " + str);
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
