package ui;
import model.Feedback;
import model.Event;
import service.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FeedbackDialog extends JPanel {

    private EventManager eventManager;
    private FeedbackManager feedbackManager;
    private MainFrame mainFrame;

    private JTable table;
    private DefaultTableModel tableModel;

    public FeedbackDialog(EventManager eventManager, FeedbackManager feedbackManager, MainFrame mainFrame) {
        this.eventManager = eventManager;
        this.feedbackManager = feedbackManager;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Feedback", JLabel.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        add(lbl, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Event","User","Rating","Comment","Response"},0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> mainFrame.switchPanel(new DashboardPanel(mainFrame.userManager.getAdmin(),
                eventManager, mainFrame.roomManager, feedbackManager, mainFrame.notificationService, mainFrame)));
        btnPanel.add(btnBack);
        add(btnPanel, BorderLayout.SOUTH);

        JButton btnRespond = new JButton("Respond");
        btnRespond.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected < 0) {
                JOptionPane.showMessageDialog(this, "Select a feedback to respond to.");
                return;
            }

            Feedback fb = feedbackManager.getFeedback().get(selected);

            JTextArea responseArea = new JTextArea(fb.getResponse(), 5, 30);
            JScrollPane scroll = new JScrollPane(responseArea);

            int option = JOptionPane.showConfirmDialog(this, scroll, "Respond to Feedback", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                fb.setResponse(responseArea.getText().trim());
                feedbackManager.saveFeedback(feedbackManager.getFeedback());
                loadTable();
            }
        });
        btnPanel.add(btnRespond);


        loadTable();
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        List<Feedback> feedbacks = feedbackManager.getFeedback();
        for (Feedback f : feedbacks) {
            Event e = eventManager.getEvents().stream().filter(ev -> ev.getId().equals(f.getEventId())).findFirst().orElse(null);
            if (e != null) {
                tableModel.addRow(new Object[]{e.getTitle(), f.getUserId(), f.getRating(), f.getComment(), f.getResponse()});
            }
        }
    }
}
