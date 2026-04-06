package panes.Techinician;

import javax.swing.*;
import panes.SidebarPanel;
import java.awt.*;

public class TechnicianMainPane extends JFrame {
    public TechnicianMainPane() {

        setTitle("APU-ASC: Technician Portal");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Set up Technician Sidebar
        SidebarPanel sidebarPanel = new SidebarPanel("Technician");

        JScrollPane sidePane = new JScrollPane(sidebarPanel);
        sidePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(sidePane, BorderLayout.WEST);

        // Set up the main container area
        JPanel cardContainer = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardContainer.setLayout(cardLayout);

        // Initialize the Technician Dashboard
        TechnicianDashboardPane TechnicianDashboard = new TechnicianDashboardPane("Hou Li Fa");

        // Add the Dashboard to card container
        cardContainer.add(TechnicianDashboard, "Dashboard");

        // Add main container area to the center of the screen
        contentPane.add(cardContainer, BorderLayout.CENTER);

        // Display the Dashboard as a default view interface
        cardLayout.show(cardContainer, "Dashboard");


        // Technician Profile Page
        TechnicianProfilePane profilePane = new TechnicianProfilePane(
                "Ho Li Fa",
                "TC123456",
                "LiFa@gmail.com",
                "03 February 2026",
                "+60 16-220 9658",
                "10, Jalan Ayam Hutan, Kampung Bunga Baru, 47130, Puchong, Selangor"
        );

        // Add into the main container card
        cardContainer.add(profilePane, "TechnicianProfile");

        // Technician Appointment Page
        TechnicianAppointmentPane appointmentPane = new TechnicianAppointmentPane();

        // Add appointment pane into the container card
        cardContainer.add(appointmentPane, "Appointment");

        sidebarPanel.getHomeBtn().addActionListener(e -> {
            cardLayout.show(cardContainer, "Dashboard");

            // remove white hover button when home button is clicked
            sidebarPanel.clearSelection();
        });

        // Execution of profile page when "My Profile" option is clicked
        sidebarPanel.getTechProfileBtn().addActionListener(e ->{
            cardLayout.show(cardContainer, "TechnicianProfile");
        });

        // Execution of Appointment page when "Appointment" option is clicked
        sidebarPanel.getAppointmentBtn().addActionListener(e ->{
            cardLayout.show(cardContainer, "Appointment");
        });



    }
}
