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
        CounterStaffMainCustomerPane customerListPane = new CounterStaffMainCustomerPane();
        CounterStaffMainAppointmentPane appointmentPane = new CounterStaffMainAppointmentPane();
        CounterStaffMainPaymentPane paymentPane = new CounterStaffMainPaymentPane();

        JScrollPane sidePane = new JScrollPane(sidebarPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sidePane.setPreferredSize(new Dimension(350, Integer.MAX_VALUE));

        contentPane.add(sidePane, BorderLayout.WEST);
        contentPane.add(mainPane, BorderLayout.CENTER);

        mainPane.add(dashboardPane, "DASHBOARD");
        mainPane.add(customerListPane, "CUSTOMER");
        mainPane.add(appointmentPane, "APPOINTMENT");
        mainPane.add(paymentPane, "PAYMENT");



        sidebarPanel.getProfileBtn().addActionListener(e -> {
            cardLayout.show(mainPane, "DASHBOARD");
        });

        sidebarPanel.getCustomerListBtn().addActionListener(e -> {
            cardLayout.show(mainPane, "CUSTOMER");
        });
        
        sidebarPanel.getAppointmentBtn().addActionListener(e -> {
            cardLayout.show(mainPane, "APPOINTMENT");
        });

        sidebarPanel.getPaymentBtn().addActionListener(e -> {
            cardLayout.show(mainPane, "PAYMENT");
        });

    }
}