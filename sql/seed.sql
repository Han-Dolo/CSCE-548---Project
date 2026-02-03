USE campus_rsvp;

INSERT INTO venues (name, building, room, capacity, is_outdoor) VALUES
('Student Union Ballroom', 'Student Union', 'Ballroom A', 300, FALSE),
('Engineering Atrium', 'Engineering Complex', 'Atrium', 150, FALSE),
('Library Commons', 'Main Library', 'Commons', 120, FALSE),
('West Lawn', 'Central Campus', 'Lawn', 500, TRUE),
('Fine Arts Theater', 'Fine Arts Center', 'Theater', 220, FALSE),
('Business Hall 101', 'Business Hall', '101', 80, FALSE),
('Science Quad', 'Science Building', 'Quad', 250, TRUE),
('Rec Center Court', 'Recreation Center', 'Court 1', 200, FALSE),
('Innovation Lab', 'Tech Center', 'Lab 3', 60, FALSE),
('Alumni Pavilion', 'Alumni Center', 'Pavilion', 180, FALSE);

INSERT INTO events (title, description, event_date, start_time, end_time, venue_id, organizer, category) VALUES
('Welcome Back Bash', 'Kickoff social with music and food trucks.', '2026-02-10', '18:00:00', '21:00:00', 1, 'Student Activities', 'Social'),
('AI Club Meetup', 'Monthly meetup with a guest speaker.', '2026-02-12', '17:30:00', '19:00:00', 9, 'AI Club', 'Academic'),
('Career Fair Prep', 'Resume reviews and mock interviews.', '2026-02-13', '14:00:00', '16:30:00', 6, 'Career Services', 'Career'),
('Outdoor Movie Night', 'Movie under the stars with blankets.', '2026-02-14', '19:30:00', '22:00:00', 4, 'Residence Life', 'Social'),
('Jazz Ensemble', 'Live student jazz performance.', '2026-02-15', '20:00:00', '21:30:00', 5, 'Music Department', 'Arts'),
('Hackathon Kickoff', '24-hour build challenge kickoff.', '2026-02-16', '18:00:00', '19:00:00', 2, 'CS Society', 'Academic'),
('Yoga Flow', 'Beginner-friendly yoga session.', '2026-02-17', '08:00:00', '09:00:00', 7, 'Wellness Center', 'Wellness'),
('Finance 101 Workshop', 'Budgeting and personal finance tips.', '2026-02-18', '12:00:00', '13:30:00', 6, 'Business Club', 'Career'),
('Robotics Demo Day', 'Robotics teams showcase builds.', '2026-02-19', '15:00:00', '17:00:00', 2, 'Robotics Club', 'Academic'),
('Volunteer Fair', 'Local nonprofits recruit volunteers.', '2026-02-20', '10:00:00', '13:00:00', 3, 'Community Outreach', 'Service'),
('E-Sports Tournament', 'Campus-wide gaming competition.', '2026-02-21', '13:00:00', '18:00:00', 8, 'E-Sports Club', 'Social'),
('Poetry Night', 'Open mic for poetry and spoken word.', '2026-02-22', '19:00:00', '20:30:00', 3, 'Literary Society', 'Arts'),
('Research Showcase', 'Undergrad research poster session.', '2026-02-23', '11:00:00', '14:00:00', 1, 'Undergrad Research', 'Academic'),
('Sustainability Talk', 'Speaker on campus sustainability.', '2026-02-24', '16:00:00', '17:00:00', 10, 'Green Initiative', 'Academic'),
('Intramural Signup', 'Information session and registration.', '2026-02-25', '17:00:00', '18:00:00', 8, 'Campus Recreation', 'Wellness');

INSERT INTO attendees (first_name, last_name, email, major, class_year) VALUES
('Ava', 'Johnson', 'ava.johnson@example.edu', 'Computer Science', 2027),
('Liam', 'Smith', 'liam.smith@example.edu', 'Mechanical Engineering', 2026),
('Noah', 'Brown', 'noah.brown@example.edu', 'Business', 2028),
('Emma', 'Davis', 'emma.davis@example.edu', 'Biology', 2027),
('Olivia', 'Miller', 'olivia.miller@example.edu', 'Psychology', 2026),
('Elijah', 'Wilson', 'elijah.wilson@example.edu', 'Physics', 2025),
('Isabella', 'Moore', 'isabella.moore@example.edu', 'English', 2028),
('Mason', 'Taylor', 'mason.taylor@example.edu', 'Marketing', 2027),
('Sophia', 'Anderson', 'sophia.anderson@example.edu', 'Mathematics', 2025),
('James', 'Thomas', 'james.thomas@example.edu', 'Chemistry', 2026),
('Mia', 'Jackson', 'mia.jackson@example.edu', 'Nursing', 2027),
('Benjamin', 'White', 'benjamin.white@example.edu', 'Economics', 2025),
('Charlotte', 'Harris', 'charlotte.harris@example.edu', 'Political Science', 2028),
('Lucas', 'Martin', 'lucas.martin@example.edu', 'Computer Engineering', 2026),
('Amelia', 'Thompson', 'amelia.thompson@example.edu', 'Art', 2027),
('Henry', 'Garcia', 'henry.garcia@example.edu', 'History', 2025),
('Harper', 'Martinez', 'harper.martinez@example.edu', 'Education', 2028),
('Alexander', 'Robinson', 'alexander.robinson@example.edu', 'Statistics', 2026),
('Evelyn', 'Clark', 'evelyn.clark@example.edu', 'Environmental Science', 2027),
('Daniel', 'Rodriguez', 'daniel.rodriguez@example.edu', 'Information Systems', 2025);

INSERT INTO rsvps (event_id, attendee_id, status, checked_in) VALUES
(1, 1, 'Going', FALSE), (1, 2, 'Going', FALSE), (1, 3, 'Interested', FALSE), (1, 4, 'Going', FALSE), (1, 5, 'Going', FALSE),
(2, 1, 'Going', FALSE), (2, 6, 'Going', FALSE), (2, 9, 'Interested', FALSE), (2, 14, 'Going', FALSE), (2, 18, 'Going', FALSE),
(3, 3, 'Going', FALSE), (3, 8, 'Going', FALSE), (3, 10, 'Going', FALSE), (3, 12, 'Interested', FALSE), (3, 20, 'Going', FALSE),
(4, 4, 'Going', FALSE), (4, 7, 'Interested', FALSE), (4, 11, 'Going', FALSE), (4, 13, 'Going', FALSE), (4, 15, 'Interested', FALSE),
(5, 5, 'Going', FALSE), (5, 7, 'Going', FALSE), (5, 10, 'Interested', FALSE), (5, 15, 'Going', FALSE), (5, 16, 'Going', FALSE),
(6, 1, 'Going', FALSE), (6, 2, 'Going', FALSE), (6, 14, 'Going', FALSE), (6, 18, 'Interested', FALSE), (6, 19, 'Going', FALSE),
(7, 11, 'Going', FALSE), (7, 17, 'Going', FALSE), (7, 20, 'Interested', FALSE), (7, 9, 'Going', FALSE), (7, 6, 'Interested', FALSE),
(8, 3, 'Going', FALSE), (8, 8, 'Going', FALSE), (8, 12, 'Interested', FALSE), (8, 2, 'Going', FALSE), (8, 5, 'Interested', FALSE),
(9, 14, 'Going', FALSE), (9, 18, 'Going', FALSE), (9, 1, 'Interested', FALSE), (9, 6, 'Going', FALSE), (9, 9, 'Going', FALSE),
(10, 7, 'Going', FALSE), (10, 13, 'Going', FALSE), (10, 16, 'Interested', FALSE), (10, 17, 'Going', FALSE), (10, 19, 'Going', FALSE),
(11, 2, 'Going', FALSE), (11, 3, 'Interested', FALSE), (11, 8, 'Going', FALSE), (11, 10, 'Going', FALSE), (11, 12, 'Interested', FALSE),
(12, 4, 'Going', FALSE), (12, 15, 'Going', FALSE), (12, 18, 'Interested', FALSE), (12, 20, 'Going', FALSE), (12, 11, 'Interested', FALSE);
