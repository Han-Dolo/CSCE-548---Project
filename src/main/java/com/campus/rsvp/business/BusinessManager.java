package com.campus.rsvp.business;

import com.campus.rsvp.dao.AttendeeDao;
import com.campus.rsvp.dao.EventDao;
import com.campus.rsvp.dao.RsvpDao;
import com.campus.rsvp.dao.VenueDao;
import com.campus.rsvp.model.Attendee;
import com.campus.rsvp.model.Event;
import com.campus.rsvp.model.Rsvp;
import com.campus.rsvp.model.Venue;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class BusinessManager {
    private final EventDao eventDao = new EventDao();
    private final VenueDao venueDao = new VenueDao();
    private final AttendeeDao attendeeDao = new AttendeeDao();
    private final RsvpDao rsvpDao = new RsvpDao();

    public int saveEvent(Event event) throws SQLException {
        validateEvent(event);
        if (event.getEventId() == 0) {
            return eventDao.create(event);
        }
        boolean updated = eventDao.update(event);
        return updated ? event.getEventId() : -1;
    }

    public Event getEventById(int eventId) throws SQLException {
        return eventDao.getById(eventId);
    }

    public List<Event> getAllEvents() throws SQLException {
        return eventDao.getAll();
    }

    public boolean deleteEvent(int eventId) throws SQLException {
        return eventDao.delete(eventId);
    }

    public int saveVenue(Venue venue) throws SQLException {
        validateVenue(venue);
        if (venue.getVenueId() == 0) {
            return venueDao.create(venue);
        }
        boolean updated = venueDao.update(venue);
        return updated ? venue.getVenueId() : -1;
    }

    public Venue getVenueById(int venueId) throws SQLException {
        return venueDao.getById(venueId);
    }

    public List<Venue> getAllVenues() throws SQLException {
        return venueDao.getAll();
    }

    public boolean deleteVenue(int venueId) throws SQLException {
        return venueDao.delete(venueId);
    }

    public int saveAttendee(Attendee attendee) throws SQLException {
        validateAttendee(attendee);
        if (attendee.getAttendeeId() == 0) {
            return attendeeDao.create(attendee);
        }
        boolean updated = attendeeDao.update(attendee);
        return updated ? attendee.getAttendeeId() : -1;
    }

    public Attendee getAttendeeById(int attendeeId) throws SQLException {
        return attendeeDao.getById(attendeeId);
    }

    public List<Attendee> getAllAttendees() throws SQLException {
        return attendeeDao.getAll();
    }

    public boolean deleteAttendee(int attendeeId) throws SQLException {
        return attendeeDao.delete(attendeeId);
    }

    public int saveRsvp(Rsvp rsvp) throws SQLException {
        validateRsvp(rsvp);
        if (rsvp.getRsvpId() == 0) {
            return rsvpDao.create(rsvp);
        }
        boolean updated = rsvpDao.update(rsvp);
        return updated ? rsvp.getRsvpId() : -1;
    }

    public Rsvp getRsvpById(int rsvpId) throws SQLException {
        return rsvpDao.getById(rsvpId);
    }

    public List<Rsvp> getAllRsvps() throws SQLException {
        return rsvpDao.getAll();
    }

    public List<Rsvp> getRsvpsForEvent(int eventId) throws SQLException {
        return rsvpDao.getByEvent(eventId);
    }

    public boolean deleteRsvp(int rsvpId) throws SQLException {
        return rsvpDao.delete(rsvpId);
    }

    private void validateEvent(Event event) throws SQLException {
        if (event == null) {
            throw new IllegalArgumentException("Event is required.");
        }
        if (isBlank(event.getTitle())) {
            throw new IllegalArgumentException("Event title is required.");
        }
        if (isBlank(event.getDescription())) {
            throw new IllegalArgumentException("Event description is required.");
        }
        if (event.getEventDate() == null) {
            throw new IllegalArgumentException("Event date is required.");
        }
        if (event.getStartTime() == null || event.getEndTime() == null) {
            throw new IllegalArgumentException("Event start and end time are required.");
        }
        if (!isEndAfterStart(event.getStartTime(), event.getEndTime())) {
            throw new IllegalArgumentException("Event end time must be after start time.");
        }
        if (event.getVenueId() <= 0) {
            throw new IllegalArgumentException("Venue ID is required.");
        }
        if (venueDao.getById(event.getVenueId()) == null) {
            throw new IllegalArgumentException("Venue does not exist.");
        }
        if (isBlank(event.getOrganizer())) {
            throw new IllegalArgumentException("Event organizer is required.");
        }
        if (isBlank(event.getCategory())) {
            throw new IllegalArgumentException("Event category is required.");
        }
    }

    private void validateVenue(Venue venue) {
        if (venue == null) {
            throw new IllegalArgumentException("Venue is required.");
        }
        if (isBlank(venue.getName())) {
            throw new IllegalArgumentException("Venue name is required.");
        }
        if (isBlank(venue.getBuilding())) {
            throw new IllegalArgumentException("Venue building is required.");
        }
        if (isBlank(venue.getRoom())) {
            throw new IllegalArgumentException("Venue room is required.");
        }
        if (venue.getCapacity() <= 0) {
            throw new IllegalArgumentException("Venue capacity must be greater than 0.");
        }
    }

    private void validateAttendee(Attendee attendee) {
        if (attendee == null) {
            throw new IllegalArgumentException("Attendee is required.");
        }
        if (isBlank(attendee.getFirstName()) || isBlank(attendee.getLastName())) {
            throw new IllegalArgumentException("Attendee first and last name are required.");
        }
        if (isBlank(attendee.getEmail()) || !attendee.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid attendee email is required.");
        }
        if (isBlank(attendee.getMajor())) {
            throw new IllegalArgumentException("Attendee major is required.");
        }
        if (attendee.getClassYear() < 1900 || attendee.getClassYear() > 2100) {
            throw new IllegalArgumentException("Attendee class year is out of range.");
        }
    }

    private void validateRsvp(Rsvp rsvp) throws SQLException {
        if (rsvp == null) {
            throw new IllegalArgumentException("RSVP is required.");
        }
        if (rsvp.getEventId() <= 0) {
            throw new IllegalArgumentException("Event ID is required for RSVP.");
        }
        if (rsvp.getAttendeeId() <= 0) {
            throw new IllegalArgumentException("Attendee ID is required for RSVP.");
        }
        if (eventDao.getById(rsvp.getEventId()) == null) {
            throw new IllegalArgumentException("Event does not exist.");
        }
        if (attendeeDao.getById(rsvp.getAttendeeId()) == null) {
            throw new IllegalArgumentException("Attendee does not exist.");
        }
        String status = rsvp.getStatus() == null ? "" : rsvp.getStatus().trim().toLowerCase();
        if (!(status.equals("going") || status.equals("interested") || status.equals("not going"))) {
            throw new IllegalArgumentException("RSVP status must be Going, Interested, or Not Going.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isEndAfterStart(LocalTime start, LocalTime end) {
        return end.isAfter(start);
    }
}
