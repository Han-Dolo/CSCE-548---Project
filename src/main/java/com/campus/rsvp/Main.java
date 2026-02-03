package com.campus.rsvp;

import com.campus.rsvp.dao.AttendeeDao;
import com.campus.rsvp.dao.EventDao;
import com.campus.rsvp.dao.RsvpDao;
import com.campus.rsvp.dao.VenueDao;
import com.campus.rsvp.model.Attendee;
import com.campus.rsvp.model.Event;
import com.campus.rsvp.model.Rsvp;
import com.campus.rsvp.model.Venue;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final EventDao EVENT_DAO = new EventDao();
    private static final VenueDao VENUE_DAO = new VenueDao();
    private static final AttendeeDao ATTENDEE_DAO = new AttendeeDao();
    private static final RsvpDao RSVP_DAO = new RsvpDao();

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("Campus Event RSVP Tracker");

        while (running) {
            printMenu();
            String choice = SCANNER.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> listEvents();
                    case "2" -> listVenues();
                    case "3" -> listAttendees();
                    case "4" -> listRsvpsForEvent();
                    case "5" -> createEvent();
                    case "6" -> createVenue();
                    case "7" -> createAttendee();
                    case "8" -> createRsvp();
                    case "9" -> updateRsvpStatus();
                    case "10" -> deleteEvent();
                    case "11" -> deleteVenue();
                    case "12" -> deleteAttendee();
                    case "13" -> deleteRsvp();
                    case "0" -> running = false;
                    default -> System.out.println("Unknown option. Try again.");
                }
            } catch (SQLException ex) {
                System.out.println("Database error: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                System.out.println("Input error: " + ex.getMessage());
            }
        }

        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\nMenu");
        System.out.println("1. List events");
        System.out.println("2. List venues");
        System.out.println("3. List attendees");
        System.out.println("4. List RSVPs for event");
        System.out.println("5. Create event");
        System.out.println("6. Create venue");
        System.out.println("7. Create attendee");
        System.out.println("8. Create RSVP");
        System.out.println("9. Update RSVP status");
        System.out.println("10. Delete event");
        System.out.println("11. Delete venue");
        System.out.println("12. Delete attendee");
        System.out.println("13. Delete RSVP");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void listEvents() throws SQLException {
        List<Event> events = EVENT_DAO.getAll();
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }
        events.forEach(System.out::println);
    }

    private static void listVenues() throws SQLException {
        List<Venue> venues = VENUE_DAO.getAll();
        if (venues.isEmpty()) {
            System.out.println("No venues found.");
            return;
        }
        venues.forEach(System.out::println);
    }

    private static void listAttendees() throws SQLException {
        List<Attendee> attendees = ATTENDEE_DAO.getAll();
        if (attendees.isEmpty()) {
            System.out.println("No attendees found.");
            return;
        }
        attendees.forEach(System.out::println);
    }

    private static void listRsvpsForEvent() throws SQLException {
        int eventId = readInt("Event ID: ");
        List<Rsvp> rsvps = RSVP_DAO.getByEvent(eventId);
        if (rsvps.isEmpty()) {
            System.out.println("No RSVPs found for event " + eventId + ".");
            return;
        }
        rsvps.forEach(System.out::println);
    }

    private static void createEvent() throws SQLException {
        System.out.println("Create Event");
        String title = readLine("Title: ");
        String description = readLine("Description: ");
        LocalDate date = LocalDate.parse(readLine("Date (YYYY-MM-DD): "));
        LocalTime start = LocalTime.parse(readLine("Start Time (HH:MM): "));
        LocalTime end = LocalTime.parse(readLine("End Time (HH:MM): "));
        int venueId = readInt("Venue ID: ");
        String organizer = readLine("Organizer: ");
        String category = readLine("Category: ");

        Event event = new Event(0, title, description, date, start, end, venueId, organizer, category);
        int id = EVENT_DAO.create(event);
        System.out.println("Created event with ID: " + id);
    }

    private static void createVenue() throws SQLException {
        System.out.println("Create Venue");
        String name = readLine("Name: ");
        String building = readLine("Building: ");
        String room = readLine("Room: ");
        int capacity = readInt("Capacity: ");
        boolean outdoor = readBoolean("Outdoor? (true/false): ");

        Venue venue = new Venue(0, name, building, room, capacity, outdoor);
        int id = VENUE_DAO.create(venue);
        System.out.println("Created venue with ID: " + id);
    }

    private static void createAttendee() throws SQLException {
        System.out.println("Create Attendee");
        String first = readLine("First name: ");
        String last = readLine("Last name: ");
        String email = readLine("Email: ");
        String major = readLine("Major: ");
        int classYear = readInt("Class year: ");

        Attendee attendee = new Attendee(0, first, last, email, major, classYear);
        int id = ATTENDEE_DAO.create(attendee);
        System.out.println("Created attendee with ID: " + id);
    }

    private static void createRsvp() throws SQLException {
        System.out.println("Create RSVP");
        int eventId = readInt("Event ID: ");
        int attendeeId = readInt("Attendee ID: ");
        String status = readLine("Status (Going/Interested/Not Going): ");
        boolean checkedIn = readBoolean("Checked in? (true/false): ");

        Rsvp rsvp = new Rsvp(0, eventId, attendeeId, status, checkedIn, null);
        int id = RSVP_DAO.create(rsvp);
        System.out.println("Created RSVP with ID: " + id);
    }

    private static void updateRsvpStatus() throws SQLException {
        System.out.println("Update RSVP");
        int rsvpId = readInt("RSVP ID: ");
        String status = readLine("New status: ");
        boolean checkedIn = readBoolean("Checked in? (true/false): ");

        Rsvp existing = RSVP_DAO.getById(rsvpId);
        if (existing == null) {
            System.out.println("RSVP not found.");
            return;
        }
        existing.setStatus(status);
        existing.setCheckedIn(checkedIn);
        RSVP_DAO.update(existing);
        System.out.println("RSVP updated.");
    }

    private static void deleteEvent() throws SQLException {
        int eventId = readInt("Event ID to delete: ");
        boolean deleted = EVENT_DAO.delete(eventId);
        System.out.println(deleted ? "Event deleted." : "Event not found.");
    }

    private static void deleteVenue() throws SQLException {
        int venueId = readInt("Venue ID to delete: ");
        boolean deleted = VENUE_DAO.delete(venueId);
        System.out.println(deleted ? "Venue deleted." : "Venue not found.");
    }

    private static void deleteAttendee() throws SQLException {
        int attendeeId = readInt("Attendee ID to delete: ");
        boolean deleted = ATTENDEE_DAO.delete(attendeeId);
        System.out.println(deleted ? "Attendee deleted." : "Attendee not found.");
    }

    private static void deleteRsvp() throws SQLException {
        int rsvpId = readInt("RSVP ID to delete: ");
        boolean deleted = RSVP_DAO.delete(rsvpId);
        System.out.println(deleted ? "RSVP deleted." : "RSVP not found.");
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        String input = SCANNER.nextLine().trim();
        return Integer.parseInt(input);
    }

    private static boolean readBoolean(String prompt) {
        System.out.print(prompt);
        String input = SCANNER.nextLine().trim();
        return Boolean.parseBoolean(input);
    }
}
