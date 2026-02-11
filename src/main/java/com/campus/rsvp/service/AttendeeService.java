package com.campus.rsvp.service;

import com.campus.rsvp.business.BusinessManager;
import com.campus.rsvp.model.Attendee;

import java.sql.SQLException;
import java.util.List;

public class AttendeeService {
    private final BusinessManager business;

    public AttendeeService(BusinessManager business) {
        this.business = business;
    }

    public int save(Attendee attendee) throws SQLException {
        return business.saveAttendee(attendee);
    }

    public Attendee getById(int attendeeId) throws SQLException {
        return business.getAttendeeById(attendeeId);
    }

    public List<Attendee> getAll() throws SQLException {
        return business.getAllAttendees();
    }

    public boolean delete(int attendeeId) throws SQLException {
        return business.deleteAttendee(attendeeId);
    }
}
