INSERT INTO user (first_name, last_name, email, phone_number, address, is_deleted, password, role)
VALUES ('John', 'Doe', 'john.doe@example.com', '1234567890', '123 Street', false, 'password123', 'Specialist');
INSERT INTO specialist (specialty, description, price, appointment_time, user_id)
VALUES ('Psychiatry', 'Psychiatry', 150.00, '9AM-5PM', 1);
INSERT INTO pacient (user_id, social_category, username, has_insurance)
VALUES (1, 'Employee', 'pacient1', true);
INSERT INTO location (address, name)
VALUES ('456 Street', 'Hospital XYZ');
INSERT INTO appointment (pacient_id, doctor_id, appointment_type, appointment_date, appointment_status)
VALUES (1, 1, 'Online', '2024-04-12 09:00:00', 'Programare_Acceptata'); --

INSERT INTO doctor_reviews (pacient_id, doctor_id, review, comment_date, stars_number)
VALUES (1, 1, 'Great service!', '2024-04-12 10:00:00', 5);
INSERT INTO user_page_journal (content, is_public, page_date, pacient_id)
VALUES ('This is a public page', true, '2024-04-12 10:00:00', 1);
INSERT INTO payment_detail (payment_id, appointment_id, status, amount, currency, payment_date)
VALUES (1, 1, 'Paid', 150.00, 'USD', '2024-04-12 10:00:00');

INSERT INTO user (first_name, last_name, email, phone_number, address, is_deleted, password, role)
VALUES ('Alice', 'Smith', 'alice.smith@example.com', '9876543210', '456 Avenue', false, 'password456', 'Pacient');
INSERT INTO specialist (specialty, description, price, appointment_time, user_id)
VALUES ('Psychotherapy', 'Psychotherapy', 200.00, '10AM-6PM', 2);
INSERT INTO pacient (user_id, social_category, username, has_insurance)
VALUES (2, 'student', 'pacient2', true);
INSERT INTO location (address, name)
VALUES ('789 Boulevard', 'Clinic ABC');
INSERT INTO appointment (pacient_id, doctor_id, appointment_type, appointment_date, appointment_status)
VALUES (2, 2, 'Online', '2024-04-12 09:00:00', 'Programare_Acceptata');

INSERT INTO doctor_reviews (pacient_id, doctor_id, review, comment_date, stars_number)
VALUES (2, 2, 'Excellent service!', '2024-04-13 09:30:00', 5);
INSERT INTO user_page_journal (content, is_public, page_date, pacient_id)
VALUES ('Another public page', true, '2024-04-13 09:30:00', 2);
INSERT INTO payment_detail (payment_id, appointment_id, status, amount, currency, payment_date)
VALUES (2, 2, 'Paid', 200.00, 'USD', '2024-04-13 09:30:00');
#
# # delete from appointment;
# delete from payment_detail;