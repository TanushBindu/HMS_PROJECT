CREATE DATABASE IF NOT EXISTS hms_db;
USE hms_db;

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
