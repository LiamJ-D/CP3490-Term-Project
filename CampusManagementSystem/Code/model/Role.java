package model;

public enum Role {
    ADMIN,
    ORGANIZER,
    PARTICIPANT;

    public boolean isEmpty() {
        return "empty".equals(this.toString());
    }
}
