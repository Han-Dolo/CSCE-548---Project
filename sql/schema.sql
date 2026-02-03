CREATE DATABASE IF NOT EXISTS campus_rsvp;
USE campus_rsvp;

DROP TABLE IF EXISTS rsvps;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS attendees;
DROP TABLE IF EXISTS venues;

CREATE TABLE venues (
    venue_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    building VARCHAR(100) NOT NULL,
    room VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    is_outdoor BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description VARCHAR(500) NOT NULL,
    event_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    venue_id INT NOT NULL,
    organizer VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    FOREIGN KEY (venue_id) REFERENCES venues(venue_id)
);

CREATE TABLE attendees (
    attendee_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    major VARCHAR(80) NOT NULL,
    class_year INT NOT NULL
);

CREATE TABLE rsvps (
    rsvp_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT NOT NULL,
    attendee_id INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    checked_in BOOLEAN NOT NULL DEFAULT FALSE,
    rsvp_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (event_id, attendee_id),
    FOREIGN KEY (event_id) REFERENCES events(event_id),
    FOREIGN KEY (attendee_id) REFERENCES attendees(attendee_id)
);
