CREATE DATABASE IF NOT EXISTS hospital_db;
USE hospital_db;

-- Create doctors table if not already created
CREATE TABLE IF NOT EXISTS doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL,
    available_today BOOLEAN DEFAULT FALSE
);

-- Insert some dummy doctors
INSERT INTO doctors (name, department, available_today) VALUES
('Dr. John Smith', 'Cardiology', TRUE),
('Dr. Emily Davis', 'Neurology', FALSE),
('Dr. Michael Brown', 'Orthopedics', TRUE),
('Dr. Sarah Wilson', 'Pediatrics', TRUE),
('Dr. Raj Kumar', 'General Medicine', FALSE);

ALTER TABLE doctors ADD COLUMN about TEXT;
ALTER TABLE doctors ADD COLUMN available_today BOOLEAN DEFAULT false;
ALTER TABLE doctors MODIFY COLUMN available_today BOOLEAN NOT NULL DEFAULT 0;

CREATE TABLE IF NOT EXISTS doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL,
    available_today BOOLEAN DEFAULT FALSE
);


INSERT INTO patients (name, age, gender, contact_number, address, type) VALUES
('John Doe', 30, 'Male', '9876543210', 'Bangalore', 'OPD'),
('Jane Smith', 40, 'Female', '8765432109', 'Chennai', 'In-Patient'),
('Amit Kumar', 25, 'Male', '7654321098', 'Delhi', 'OPD');


CREATE TABLE patients (
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10),
    contact_number VARCHAR(15),
    address VARCHAR(255),
    type VARCHAR(20) -- OPD or In-Patient
);

-- Dummy Patients
INSERT INTO patients (name, age, gender, contact_number, address, type) VALUES
('John Doe', 30, 'Male', '9876543210', 'Bangalore', 'OPD'),
('Jane Smith', 40, 'Female', '8765432109', 'Chennai', 'In-Patient'),
('Amit Kumar', 25, 'Male', '7654321098', 'Delhi', 'OPD'),
('Priya Sharma', 32, 'Female', '6543210987', 'Mumbai', 'In-Patient');

-- Dummy Doctors
INSERT INTO doctors (name, department, available_today) VALUES
('Dr. John', 'Cardiology', TRUE),
('Dr. Smith', 'Neurology', FALSE),
('Dr. Meera', 'Orthopedics', TRUE);

-- Dummy Appointments
INSERT INTO appointments (patient_id, doctor_id, appointment_date, status) VALUES
(1, 1, '2025-09-15 10:30:00', 'Scheduled'),
(2, 2, '2025-09-16 11:00:00', 'Completed'),
(3, 3, '2025-09-17 09:00:00', 'Cancelled'),
(4, 1, '2025-09-18 15:00:00', 'Scheduled');



SELECT id, name, age, gender, contact_number, address, type FROM patients;


CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_date DATETIME NOT NULL,
    status VARCHAR(50),
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

-- Patients
INSERT INTO patients (id, name, age, gender, contact_number, address, type)
VALUES
(1, 'John Doe', 32, 'Male', '9876543210', 'Bangalore', 'OPD'),
(2, 'Mary Jane', 28, 'Female', '9876500000', 'Chennai', 'In-Patient');

-- Doctors
INSERT INTO doctors (id, name, department, available_today)
VALUES
(1, 'Dr. Smith', 'Cardiology', true),
(2, 'Dr. Watson', 'Orthopedics', true);

CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    date_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reason VARCHAR(255),
    status VARCHAR(50),
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);