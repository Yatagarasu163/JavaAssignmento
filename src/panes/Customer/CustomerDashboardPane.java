package panes.Customer;

import components.FloatingButton;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CustomerDashboardPane extends JPanel {

    private final Color primaryPurple = new Color(128, 128, 255);

    public CustomerDashboardPane(String customerName) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 245, 250));
        setBorder(new EmptyBorder(40, 40, 40, 40));

        // --- PART 1: WELCOME MESSAGE ---
        JLabel welcomeLabel = new JLabel("Welcome, " + customerName);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        welcomeLabel.setForeground(primaryPurple);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- PART 2: MY VEHICLES ---
        JLabel vehiclesTitle = new JLabel("My Vehicles");
        vehiclesTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        vehiclesTitle.setForeground(primaryPurple);
        vehiclesTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel vehiclesContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        vehiclesContainer.setBackground(getBackground());
        vehiclesContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        vehiclesContainer.add(createVehicleCard("ABC 1234", "Proton Saga"));
        vehiclesContainer.add(createVehicleCard("VFQ 7574", "Perodua Myvi"));

        // --- PART 3: APPOINTMENTS (Now with Headers & Scrolling) ---
        JLabel appointmentsTitle = new JLabel("Appointments");
        appointmentsTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        appointmentsTitle.setForeground(primaryPurple);
        appointmentsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 3A. Create the Header Row (Fixed at the top)
        JPanel headerRow = new JPanel(new GridLayout(1, 4, 10, 0));
        headerRow.setBackground(Color.WHITE);
        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        headerRow.add(createHeaderLabel("Appointment Status"));
        headerRow.add(createHeaderLabel("Car Plate Number"));
        headerRow.add(createHeaderLabel("Service Type"));
        headerRow.add(createHeaderLabel("Action"));

        // 3B. Create the panel that holds the actual data rows
        JPanel appointmentsTable = new JPanel();
        appointmentsTable.setLayout(new BoxLayout(appointmentsTable, BoxLayout.Y_AXIS));
        appointmentsTable.setBackground(Color.WHITE);

        // Add enough rows so you can actually test the scrolling!
        appointmentsTable.add(createAppointmentRow("In Queue", new Color(255, 200, 200), "ABC 1234", "Normal Service"));
        appointmentsTable.add(Box.createVerticalStrut(10));
        appointmentsTable.add(createAppointmentRow("In Service", new Color(255, 230, 150), "VFQ 7574", "Major Service"));
        appointmentsTable.add(Box.createVerticalStrut(10));
        appointmentsTable.add(createAppointmentRow("Completed", new Color(180, 255, 180), "ABC 1234", "Major Service"));
        appointmentsTable.add(Box.createVerticalStrut(10));
        appointmentsTable.add(createAppointmentRow("Completed", new Color(180, 255, 180), "VFQ 7574", "Normal Service"));
        appointmentsTable.add(Box.createVerticalStrut(10));
        appointmentsTable.add(createAppointmentRow("Completed", new Color(180, 255, 180), "ABC 1234", "Normal Service"));
        appointmentsTable.add(Box.createVerticalStrut(10));
        appointmentsTable.add(createAppointmentRow("Completed", new Color(180, 255, 180), "VFQ 7574", "Major Service"));

        // 3C. Wrap the data rows in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Removes the ugly default scroll border
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Makes mouse-wheel scrolling smoother

        // 3D. Create an outer container to hold the header AND the scroll pane together
        JPanel tableContainer = new JPanel();
        tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableContainer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primaryPurple, 3, true), // Purple border around everything
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Assemble the table
        tableContainer.add(headerRow);
        tableContainer.add(Box.createVerticalStrut(10)); // Gap between header and rows
        tableContainer.add(scrollPane);

        // --- ASSEMBLE THE PAGE ---
        add(welcomeLabel);
        add(Box.createVerticalStrut(40));

        add(vehiclesTitle);
        add(Box.createVerticalStrut(15));
        add(vehiclesContainer);
        add(Box.createVerticalStrut(40));

        add(appointmentsTitle);
        add(Box.createVerticalStrut(15));
        add(tableContainer); // Add the new combined table container here
    }

    // --- HELPER METHOD: Creates a Header Label ---
    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(Color.GRAY);
        return label;
    }

    // --- HELPER METHOD: Creates a Vehicle Card ---
    private JPanel createVehicleCard(String plate, String model) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(200, 120));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(25, 20, 25, 20)
        ));

        JLabel plateLabel = new JLabel(plate);
        plateLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        plateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel modelLabel = new JLabel(model);
        modelLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        modelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(plateLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(modelLabel);
        card.add(Box.createVerticalGlue());

        return card;
    }

    // --- HELPER METHOD: Creates an Appointment Row ---
    private JPanel createAppointmentRow(String statusText, Color statusColor, String plate, String serviceType) {
        JPanel row = new JPanel(new GridLayout(1, 4, 10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Column 1: Status Pill
        JLabel statusLabel = new JLabel(statusText, SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(statusColor);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        JPanel statusContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 7)); // Center the pill vertically
        statusContainer.setBackground(Color.WHITE);
        statusLabel.setPreferredSize(new Dimension(100, 25));
        statusContainer.add(statusLabel);

        // Column 2: Plate Number
        JLabel plateLabel = new JLabel(plate, SwingConstants.CENTER);
        plateLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Column 3: Service Type
        JLabel serviceLabel = new JLabel(serviceType, SwingConstants.CENTER);
        serviceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Column 4: Comments Button
        FloatingButton commentBtn = new FloatingButton("Comments \u270E", 15);
        commentBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        commentBtn.setPreferredSize(new Dimension(110, 30));

        commentBtn.addActionListener(e -> {
            System.out.println("Navigating to comments for appointment: " + plate);
        });

        JPanel btnContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        btnContainer.setBackground(Color.WHITE);
        btnContainer.add(commentBtn);

        row.add(statusContainer);
        row.add(plateLabel);
        row.add(serviceLabel);
        row.add(btnContainer);

        return row;
    }
}