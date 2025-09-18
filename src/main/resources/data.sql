-- Doctors
INSERT INTO doctors (name, department, available_today) VALUES
('Dr. John Smith', 'Cardiology', TRUE),
('Dr. Emily Davis', 'Orthopedics', TRUE),
('Dr. Michael Brown', 'Neurology', FALSE);

-- Patients
INSERT INTO patients (name, gender, type) VALUES
('Alice Johnson', 'Female', 'OPD'),
('Robert Wilson', 'Male', 'INPATIENT'),
('Sophia Miller', 'Female', 'OPD');

-- Appointments
INSERT INTO appointments (appointment_date, status, reason, doctor_id, patient_id) VALUES
(NOW(), 'Scheduled', 'Chest Pain', 1, 1),
(NOW(), 'Completed', 'Knee Checkup', 2, 2),
(NOW() + INTERVAL 1 DAY, 'Scheduled', 'Headache', 3, 3);
