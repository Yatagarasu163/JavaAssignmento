package panes.Technician;

import javax.swing.*;

import IO.FileHandler;
import panes.SidebarPanel;
import java.awt.*;
import java.util.List;

public class TechnicianMainPane extends JFrame {
    private panes.Technician.TechnicianDashboardPane TechnicianDashboard;
    private panes.Technician.TechnicianAppointmentPane appointmentPane;
    private JPanel cardContainer;
    private CardLayout cardLayout;

    public TechnicianMainPane(String UserID) {

        // set Title and structure of Interface
        setTitle("APU-ASC");
        setSize(1000, 700);
        components.ProgramTerminator.enableSafeExit(this);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        SidebarPanel sidebarPanel = new SidebarPanel("Technician");
        JScrollPane sidePane = new JScrollPane(sidebarPanel);
        sidePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(sidePane, BorderLayout.WEST);

        cardContainer = new JPanel();
        cardLayout = new CardLayout();
        cardContainer.setLayout(cardLayout);
        contentPane.add(cardContainer, BorderLayout.CENTER);

        String[] TechnicianInfo = getTechnicianInfo();
        String fullName = TechnicianInfo[1] + " " + TechnicianInfo[2];

        // Technician Dashboard
        TechnicianDashboard = new panes.Technician.TechnicianDashboardPane(TechnicianInfo[3], TechnicianInfo[0], this);
        // Technician Appointment Pane
        appointmentPane = new panes.Technician.TechnicianAppointmentPane(TechnicianInfo[0]);
        // Technician Profile Page
        TechnicianProfilePane profilePane = new TechnicianProfilePane(fullName, TechnicianInfo[0], TechnicianInfo[5],
                TechnicianInfo[9], TechnicianInfo[6], TechnicianInfo[8]);

        // Addressing each name for pane section
        cardContainer.add(TechnicianDashboard, "Dashboard");
        cardContainer.add(profilePane, "TechnicianProfile");
        cardContainer.add(appointmentPane, "Appointment");

        cardLayout.show(cardContainer, "Dashboard");

        // Add each section to nav bar
        sidebarPanel.getHomeBtn().addActionListener(e -> {
            cardContainer.remove(TechnicianDashboard);
            TechnicianDashboard = new panes.Technician.TechnicianDashboardPane(TechnicianInfo[3], TechnicianInfo[0], this);
            cardContainer.add(TechnicianDashboard, "Dashboard");

            cardLayout.show(cardContainer, "Dashboard");
            sidebarPanel.clearSelection();
        });

        sidebarPanel.getTechProfileBtn().addActionListener(e ->{
            cardLayout.show(cardContainer, "TechnicianProfile");
        });

        sidebarPanel.getAppointmentBtn().addActionListener(e ->{
            cardLayout.show(cardContainer, "Appointment");
        });
    }

    // Enable navigation to specific vehicle appointment section
    public void navigateToAppointment(String plateNumber) {
        cardLayout.show(cardContainer, "Appointment");
        appointmentPane.openSpecificAppointment(plateNumber);
    }

    // get Technician Information
    public String[] getTechnicianInfo() {

        List<String[]> currentUser = FileHandler.read("CurrentUser.txt");

        for (String[] Technician : currentUser) {
            if (Technician[4].equals("Technician")) {
                return Technician;
            }
        }

        // ensure the ID part show Technician Not Found
        return new String[]{"N/A", "Not Found", "N/A", "N/A", "N/A", "N/A"};
    }
}

