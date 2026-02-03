package com.campus.rsvp.dao;

import com.campus.rsvp.db.ConnectionManager;
import com.campus.rsvp.model.Venue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenueDao {

    public int create(Venue venue) throws SQLException {
        String sql = "INSERT INTO venues (name, building, room, capacity, is_outdoor) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, venue.getName());
            statement.setString(2, venue.getBuilding());
            statement.setString(3, venue.getRoom());
            statement.setInt(4, venue.getCapacity());
            statement.setBoolean(5, venue.isOutdoor());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }

    public Venue getById(int venueId) throws SQLException {
        String sql = "SELECT * FROM venues WHERE venue_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, venueId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }
        }
        return null;
    }

    public List<Venue> getAll() throws SQLException {
        List<Venue> venues = new ArrayList<>();
        String sql = "SELECT * FROM venues ORDER BY name";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                venues.add(mapRow(resultSet));
            }
        }
        return venues;
    }

    public boolean update(Venue venue) throws SQLException {
        String sql = "UPDATE venues SET name = ?, building = ?, room = ?, capacity = ?, is_outdoor = ? WHERE venue_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, venue.getName());
            statement.setString(2, venue.getBuilding());
            statement.setString(3, venue.getRoom());
            statement.setInt(4, venue.getCapacity());
            statement.setBoolean(5, venue.isOutdoor());
            statement.setInt(6, venue.getVenueId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int venueId) throws SQLException {
        String sql = "DELETE FROM venues WHERE venue_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, venueId);
            return statement.executeUpdate() > 0;
        }
    }

    private Venue mapRow(ResultSet resultSet) throws SQLException {
        return new Venue(
                resultSet.getInt("venue_id"),
                resultSet.getString("name"),
                resultSet.getString("building"),
                resultSet.getString("room"),
                resultSet.getInt("capacity"),
                resultSet.getBoolean("is_outdoor")
        );
    }
}
