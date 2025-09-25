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



