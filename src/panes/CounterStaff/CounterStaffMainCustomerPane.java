package panes.CounterStaff;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import panes.CounterStaff.components.CustomerPanelListener;

public class CounterStaffMainCustomerPane extends JPanel implements CustomerPanelListener{

    private CustomerPanelListener listener;
    private CardLayout cardLayout = new CardLayout();
    private CounterStaffCustomerList customerListPane;
    private CounterStaffCustomerDetailsPane detailsPane;
    private CounterStaffCreateCustomerPane createPane;
    private CounterStaffAddVehiclePane vehiclePane;

    public CounterStaffMainCustomerPane() {
        setBackground(Color.WHITE);
        setLayout(cardLayout);
        setBorder(new EmptyBorder(20, 20, 20, 20));
    
        customerListPane = new CounterStaffCustomerList(this);
        detailsPane = new CounterStaffCustomerDetailsPane(this);
        createPane = new CounterStaffCreateCustomerPane(this);
        vehiclePane = new CounterStaffAddVehiclePane(this);
        
        add(customerListPane, "LIST");
        add(detailsPane, "DETAILS");
        add(createPane, "CREATE");
        add(vehiclePane, "VEHICLE");
        cardLayout.show(this, "LIST");
    }

    public void onViewCustomerDetails(String customerID){
        System.out.println(customerID);
        detailsPane.loadCustomer(customerID);

        cardLayout.show(this, "DETAILS");
    }

    public void onCreateCustomer(){
        cardLayout.show(this, "CREATE");
    }

    public void onAddVehicle(String customerID){
        vehiclePane.loadCustomer(customerID);
        cardLayout.show(this, "VEHICLE");
    }

    @Override
    public void onBackToList(){
        cardLayout.show(this, "LIST");
    }

    @Override
    public void onViewVehicleDetails(String vehicleID) {

    }

}