package panes;
import javax.swing.*;
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

        // TODO: In the future, add ActionListeners for your sidebarPanel buttons here
        // Example:
        // sidebarPanel.getMenuBtn().addActionListener(e -> cardLayout.show(cardsContainer, "MENU"));
    }
}