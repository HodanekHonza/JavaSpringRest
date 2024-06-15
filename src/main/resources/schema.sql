-- Drop existing tables if they exist
DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS doctor CASCADE;
DROP TABLE IF EXISTS operating_hall CASCADE;
DROP TABLE IF EXISTS patient CASCADE;

-- Create the doctor table
CREATE TABLE doctor
(
    doctor_id    SERIAL PRIMARY KEY,
    first_name   VARCHAR(50)         NOT NULL,
    last_name    VARCHAR(50)         NOT NULL,
    specialty    VARCHAR(100)        NOT NULL,
    phone_number VARCHAR(15),
    email        VARCHAR(100) UNIQUE NOT NULL
);

-- Create the operating hall table
CREATE TABLE operating_hall
(
    hall_id   SERIAL PRIMARY KEY,
    hall_name VARCHAR(100) NOT NULL,
    location  VARCHAR(100) NOT NULL,
);

-- Create the patient table
CREATE TABLE patient
(
    patient_id    SERIAL PRIMARY KEY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
);

-- Create the reservation table
CREATE TABLE reservation
(
    reservation_id   SERIAL PRIMARY KEY,
    doctor_id        INT         NOT NULL,
    patient_id       INT         NOT NULL,
    hall_id          INT         NOT NULL,
    reservation_date DATE        NOT NULL,
    start_time       TIME        NOT NULL,
    end_time         TIME        NOT NULL,
    description      TEXT,
    status           VARCHAR(50) NOT NULL,

    FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patient (patient_id) ON DELETE CASCADE,
    FOREIGN KEY (hall_id) REFERENCES operating_hall (hall_id) ON DELETE CASCADE
);

-- Sample data insertion (optional)
INSERT INTO doctor (first_name, last_name, specialty, phone_number, email)
VALUES ('John', 'Doe', 'Cardiology', '123-456-7890', 'johndoe@example.com'),
       ('Jane', 'Smith', 'Neurology', '234-567-8901', 'janesmith@example.com');

INSERT INTO operating_hall (hall_name, location, capacity)
VALUES ('Hall A', 'First Floor', 2),
       ('Hall B', 'Second Floor', 3);

INSERT INTO patient (first_name, last_name, date_of_birth, gender, phone_number, email)
VALUES ('Alice', 'Johnson', '1980-01-01', 'Female', '345-678-9012', 'alicejohnson@example.com'),
       ('Bob', 'Brown', '1975-05-05', 'Male', '456-789-0123', 'bobbrown@example.com');

INSERT INTO reservation (doctor_id, patient_id, hall_id, reservation_date, start_time, end_time, description, status)
VALUES (1, 1, 1, '2024-06-20', '10:00:00', '11:00:00', 'Regular checkup', 'Scheduled'),
       (2, 2, 2, '2024-06-21', '14:00:00', '15:00:00', 'Neurological assessment', 'Scheduled');
