package service;
import data.XMLDataStore;
import model.Event;
import java.util.List;

public class EventManager {

    private XMLDataStore store = new XMLDataStore();

    public List<Event> getEvents() {
        return store.loadEvents();
    }

    public void saveEvents(List<Event> events) {
        store.saveEvents(events);
    }

    public void addEvent(Event e) {
        List<Event> list = getEvents();
        list.add(e);
        saveEvents(list);
    }

    public void deleteEvent(String id) {
        List<Event> list = getEvents();
        list.removeIf(e -> e.getId().equals(id));
        saveEvents(list);
    }

    public boolean hasConflict(Event newEvent) {
        for (Event e : getEvents()) {
            if (e.getRoomId().equals(newEvent.getRoomId())) {
                boolean overlap = newEvent.getStart().isBefore(e.getEnd())
                        && e.getStart().isBefore(newEvent.getEnd());

                if (overlap && !e.getId().equals(newEvent.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
