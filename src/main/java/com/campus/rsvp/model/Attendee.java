package com.campus.rsvp.model;

public class Attendee {
    private int attendeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String major;
    private int classYear;

    public Attendee() {
    }

    public Attendee(int attendeeId, String firstName, String lastName, String email, String major, int classYear) {
        this.attendeeId = attendeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.major = major;
        this.classYear = classYear;
    }

    public int getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(int attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getClassYear() {
        return classYear;
    }

    public void setClassYear(int classYear) {
        this.classYear = classYear;
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "attendeeId=" + attendeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", major='" + major + '\'' +
                ", classYear=" + classYear +
                '}';
    }
}
