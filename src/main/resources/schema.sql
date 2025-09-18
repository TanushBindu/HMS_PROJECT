CREATE DATABASE IF NOT EXISTS hospital_db;
USE hospital_db;

DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS doctors;

CREATE TABLE doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    available_today BOOLEAN DEFAULT TRUE
);

CREATE TABLE patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(10),
    type VARCHAR(20) -- OPD / INPATIENT
);

CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_date DATETIME NOT NULL,
    status VARCHAR(50),
    reason VARCHAR(255),
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    CONSTRAINT fk_doc FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_pat FOREIGN KEY (patient_id) REFERENCES patients(id)
);
