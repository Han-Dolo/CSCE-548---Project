package com.campus.rsvp.service;

import com.campus.rsvp.business.BusinessManager;
import com.campus.rsvp.model.Venue;

import java.sql.SQLException;
import java.util.List;

public class VenueService {
    private final BusinessManager business;

    public VenueService(BusinessManager business) {
        this.business = business;
    }

    public int save(Venue venue) throws SQLException {
        return business.saveVenue(venue);
    }

    public Venue getById(int venueId) throws SQLException {
        return business.getVenueById(venueId);
    }

    public List<Venue> getAll() throws SQLException {
        return business.getAllVenues();
    }

    public boolean delete(int venueId) throws SQLException {
        return business.deleteVenue(venueId);
    }
}
