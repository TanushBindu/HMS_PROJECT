-- Drop old tables if exist
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS doctors;

-- Patients Table
CREATE TABLE patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10),
    contact_number VARCHAR(15),
    address VARCHAR(255),
    type VARCHAR(20) -- OPD or In-Patient
);

-- Doctors Table
CREATE TABLE IF EXISTS doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    available_today BOOLEAN
);

CREATE TABLE IF EXISTS appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_date DATETIME(6) NOT NULL,
    status VARCHAR(50),
    reason VARCHAR(255),
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
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


SELECT id, name, age, gender, contact_number, address, type FROM patients;

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


INSERT INTO appointments (appointment_date, status, reason, doctor_id, patient_id) VALUES
('2025-09-18 10:30:00', 'Scheduled', 'Routine Checkup', 1, 1),
('2025-09-18 14:00:00', 'Completed', 'Headache and dizziness', 2, 2),
('2025-09-19 09:00:00', 'Scheduled', 'Follow-up Consultation', 1, 2);


