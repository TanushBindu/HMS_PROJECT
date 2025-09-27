INSERT INTO doctor (name, specialization, contact) VALUES
('Dr. Adams', 'Cardiology', '7777777777'),
('Dr. Brown', 'Neurology', '6666666666');


INSERT INTO appointment (patient_id, doctor_id, date, reason, status) VALUES
(1, 1, '2025-09-25 10:00:00', 'Heart Checkup', 'SCHEDULED'),
(2, 2, '2025-09-26 11:00:00', 'Headache', 'SCHEDULED');


INSERT INTO invoice (patient_id, doctor_id, treatment, amount, payment_mode, paid) VALUES
(1, 1, 'Heart Checkup', 1500, 'CASH', TRUE),
(2, 2, 'Brain MRI', 3000, 'CARD', FALSE);


SELECT d.specialization,
       SUM(i.amount) AS total_income,
       MONTH(i.date) AS month
FROM invoice i
JOIN doctor d ON i.doctor_id = d.id
WHERE YEAR(i.date) = :year
GROUP BY d.specialization, MONTH(i.date);


SELECT p.type,
       SUM(i.amount) AS total_income,
       MONTH(i.date) AS month
FROM invoice i
JOIN patient p ON i.patient_id = p.id
WHERE YEAR(i.date) = :year
GROUP BY p.patientType, MONTH(i.date);

SELECT new com.hms.dto.AccountsMonthlyIncome(p.patientType, SUM(i.amount), MONTH(i.date))
FROM Invoice i
JOIN i.patient p
WHERE YEAR(i.date) = :year
GROUP BY p.patientType, MONTH(i.date)



-- Administration
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Alice Johnson', '9876543210', 'Administration', 'Female', 'ADMIN', 'alice_admin', 'pass123');

-- Reception
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Rajesh Kumar', '9876543211', 'Reception', 'Male', 'USER', 'rajesh_recep', 'pass123');

-- Nursing
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Mary Thomas', '9876543212', 'Nursing', 'Female', 'USER', 'mary_nurse', 'pass123');

-- Pharmacy
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('John Mathew', '9876543213', 'Pharmacy', 'Male', 'USER', 'john_pharma', 'pass123');

-- Laboratory
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Anita Sharma', '9876543214', 'Laboratory', 'Female', 'USER', 'anita_lab', 'pass123');

-- Radiology
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Michael Smith', '9876543215', 'Radiology', 'Male', 'USER', 'mike_radio', 'pass123');

-- Surgery
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Sunil Verma', '9876543216', 'Surgery', 'Male', 'USER', 'sunil_surg', 'pass123');

-- Pediatrics
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Priya Reddy', '9876543217', 'Pediatrics', 'Female', 'USER', 'priya_ped', 'pass123');

-- Orthopedics
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('David Lee', '9876543218', 'Orthopedics', 'Male', 'USER', 'david_ortho', 'pass123');

-- Cardiology
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Ravi Patel', '9876543219', 'Cardiology', 'Male', 'USER', 'ravi_cardio', 'pass123');

-- Neurology
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Samantha Green', '9876543220', 'Neurology', 'Female', 'USER', 'sam_neuro', 'pass123');

-- Dermatology
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Kiran Mehta', '9876543221', 'Dermatology', 'Male', 'USER', 'kiran_derma', 'pass123');

-- Emergency
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Arun Nair', '9876543222', 'Emergency', 'Male', 'USER', 'arun_emer', 'pass123');

-- ICU
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Neha Gupta', '9876543223', 'ICU', 'Female', 'USER', 'neha_icu', 'pass123');

-- Billing / Accounts
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Peter Wilson', '9876543224', 'Billing', 'Male', 'USER', 'peter_bill', 'pass123');

-- Biomedical Waste / Housekeeping
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Shweta Pandey', '9876543225', 'Housekeeping', 'Female', 'USER', 'shweta_house', 'pass123');

-- Security
INSERT INTO staff (name, phone_number, department, gender, role, username, password)
VALUES ('Mohammed Ali', '9876543226', 'Security', 'Male', 'USER', 'ali_sec', 'pass123');


-- For Admin (Alice Johnson, staff_id = 1)
INSERT INTO access_control (staff_id, module_name, can_access) VALUES
(1, 'Patients', TRUE),
(1, 'Doctors', TRUE),
(1, 'Appointments', TRUE),
(1, 'Accounts', TRUE),
(1, 'Staff Management', TRUE),
(1, 'Access Control', TRUE);

-- For Reception (Rajesh Kumar, staff_id = 2)
INSERT INTO access_control (staff_id, module_name, can_access) VALUES
(2, 'Patients', TRUE),
(2, 'Appointments', TRUE),
(2, 'Doctors', FALSE),
(2, 'Accounts', FALSE),
(2, 'Staff Management', FALSE),
(2, 'Access Control', FALSE);

-- For Nursing (Mary Thomas, staff_id = 3)
INSERT INTO access_control (staff_id, module_name, can_access) VALUES
(3, 'Patients', TRUE),
(3, 'Appointments', TRUE),
(3, 'Doctors', TRUE),
(3, 'Accounts', FALSE),
(3, 'Staff Management', FALSE),
(3, 'Access Control', FALSE);

-- For Pharmacy (John Mathew, staff_id = 4)
INSERT INTO access_control (staff_id, module_name, can_access) VALUES
(4, 'Patients', FALSE),
(4, 'Appointments', FALSE),
(4, 'Doctors', FALSE),
(4, 'Accounts', TRUE),
(4, 'Staff Management', FALSE),
(4, 'Access Control', FALSE);

-- For Laboratory (Anita Sharma, staff_id = 5)
INSERT INTO access_control (staff_id, module_name, can_access) VALUES
(5, 'Patients', TRUE),
(5, 'Appointments', FALSE),
(5, 'Doctors', TRUE),
(5, 'Accounts', FALSE),
(5, 'Staff Management', FALSE),
(5, 'Access Control', FALSE);

-- For Security (Mohammed Ali, staff_id = 17)
INSERT INTO access_control (staff_id, module_name, can_access) VALUES
(17, 'Patients', FALSE,1),
(17, 'Appointments', FALSE,2),
(17, 'Doctors', FALSE,3),
(17, 'Accounts', FALSE,4),
(17, 'Staff Management', FALSE,5),
(17, 'Access Control', FALSE,6);
