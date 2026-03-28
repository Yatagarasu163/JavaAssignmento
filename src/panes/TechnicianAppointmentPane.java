package panes;

import components.FloatingButton;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class TechnicianAppointmentPane extends JPanel {

    private final Color primaryPurple = new Color(128, 128, 255);
    private final Color lightPurple = new Color(180, 180, 255);
    private final Color bgPurple = new Color(200, 200, 255); // Left panel background
    private final Color bgColor = new Color(248, 248, 250);

    // Interactive Components
    private List<JCheckBox> taskCheckboxes;
    private FloatingButton completeBtn;
    private JLabel activeStatusLabel; // Holds the status of the currently selected appointment card

    public TechnicianAppointmentPane() {
        setLayout(new BorderLayout(30, 0));
        setBackground(bgColor);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // --- LEFT SIDE: APPOINTMENT LIST ---
        JPanel leftPanel = createLeftPanel();
        leftPanel.setPreferredSize(new Dimension(320, 0));
        add(leftPanel, BorderLayout.WEST);

        // --- RIGHT SIDE: APPOINTMENT DETAILS ---
        JPanel rightPanel = createRightPanel();

        // Wrap right panel in a scroll pane in case of smaller screens
        JScrollPane rightScroll = new JScrollPane(rightPanel);
        rightScroll.setBorder(null);
        rightScroll.getVerticalScrollBar().setUnitIncrement(16);
        rightScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightScroll.setOpaque(false);
        rightScroll.getViewport().setOpaque(false);

        add(rightScroll, BorderLayout.CENTER);
    }

    // ==========================================
    // PART 1: APPOINTMENT SELECTION (LEFT PANEL)
    // ==========================================
    private JPanel createLeftPanel() {
        // Custom panel for the large light-purple rounded background
        JPanel container = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgPurple);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        container.setOpaque(false);
        container.setLayout(new BorderLayout());
        container.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        // Create Dummy Cards
        JPanel card1 = createAppointmentCard("9.00 AM", "Perodua Myvi", "VKA 1234", "In Queue", true);
        JPanel card2 = createAppointmentCard("11.30 AM", "Proton Saga", "ABC 9876", "In Queue", false);
        JPanel card3 = createAppointmentCard("2.00 PM", "Honda City", "WQR 5544", "In Queue", false);

        listPanel.add(card1);
        listPanel.add(Box.createVerticalStrut(15));
        listPanel.add(card2);
        listPanel.add(Box.createVerticalStrut(15));
        listPanel.add(card3);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        container.add(scrollPane, BorderLayout.CENTER);
        return container;
    }

    private JPanel createAppointmentCard(String time, String model, String plate, String status, boolean isSelected) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Fill background based on selection
                g2.setColor(isSelected ? primaryPurple : new Color(220, 220, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Outline border
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
        card.setLayout(new GridBagLayout());
        card.setOpaque(false);
        card.setMaximumSize(new Dimension(280, 120));
        card.setPreferredSize(new Dimension(280, 120));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color textColor = isSelected ? Color.WHITE : Color.DARK_GRAY;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 15, 5, 15);

        // Time
        JLabel timeLbl = new JLabel(time);
        timeLbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        timeLbl.setForeground(textColor);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
        card.add(timeLbl, gbc);

        // Status Pill
        JLabel statusLbl = new JLabel(status, SwingConstants.CENTER);
        statusLbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        statusLbl.setOpaque(true);
        statusLbl.setBackground(isSelected ? new Color(100, 100, 220) : primaryPurple);
        statusLbl.setForeground(Color.WHITE);
        statusLbl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(statusLbl.getBackground(), 1, true),
                new EmptyBorder(4, 10, 4, 10)
        ));
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        card.add(statusLbl, gbc);

        // Model
        JLabel modelLbl = new JLabel(model);
        modelLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        modelLbl.setForeground(textColor);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        card.add(modelLbl, gbc);

        // Plate
        JLabel plateLbl = new JLabel(plate);
        plateLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        plateLbl.setForeground(textColor);
        gbc.gridx = 0; gbc.gridy = 2;
        card.add(plateLbl, gbc);

        if (isSelected) activeStatusLabel = statusLbl; // Map logic to the selected card

        return card;
    }

    // ==========================================
    // RIGHT PANEL COMPONENTS (PARTS 2-5)
    // ==========================================
    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // PART 2: Maintenance Feedback
        JLabel section1Title = new JLabel("Maintenance Feedback: Perodua Myvi");
        section1Title.setFont(new Font("SansSerif", Font.BOLD, 16));
        section1Title.setForeground(primaryPurple);
        section1Title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel infoGrid = new JPanel(new GridLayout(2, 4, 10, 5));
        infoGrid.setOpaque(false);
        infoGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoGrid.setMaximumSize(new Dimension(700, 50));

        infoGrid.add(createHeaderLabel("Name"));
        infoGrid.add(createValueLabel("Ali Bin Supaman"));
        infoGrid.add(createHeaderLabel("Contact No."));
        infoGrid.add(createValueLabel("0162203974"));
        infoGrid.add(createHeaderLabel("Plat Number"));
        infoGrid.add(createValueLabel("VKA 1234"));
        infoGrid.add(createHeaderLabel("Email"));
        infoGrid.add(createValueLabel("AliSupaman@gmail.com"));

        JTextArea descArea = new JTextArea(
                "Over the past two weeks, the engine has felt slightly rough during idle, with small vibrations noticeable through the steering wheel when stopped at traffic lights. I also hear a light squeaking sound when braking at low speeds, although the braking performance still feels normal. The air conditioning takes longer than usual to get cold and the airflow sometimes feels weaker. Additionally, the car slightly pulls to the left when driving straight, and I occasionally hear a faint knocking sound from the front when going over bumps. Fuel consumption also seems a bit higher than usual in the past month."
        );
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descArea.setForeground(Color.GRAY);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        // PART 3: Assigned Tasks
        JLabel section2Title = new JLabel("Assigned Tasks");
        section2Title.setFont(new Font("SansSerif", Font.BOLD, 16));
        section2Title.setForeground(primaryPurple);
        section2Title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        tasksPanel.setOpaque(false);
        tasksPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        taskCheckboxes = new ArrayList<>();
        String[] tasks = {"10,000 km schedule service", "Replace Oil Filter", "Rotate Tires", "Add aerospace oil", "Nasi Goreng Ayam"};

        ItemListener taskLogicListener = e -> evaluateTaskLogic();

        for (String task : tasks) {
            JCheckBox cb = new JCheckBox(task);
            cb.setFont(new Font("SansSerif", Font.BOLD, 12));
            cb.setForeground(Color.GRAY);
            cb.setOpaque(false);
            cb.setFocusPainted(false);
            cb.addItemListener(taskLogicListener); // Attach logic
            taskCheckboxes.add(cb);
            tasksPanel.add(cb);
            tasksPanel.add(Box.createVerticalStrut(5));
        }

        // PART 4: Comment & History
        JLabel section3Title = new JLabel("Comment & History");
        section3Title.setFont(new Font("SansSerif", Font.BOLD, 16));
        section3Title.setForeground(primaryPurple);
        section3Title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setOpaque(false);
        chatPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        chatPanel.add(createChatBubble("CUSTOMER", "Hearing a grinding voice from left wheels. Me feel cooked...", true));
        chatPanel.add(Box.createVerticalStrut(10));
        chatPanel.add(createChatBubble("TECHNICIAN", "Gud Luck :)", false));

        // Input Line
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
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel sendIcon = new JLabel("➤"); // Simple send icon placeholder
        sendIcon.setFont(new Font("SansSerif", Font.PLAIN, 18));
        sendIcon.setForeground(Color.GRAY);
        sendIcon.setBorder(new EmptyBorder(0, 10, 0, 10));
        sendIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        inputContainer.add(inputField, BorderLayout.CENTER);
        inputContainer.add(sendIcon, BorderLayout.EAST);

        // PART 5: Complete Button
        completeBtn = new FloatingButton("Maintenance Complete", 20);
        completeBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        completeBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        completeBtn.setBackground(new Color(220, 220, 255)); // Disabled look initially
        completeBtn.setEnabled(false); // Disabled by default

        completeBtn.addActionListener(e -> {
            activeStatusLabel.setText("Completed");
            activeStatusLabel.setBackground(new Color(100, 200, 100)); // Change pill to green
            completeBtn.setText("Maintenance Logged");
            completeBtn.setEnabled(false);
        });

        // Assemble Right Panel
        panel.add(section1Title);
        panel.add(Box.createVerticalStrut(15));
        panel.add(infoGrid);
        panel.add(Box.createVerticalStrut(15));
        panel.add(descArea);

        panel.add(Box.createVerticalStrut(30));
        panel.add(section2Title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(tasksPanel);

        panel.add(Box.createVerticalStrut(30));
        panel.add(section3Title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(chatPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(inputContainer);

        panel.add(Box.createVerticalStrut(30));
        panel.add(completeBtn);
        panel.add(Box.createVerticalStrut(20));

        return panel;
    }

    // ==========================================
    // LOGIC CONTROLLERS & HELPER METHODS
    // ==========================================

    // Evaluates checkboxes to change appointment status and button enable state
    private void evaluateTaskLogic() {
        int total = taskCheckboxes.size();
        int checked = 0;

        for (JCheckBox cb : taskCheckboxes) {
            if (cb.isSelected()) checked++;
        }

        if (checked == 0) {
            activeStatusLabel.setText("In Queue");
            activeStatusLabel.setBackground(new Color(100, 100, 220)); // Return to normal selected color
            completeBtn.setEnabled(false);
            completeBtn.setBackground(new Color(220, 220, 255));
        } else if (checked > 0 && checked < total) {
            activeStatusLabel.setText("In Service");
            activeStatusLabel.setBackground(new Color(255, 180, 50)); // Orange/Yellow for In Service
            completeBtn.setEnabled(false);
            completeBtn.setBackground(new Color(220, 220, 255));
        } else if (checked == total) {
            activeStatusLabel.setText("In Service");
            activeStatusLabel.setBackground(new Color(255, 180, 50));
            completeBtn.setEnabled(true);
            completeBtn.setBackground(primaryPurple); // Light up the complete button!
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
        bubble.setMaximumSize(new Dimension(800, 60));

        JLabel senderLbl = new JLabel(sender);
        senderLbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        senderLbl.setForeground(primaryPurple);

        JLabel msgLbl = new JLabel(message);
        msgLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        msgLbl.setForeground(Color.DARK_GRAY);

        bubble.add(senderLbl);
        bubble.add(Box.createVerticalStrut(5));
        bubble.add(msgLbl);

        // Wrapper to align left or right (Currently both left-aligned per mockup)
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(bubble);
        return wrapper;
    }
}