package service;
import model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import service.UserManager;

public class XMLDefaults {

    public static List<User> defaultUsers() {
        List<User> users = new ArrayList<>();

        users.add(new User(
                "a001",
                "admin",
                "1234",
                Role.ORGANIZER
        ));

        users.add(new User(
                "o002",
                "Dr. John Miller",
                "Organizer123",
                Role.ORGANIZER
        ));

        users.add(new User(
                "o003",
                "Dr. Sarah Reed",
                "Organizer123",
                Role.ORGANIZER
        ));

        users.add(new User(
                "s004",
                "Emily Carter",
                "student123",
                Role.PARTICIPANT
        ));

        users.add(new User(
                "s005",
                "Michael Brown",
                "student123",
                Role.PARTICIPANT
        ));

        users.add(new User(
                "s006",
                "Lucas Nguyen",
                "student123",
                Role.PARTICIPANT
        ));

        users.add(new User(
                "o007",
                "Mr. Billy joe",
                "Organizer123",
                Role.ORGANIZER
        ));

        return users;
    }



    public static List<Room> defaultRooms() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("A101", 50, "Building A, 1st Floor", true, true ));
        rooms.add(new Room("B202", 30, "Building B, 2nd Floor", false, true ));
        rooms.add(new Room("C303", 100, "Main Auditorium", true, false ));
        rooms.add(new Room("D404", 20, "Building A, 3rd Floor", true, false));
        rooms.add(new Room("E505", 75, "Building B, 1st Floor", true, true));
        return rooms;
    }

    public static List<Event> defaultEvents() {
        List<Event> events = new ArrayList<>();

        Event e1 = new Event();
        e1.setId("e001");
        e1.setTitle("Campus Welcome Day");
        e1.setDescription("Introduction for new students and faculty.");
        e1.setStart(LocalDateTime.of(2025,12,10,9,0));
        e1.setEnd(LocalDateTime.of(2025,12,10,12,0));
        e1.setRoomId("A101");
        e1.setOrganizerId("admin");
        e1.setCapacity(50);
        events.add(e1);

        Event e2 = new Event();
        e2.setId("e002");
        e2.setTitle("Science Seminar");
        e2.setDescription("Guest lecture on AI and robotics.");
        e2.setStart(LocalDateTime.of(2025,12,12,14,0));
        e2.setEnd(LocalDateTime.of(2025,12,12,16,0));
        e2.setRoomId("C303");
        e2.setOrganizerId("admin");
        e2.setCapacity(100);
        events.add(e2);

        Event e3 = new Event();
        e3.setId("e003");
        e3.setTitle("Art Workshop");
        e3.setDescription("Hands-on painting and sketching workshop.");
        e3.setStart(LocalDateTime.of(2025,12,15,10,0));
        e3.setEnd(LocalDateTime.of(2025,12,15,13,0));
        e3.setRoomId("B202");
        e3.setOrganizerId("admin");
        e3.setCapacity(30);
        events.add(e3);

        return events;
    }

    public static List<Feedback> defaultFeedback() {
        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback("f001", "e001", "student01", 5, "Great introduction event, very welcoming!"));
        feedbacks.add(new Feedback("f002", "e002", "Organizer01", 4, "Informative seminar, but could have been longer."));
        feedbacks.add(new Feedback("f003", "e003", "student05", 5, "Fun and interactive art session!"));
        return feedbacks;
    }
}
