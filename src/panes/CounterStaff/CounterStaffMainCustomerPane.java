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

    public CounterStaffMainCustomerPane() {
        setBackground(Color.WHITE);
        setLayout(cardLayout);
        setBorder(new EmptyBorder(20, 20, 20, 20));
    
        customerListPane = new CounterStaffCustomerList(this);
        detailsPane = new CounterStaffCustomerDetailsPane(this);
        
        add(customerListPane, "LIST");
        add(detailsPane, "DETAILS");
        cardLayout.show(this, "LIST");
    }

    public void onViewCustomerDetails(String customerID){
        //Handle the customer logic here.
        System.out.println(customerID);
        detailsPane.loadCustomer(customerID);

        cardLayout.show(this, "DETAILS");
    }

    @Override
    public void onBackToList(){
        System.out.println("Going back to the customer list pane!");

        cardLayout.show(this, "LIST");
    }

}