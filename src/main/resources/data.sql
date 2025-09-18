INSERT INTO doctors (name, department, email, phone, available_today) VALUES
('Dr. Rajesh Kumar','Cardiology','rajesh.kumar@hms.com','9876543210',true),
('Dr. Neha Sharma','Neurology','neha.sharma@hms.com','9876501234',false),
('Dr. Anil Verma','Orthopedics','anil.verma@hms.com','9876512345',true);



-- Appointments table
CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_date DATETIME(6) NOT NULL,
    status VARCHAR(50),
    reason VARCHAR(255),
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE TABLE IF NOT EXISTS patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    contactnumber VARCHAR(20) NOT NULL,
    address VARCHAR(255),
    type ENUM('OPD', 'In-Patient') NOT NULL
);


INSERT INTO patients (name, age, gender, contactnumber, address, type) VALUES
('Ravi Kumar', 32, 'Male', '9876543210', 'Bangalore', 'OPD'),
('Sneha Rao', 28, 'Female', '9123456789', 'Mysore', 'In-Patient'),
('Ajay Sharma', 45, 'Male', '9988776655', 'Chennai', 'OPD'),
('Meera Iyer', 52, 'Female', '9090909090', 'Hyderabad', 'In-Patient');

SELECT * FROM patients;


INSERT INTO appointments (appointment_date, status, reason, doctor_id, patient_id) VALUES
('2025-09-18 10:30:00', 'Scheduled', 'Routine Checkup', 1, 1),
('2025-09-18 14:00:00', 'Completed', 'Headache and dizziness', 2, 2),
('2025-09-19 09:00:00', 'Scheduled', 'Follow-up Consultation', 1, 2);

INSERT INTO appointments (appointment_date, status, reason, doctor_id, patient_id)
VALUES
('2025-09-19 10:00:00', 'Scheduled', 'Routine Checkup', 1, 1),
('2025-09-19 14:00:00', 'Completed', 'Headache Follow-up', 2, 2);
