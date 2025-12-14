package service;
import model.Feedback;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FeedbackManager {

    private final String FEEDBACK_FILE = "feedback.xml";
    private List<Feedback> feedbackList = new ArrayList<>();

    public FeedbackManager() {
        feedbackList = loadFeedback();
    }

    public List<Feedback> loadFeedback() {
        List<Feedback> list = new ArrayList<>();
        try {
            File file = new File(FEEDBACK_FILE);
            if (!file.exists()) return list;

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList nodes = doc.getElementsByTagName("feedback");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element elem = (Element) nodes.item(i);
                String id = elem.getAttribute("id");
                String eventId = elem.getElementsByTagName("eventId").item(0).getTextContent();
                String userId = elem.getElementsByTagName("userId").item(0).getTextContent();
                int rating = Integer.parseInt(elem.getElementsByTagName("rating").item(0).getTextContent());
                String comment = elem.getElementsByTagName("comment").item(0).getTextContent();
                list.add(new Feedback(id, eventId, userId, rating, comment));
                String response = "";
                NodeList respNodes = elem.getElementsByTagName("response"); //new for feedback response
                if (respNodes.getLength() > 0) {
                    response = respNodes.item(0).getTextContent();
                }
                Feedback f = new Feedback(id, eventId, userId, rating, comment);
                f.setResponse(response);
                list.add(f);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveFeedback(List<Feedback> feedbacks) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("feedbackList");
            doc.appendChild(root);

            for (Feedback f : feedbacks) {
                Element feedbackElem = doc.createElement("feedback");
                feedbackElem.setAttribute("id", f.getId());

                Element eventId = doc.createElement("eventId");
                eventId.appendChild(doc.createTextNode(f.getEventId()));
                feedbackElem.appendChild(eventId);

                Element userId = doc.createElement("userId");
                userId.appendChild(doc.createTextNode(f.getUserId()));
                feedbackElem.appendChild(userId);

                Element rating = doc.createElement("rating");
                rating.appendChild(doc.createTextNode(String.valueOf(f.getRating())));
                feedbackElem.appendChild(rating);

                Element comment = doc.createElement("comment");
                comment.appendChild(doc.createTextNode(f.getComment()));
                feedbackElem.appendChild(comment);

                Element responseElem = doc.createElement("response");
                responseElem.appendChild(doc.createTextNode(f.getResponse()));
                feedbackElem.appendChild(responseElem);

                root.appendChild(feedbackElem);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FEEDBACK_FILE));
            transformer.transform(source, result);

            feedbackList = feedbacks;

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public List<Feedback> getFeedback() {
        return feedbackList;
    }

    public void addFeedback(Feedback f) {
        feedbackList.add(f);
        saveFeedback(feedbackList);
    }
}
