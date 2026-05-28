package panes.CounterStaff;

import javax.swing.*;
import java.awt.*;
import panes.SidebarPanel;
import panes.CounterStaff.components.CustomerPanelListener;

public class CounterStaffMainPane extends JFrame implements CustomerPanelListener{
    private JPanel mainPane;
    private CardLayout cardLayout;
    private CounterStaffVehicleDetailsPane vehicleDetailsPane;

    public CounterStaffMainPane() {
        setTitle("APU-ASC");
        setSize(800, 600);
        components.ProgramTerminator.enableSafeExit(this);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        SidebarPanel sidebarPanel = new SidebarPanel("Counter Staff");

        mainPane = new JPanel();
        cardLayout = new CardLayout();
        mainPane.setLayout(cardLayout);

        CounterStaffDashboardPane dashboardPane = new CounterStaffDashboardPane();
        CounterStaffMainCustomerPane customerListPane = new CounterStaffMainCustomerPane();
        CounterStaffMainAppointmentPane appointmentPane = new CounterStaffMainAppointmentPane();
        CounterStaffMainPaymentPane paymentPane = new CounterStaffMainPaymentPane();
        CounterStaffVehicleListPane vehicleListPane = new CounterStaffVehicleListPane(this);
        vehicleDetailsPane = new CounterStaffVehicleDetailsPane(this);

        JScrollPane sidePane = new JScrollPane(sidebarPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sidePane.setPreferredSize(new Dimension(350, Integer.MAX_VALUE));

        contentPane.add(sidePane, BorderLayout.WEST);
        contentPane.add(mainPane, BorderLayout.CENTER);

        mainPane.add(dashboardPane, "DASHBOARD");
        mainPane.add(customerListPane, "CUSTOMER");
        mainPane.add(appointmentPane, "APPOINTMENT");
        mainPane.add(paymentPane, "PAYMENT");
        mainPane.add(vehicleListPane, "VEHICLE_LIST");
        mainPane.add(vehicleDetailsPane, "VEHICLE_DETAILS");

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

        sidebarPanel.getVehicleListBtn().addActionListener(e -> {
            cardLayout.show(mainPane, "VEHICLE_LIST");
        });

    }

    @Override
    public void onViewVehicleDetails(String vehicleID) {
        // Step 1: Tell the details page to load the data for this specific car
        vehicleDetailsPane.loadVehicle(vehicleID);

        // Step 2: Flip the card layout to show the details page
        cardLayout.show(mainPane, "VEHICLE_DETAILS");
    }

    // (If you have other interface methods like onBackToList(), they go here too!)
    @Override
    public void onBackToList() {
        cardLayout.show(mainPane, "VEHICLE_LIST"); // Or whatever page you want them to return to
    }

    @Override
    public void onCreateCustomer() {
        // Example: cardLayout.show(mainPane, "CREATE_CUSTOMER");
    }

    @Override
    public void onViewCustomerDetails(String customerID) {
        // Example: customerDetailsPane.loadCustomer(customerID);
        // cardLayout.show(mainPane, "CUSTOMER_DETAILS");
    }

    @Override
    public void onAddVehicle(String customerID) {
        // If you have an add vehicle pane, load the customer ID and flip to it
        // addVehiclePane.loadCustomer(customerID);
        // cardLayout.show(mainPane, "ADD_VEHICLE");
    }

}