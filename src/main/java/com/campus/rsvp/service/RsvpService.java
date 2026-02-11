package com.campus.rsvp.service;

import com.campus.rsvp.business.BusinessManager;
import com.campus.rsvp.model.Rsvp;

import java.sql.SQLException;
import java.util.List;

public class RsvpService {
    private final BusinessManager business;

    public RsvpService(BusinessManager business) {
        this.business = business;
    }

    public int save(Rsvp rsvp) throws SQLException {
        return business.saveRsvp(rsvp);
    }

    public Rsvp getById(int rsvpId) throws SQLException {
        return business.getRsvpById(rsvpId);
    }

    public List<Rsvp> getAll() throws SQLException {
        return business.getAllRsvps();
    }

    public List<Rsvp> getByEvent(int eventId) throws SQLException {
        return business.getRsvpsForEvent(eventId);
    }

    public boolean delete(int rsvpId) throws SQLException {
        return business.deleteRsvp(rsvpId);
    }
}
