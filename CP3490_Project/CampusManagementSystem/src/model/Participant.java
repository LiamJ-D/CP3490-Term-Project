package model;

public class Participant extends User {
    public Participant(int id, String name, String email, String password) {
        super(id, name, email, "Participant", password);
    }
}