package src.panes.Technician;

import javax.swing.*;

import IO.FileHandler;
import src.panes.SidebarPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        // TODO: Update with current session user ID and User Name
        String[] TechnicianInfo = getTechnicianInfo();
        String fullName = TechnicianInfo[1] + " " + TechnicianInfo[2];
        panes.Technician.TechnicianDashboardPane TechnicianDashboard = new panes.Technician.TechnicianDashboardPane(fullName, TechnicianInfo[0]);

        // Add the Dashboard to card container
        cardContainer.add(TechnicianDashboard, "Dashboard");

        // Add main container area to the center of the screen
        contentPane.add(cardContainer, BorderLayout.CENTER);

        // Display the Dashboard as a default view interface
        cardLayout.show(cardContainer, "Dashboard");


        //TODO: Technician Profile Page (modify the data transfer)

        //String name, String id, String email, String date, String phone, String address
        TechnicianProfilePane profilePane = new TechnicianProfilePane(
                fullName,
                TechnicianInfo[0],
                TechnicianInfo[5],
                TechnicianInfo[9],
                TechnicianInfo[6],
                TechnicianInfo[8]
        );

        // Add into the main container card
        cardContainer.add(profilePane, "TechnicianProfile");

        //TODO: Technician Appointment Page (modify the data transfer)
        panes.Technician.TechnicianAppointmentPane appointmentPane = new panes.Technician.TechnicianAppointmentPane("TC123456");

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

    public String[] getTechnicianInfo() {

        List<String[]> currentUser = FileHandler.read("CurrentUser.txt");

        for (String[] Technician: currentUser){
            if (Technician[4].equals("Technician")){
                return Technician;
            }
        }

        return new String[] {"N/A", "Not Found", "N/A", "N/A", "N/A", "N/A"};
    }
}
