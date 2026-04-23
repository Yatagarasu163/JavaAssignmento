package panes.Technician;

import javax.swing.*;

import IO.FileHandler;
import panes.SidebarPanel;
import java.awt.*;
import java.util.ArrayList;

public class TechnicianMainPane extends JFrame {
    public TechnicianMainPane(String UserID) {

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
        TechnicianDashboardPane TechnicianDashboard = new TechnicianDashboardPane("Chen Yi Hung");

        // Add the Dashboard to card container
        cardContainer.add(TechnicianDashboard, "Dashboard");

        // Add main container area to the center of the screen
        contentPane.add(cardContainer, BorderLayout.CENTER);

        // Display the Dashboard as a default view interface
        cardLayout.show(cardContainer, "Dashboard");


        // Technician Profile Page
        String[] TechnicianInfo = getTechnicianInfo(UserID);

        System.out.println(TechnicianInfo[0]);

        TechnicianProfilePane profilePane = new TechnicianProfilePane(
                TechnicianInfo[1],
                TechnicianInfo[0],
                TechnicianInfo[2],
                TechnicianInfo[3],
                TechnicianInfo[4],
                TechnicianInfo[5].replace("\"", "")
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

    public String[] getTechnicianInfo(String TechnicianID){
        ArrayList<String> TechnicianList = FileHandler.read("Technician.txt");

        for (String Technician:TechnicianList) {

            String[] values = Technician.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            if (values[0].equals(TechnicianID)) {
                return values;
            }

        }

        // The loop unable to find a match user information, return null
        return new String[] {"N/A", "Not Found",  "N/A",  "N/A", "N/A", "N/A"};
    }
}
