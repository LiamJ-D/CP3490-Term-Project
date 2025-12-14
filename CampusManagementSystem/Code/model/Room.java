package model;

public class Room {
    private String id;
    private int capacity;
    private String location;
    private boolean projector;
    private boolean whiteboard;

    public Room(String id, int capacity, String location, boolean projector, boolean whiteboard) {
        this.id = id;
        this.capacity = capacity;
        this.location = location;
        this.projector = projector;
        this.whiteboard = whiteboard;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean isProjector() { return projector; }
    public void setProjector(boolean projector) { this.projector = projector; }

    public boolean isWhiteboard() { return whiteboard; }
    public void setWhiteboard(boolean whiteboard) { this.whiteboard = whiteboard; }
}

