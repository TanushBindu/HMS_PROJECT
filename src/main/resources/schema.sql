--CREATE DATABASE IF NOT EXISTS hospital_db;
--USE hospital_db;
--
--DROP TABLE IF EXISTS appointments;
--DROP TABLE IF EXISTS patients;
--DROP TABLE IF EXISTS doctors;
--
--CREATE TABLE doctors (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    name VARCHAR(100) NOT NULL,
--    department VARCHAR(100),
--    available_today BOOLEAN DEFAULT TRUE
--);
--
--CREATE TABLE patients (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    name VARCHAR(100) NOT NULL,
--    gender VARCHAR(10),
--    phone VARCHAR(10),
--    type VARCHAR(20) -- OPD / INPATIENT
--);
--
--CREATE TABLE appointments (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    appointment_date DATETIME NOT NULL,
--    status VARCHAR(50),
--    reason VARCHAR(255),
--    doctor_id BIGINT NOT NULL,
--    patient_id BIGINT NOT NULL,
--    CONSTRAINT fk_doc FOREIGN KEY (doctor_id) REFERENCES doctors(id),
--    CONSTRAINT fk_pat FOREIGN KEY (patient_id) REFERENCES patients(id)
--);




CREATE DATABASE hospital_db_new;
USE hospital_db_new;


DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS invoice;

CREATE TABLE patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT,
    gender VARCHAR(10),
    contact VARCHAR(50)
);


CREATE TABLE doctor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    contact VARCHAR(50)
);


CREATE TABLE appointment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    date DATETIME NOT NULL,
    reason VARCHAR(255),
    status ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED') NOT NULL,

    CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
    CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE
);


CREATE TABLE `invoice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(255) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `date` date NOT NULL,
  `status` varchar(20) NOT NULL,
  `patient_id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `paid` bit(1) NOT NULL,
  `payment_mode` enum('CARD','CASH') DEFAULT NULL,
  `treatment` varchar(255) DEFAULT NULL,
  `doctor_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
);



CREATE TABLE biomedical_waste_income (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(255),
  amount DOUBLE NOT NULL,
  date DATE NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE staff (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    department VARCHAR(50),
    gender VARCHAR(10),
    role VARCHAR(20) NOT NULL,   -- ADMIN, USER, etc
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE access_control (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_name VARCHAR(100) NOT NULL,
    can_access BOOLEAN DEFAULT FALSE,
    staff_id BIGINT NOT NULL,
    CONSTRAINT fk_staff FOREIGN KEY (staff_id) REFERENCES staff(id) ON DELETE CASCADE
);
