CREATE DATABASE IF NOT EXISTS sport CHARACTER SET utf8 COLLATE utf8_general_ci;

USE sport;

CREATE TABLE IF NOT EXISTS users (
	id_user INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	login VARCHAR(20) NOT NULL UNIQUE,
	password VARCHAR(20) NOT NULL,
	user_type INT(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_types (
	user_type INT(1) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20) NOT NULL UNIQUE,
	privilege VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS logs (
	id_log INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_user INT(5) NOT NULL REFERENCES users,
	id_action INT(2) NOT NULL REFERENCES actions,
	table_name VARCHAR(20) NOT NULL,
	datetime_log DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS actions (
	id_action INT(2) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	description VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS broadcast (
	id_broadcast INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	channel VARCHAR(20) NOT NULL,
	date_broadcast DATE NOT NULL,
	time_broadcast TIME NOT NULL,
	id_program INT(5) NOT NULL REFERENCES program
);

CREATE TABLE IF NOT EXISTS programs (
	id_program INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL UNIQUE,
	description VARCHAR(100),
	date_of_creation DATE NOT NULL,
	duration TIME NOT NULL
);

CREATE TABLE IF NOT EXISTS employees (
	id_employee INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_user INT(2) REFERENCES users,
	last_name VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	middle_name VARCHAR(20),
	position VARCHAR(20),
	salary VARCHAR(10),
	date_of_birth DATE,
	address VARCHAR(100),
	phone_number VARCHAR(16),
	start_date DATE NOT NULL,
	end_date DATE
);

CREATE TABLE IF NOT EXISTS persons (
	id_person INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	last_name VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	middle_name VARCHAR(20),
	gender VARCHAR(1),
	country VARCHAR(25),
	occupation VARCHAR(50),
	biography VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS sportsmen (
	id_sportsman INT(5) NOT NUll PRIMARY KEY AUTO_INCREMENT,
	id_person INT(5) NOT NULL REFERENCES person,
	id_sport INT(2) NOT NULL REFERENCES sport,
	rank VARCHAR(50),
	achievements VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS teams (
	id_team INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50),
	country VARCHAR(25),
	trainer INT(5) REFERENCES person,
	achievements VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS sports (
	id_sport INT(2) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	kind VARCHAR(30) NOT NULL UNIQUE,
	description VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS competitions (
	id_competition INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	description VARCHAR(100) NOT NULL,
	date_competition DATE NOT NULL,
	location VARCHAR(50) NOT NULL,
	id_sport INT(2) NOT NULL REFERENCES sport
);

CREATE TABLE IF NOT EXISTS sportsman_competition (
	id_sportsman_competition INT(6) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_person INT(5) REFERENCES sportsman,
	id_competition INT(5) REFERENCES competition,
	points FLOAT(3),
	place INT(3)
);

CREATE TABLE IF NOT EXISTS team_competition (
	id_team_competition INT(6) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_team INT(5) REFERENCES team,
	id_competition INT(5) REFERENCES competition,
	points FLOAT(3),
	place INT(3)
);

CREATE TABLE IF NOT EXISTS guests (
	id_guests INT(6) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_program INT(5) REFERENCES program,
	id_person INT(5) REFERENCES person
);

CREATE TABLE IF NOT EXISTS program_employee (
	id_program_employee INT(6) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_program INT(5) REFERENCES program,
	id_employee INT(5) REFERENCES employee
);

CREATE TABLE IF NOT EXISTS program_competition (
	id_program_competition INT(6) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_program INT(5) REFERENCES program,
	id_competition INT(5) REFERENCES competition
);

CREATE TABLE IF NOT EXISTS program_sportsman (
	id_program_sportsman INT(6) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_program INT(5) REFERENCES program,
	id_person INT(5) REFERENCES sportsman
);

CREATE TABLE IF NOT EXISTS program_team (
	program_team INT(6) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_program INT(5) REFERENCES program,
	id_team INT(5) REFERENCES team
);
