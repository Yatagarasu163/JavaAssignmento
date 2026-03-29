package panes;

import components.FloatingButton;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TechnicianDashboardPane extends JPanel {

    private final Color primaryPurple = new Color(128, 128, 255);
    private final Color bgColor = new Color(248, 248, 250); // Slightly off-white background

    public TechnicianDashboardPane(String userName) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(bgColor);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        // --- PART 1: GREETING ---
        JLabel greetingLabel = new JLabel("Hi, " + userName);
        greetingLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        greetingLabel.setForeground(primaryPurple);
        greetingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- PART 2: SUMMARY CARDS ---
        JPanel cardsContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        cardsContainer.setBackground(bgColor);
        cardsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);


        // TODO: Replace the unicode strings with your actual SVG/PNG ImageIcons if desired
        cardsContainer.add(createSummaryCard("🚘", "Appointments", "5"));
        cardsContainer.add(createSummaryCard("✅", "Completed", "5"));
        cardsContainer.add(createSummaryCard("🕒", "Pending", "5"));

        // --- PART 3: SCROLLING TABLE ---
        // 3A. Header Row
        JPanel headerRow = new JPanel(new GridLayout(1, 5, 10, 0));
        headerRow.setBackground(Color.WHITE);
        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        headerRow.setBorder(new EmptyBorder(10, 20, 10, 20));

        headerRow.add(createHeaderLabel("Car Plate"));
        headerRow.add(createHeaderLabel("Car Model"));
        headerRow.add(createHeaderLabel("Client Name"));
        headerRow.add(createHeaderLabel("Status"));
        headerRow.add(new JLabel("")); // Empty header for the button column

        // 3B. Data Rows Container
        JPanel tableContent = new JPanel();
        tableContent.setLayout(new BoxLayout(tableContent, BoxLayout.Y_AXIS));
        tableContent.setBackground(Color.WHITE);

        // Adding dummy data matching the mockup
        tableContent.add(createTableRow("WIU 2395", "Myvi Perodua", "Ali Bin Supaman", "In Queue"));
        tableContent.add(createTableRow("WIU 2395", "Myvi Perodua", "Ali Bin Supaman", "In Service"));
        tableContent.add(createTableRow("WIU 2395", "Myvi Perodua", "Ali Bin Supaman", "Completed"));
        tableContent.add(createTableRow("WIU 2395", "Myvi Perodua", "Ali Bin Supaman", "In Queue"));
        tableContent.add(createTableRow("WIU 2395", "Myvi Perodua", "Ali Bin Supaman", "In Service"));
        tableContent.add(createTableRow("WIU 2395", "Myvi Perodua", "Ali Bin Supaman", "Completed"));

        // 3C. Wrap in ScrollPane
        JScrollPane scrollPane = new JScrollPane(tableContent);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // 3D. Outer Table Container (Holds Header + ScrollPane + Rounded Border)
        JPanel tableContainer = new JPanel();
        tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableContainer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 230), 1, true), // Soft border
                new EmptyBorder(10, 10, 10, 10)
        ));

        tableContainer.add(headerRow);
        tableContainer.add(Box.createVerticalStrut(5));
        tableContainer.add(scrollPane);

        // --- ASSEMBLE THE PAGE ---
        add(greetingLabel);
        add(Box.createVerticalStrut(40));
        add(cardsContainer);
        add(Box.createVerticalStrut(50));
        add(tableContainer);
    }

    // --- HELPER: Creates the top Summary Cards ---
    private JPanel createSummaryCard(String iconPlaceholder, String title, String count) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(200, 160));
        card.setBackground(Color.WHITE);
//        card.setBorder(new LineBorder(primaryPurple, 1, true));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primaryPurple, 1, true),
                new EmptyBorder(25, 20, 25, 20)

        ));

        JLabel iconLabel = new JLabel(iconPlaceholder);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 36));
        iconLabel.setForeground(primaryPurple);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(primaryPurple);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel countLabel = new JLabel(count);
        countLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
        countLabel.setForeground(primaryPurple);
        countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(countLabel);
        card.add(Box.createVerticalGlue());

        return card;
    }

    // --- HELPER: Creates a Header Label ---
    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.LEFT);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(Color.GRAY);
        return label;
    }

    // --- HELPER: Creates a Table Row ---
    private JPanel createTableRow(String plate, String model, String client, String status) {
        JPanel row = new JPanel(new GridLayout(1, 5, 10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 245))); // Light divider line

        // 1. Plate Label
        JLabel plateLabel = new JLabel(plate);
        plateLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        plateLabel.setBorder(new EmptyBorder(0, 20, 0, 0)); // Pad left

        // 2. Model Label
        JLabel modelLabel = new JLabel(model);
        modelLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        // 3. Client Label
        JLabel clientLabel = new JLabel(client);
        clientLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        // 4. Status Pill
        JPanel statusContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 15)); // Centers vertically
        statusContainer.setBackground(Color.WHITE);

        JLabel statusLabel = new JLabel(status, SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        statusLabel.setPreferredSize(new Dimension(100, 30));

        // Apply colors based on status matching the mockup
        switch (status) {
            case "In Queue":
                statusLabel.setBackground(new Color(255, 180, 180)); // Pastel Red
                statusLabel.setForeground(new Color(200, 0, 0));
                break;
            case "In Service":
                statusLabel.setBackground(new Color(255, 220, 130)); // Pastel Yellow
                statusLabel.setForeground(new Color(180, 120, 0));
                break;
            case "Completed":
                statusLabel.setBackground(new Color(180, 255, 180)); // Pastel Green
                statusLabel.setForeground(new Color(0, 150, 0));
                break;
        }
        statusContainer.add(statusLabel);

        // 5. Action Button Column
        JPanel btnContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 12));
        btnContainer.setBackground(Color.WHITE);

        FloatingButton viewBtn = new FloatingButton("View Details", 10);
        viewBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        viewBtn.setPreferredSize(new Dimension(110, 35));

        viewBtn.addActionListener(e -> {
            System.out.println("Navigating to view details for: " + plate);
            // TODO: Add CardLayout switching logic here
        });

        btnContainer.add(viewBtn);

        // Add everything to the row
        row.add(plateLabel);
        row.add(modelLabel);
        row.add(clientLabel);
        row.add(statusContainer);
        row.add(btnContainer);

        return row;
    }
}