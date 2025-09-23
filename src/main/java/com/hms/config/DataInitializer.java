package com.hms.config;

import com.hms.model.*;
import com.hms.repository.*;
import com.hms.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;



    public DataInitializer(UserRepository userRepository, PatientRepository patientRepository,
                           DoctorRepository doctorRepository, AppointmentRepository appointmentRepository,
                           PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {

        if(userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);

            User doctor = new User();
            doctor.setUsername("doctor1");
            doctor.setPassword(passwordEncoder.encode("doctor123"));
            doctor.setRole(User.Role.DOCTOR);

            User patient = new User();
            patient.setUsername("patient1");
            patient.setPassword(passwordEncoder.encode("patient123"));
            patient.setRole(User.Role.PATIENT);

            userRepository.saveAll(Arrays.asList(admin, doctor, patient));
        }

        if(doctorRepository.count() == 0) {
            Doctor drSmith = new Doctor();
            drSmith.setName("Dr. Smith");
            drSmith.setSpecialization("Cardiology");
            drSmith.setContact("9876543210");

            Doctor drJones = new Doctor();
            drJones.setName("Dr. Jones");
            drJones.setSpecialization("Neurology");
            drJones.setContact("9876543211");

            doctorRepository.saveAll(Arrays.asList(drSmith, drJones));
        }

        if(patientRepository.count() == 0) {
            Patient john = new Patient();
            john.setName("John Doe");
            john.setAge(30);
            john.setGender("Male");
            john.setContact("1234567890");
            john.setType("OPD");

            Patient jane = new Patient();
            jane.setName("Jane Roe");
            jane.setAge(25);
            jane.setGender("Female");
            jane.setContact("1234567891");
            jane.setType("InPatient");

            patientRepository.saveAll(Arrays.asList(john, jane));
        }

        if(appointmentRepository.count() == 0) {
            Doctor drSmith = doctorRepository.findById(1L).get();
            Patient john = patientRepository.findById(1L).get();

            Appointment appt1 = new Appointment();
            appt1.setDoctor(drSmith);
            appt1.setPatient(john);
            appt1.setDateTime(LocalDateTime.now().plusDays(1));
            appt1.setStatus("Scheduled");

            Appointment appt2 = new Appointment();
            appt2.setDoctor(drSmith);
            appt2.setPatient(john);
            appt2.setDateTime(LocalDateTime.now().minusDays(2));
            appt2.setStatus("Completed");

            appointmentRepository.saveAll(Arrays.asList(appt1, appt2));
        }

        System.out.println("Sample data initialized!");
    }
}
