package service;
import model.Room;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {

    private final String ROOM_FILE = "rooms.xml";
    private List<Room> roomList = new ArrayList<>();

    public RoomManager() {
        roomList = loadRooms();
    }

    public List<Room> getRooms() { return roomList; }

    public void addRoom(Room r) {
        roomList.add(r);
        saveRooms(roomList);
    }

    public void editRoom(Room r) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getId().equals(r.getId())) {
                roomList.set(i, r);
                break;
            }
        }
        saveRooms(roomList);
    }

    public void deleteRoom(String id) {
        roomList.removeIf(r -> r.getId().equals(id));
        saveRooms(roomList);
    }

    public List<Room> loadRooms() {
        List<Room> list = new ArrayList<>();
        try {
            File file = new File(ROOM_FILE);
            if (!file.exists()) return list;

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList nodes = doc.getElementsByTagName("room");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element elem = (Element) nodes.item(i);
                String id = elem.getAttribute("id");
                int capacity = Integer.parseInt(elem.getAttribute("capacity"));
                String location = elem.getElementsByTagName("location").item(0).getTextContent();
                boolean projector = Boolean.parseBoolean(elem.getElementsByTagName("projector").item(0).getTextContent());
                boolean whiteboard = Boolean.parseBoolean(elem.getElementsByTagName("whiteboard").item(0).getTextContent());

                list.add(new Room(id, capacity, location, projector, whiteboard));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveRooms(List<Room> rooms) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("rooms");
            doc.appendChild(root);

            for (Room r : rooms) {
                Element roomElem = doc.createElement("room");
                roomElem.setAttribute("id", r.getId());
                roomElem.setAttribute("capacity", String.valueOf(r.getCapacity()));

                Element loc = doc.createElement("location");
                loc.appendChild(doc.createTextNode(r.getLocation()));
                roomElem.appendChild(loc);

                Element proj = doc.createElement("projector");
                proj.appendChild(doc.createTextNode(String.valueOf(r.isProjector())));
                roomElem.appendChild(proj);

                Element white = doc.createElement("whiteboard");
                white.appendChild(doc.createTextNode(String.valueOf(r.isWhiteboard())));
                roomElem.appendChild(white);

                root.appendChild(roomElem);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(ROOM_FILE)));

            roomList = rooms;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Room findRoom(String id) {
        return getRooms().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}

