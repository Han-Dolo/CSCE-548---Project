package com.campus.rsvp.service;

import com.campus.rsvp.business.BusinessManager;
import com.campus.rsvp.model.Event;

import java.sql.SQLException;
import java.util.List;

public class EventService {
    private final BusinessManager business;

    public EventService(BusinessManager business) {
        this.business = business;
    }

    public int save(Event event) throws SQLException {
        return business.saveEvent(event);
    }

    public Event getById(int eventId) throws SQLException {
        return business.getEventById(eventId);
    }

    public List<Event> getAll() throws SQLException {
        return business.getAllEvents();
    }

    public boolean delete(int eventId) throws SQLException {
        return business.deleteEvent(eventId);
    }
}
