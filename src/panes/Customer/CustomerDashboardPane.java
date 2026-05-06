package panes.Customer;

import components.FloatingButton;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import IO.FileHandler;
import java.util.List;
import config.UIConfig;

public class CustomerDashboardPane extends JPanel {

    private final Color primaryPurple = UIConfig.mainBackground;
    private String loggedInCustomerID;

    public CustomerDashboardPane(String cusID) {
        this.loggedInCustomerID = cusID;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(UIConfig.whiteBackground);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        String customerName = "Customer";
        List<String[]> currentUserData = FileHandler.read("CurrentUser.txt");

        if (!currentUserData.isEmpty() && currentUserData.get(0).length > 1) {
            customerName = currentUserData.get(0)[3].trim();
        }

        JLabel welcomeLabel = new JLabel("Welcome, " + customerName);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        welcomeLabel.setForeground(primaryPurple);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel vehiclesTitle = new JLabel("My Vehicles");
        vehiclesTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        vehiclesTitle.setForeground(primaryPurple);
        vehiclesTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel vehiclesContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        vehiclesContainer.setBackground(getBackground());
        vehiclesContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        List<String[]> vehicleList = FileHandler.read("Vehicle.txt");
        int vehicleCount = 0;

        for (String[] carRow: vehicleList){
            if (carRow.length >= 5){
                if (carRow[4].equals(loggedInCustomerID)){
                    if (vehicleCount < 3) {
                        String plateNumber = carRow[1];
                        String carModel = carRow[2];

                        vehiclesContainer.add(createVehicleCard(plateNumber, carModel));
                        vehicleCount++;
                    }
                }
            }
        }

        if (vehicleCount == 0) {
            JLabel noVehiclesLabel = new JLabel("No vehicles registered yet.");
            noVehiclesLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
            noVehiclesLabel.setForeground(Color.GRAY);
            vehiclesContainer.add(noVehiclesLabel);
        }

        JLabel appointmentsTitle = new JLabel("Appointments");
        appointmentsTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        appointmentsTitle.setForeground(primaryPurple);
        appointmentsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel headerRow = new JPanel(new GridLayout(1, 4, 10, 0));
        headerRow.setBackground(Color.WHITE);
        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        headerRow.add(createHeaderLabel("Appointment Status"));
        headerRow.add(createHeaderLabel("Car Plate Number"));
        headerRow.add(createHeaderLabel("Service Type"));
        headerRow.add(createHeaderLabel("Action"));

        JPanel appointmentsTable = new JPanel();
        appointmentsTable.setLayout(new BoxLayout(appointmentsTable, BoxLayout.Y_AXIS));
        appointmentsTable.setBackground(Color.WHITE);

        List<String[]> apptList = FileHandler.read("Appointment.txt");
        int apptCount = 0;

        for (String[] apptRow : apptList) {
            if (apptRow.length >= 10) {
                if (apptRow[7].equals(loggedInCustomerID)) {

                    String serviceType = apptRow[2];
                    String status = apptRow[4];
                    String vehicleId = apptRow[9];
                    String displayPlate = vehicleId;

                    for (String[] vRow : vehicleList) {
                        if (vRow[0].equals(vehicleId)) {
                            displayPlate = vRow[1];
                            break;
                        }
                    }

                    Color statusColor = Color.LIGHT_GRAY;
                    if (status.equalsIgnoreCase("In Queue")) {
                        statusColor = UIConfig.inQueueStatus;
                    } else if (status.equalsIgnoreCase("In Service")) {
                        statusColor = UIConfig.inServiceStatus;
                    } else if (status.equalsIgnoreCase("Completed")) {
                        statusColor = UIConfig.completedStatus;
                    }

                    appointmentsTable.add(createAppointmentRow(status, statusColor, displayPlate, serviceType));
                    appointmentsTable.add(Box.createVerticalStrut(10));
                    apptCount++;
                }
            }
        }

        if (apptCount == 0) {
            JLabel noApptLabel = new JLabel("No appointments scheduled.");
            noApptLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
            noApptLabel.setForeground(Color.GRAY);
            appointmentsTable.add(noApptLabel);
        }

        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel tableContainer = new JPanel();
        tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableContainer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primaryPurple, 3, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        tableContainer.add(headerRow);
        tableContainer.add(Box.createVerticalStrut(10));
        tableContainer.add(scrollPane);

        add(welcomeLabel);
        add(Box.createVerticalStrut(40));

        add(vehiclesTitle);
        add(Box.createVerticalStrut(15));
        add(vehiclesContainer);
        add(Box.createVerticalStrut(40));

        add(appointmentsTitle);
        add(Box.createVerticalStrut(15));
        add(tableContainer);
    }

    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(Color.GRAY);
        return label;
    }

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

    private JPanel createAppointmentRow(String statusText, Color statusColor, String plate, String serviceType) {
        JPanel row = new JPanel(new GridLayout(1, 4, 10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel statusLabel = new JLabel(statusText, SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(statusColor);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        JPanel statusContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 7)); // Center the pill vertically
        statusContainer.setBackground(Color.WHITE);
        statusLabel.setPreferredSize(new Dimension(100, 25));
        statusContainer.add(statusLabel);

        JLabel plateLabel = new JLabel(plate, SwingConstants.CENTER);
        plateLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel serviceLabel = new JLabel(serviceType, SwingConstants.CENTER);
        serviceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

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