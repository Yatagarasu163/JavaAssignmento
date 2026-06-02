package panes.Technician;

import IO.FileHandler;
import components.FloatingButton;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.util.ArrayList;

public class TechnicianDashboardPane extends JPanel {

    private final Color primaryPurple = new Color(128, 128, 255);
    private final Color bgColor = new Color(248, 248, 250);
    private TechnicianMainPane mainController;

    public TechnicianDashboardPane(String username, String UserID, TechnicianMainPane mainController) {
        this.mainController = mainController;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(bgColor);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel greetingLabel = new JLabel("Hi, " + username);
        greetingLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        greetingLabel.setForeground(primaryPurple);
        greetingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel cardsContainer = new JPanel();
        cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.X_AXIS));
        cardsContainer.setBackground(bgColor);
        cardsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Get Today Appointment Details
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate =  today.format(formatter);
        List<String[]> TodayAppointment = getTodayAppointments(UserID, formattedDate);

        // Calculate Today Appointment Amount
        long AppointmentQuan = TodayAppointment.size();
        // Calculate Completed Appointment
        long CompletedTask = TodayAppointment.stream().filter(row -> row[3].equals("Completed")).count();
        // Calculate Pending Appointment
        long PendingTask = AppointmentQuan - CompletedTask;

        // Create card for different appointment amount display
        cardsContainer.add(createSummaryCard("🚘", "Appointments", String.valueOf(AppointmentQuan)));
        cardsContainer.add(createSummaryCard("✅", "Completed", String.valueOf(CompletedTask)));
        cardsContainer.add(createSummaryCard("🕒", "Pending", String.valueOf(PendingTask)));


        // Define the Column Structure
        JPanel headerRow = new JPanel(new GridLayout(1, 5, 10, 0));
        headerRow.setBackground(Color.WHITE);
        headerRow.setMaximumSize(new Dimension(890, 40));
        headerRow.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Define the Table Column Name and amount
        headerRow.add(createHeaderLabel("Car Plate"));
        headerRow.add(createHeaderLabel("Car Model"));
        headerRow.add(createHeaderLabel("Client Name"));
        headerRow.add(createHeaderLabel("Status"));
        headerRow.add(new JLabel(""));

        // Define the Table Row
        JPanel tableContent = new JPanel();
        tableContent.setLayout(new BoxLayout(tableContent, BoxLayout.Y_AXIS));
        tableContent.setBackground(Color.WHITE);

        // Add Each Appointment Details to the Table Row
        for (String[] Appointments: TodayAppointment){
            tableContent.add(createTableRow(
                    Appointments[0],
                    Appointments[1],
                    Appointments[2],
                    Appointments[3],
                    UserID
            ));
        }

        // Make the Table able to be Scroll when Table exceed 5 Rows
        JScrollPane scrollPane = new JScrollPane(tableContent);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        scrollPane.setPreferredSize(new Dimension(890, 310));
        scrollPane.setMaximumSize(new Dimension(890, 310));

        // define the container of the table
        JPanel tableContainer = new JPanel();
        tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableContainer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 230), 1, true), // Soft border
                new EmptyBorder(10, 10, 10, 10)
        ));

        tableContainer.setMaximumSize(new Dimension(920, 400));
        tableContainer.setPreferredSize(new Dimension(920   , 400));

        // Assemble every component of the Table
        tableContainer.add(headerRow);
        tableContainer.add(Box.createVerticalStrut(5));
        tableContainer.add(scrollPane);

        // Assemble every component of the dashboard interface
        add(greetingLabel);
        add(Box.createVerticalStrut(30));
        add(cardsContainer);
        add(Box.createVerticalStrut(20));
        add(tableContainer);
        add(Box.createVerticalGlue());
    }

    private JPanel createSummaryCard(String iconPlaceholder, String title, String count) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS)); // card shape
        card.setPreferredSize(new Dimension(300, 220)); // set the size of card
        card.setMaximumSize(new Dimension(300, 220)); // set the size of the card can go
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder( // set border color, tickness, and margin
                new LineBorder(primaryPurple, 1, true),
                new EmptyBorder(25, 20, 25, 20)

        ));

        // icon size and location
        JLabel iconLabel = new JLabel(iconPlaceholder);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 36));
        iconLabel.setForeground(primaryPurple);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // icon title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(primaryPurple);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // icon integer count
        JLabel countLabel = new JLabel(count);
        countLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
        countLabel.setForeground(primaryPurple);
        countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // assemble the design and customization
        card.add(Box.createVerticalGlue());
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(countLabel);
        card.add(Box.createVerticalGlue());

        return card;
    }

    // customize the column display
    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.LEFT);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(Color.GRAY);
        return label;
    }

    // Customize the table row design
    private JPanel createTableRow(String plate, String model, String client, String status, String userID) {
        JPanel row = new JPanel(new GridLayout(1, 5, 10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(890, 60)); // max size of row
        row.setPreferredSize(new Dimension(890, 60));// the display row size
        row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 245))); // Light divider line

        // Vehicle Plate
        JLabel plateLabel = new JLabel(plate);
        plateLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        plateLabel.setBorder(new EmptyBorder(0, 20, 0, 0)); // Pad left

        // Vehicle Model
        JLabel modelLabel = new JLabel(model);
        modelLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Client Name
        JLabel clientLabel = new JLabel(client);
        clientLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Appointment Status
        JPanel statusContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 15)); // Centers vertically
        statusContainer.setBackground(Color.WHITE);

        // Appointment Status Indicator
        JLabel statusLabel = new JLabel(status, SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        statusLabel.setPreferredSize(new Dimension(100, 30));

        // color indicator for each appointment status
        switch (status) {
            case "In Queue":
                statusLabel.setBackground(new Color(255, 180, 180)); // Red
                statusLabel.setForeground(new Color(200, 0, 0));
                break;
            case "In Service":
                statusLabel.setBackground(new Color(255, 220, 130)); // Yellow
                statusLabel.setForeground(new Color(180, 120, 0));
                break;
            case "Completed":
                statusLabel.setBackground(new Color(180, 255, 180)); // Green
                statusLabel.setForeground(new Color(0, 150, 0));
                break;
        }
        statusContainer.add(statusLabel);

        // Specific Appointment Navigation Button
        JPanel btnContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 12));
        btnContainer.setBackground(Color.WHITE);

        // Navigation Button Style Configuration
        FloatingButton viewBtn = new FloatingButton("View Details", 10);
        viewBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        viewBtn.setPreferredSize(new Dimension(110, 35));

        viewBtn.addActionListener(e -> {
            System.out.println("Navigating to view details for: " + plate);
            mainController.navigateToAppointment(plate);
        });

        btnContainer.add(viewBtn);

        // Assemble each component for table row
        row.add(plateLabel);
        row.add(modelLabel);
        row.add(clientLabel);
        row.add(statusContainer);
        row.add(btnContainer);

        return row;
    }

    // get today appointment details
    public static List<String[]> getTodayAppointments(String userId, String appointmentDate){
        List<String[]> appointmentList = FileHandler.read("Appointment.txt");
        List<String[]> userList = FileHandler.read("Users.txt");
        List<String[]> vehicleList = FileHandler.read("Vehicle.txt");
        List<String[]> todayAppointments = new ArrayList<>();

        // get today appointment table row details, each row fill by the value ID need to be fetched
        for (String[] appointment: appointmentList){
            if (appointment[6].equals(userId) & appointment[5].equals(appointmentDate)) {
                String[] availableAppointments = new String[] {
                        appointment[9],
                        appointment[9],
                        appointment[7],
                        appointment[4]
                };
                todayAppointments.add(availableAppointments);
            }
        }

        // get the vehicle plate number and model info
        for (String[] vehicle: vehicleList){
            for(String[] appointments: todayAppointments){
                if (vehicle[0].equals(appointments[0])){
                    appointments[0] = vehicle[1];
                    appointments[1] = vehicle[2];
                }
            }
        }

        // get user full name
        for (String[] user: userList){
            for (String[] appointments: todayAppointments){
                if (user[0].equals(appointments[2])){
                    String fullName = user[1] + user[2];
                    appointments[2] = fullName;
                }
            }
        }
        return todayAppointments;
    }


}
