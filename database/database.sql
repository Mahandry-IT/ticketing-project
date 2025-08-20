-- Improved database schema with role management
TRUNCATE TABLE reservation_details CASCADE;
TRUNCATE TABLE plane_seat CASCADE;
TRUNCATE TABLE offer CASCADE;
TRUNCATE TABLE reservation CASCADE;
TRUNCATE TABLE flight CASCADE;
TRUNCATE TABLE plane CASCADE;
TRUNCATE TABLE model CASCADE;
TRUNCATE TABLE age_offer CASCADE;
TRUNCATE TABLE age_category CASCADE;
TRUNCATE TABLE reservation_param CASCADE;
TRUNCATE TABLE seat_type CASCADE;
TRUNCATE TABLE appuser CASCADE;
TRUNCATE TABLE role CASCADE;
TRUNCATE TABLE city CASCADE;

-- Réinitialiser toutes les séquences à 1
ALTER SEQUENCE age_category_id_category_seq RESTART WITH 1;
ALTER SEQUENCE age_offer_id_age_seq RESTART WITH 1;
ALTER SEQUENCE appuser_id_user_seq RESTART WITH 1;
ALTER SEQUENCE city_id_city_seq RESTART WITH 1;
ALTER SEQUENCE flight_id_flight_seq RESTART WITH 1;
ALTER SEQUENCE model_id_model_seq RESTART WITH 1;
ALTER SEQUENCE offer_id_offer_seq RESTART WITH 1;
ALTER SEQUENCE plane_id_plane_seq RESTART WITH 1;
ALTER SEQUENCE plane_seat_id_price_seq RESTART WITH 1;
ALTER SEQUENCE reservation_details_id_details_seq RESTART WITH 1;
ALTER SEQUENCE reservation_id_reservation_seq RESTART WITH 1;
ALTER SEQUENCE reservation_param_id_parameter_seq RESTART WITH 1;
ALTER SEQUENCE role_id_role_seq RESTART WITH 1;
ALTER SEQUENCE seat_type_id_type_seq RESTART WITH 1;
ALTER SEQUENCE promotion_alea_id_promotion_seq RESTART WITH 1;

-- Drop existing tables if they exist (in correct order)
DROP TABLE IF EXISTS reservation_details CASCADE;
DROP TABLE IF EXISTS plane_seat CASCADE;
DROP TABLE IF EXISTS offer CASCADE;
DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS flight CASCADE;
DROP TABLE IF EXISTS plane CASCADE;
DROP TABLE IF EXISTS model CASCADE;
DROP TABLE IF EXISTS age_offer CASCADE;
DROP TABLE IF EXISTS age_category CASCADE;
DROP TABLE IF EXISTS reservation_param CASCADE;
DROP TABLE IF EXISTS promotion_alea CASCADE;
DROP TABLE IF EXISTS seat_type CASCADE;
DROP TABLE IF EXISTS appuser CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS city CASCADE;

-- Create role table
CREATE TABLE role(
   id_role SERIAL,
   name VARCHAR(50) NOT NULL,
   PRIMARY KEY(id_role),
   UNIQUE(name)
);

CREATE TABLE city(
   id_city SERIAL,
   name VARCHAR(255) NOT NULL,
   PRIMARY KEY(id_city),
   UNIQUE(name)
);

CREATE TABLE appuser(
   id_user SERIAL,
   email VARCHAR(255) NOT NULL,
   pwd VARCHAR(255) NOT NULL,
   username VARCHAR(255) NOT NULL,
   id_role INTEGER NOT NULL,
   PRIMARY KEY(id_user),
   UNIQUE(email),
   UNIQUE(username),
   FOREIGN KEY(id_role) REFERENCES role(id_role)
);

CREATE TABLE seat_type(
   id_type SERIAL,
   name VARCHAR(255) NOT NULL,
   PRIMARY KEY(id_type),
   UNIQUE(name)
);

CREATE TABLE model(
   id_model SERIAL,
   name VARCHAR(255) NOT NULL,
   PRIMARY KEY(id_model),
   UNIQUE(name)
);

CREATE TABLE age_category(
   id_category SERIAL,
   minimal INTEGER NOT NULL,
   maximal INTEGER NOT NULL,
   name VARCHAR(255) NOT NULL,
   PRIMARY KEY(id_category),
   UNIQUE(name)
);

CREATE TABLE age_offer(
   id_age SERIAL,
   offer NUMERIC(5,2) NOT NULL CHECK (offer >= 0 AND offer <= 100),
   id_category INTEGER NOT NULL,
   PRIMARY KEY(id_age),
   FOREIGN KEY(id_category) REFERENCES age_category(id_category)
);

CREATE TABLE reservation_param(
   id_parameter SERIAL,
   cancel_time INTEGER NOT NULL,
   reservation_time INTEGER NOT NULL,
   PRIMARY KEY(id_parameter)
);

CREATE TABLE plane(
   id_plane SERIAL,
   name VARCHAR(255) NOT NULL,
   manufacturing_date DATE NOT NULL,
   id_model INTEGER NOT NULL,
   PRIMARY KEY(id_plane),
   UNIQUE(name),
   FOREIGN KEY(id_model) REFERENCES model(id_model)
);

CREATE TABLE flight(
   id_flight SERIAL,
   departure_time TIMESTAMP NOT NULL,
   arrival_time TIMESTAMP NOT NULL,
   id_departure_city INTEGER NOT NULL,
   id_arrival_city INTEGER NOT NULL,
   id_plane INTEGER NOT NULL,
   PRIMARY KEY(id_flight),
   FOREIGN KEY(id_departure_city) REFERENCES city(id_city),
   FOREIGN KEY(id_arrival_city) REFERENCES city(id_city),
   FOREIGN KEY(id_plane) REFERENCES plane(id_plane),
   CHECK (departure_time < arrival_time),
   CHECK (id_departure_city != id_arrival_city)
);

CREATE TABLE reservation(
   id_reservation SERIAL,
   reservation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   total_price NUMERIC(15,2) NOT NULL,
   status VARCHAR(50) NOT NULL DEFAULT 'NOT_PAID',
   passenger_count INTEGER NOT NULL DEFAULT 1,
   id_user INTEGER NOT NULL,
   id_flight INTEGER NOT NULL,
   PRIMARY KEY(id_reservation),
   FOREIGN KEY(id_user) REFERENCES appuser(id_user),
   FOREIGN KEY(id_flight) REFERENCES flight(id_flight)
);

CREATE TABLE offer(
   id_offer SERIAL,
   number INTEGER NOT NULL,
   offer NUMERIC(5,2) NOT NULL CHECK (offer >= 0 AND offer <= 100),
   id_type INTEGER NOT NULL,
   id_flight INTEGER NOT NULL,
   PRIMARY KEY(id_offer),
   FOREIGN KEY(id_type) REFERENCES seat_type(id_type),
   FOREIGN KEY(id_flight) REFERENCES flight(id_flight)
);

CREATE TABLE plane_seat(
   id_price SERIAL,
   price NUMERIC(15,2) NOT NULL,
   quantity INTEGER NOT NULL DEFAULT 0,
   id_flight INTEGER NOT NULL,
   id_type INTEGER NOT NULL,
   PRIMARY KEY(id_price),
   FOREIGN KEY(id_flight) REFERENCES flight(id_flight),
   FOREIGN KEY(id_type) REFERENCES seat_type(id_type)
);

CREATE TABLE reservation_details(
   id_details SERIAL,
   passenger_name VARCHAR(255),
   age INTEGER NOT NULL,
   passport VARCHAR(255) NOT NULL,
   price NUMERIC(15,2) NOT NULL,
   id_reservation INTEGER NOT NULL,
   id_type INTEGER NOT NULL,
   PRIMARY KEY(id_details),
   FOREIGN KEY(id_reservation) REFERENCES reservation(id_reservation),
   FOREIGN KEY(id_type) REFERENCES seat_type(id_type)
);

CREATE TABLE promotion_alea(
    id_promotion SERIAL,
    number INTEGER,
    price NUMERIC(15,2) NOT NULL,
    final_date DATE NOT NULL,
    id_flight INTEGER NOT NULL,
    id_type INTEGER NOT NULL,
    PRIMARY KEY(id_promotion),
    FOREIGN KEY(id_flight) REFERENCES flight(id_flight),
    FOREIGN KEY(id_type) REFERENCES seat_type(id_type)
);

-- Insert initial data
INSERT INTO role (name) VALUES
('ADMIN'),
('USER');

INSERT INTO seat_type (name) VALUES
('Economique'),
('Affaire');

INSERT INTO age_category (minimal, maximal, name) VALUES 
(0, 2, 'INFANT'),
(3, 11, 'CHILD'),
(12, 64, 'ADULT'),
(65, 120, 'SENIOR');

INSERT INTO age_offer (offer, id_category) VALUES 
(90.00, 1), -- 90% discount for infants
(50.00, 2), -- 50% discount for children
(0.00, 3),  -- No discount for adults
(20.00, 4); -- 20% discount for seniors

INSERT INTO reservation_param (cancel_time, reservation_time) VALUES 
(24, 2); -- Cancel 24h before, reserve 2h before

-- Insert sample cities
INSERT INTO city (name) VALUES
('Antananarivo'),
('Paris CDG'),
('Mauritius'),
('Addis Abeba');

-- Insert sample models
INSERT INTO model (name) VALUES
('Boeing 737-800'),
('Airbus A320'),
('Boeing 777-300ER');

-- Insert sample admin user (password: admin123)
INSERT INTO appuser (email, pwd, username, id_role) VALUES
('admin@ticketing.com', 'admin123', 'admin', 1);

-- Insert sample regular user (password: user123)
INSERT INTO appuser (email, pwd, username, id_role) VALUES
('user@ticketing.com', 'user123', 'user', 2);

-- Sample data for the 'plane' table
INSERT INTO plane (name, manufacturing_date, id_model) VALUES
('Air Madagascar', '2014-05-20', 1),
('Air France A1', '2016-09-15', 2);

-- Ajouter la contrainte de vérification sur la nouvelle colonne
ALTER TABLE reservation
    ADD CONSTRAINT chk_status
        CHECK (status IN ('PAID', 'CANCELED', 'NOT PAID'));

-- ============================================
-- Flights
-- ============================================
INSERT INTO flight (departure_time, arrival_time, id_plane, id_departure_city, id_arrival_city) VALUES
('2025-09-05 08:00:00', '2025-09-05 22:00:00', 1, 1, 4),
('2025-09-15 21:00:00', '2025-09-16 21:00:00', 2, 2, 3); -- Dubai -> Paris

-- ============================================
-- Promotions
-- ============================================
-- VOL1: Economy promotions
INSERT INTO promotion_alea (number, price, final_date, id_flight, id_type) VALUES
(4, 200.00, '2025-08-27', 1, 1),  -- 4 places à 200€ jusqu'au 27 août
(2, 300.00, '2025-09-03', 1, 1);  -- 2 places à 300€ jusqu'au 3 septembre

-- VOL2: Economy promotions
INSERT INTO promotion_alea (number, price, final_date, id_flight, id_type) VALUES
(3, 350.00, '2025-09-05', 2, 1),  -- 3 places à 350€ jusqu'au 5 septembre
(1, 400.00, '2025-09-13', 2, 1);  -- 1 place à 400€ jusqu'au 13 septembre

-- ============================================
-- Reservations
-- ============================================
-- User normal (id_user = 2)
INSERT INTO reservation (reservation_time, total_price, status, passenger_count, id_user, id_flight) VALUES
-- VOL1 reservations
('2025-08-20 12:00:00', 200.00, 'PAID', 1, 2, 1),        -- res1: payée
('2025-08-21 10:00:00', 200.00, 'NOT PAID', 1, 2, 1),    -- res2: non payée
('2025-08-21 14:00:00', 200.00, 'PAID', 1, 2, 1),        -- res3: payée
('2025-08-28 09:00:00', 300.00, 'PAID', 1, 2, 1),        -- res4: payée
('2025-08-29 11:00:00', 300.00, 'PAID', 1, 2, 1),        -- res5: payée
('2025-09-01 15:00:00', 300.00, 'NOT PAID', 1, 2, 1),    -- res6: non payée
('2025-09-02 16:00:00', 300.00, 'PAID', 1, 2, 1),        -- res7: payée

-- VOL2 reservations
('2025-09-01 10:00:00', 350.00, 'PAID', 1, 2, 2),        -- res8: payée
('2025-09-02 11:00:00', 350.00, 'NOT PAID', 1, 2, 2),    -- res9: non payée
('2025-09-08 14:00:00', 400.00, 'PAID', 1, 2, 2),        -- res10: payée
('2025-09-10 16:00:00', 400.00, 'PAID', 1, 2, 2);        -- res11: payée

-- ============================================
-- Reservation details
-- ============================================
-- VOL1 Economy reservations
INSERT INTO reservation_details (passenger_name, age, passport, price, id_reservation, id_type) VALUES
-- res1 (payée)
('Alice Dupont', 30, '/ticketing_project_war_exploded/uploads/passeport-1.jpg', 200.00, 1, 1),

-- res2 (non payée)
('Bob Martin', 28, '/ticketing_project_war_exploded/uploads/passeport-2.jpg', 200.00, 2, 1),

-- res3 (payée)
('Charlie Brown', 35, '/ticketing_project_war_exploded/uploads/passeport-3.jpg', 200.00, 3, 1),

-- res4 (payée)
('Diana Prince', 32, '/ticketing_project_war_exploded/uploads/passeport-1.jpg', 300.00, 4, 1),

-- res5 (payée)
('Edward Smith', 40, '/ticketing_project_war_exploded/uploads/passeport-2.jpg', 300.00, 5, 1),

-- res6 (non payée)
('Fiona Johnson', 29, '/ticketing_project_war_exploded/uploads/passeport-3.jpg', 300.00, 6, 1),

-- res7 (payée)
('George Williams', 45, '/ticketing_project_war_exploded/uploads/passeport-1.jpg', 300.00, 7, 1),

-- VOL2 Economy reservations
-- res8 (payée)
('Hannah Davis', 33, '/ticketing_project_war_exploded/uploads/passeport-2.jpg', 350.00, 8, 1),

-- res9 (non payée)
('Ian Wilson', 31, '/ticketing_project_war_exploded/uploads/passeport-3.jpg', 350.00, 9, 1),

-- res10 (payée)
('Jessica Taylor', 27, '/ticketing_project_war_exploded/uploads/passeport-1.jpg', 400.00, 10, 1),

-- res11 (payée)
('Kevin Brown', 38, '/ticketing_project_war_exploded/uploads/passeport-2.jpg', 400.00, 11, 1);