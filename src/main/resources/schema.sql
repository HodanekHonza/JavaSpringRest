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
    email        VARCHAR(100) UNIQUE NOT NULL
);

-- Create the operating hall table
CREATE TABLE operating_hall
(
    hall_id   SERIAL PRIMARY KEY,
    hall_name VARCHAR(100) NOT NULL,
    location  VARCHAR(100) NOT NULL
);

-- Create the patient table
CREATE TABLE patient
(
    patient_id    SERIAL PRIMARY KEY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL
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
INSERT INTO doctor (first_name, last_name, specialty, email)
VALUES ('John', 'Doe', 'Cardiology', 'johndoe@example.com'),
       ('Jane', 'Smith', 'Neurology', 'janesmith@example.com');

INSERT INTO operating_hall (hall_name, location)
VALUES ('Hall A', 'First Floor'),
       ('Hall B', 'Second Floor');

INSERT INTO patient (first_name, last_name)
VALUES ('Alice', 'Johnson'),
       ('Bob', 'Brown');

INSERT INTO reservation (doctor_id, patient_id, hall_id, reservation_date, start_time, end_time, description, status)
VALUES (1, 1, 1, '2024-06-20', '10:00:00', '11:00:00', 'Regular checkup', 'Scheduled'),
       (2, 2, 2, '2024-06-21', '14:00:00', '15:00:00', 'Neurological assessment', 'Scheduled');
