package model;

public class Organizer extends User {
    public Organizer(int id, String name, String email, String password) {
        super(id, name, email, "Organizer", password);
    }
}