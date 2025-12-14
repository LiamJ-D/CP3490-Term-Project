package data;
import model.*;
import org.w3c.dom.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class XMLDataStore {

    private static final String EVENTS_FILE = "events.xml";
    private static final String ROOMS_FILE = "rooms.xml";
    private static final String FEEDBACK_FILE = "feedback.xml";

    public List<Event> loadEvents() {
        List<Event> events = new ArrayList<>();

        try {
            Document doc = XMLUtils.load(EVENTS_FILE);
            NodeList list = doc.getElementsByTagName("event");

            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);
                Event ev = new Event();

                ev.setId(e.getAttribute("id"));
                ev.setTitle(XMLUtils.get(e, "title"));
                ev.setDescription(XMLUtils.get(e, "description"));
                ev.setStart(LocalDateTime.parse(XMLUtils.get(e, "start")));
                ev.setEnd(LocalDateTime.parse(XMLUtils.get(e, "end")));
                ev.setRoomId(XMLUtils.get(e, "roomId"));
                ev.setOrganizerId(XMLUtils.get(e, "organizerId"));
                ev.setCapacity(Integer.parseInt(XMLUtils.get(e, "capacity")));

                List<String> participants = new ArrayList<>();
                NodeList plist = e.getElementsByTagName("user");
                for (int j = 0; j < plist.getLength(); j++) {
                    participants.add(plist.item(j).getTextContent());
                }
                ev.setParticipants(participants);

                events.add(ev);
            }

        } catch (Exception ex) {
            System.out.println("Error loading events: " + ex.getMessage());
        }
        return events;
    }

    public void saveEvents(List<Event> events) {
        try {
            Document doc = XMLUtils.newDoc();
            Element root = doc.createElement("events");
            doc.appendChild(root);

            for (Event e : events) {
                Element node = doc.createElement("event");
                node.setAttribute("id", e.getId());

                XMLUtils.add(doc, node, "title", e.getTitle());
                XMLUtils.add(doc, node, "description", e.getDescription());
                XMLUtils.add(doc, node, "start", e.getStart().toString());
                XMLUtils.add(doc, node, "end", e.getEnd().toString());
                XMLUtils.add(doc, node, "roomId", e.getRoomId());
                XMLUtils.add(doc, node, "organizerId", e.getOrganizerId());
                XMLUtils.add(doc, node, "capacity", String.valueOf(e.getCapacity()));

                Element p = doc.createElement("participants");
                for (String s : e.getParticipants()) {
                    XMLUtils.add(doc, p, "user", s);
                }
                node.appendChild(p);

                root.appendChild(node);
            }

            XMLUtils.save(doc, EVENTS_FILE);
        } catch (Exception ex) {
            System.out.println("Error saving events: " + ex.getMessage());
        }
    }

    public List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();

        try {
            Document doc = XMLUtils.load(ROOMS_FILE);
            NodeList list = doc.getElementsByTagName("room");

            for (int i = 0; i < list.getLength(); i++) {
                Element r = (Element) list.item(i);

                String id = r.getAttribute("id");
                int capacity = Integer.parseInt(r.getAttribute("capacity"));

                String location = XMLUtils.get(r, "location");
                boolean projector = Boolean.parseBoolean(XMLUtils.get(r, "projector"));
                boolean whiteboard = Boolean.parseBoolean(XMLUtils.get(r, "whiteboard"));

                rooms.add(new Room(id, capacity, location, projector, whiteboard));
            }
        } catch (Exception e) {
            System.out.println("Error loading rooms: " + e.getMessage());
        }

        return rooms;
    }

    public void saveRooms(List<Room> rooms) {
        try {
            Document doc = XMLUtils.newDoc();
            Element root = doc.createElement("rooms");
            doc.appendChild(root);

            for (Room r : rooms) {
                Element node = doc.createElement("room");
                node.setAttribute("id", r.getId());
                node.setAttribute("capacity", String.valueOf(r.getCapacity()));

                // New attributes
                XMLUtils.add(doc, node, "location", r.getLocation());
                XMLUtils.add(doc, node, "projector", String.valueOf(r.isProjector()));
                XMLUtils.add(doc, node, "whiteboard", String.valueOf(r.isWhiteboard()));

                root.appendChild(node);
            }

            XMLUtils.save(doc, ROOMS_FILE);
        } catch (Exception ex) {
            System.out.println("Error saving rooms: " + ex.getMessage());
        }
    }

    public List<Feedback> loadFeedback() {
        List<Feedback> feedback = new ArrayList<>();

        try {
            Document doc = XMLUtils.load(FEEDBACK_FILE);
            NodeList list = doc.getElementsByTagName("feedback");

            for (int i = 0; i < list.getLength(); i++) {
                Element f = (Element) list.item(i);
                feedback.add(new Feedback(
                        f.getAttribute("id"),
                        XMLUtils.get(f, "eventId"),
                        XMLUtils.get(f, "userId"),
                        Integer.parseInt(XMLUtils.get(f, "rating")),
                        XMLUtils.get(f, "comment")
                ));
            }

        } catch (Exception e) {
            System.out.println("Error loading feedback: " + e.getMessage());
        }
        return feedback;
    }

    public void saveFeedback(List<Feedback> items) {
        try {
            Document doc = XMLUtils.newDoc();
            Element root = doc.createElement("feedbackList");
            doc.appendChild(root);

            for (Feedback fb : items) {
                Element n = doc.createElement("feedback");
                n.setAttribute("id", fb.getId());

                XMLUtils.add(doc, n, "eventId", fb.getEventId());
                XMLUtils.add(doc, n, "userId", fb.getUserId());
                XMLUtils.add(doc, n, "rating", String.valueOf(fb.getRating()));
                XMLUtils.add(doc, n, "comment", fb.getComment());

                root.appendChild(n);
            }

            XMLUtils.save(doc, FEEDBACK_FILE);

        } catch (Exception ex) {
            System.out.println("Error saving feedback: " + ex.getMessage());
        }
    }
}
