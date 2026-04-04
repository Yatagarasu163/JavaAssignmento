package panes.CounterStaff;

import javax.swing.*;


import java.awt.*;

import panes.SidebarPanel;

public class CounterStaffMainPane extends JFrame{

    public CounterStaffMainPane() {
        setTitle("Counter Staff");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        SidebarPanel sidebarPanel = new SidebarPanel("Counter Staff");

        JPanel mainPane = new JPanel();
        CardLayout cardLayout = new CardLayout();
        mainPane.setLayout(cardLayout);

        CounterStaffDashboardPane dashboardPane = new CounterStaffDashboardPane();

        JScrollPane sidePane = new JScrollPane(sidebarPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sidePane.setPreferredSize(new Dimension(350, Integer.MAX_VALUE));

        contentPane.add(sidePane, BorderLayout.WEST);
        contentPane.add(mainPane, BorderLayout.CENTER);

        mainPane.add(dashboardPane, "DASHBOARD");

        sidebarPanel.getProfileBtn().addActionListener(e -> {
            cardLayout.show(mainPane, "DASHBOARD");
        });

        sidebarPanel.getAppointmentBtn().addActionListener(e -> {
            cardLayout.show(mainPane, "DASHBOARD");
        });
    }
}