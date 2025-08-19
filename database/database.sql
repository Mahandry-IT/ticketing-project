-- Improved database schema with role management

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
   status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
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

-- Insert initial data
INSERT INTO role (name) VALUES
('ADMIN'),
('USER');

INSERT INTO seat_type (name) VALUES
('ECONOMY'),
('BUSINESS'),
('FIRST');

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
('Paris'),
('London'),
('New York'),
('Tokyo'),
('Dubai');

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
('EagleJet 101', '2014-05-20', 1),  -- Boeing 737-800
('SkyCruiser A1', '2016-09-15', 2), -- Airbus A320
('TitanAir 777', '2019-12-03', 3),  -- Boeing 777-300ER
('JetStream 737', '2015-07-11', 1), -- Boeing 737-800
('NovaAir A320', '2021-03-22', 2);  -- Airbus A320

-- Ajouter la contrainte de vérification sur la nouvelle colonne
ALTER TABLE reservation
    ADD CONSTRAINT chk_status
        CHECK (status IN ('CONFIRMED', 'CANCELED', 'PENDING'));