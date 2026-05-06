package src.panes.Customer;

import javax.swing.*;
import src.panes.SidebarPanel;
import java.awt.*;
import java.util.List;
import IO.FileHandler;

public class CustomerMainPane extends JFrame {
    private String loggedInUserID = "UNKNOWN";

    public CustomerMainPane() {
        setTitle("APU-ASC: Customer Portal");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        List<String[]> currentUserData = FileHandler.read("CurrentUser.txt");

        if (!currentUserData.isEmpty() && currentUserData.get(0).length > 0) {
            loggedInUserID = currentUserData.get(0)[0].trim();
        } else {
            JOptionPane.showMessageDialog(this, "Error reading user session. Please log in again.", "Session Error", JOptionPane.ERROR_MESSAGE);
        }

        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        SidebarPanel sidebarPanel = new SidebarPanel("Customer");

        JScrollPane sidePane = new JScrollPane(sidebarPanel);
        sidePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(sidePane, BorderLayout.WEST);

        JPanel cardsContainer = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardsContainer.setLayout(cardLayout);

        src.panes.Customer.CustomerDashboardPane dashboardPane = new src.panes.Customer.CustomerDashboardPane(cardsContainer, cardLayout, loggedInUserID);
        cardsContainer.add(dashboardPane, "DASHBOARD");

        contentPane.add(cardsContainer, BorderLayout.CENTER);

        cardLayout.show(cardsContainer, "DASHBOARD");

        src.panes.Customer.CustomerProfilePane profilePane = new src.panes.Customer.CustomerProfilePane(loggedInUserID);
        cardsContainer.add(profilePane, "PROFILE");

        src.panes.Customer.CustomerHistoryPane historyPane = new src.panes.Customer.CustomerHistoryPane(cardsContainer, cardLayout, loggedInUserID);
        cardsContainer.add(historyPane, "HISTORY");

        sidebarPanel.getHomeBtn().addActionListener(e -> {
            cardLayout.show(cardsContainer, "DASHBOARD");
            sidebarPanel.clearSelection();
        });

        sidebarPanel.getProfileBtn().addActionListener(e -> {
            cardLayout.show(cardsContainer, "PROFILE");
        });

        sidebarPanel.getHistoryBtn().addActionListener(e -> {
            cardLayout.show(cardsContainer, "HISTORY");
        });
    }
}