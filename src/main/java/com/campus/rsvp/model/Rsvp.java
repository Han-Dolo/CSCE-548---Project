package com.campus.rsvp.model;

import java.time.LocalDateTime;

public class Rsvp {
    private int rsvpId;
    private int eventId;
    private int attendeeId;
    private String status;
    private boolean checkedIn;
    private LocalDateTime rsvpTimestamp;

    public Rsvp() {
    }

    public Rsvp(int rsvpId, int eventId, int attendeeId, String status, boolean checkedIn, LocalDateTime rsvpTimestamp) {
        this.rsvpId = rsvpId;
        this.eventId = eventId;
        this.attendeeId = attendeeId;
        this.status = status;
        this.checkedIn = checkedIn;
        this.rsvpTimestamp = rsvpTimestamp;
    }

    public int getRsvpId() {
        return rsvpId;
    }

    public void setRsvpId(int rsvpId) {
        this.rsvpId = rsvpId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(int attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public LocalDateTime getRsvpTimestamp() {
        return rsvpTimestamp;
    }

    public void setRsvpTimestamp(LocalDateTime rsvpTimestamp) {
        this.rsvpTimestamp = rsvpTimestamp;
    }

    @Override
    public String toString() {
        return "Rsvp{" +
                "rsvpId=" + rsvpId +
                ", eventId=" + eventId +
                ", attendeeId=" + attendeeId +
                ", status='" + status + '\'' +
                ", checkedIn=" + checkedIn +
                ", rsvpTimestamp=" + rsvpTimestamp +
                '}';
    }
}
