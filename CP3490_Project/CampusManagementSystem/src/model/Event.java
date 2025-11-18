package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private int eventID;
    private String title;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String description;
    private Venue venue;

    public Event(int id, String title, LocalDate date, LocalTime time,
                 String location, String description, Venue venue) {

        this.eventID = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
        this.venue = venue;
    }

    public int getEventID() { return eventID; }
    public String getTitle() { return title; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public Venue getVenue() { return venue; }

    @Override
    public String toString() {
        return title + " (" + date + " " + time + ") - " + venue;
    }
}