package com.campus.rsvp.dao;

import com.campus.rsvp.db.ConnectionManager;
import com.campus.rsvp.model.Rsvp;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RsvpDao {

    public int create(Rsvp rsvp) throws SQLException {
        String sql = "INSERT INTO rsvps (event_id, attendee_id, status, checked_in) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, rsvp.getEventId());
            statement.setInt(2, rsvp.getAttendeeId());
            statement.setString(3, rsvp.getStatus());
            statement.setBoolean(4, rsvp.isCheckedIn());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }

    public Rsvp getById(int rsvpId) throws SQLException {
        String sql = "SELECT * FROM rsvps WHERE rsvp_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rsvpId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }
        }
        return null;
    }

    public List<Rsvp> getAll() throws SQLException {
        List<Rsvp> rsvps = new ArrayList<>();
        String sql = "SELECT * FROM rsvps ORDER BY rsvp_timestamp DESC";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                rsvps.add(mapRow(resultSet));
            }
        }
        return rsvps;
    }

    public List<Rsvp> getByEvent(int eventId) throws SQLException {
        List<Rsvp> rsvps = new ArrayList<>();
        String sql = "SELECT * FROM rsvps WHERE event_id = ? ORDER BY rsvp_timestamp DESC";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    rsvps.add(mapRow(resultSet));
                }
            }
        }
        return rsvps;
    }

    public boolean update(Rsvp rsvp) throws SQLException {
        String sql = "UPDATE rsvps SET status = ?, checked_in = ? WHERE rsvp_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, rsvp.getStatus());
            statement.setBoolean(2, rsvp.isCheckedIn());
            statement.setInt(3, rsvp.getRsvpId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int rsvpId) throws SQLException {
        String sql = "DELETE FROM rsvps WHERE rsvp_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rsvpId);
            return statement.executeUpdate() > 0;
        }
    }

    private Rsvp mapRow(ResultSet resultSet) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp("rsvp_timestamp");
        LocalDateTime time = timestamp != null ? timestamp.toLocalDateTime() : null;
        return new Rsvp(
                resultSet.getInt("rsvp_id"),
                resultSet.getInt("event_id"),
                resultSet.getInt("attendee_id"),
                resultSet.getString("status"),
                resultSet.getBoolean("checked_in"),
                time
        );
    }
}
