package com.campus.rsvp.model;

public class Venue {
    private int venueId;
    private String name;
    private String building;
    private String room;
    private int capacity;
    private boolean outdoor;

    public Venue() {
    }

    public Venue(int venueId, String name, String building, String room, int capacity, boolean outdoor) {
        this.venueId = venueId;
        this.name = name;
        this.building = building;
        this.room = room;
        this.capacity = capacity;
        this.outdoor = outdoor;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "venueId=" + venueId +
                ", name='" + name + '\'' +
                ", building='" + building + '\'' +
                ", room='" + room + '\'' +
                ", capacity=" + capacity +
                ", outdoor=" + outdoor +
                '}';
    }
}
