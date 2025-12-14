package model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private String id;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private String roomId;
    private String organizerId;
    private int capacity;

    private List<String> participants = new ArrayList<>();

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
    public String getRoomId() { return roomId; }
    public String getOrganizerId() { return organizerId; }
    public int getCapacity() { return capacity; }
    public List<String> getParticipants() { return participants; }

    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStart(LocalDateTime start) { this.start = start; }
    public void setEnd(LocalDateTime end) { this.end = end; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public void setOrganizerId(String organizerId) { this.organizerId = organizerId; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setParticipants(List<String> participants) { this.participants = participants; }
}
