package com.campus.rsvp.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private int eventId;
    private String title;
    private String description;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int venueId;
    private String organizer;
    private String category;

    public Event() {
    }

    public Event(int eventId, String title, String description, LocalDate eventDate, LocalTime startTime,
                 LocalTime endTime, int venueId, String organizer, String category) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venueId = venueId;
        this.organizer = organizer;
        this.category = category;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", date=" + eventDate +
                ", start=" + startTime +
                ", end=" + endTime +
                ", venueId=" + venueId +
                ", organizer='" + organizer + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
