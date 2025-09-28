CREATE DATABASE IF NOT EXISTS hospital_db_final;
USE hospital_db_final;

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
    phone VARCHAR(10),
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

CREATE TABLE staff (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    contact VARCHAR(20),
    gender VARCHAR(10),
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE
);
