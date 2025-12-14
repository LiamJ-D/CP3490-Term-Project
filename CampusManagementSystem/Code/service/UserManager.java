package service;
import model.Role;
import model.User;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private final String USERS_FILE = "users.xml";
    private List<User> users = new ArrayList<>();

    private final User admin;

    public UserManager() {
        admin = new User("a001", "admin", "1234", Role.ADMIN);
    }

    public User login(String id, String password) {
        if (id.equals(admin.getId()) && password.equals(admin.getPassword())) {
            return admin;
        }
        return null;
    }

    public User getAdmin() {
        return admin;
    }

    public User createUser(String id, String name, String pass, Role role) {
        return new User(id, name, pass, role);
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User u) {
        users.add(u);
        saveUsers(users);
    }


    public void updateUser(User u) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(u.getId())) {
                users.set(i, u);
                saveUsers(users);
                return;
            }
        }
    }

    public void deleteUser(String id) {
        users.removeIf(u -> u.getId().equals(id));
        saveUsers(users);
    }

    public User getById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();

        try {
            File file = new File(USERS_FILE);
            if (!file.exists()) {
                return users;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("user");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);

                User u = new User();
                u.setId(e.getAttribute("id"));
                u.setRole(Role.valueOf(e.getAttribute("role")));

                u.setName(
                        e.getElementsByTagName("name").item(0).getTextContent()
                );

                u.setPassword(
                        e.getElementsByTagName("password").item(0).getTextContent()
                );

                users.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }


    public void saveUsers(List<User> users) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("users");
            doc.appendChild(root);

            for (User u : users) {
                Element userElem = doc.createElement("user");

                userElem.setAttribute("id", u.getId());
                userElem.setAttribute("role", u.getRole().name());

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(u.getName()));
                userElem.appendChild(name);

                Element password = doc.createElement("password");
                password.appendChild(doc.createTextNode(u.getPassword()));
                userElem.appendChild(password);

                root.appendChild(userElem);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(
                    new DOMSource(doc),
                    new StreamResult(new File(USERS_FILE))
            );


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void add(Document doc, Element parent, String tag, String val) {
        Element e = doc.createElement(tag);
        e.appendChild(doc.createTextNode(val));
        parent.appendChild(e);
    }
    private String getText(Element parent, String tag) {
        NodeList list = parent.getElementsByTagName(tag);
        return list.getLength() > 0 ? list.item(0).getTextContent() : "";
    }

}
