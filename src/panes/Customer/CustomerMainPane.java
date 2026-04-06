package panes.Customer;

import javax.swing.*;
import panes.SidebarPanel;
import java.awt.*;

public class CustomerMainPane extends JFrame {
    public CustomerMainPane() {
        setTitle("APU-ASC: Customer Portal");
        setSize(1000, 700); // Slightly larger to fit the table nicely
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // 1. Set up the Sidebar
        SidebarPanel sidebarPanel = new SidebarPanel("Customer");

        JScrollPane sidePane = new JScrollPane(sidebarPanel);
        sidePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(sidePane, BorderLayout.WEST);

        // 2. Set up the CardLayout Container (The Main Content Area)
        JPanel cardsContainer = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardsContainer.setLayout(cardLayout);

        // 3. Initialize the Dashboard (Passing the name from the mockup)
        CustomerDashboardPane dashboardPane = new CustomerDashboardPane("Sum Ting Wong");

        // 4. Add the Dashboard to our "deck" of cards
        cardsContainer.add(dashboardPane, "DASHBOARD");

        // 5. Add the cards container to the center of the screen
        contentPane.add(cardsContainer, BorderLayout.CENTER);

        // 6. Force the window to show the Dashboard as the default view
        cardLayout.show(cardsContainer, "DASHBOARD");

        // Pass the starting data into the constructor
        CustomerProfilePane profilePane = new CustomerProfilePane(
                "Sum Ting Wong",
                "CT123456",
                "tingwong@gmail.com",
                "03 March 2026",
                "+60 12 - 345 6789",
                "10, Jalan Ayam Hutan, Kampung Bunga Baru, 47130, Puchong, Selangor"
        );

        // Add it to your card layout container
        cardsContainer.add(profilePane, "PROFILE");

        CustomerHistoryPane historyPane = new CustomerHistoryPane(cardsContainer, cardLayout);

        cardsContainer.add(historyPane, "HISTORY");

        CustomerPaymentDetailsPane paymentDetailsPane = new CustomerPaymentDetailsPane();
        cardsContainer.add(paymentDetailsPane, "PAYMENT_DETAILS");

        // TODO: In the future, add ActionListeners for your sidebarPanel buttons here
        sidebarPanel.getHomeBtn().addActionListener(e -> {
            cardLayout.show(cardsContainer, "DASHBOARD");

            // Clear the other buttons "White Background"
            sidebarPanel.clearSelection();
        });

        sidebarPanel.getProfileBtn().addActionListener(e -> {
            cardLayout.show(cardsContainer, "PROFILE");
        });

        sidebarPanel.getHistoryBtn().addActionListener(e -> {
            cardLayout.show(cardsContainer, "HISTORY");
        });

        paymentDetailsPane.getBackBtn().addActionListener(e -> {
            cardLayout.show(cardsContainer, "HISTORY");
        });
    }
}
