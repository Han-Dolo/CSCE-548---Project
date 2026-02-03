package com.campus.rsvp.dao;

import com.campus.rsvp.db.ConnectionManager;
import com.campus.rsvp.model.Attendee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendeeDao {

    public int create(Attendee attendee) throws SQLException {
        String sql = "INSERT INTO attendees (first_name, last_name, email, major, class_year) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, attendee.getFirstName());
            statement.setString(2, attendee.getLastName());
            statement.setString(3, attendee.getEmail());
            statement.setString(4, attendee.getMajor());
            statement.setInt(5, attendee.getClassYear());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }

    public Attendee getById(int attendeeId) throws SQLException {
        String sql = "SELECT * FROM attendees WHERE attendee_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, attendeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }
        }
        return null;
    }

    public List<Attendee> getAll() throws SQLException {
        List<Attendee> attendees = new ArrayList<>();
        String sql = "SELECT * FROM attendees ORDER BY last_name, first_name";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                attendees.add(mapRow(resultSet));
            }
        }
        return attendees;
    }

    public boolean update(Attendee attendee) throws SQLException {
        String sql = "UPDATE attendees SET first_name = ?, last_name = ?, email = ?, major = ?, class_year = ? "
                + "WHERE attendee_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, attendee.getFirstName());
            statement.setString(2, attendee.getLastName());
            statement.setString(3, attendee.getEmail());
            statement.setString(4, attendee.getMajor());
            statement.setInt(5, attendee.getClassYear());
            statement.setInt(6, attendee.getAttendeeId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int attendeeId) throws SQLException {
        String sql = "DELETE FROM attendees WHERE attendee_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, attendeeId);
            return statement.executeUpdate() > 0;
        }
    }

    private Attendee mapRow(ResultSet resultSet) throws SQLException {
        return new Attendee(
                resultSet.getInt("attendee_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("major"),
                resultSet.getInt("class_year")
        );
    }
}
