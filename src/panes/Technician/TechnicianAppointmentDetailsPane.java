package panes.Technician;

import components.FloatingButton;
import java.awt.*;
import java.awt.event.ItemEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class TechnicianAppointmentDetailsPane extends JPanel {

    private final Color primaryPurple = new Color(128, 128, 255);
    private TechnicianAppointmentPane parentController;
    private TechnicianAppointmentPane.AppointmentData currentData;

    // Dynamic UI Containers
    private JLabel section1Title;
    private JPanel infoGrid;
    private JTextArea descArea;
    private JPanel tasksPanel;
    private JPanel chatPanel;
    private FloatingButton completeBtn;

    public TechnicianAppointmentDetailsPane(TechnicianAppointmentPane parentController) {
        this.parentController = parentController;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        initUIComponents();
    }

    private void initUIComponents() {
        // --- PART 2: Maintenance Feedback ---
        section1Title = new JLabel("Maintenance Feedback");
        section1Title.setFont(new Font("SansSerif", Font.BOLD, 16));
        section1Title.setForeground(primaryPurple);
        section1Title.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoGrid = new JPanel(new GridLayout(2, 4, 10, 5));
        infoGrid.setOpaque(false);
        infoGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoGrid.setMaximumSize(new Dimension(700, 50));

        descArea = new JTextArea();
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descArea.setForeground(Color.GRAY);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- PART 3: Tasks ---
        JLabel section2Title = new JLabel("Assigned Tasks");
        section2Title.setFont(new Font("SansSerif", Font.BOLD, 16));
        section2Title.setForeground(primaryPurple);
        section2Title.setAlignmentX(Component.LEFT_ALIGNMENT);

        tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        tasksPanel.setOpaque(false);
        tasksPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- PART 4: Chat & History ---
        JLabel section3Title = new JLabel("Comment & History");
        section3Title.setFont(new Font("SansSerif", Font.BOLD, 16));
        section3Title.setForeground(primaryPurple);
        section3Title.setAlignmentX(Component.LEFT_ALIGNMENT);

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setOpaque(false);

        // SCROLLABLE CHAT IMPLEMENTATION
        JScrollPane chatScroll = new JScrollPane(chatPanel);
        chatScroll.setBorder(BorderFactory.createLineBorder(new Color(230,230,240), 1, true));
        chatScroll.setOpaque(false);
        chatScroll.getViewport().setOpaque(false);
        chatScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Fix height so it scrolls if more than ~2 comments are added
        chatScroll.setPreferredSize(new Dimension(800, 160));
        chatScroll.setMaximumSize(new Dimension(800, 160));
        chatScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Chat Input
        JPanel inputContainer = new JPanel(new BorderLayout());
        inputContainer.setOpaque(false);
        inputContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputContainer.setMaximumSize(new Dimension(800, 40));

        JTextField inputField = new JTextField();
        inputField.setOpaque(false);
        inputField.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, Color.GRAY),
                new EmptyBorder(5, 5, 5, 5)
        ));

        JLabel sendIcon = new JLabel("➤");
        sendIcon.setFont(new Font("SansSerif", Font.PLAIN, 18));
        sendIcon.setForeground(Color.GRAY);
        sendIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendIcon.setBorder(new EmptyBorder(0,10,0,10));

        // Chat Submit Logic
        sendIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(!inputField.getText().trim().isEmpty() && currentData != null) {
                    currentData.chatHistory.add(new String[]{"TECHNICIAN", inputField.getText()});
                    inputField.setText("");
                    refreshChat();
                }
            }
        });

        inputContainer.add(inputField, BorderLayout.CENTER);
        inputContainer.add(sendIcon, BorderLayout.EAST);

        // --- PART 5: Complete Button ---
        completeBtn = new FloatingButton("Maintenance Complete", 20);
        completeBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        completeBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        completeBtn.addActionListener(e -> {
            if (currentData != null) {
                parentController.updateAppointmentStatus(currentData, "Completed");
                completeBtn.setText("Maintenance Logged");
                completeBtn.setEnabled(false);
            }
        });

        // Add to main panel
        add(section1Title);
        add(Box.createVerticalStrut(15));
        add(infoGrid);
        add(Box.createVerticalStrut(15));
        add(descArea);

        add(Box.createVerticalStrut(30));
        add(section2Title);
        add(Box.createVerticalStrut(10));
        add(tasksPanel);

        add(Box.createVerticalStrut(30));
        add(section3Title);
        add(Box.createVerticalStrut(10));
        add(chatScroll); // Added the ScrollPane here instead of raw chatPanel
        add(Box.createVerticalStrut(10));
        add(inputContainer);

        add(Box.createVerticalStrut(30));
        add(completeBtn);
        add(Box.createVerticalStrut(20));
    }

    // Called by the Controller when a new card is clicked
    public void loadAppointment(TechnicianAppointmentPane.AppointmentData data) {
        this.currentData = data;

        // Update Text Info
        section1Title.setText("Maintenance Feedback: " + data.model);
        descArea.setText(data.description);

        infoGrid.removeAll();
        infoGrid.add(createHeaderLabel("Name")); infoGrid.add(createValueLabel(data.name));
        infoGrid.add(createHeaderLabel("Contact No.")); infoGrid.add(createValueLabel(data.contact));
        infoGrid.add(createHeaderLabel("Plat Number")); infoGrid.add(createValueLabel(data.plate));
        infoGrid.add(createHeaderLabel("Email")); infoGrid.add(createValueLabel(data.email));

        // Build Checkboxes based on data state
        tasksPanel.removeAll();
        for (int i = 0; i < data.tasks.length; i++) {
            final int index = i;
            JCheckBox cb = new JCheckBox(data.tasks[i]);
            cb.setSelected(data.taskStates[i]); // Remember if it was checked before!
            cb.setFont(new Font("SansSerif", Font.BOLD, 12));
            cb.setForeground(Color.GRAY);
            cb.setOpaque(false);

            cb.addItemListener(e -> {
                data.taskStates[index] = cb.isSelected();
                evaluateLogic();
            });
            tasksPanel.add(cb);
            tasksPanel.add(Box.createVerticalStrut(5));
        }

        refreshChat();
        evaluateLogic(); // Set button state immediately

        revalidate();
        repaint();
    }

    private void refreshChat() {
        chatPanel.removeAll();
        if (currentData != null) {
            for (String[] msg : currentData.chatHistory) {
                boolean isCustomer = msg[0].equals("CUSTOMER");
                chatPanel.add(createChatBubble(msg[0], msg[1], isCustomer));
                chatPanel.add(Box.createVerticalStrut(10));
            }
        }
        chatPanel.revalidate();
        chatPanel.repaint();
    }

    private void evaluateLogic() {
        if (currentData.status.equals("Completed")) {
            completeBtn.setText("Maintenance Logged");
            completeBtn.setBackground(new Color(100, 200, 100));
            completeBtn.setEnabled(false);
            return;
        }

        int total = currentData.taskStates.length;
        int checked = 0;
        for (boolean state : currentData.taskStates) { if (state) checked++; }

        if (checked == 0) {
            parentController.updateAppointmentStatus(currentData, "In Queue");
            completeBtn.setText("Maintenance Complete");
            completeBtn.setBackground(new Color(220, 220, 255));
            completeBtn.setEnabled(false);
        } else if (checked < total) {
            parentController.updateAppointmentStatus(currentData, "In Service");
            completeBtn.setText("Maintenance Complete");
            completeBtn.setBackground(new Color(220, 220, 255));
            completeBtn.setEnabled(false);
        } else {
            parentController.updateAppointmentStatus(currentData, "In Service");
            completeBtn.setText("Complete Maintenance");
            completeBtn.setBackground(primaryPurple);
            completeBtn.setEnabled(true);
        }
    }

    private JLabel createHeaderLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(primaryPurple);
        return lbl;
    }

    private JLabel createValueLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(Color.GRAY);
        return lbl;
    }

    private JPanel createChatBubble(String sender, String message, boolean isCustomer) {
        JPanel bubble = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isCustomer ? new Color(220, 220, 255) : new Color(235, 235, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        bubble.setLayout(new BoxLayout(bubble, BoxLayout.Y_AXIS));
        bubble.setOpaque(false);
        bubble.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel senderLbl = new JLabel(sender);
        senderLbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        senderLbl.setForeground(primaryPurple);

        JLabel msgLbl = new JLabel(message);
        msgLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        msgLbl.setForeground(Color.DARK_GRAY);

        bubble.add(senderLbl);
        bubble.add(Box.createVerticalStrut(5));
        bubble.add(msgLbl);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(bubble);
        return wrapper;
    }
}