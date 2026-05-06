package src.panes.Customer;

import IO.FileHandler;
import config.UIConfig;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class CustomerAppointmentDetailsPane extends JPanel {

    private final Color primaryPurple = UIConfig.mainBackground;
    private final Color bgColor = UIConfig.whiteBackground2;

    private JPanel cardsContainer;
    private CardLayout cardLayout;

    private String loggedInCustomerID;
    private String appointmentId;

    private String status = "Unknown";
    private String description = "";
    private String carModel = "Unknown Vehicle";
    private String plateNo = "Unknown Plate";
    private String custName = "Customer";
    private String contactNo = "N/A";
    private String email = "N/A";
    private String techName = "Pending Technician";
    private String staffName = "Pending Staff";
    private String techId = "";
    private String staffId = "";
    private String[] assignedTaskIds = new String[0];

    private JPanel chatPanel;
    private JTextArea descArea;

    public CustomerAppointmentDetailsPane(JPanel cardsContainer, CardLayout cardLayout, String cusID, String appId) {
        this.cardsContainer = cardsContainer;
        this.cardLayout = cardLayout;
        this.loggedInCustomerID = cusID;
        this.appointmentId = appId;

        setLayout(new BorderLayout());
        setBackground(bgColor);

        loadAppointmentData();

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(bgColor);
        mainContent.setBorder(new EmptyBorder(30, 40, 40, 40));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setBackground(bgColor);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton backBtn = new JButton("← Back");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        backBtn.setForeground(primaryPurple);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> cardLayout.show(cardsContainer, "DASHBOARD"));
        topPanel.add(backBtn);
        mainContent.add(topPanel);
        mainContent.add(Box.createVerticalStrut(20));

        JLabel section1Title = new JLabel("Maintenance Feedback: " + carModel);
        section1Title.setFont(new Font("SansSerif", Font.BOLD, 18));
        section1Title.setForeground(primaryPurple);
        section1Title.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainContent.add(section1Title);
        mainContent.add(Box.createVerticalStrut(15));


        JPanel infoGrid = new JPanel(new GridLayout(2, 4, 10, 10));
        infoGrid.setBackground(bgColor);
        infoGrid.setMaximumSize(new Dimension(650, 60));
        infoGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoGrid.add(createHeaderLabel("Name"));       infoGrid.add(createValueLabel(custName));
        infoGrid.add(createHeaderLabel("Contact No."));infoGrid.add(createValueLabel(contactNo));
        infoGrid.add(createHeaderLabel("Plat Number"));infoGrid.add(createValueLabel(plateNo));
        infoGrid.add(createHeaderLabel("Email"));      infoGrid.add(createValueLabel(email));

        mainContent.add(infoGrid);
        mainContent.add(Box.createVerticalStrut(15));

        descArea = new JTextArea(description);
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descArea.setForeground(Color.DARK_GRAY);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primaryPurple, 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));

        if (status.equalsIgnoreCase("In Queue")) {
            descArea.setEditable(true);
            descArea.setBackground(Color.WHITE);
            descArea.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    saveInitialDescription(descArea.getText().trim());
                }
            });
        } else {
            descArea.setEditable(false);
            descArea.setBackground(UIConfig.whiteBackground);
        }

        JPanel descWrapper = new JPanel(new BorderLayout());
        descWrapper.setOpaque(false);
        descWrapper.setMaximumSize(new Dimension(650, 100));
        descWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        descWrapper.add(descArea, BorderLayout.CENTER);

        mainContent.add(descWrapper);
        mainContent.add(Box.createVerticalStrut(30));

        JLabel section2Title = new JLabel("Assigned Tasks");
        section2Title.setFont(new Font("SansSerif", Font.BOLD, 18));
        section2Title.setForeground(primaryPurple);
        section2Title.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainContent.add(section2Title);
        mainContent.add(Box.createVerticalStrut(10));

        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        tasksPanel.setOpaque(false);
        tasksPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        List<String[]> priceList = FileHandler.read("Price.txt");
        if (assignedTaskIds.length > 0 && !assignedTaskIds[0].isEmpty()) {
            for (String taskId : assignedTaskIds) {
                String cleanId = taskId.trim();
                for (String[] priceRow : priceList) {
                    if (priceRow.length >= 2 && priceRow[0].equals(cleanId)) {
                        JLabel taskLbl = new JLabel("\u2022 " + priceRow[1]);
                        taskLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
                        taskLbl.setForeground(Color.GRAY);
                        taskLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
                        tasksPanel.add(taskLbl);
                        tasksPanel.add(Box.createVerticalStrut(5));
                        break;
                    }
                }
            }
        } else {
            JLabel noTaskLbl = new JLabel("Pending diagnostics...");
            noTaskLbl.setFont(new Font("SansSerif", Font.ITALIC, 14));
            noTaskLbl.setForeground(Color.GRAY);
            noTaskLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            tasksPanel.add(noTaskLbl);
        }
        mainContent.add(tasksPanel);
        mainContent.add(Box.createVerticalStrut(30));

        JLabel section3Title = new JLabel("Comment & History");
        section3Title.setFont(new Font("SansSerif", Font.BOLD, 18));
        section3Title.setForeground(primaryPurple);
        section3Title.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainContent.add(section3Title);
        mainContent.add(Box.createVerticalStrut(10));

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(Color.WHITE);

        JScrollPane chatScroll = new JScrollPane(chatPanel);
        chatScroll.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 255), 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        chatScroll.getViewport().setBackground(Color.WHITE);
        chatScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatScroll.setPreferredSize(new Dimension(800, 180));
        chatScroll.setMaximumSize(new Dimension(650, 180));
        chatScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainContent.add(chatScroll);

        if (!status.equalsIgnoreCase("Completed")) {
            JPanel inputContainer = new JPanel(new BorderLayout());
            inputContainer.setOpaque(false);
            inputContainer.setMaximumSize(new Dimension(650, 40));
            inputContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

            JTextField inputField = new JTextField("Type here...");
            inputField.setOpaque(false);
            inputField.setForeground(Color.GRAY);
            inputField.setBorder(BorderFactory.createCompoundBorder(
                    new MatteBorder(0, 0, 1, 0, Color.GRAY),
                    new EmptyBorder(5, 5, 5, 5)
            ));

            inputField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if(inputField.getText().equals("Type here...")) {
                        inputField.setText("");
                        inputField.setForeground(Color.DARK_GRAY);
                    }
                }
            });

            JLabel sendIcon = new JLabel("➤");
            sendIcon.setFont(new Font("SansSerif", Font.PLAIN, 18));
            sendIcon.setForeground(Color.GRAY);
            sendIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            sendIcon.setBorder(new EmptyBorder(0,10,0,10));

            sendIcon.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    String msg = inputField.getText().trim();
                    if (!msg.isEmpty() && !msg.equals("Type here...")) {
                        saveNewComment(msg);
                        inputField.setText("");
                    }
                }
            });

            inputContainer.add(inputField, BorderLayout.CENTER);
            inputContainer.add(sendIcon, BorderLayout.EAST);
            mainContent.add(Box.createVerticalStrut(5));
            mainContent.add(inputContainer);
        }
        mainContent.add(Box.createVerticalStrut(30));

        if (status.equalsIgnoreCase("Completed")) {
            JLabel section4Title = new JLabel("Rating & Review");
            section4Title.setFont(new Font("SansSerif", Font.BOLD, 18));
            section4Title.setForeground(primaryPurple);
            section4Title.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainContent.add(section4Title);
            mainContent.add(Box.createVerticalStrut(10));

            JPanel ratingRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
            ratingRow.setOpaque(false);
            ratingRow.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel techRatingBox = new JPanel(new GridLayout(2, 1, 0, 5));
            techRatingBox.setOpaque(false);
            JLabel techLbl = new JLabel("Technician: " + techName);
            techLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
            techLbl.setForeground(primaryPurple);
            JComboBox<String> techCombo = new JComboBox<>(new String[]{"5 Stars", "4 Stars", "3 Stars", "2 Stars", "1 Star"});
            techRatingBox.add(techLbl);
            techRatingBox.add(techCombo);

            JPanel staffRatingBox = new JPanel(new GridLayout(2, 1, 0, 5));
            staffRatingBox.setOpaque(false);
            JLabel staffLbl = new JLabel("Counter Staff: " + staffName);
            staffLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
            staffLbl.setForeground(primaryPurple);
            JComboBox<String> staffCombo = new JComboBox<>(new String[]{"5 Stars", "4 Stars", "3 Stars", "2 Stars", "1 Star"});
            staffRatingBox.add(staffLbl);
            staffRatingBox.add(staffCombo);

            ratingRow.add(techRatingBox);
            ratingRow.add(staffRatingBox);

            mainContent.add(ratingRow);
            mainContent.add(Box.createVerticalStrut(15));

            JTextField reviewField = new JTextField("Leave your review here...");
            reviewField.setMaximumSize(new Dimension(650, 40));
            reviewField.setAlignmentX(Component.LEFT_ALIGNMENT);
            reviewField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(primaryPurple, 1, true),
                    new EmptyBorder(5, 10, 5, 10)
            ));

            reviewField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if(reviewField.getText().equals("Leave your review here...")) reviewField.setText("");
                }
            });
            mainContent.add(reviewField);
            mainContent.add(Box.createVerticalStrut(15));

            JButton saveReviewBtn = new JButton("Save");
            saveReviewBtn.setBackground(new Color(200, 200, 255));
            saveReviewBtn.setForeground(Color.WHITE);
            saveReviewBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
            saveReviewBtn.setMaximumSize(new Dimension(650, 40));
            saveReviewBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            saveReviewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            saveReviewBtn.addActionListener(e -> {
                String reviewText = reviewField.getText().trim();
                String tRate = ((String) techCombo.getSelectedItem()).substring(0,1);
                String sRate = ((String) staffCombo.getSelectedItem()).substring(0,1);

                saveReviewToDatabase(tRate, sRate, reviewText);
                saveReviewBtn.setText("Review Submitted!");
                saveReviewBtn.setEnabled(false);
            });

            mainContent.add(saveReviewBtn);
        }

        mainContent.add(Box.createVerticalGlue());

        JScrollPane mainScroll = new JScrollPane(mainContent);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        mainScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(mainScroll, BorderLayout.CENTER);

        refreshChatUI();
    }

    private void loadAppointmentData() {
        List<String[]> usersList = FileHandler.read("Users.txt");
        for (String[] u : usersList) {
            if (u[0].equals(loggedInCustomerID) && u.length >= 7) {
                custName = u[1] + " " + u[2];
                email = u[5];
                contactNo = u[6];
            }
        }

        List<String[]> apptList = FileHandler.read("Appointment.txt");
        String vehId = "";

        for (String[] row : apptList) {
            if (row.length > 0 && row[0].equals(appointmentId)) {
                description = row.length > 1 ? row[1] : "";
                if (row.length > 3 && !row[3].isEmpty()) assignedTaskIds = row[3].split(",");
                status = row.length > 4 ? row[4] : "Unknown";
                techId = row.length > 6 ? row[6] : "";
                staffId = row.length > 8 ? row[8] : "";
                vehId = row.length > 9 ? row[9] : "";
                break;
            }
        }

        List<String[]> vehicleList = FileHandler.read("Vehicle.txt");
        for (String[] v : vehicleList) {
            if (v.length > 0 && v[0].equals(vehId)) {
                plateNo = v[1];
                carModel = v[2];
                break;
            }
        }

        for (String[] u : usersList) {
            if (u.length > 0) {
                if (u[0].equals(techId)) techName = u[1] + " " + u[2];
                if (u[0].equals(staffId)) staffName = u[1] + " " + u[2];
            }
        }
    }

    private void saveInitialDescription(String newDesc) {
        List<String[]> apptList = FileHandler.read("Appointment.txt");
        for (int i = 0; i < apptList.size(); i++) {
            if (apptList.get(i).length > 0 && apptList.get(i)[0].equals(appointmentId)) {
                apptList.get(i)[1] = newDesc;
                break;
            }
        }
        FileHandler.write("Appointment.txt", apptList, false);
    }

    private void saveNewComment(String msg) {
        String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String newId = generateId("Comments.txt", "C");

        String[] newComment = {newId, msg, "Customer", formattedDateTime, appointmentId};
        List<String[]> singleRow = new ArrayList<>();
        singleRow.add(newComment);

        FileHandler.write("Comments.txt", singleRow, true);
        refreshChatUI();
    }

    private void saveReviewToDatabase(String techStars, String staffStars, String review) {
        String newId = generateId("Rating_Review.txt", "R");

        String[] newFeedback = {newId, review, techStars, staffStars, techId, staffId, appointmentId};
        List<String[]> singleRow = new ArrayList<>();
        singleRow.add(newFeedback);

        FileHandler.write("Rating_Review.txt", singleRow, true);
    }

    private void refreshChatUI() {
        chatPanel.removeAll();
        List<String[]> allComments = FileHandler.read("Comments.txt");

        boolean hasComments = false;
        if (allComments != null) {
            for (String[] msg : allComments) {
                if (msg.length >= 5 && msg[4].equals(appointmentId)) {
                    hasComments = true;
                    boolean isCustomer = msg[2].equalsIgnoreCase("Customer");
                    chatPanel.add(createChatBubble(msg[2], msg[1], isCustomer));
                    chatPanel.add(Box.createVerticalStrut(10));
                }
            }
        }

        if (!hasComments) {
            JLabel emptyLbl = new JLabel("  No comments yet...");
            emptyLbl.setForeground(Color.LIGHT_GRAY);
            chatPanel.add(emptyLbl);
        }

        chatPanel.revalidate();
        chatPanel.repaint();
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
                g2.setColor(isCustomer ? UIConfig.whiteBackground3 : UIConfig.whiteBackground);
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
        wrapper.setBackground(Color.WHITE);
        return wrapper;
    }

    private String generateId(String filename, String prefix) {
        List<String[]> dataList = FileHandler.read(filename);
        if (dataList == null || dataList.isEmpty()) {
            return prefix + "000001";
        }

        int highestNumber = 0;
        for (String[] row : dataList) {
            if (row.length > 0 && row[0].startsWith(prefix)) {
                try {
                    String numberPart = row[0].substring(prefix.length());
                    int currentNumber = Integer.parseInt(numberPart);
                    if (currentNumber > highestNumber) {
                        highestNumber = currentNumber;
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
        highestNumber++;
        String formattedNumber = String.format("%06d", highestNumber);
        return prefix + formattedNumber;
    }
}