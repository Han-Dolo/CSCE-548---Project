package com.campus.rsvp.dao;

import com.campus.rsvp.db.ConnectionManager;
import com.campus.rsvp.model.Event;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventDao {

    public int create(Event event) throws SQLException {
        String sql = "INSERT INTO events (title, description, event_date, start_time, end_time, venue_id, organizer, category) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setDate(3, Date.valueOf(event.getEventDate()));
            statement.setTime(4, Time.valueOf(event.getStartTime()));
            statement.setTime(5, Time.valueOf(event.getEndTime()));
            statement.setInt(6, event.getVenueId());
            statement.setString(7, event.getOrganizer());
            statement.setString(8, event.getCategory());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }

    public Event getById(int eventId) throws SQLException {
        String sql = "SELECT * FROM events WHERE event_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }
        }
        return null;
    }

    public List<Event> getAll() throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events ORDER BY event_date, start_time";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                events.add(mapRow(resultSet));
            }
        }
        return events;
    }

    public boolean update(Event event) throws SQLException {
        String sql = "UPDATE events SET title = ?, description = ?, event_date = ?, start_time = ?, end_time = ?, "
                + "venue_id = ?, organizer = ?, category = ? WHERE event_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setDate(3, Date.valueOf(event.getEventDate()));
            statement.setTime(4, Time.valueOf(event.getStartTime()));
            statement.setTime(5, Time.valueOf(event.getEndTime()));
            statement.setInt(6, event.getVenueId());
            statement.setString(7, event.getOrganizer());
            statement.setString(8, event.getCategory());
            statement.setInt(9, event.getEventId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int eventId) throws SQLException {
        String sql = "DELETE FROM events WHERE event_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            return statement.executeUpdate() > 0;
        }
    }

    private Event mapRow(ResultSet resultSet) throws SQLException {
        LocalDate date = resultSet.getDate("event_date").toLocalDate();
        LocalTime start = resultSet.getTime("start_time").toLocalTime();
        LocalTime end = resultSet.getTime("end_time").toLocalTime();
        return new Event(
                resultSet.getInt("event_id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                date,
                start,
                end,
                resultSet.getInt("venue_id"),
                resultSet.getString("organizer"),
                resultSet.getString("category")
        );
    }
}
