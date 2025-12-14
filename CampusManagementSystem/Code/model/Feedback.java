/*package model;

public class Feedback {
    private String id;
    private String eventId;
    private String userId;
    private int rating;      // Give rating between 1–5
    private String comment;

    public Feedback() {}

    public Feedback(String id, String eventId, String userId, int rating, String comment) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() { return id; }
    public String getEventId() { return eventId; }
    public String getUserId() { return userId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    public void setId(String id) { this.id = id; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
}*/
package model;

public class Feedback {
    private String id;
    private String eventId;
    private String userId;
    private int rating;      //Give a rating between 1–5
    private String comment;
    private String response;

    public Feedback() {}

    public Feedback(String id, String eventId, String userId, int rating, String comment) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.response = ""; // default empty
    }

    public String getId() { return id; }
    public String getEventId() { return eventId; }
    public String getUserId() { return userId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public String getResponse() { return response; }

    public void setId(String id) { this.id = id; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    public void setResponse(String response) { this.response = response; }
}

