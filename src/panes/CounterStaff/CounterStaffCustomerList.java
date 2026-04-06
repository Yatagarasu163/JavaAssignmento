package panes.CounterStaff;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import components.FloatingButton;
import config.UIConfig;
import components.TextLabel;

public class CounterStaffCustomerList extends JPanel{
    
    public CounterStaffCustomerList() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));


        JPanel addPanel = new JPanel();
        addPanel.setOpaque(false);
        addPanel.setLayout(new BorderLayout());
        addPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        addPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        FloatingButton addCustomer = new FloatingButton("Add new customer (+)");
        addCustomer.setBackground(UIConfig.mainForeground);
        addCustomer.setForeground(UIConfig.mainBackground);
        addCustomer.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIConfig.mainBackground, 3, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        addCustomer.setBorderPainted(true);
        addPanel.add(addCustomer, BorderLayout.EAST);


        JPanel customerDetailsPanel = new JPanel();
        customerDetailsPanel.setBackground(UIConfig.mainBackground);
        customerDetailsPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIConfig.mainBackground, 2, true),
            new EmptyBorder(30, 40, 30, 40)
        ));
        customerDetailsPanel.setLayout(new BoxLayout(customerDetailsPanel, BoxLayout.Y_AXIS));
        
        JPanel filterPanel = new JPanel();
        filterPanel.setOpaque(false);
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        filterPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 25));
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        TextLabel userIDlabel = new TextLabel("User ID: ");
        userIDlabel.setForeground(Color.WHITE);
        TextLabel datelabel = new TextLabel("Date: ");
        datelabel.setForeground(Color.WHITE); 
        filterPanel.add(userIDlabel);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(new JTextField());
        filterPanel.add(Box.createHorizontalStrut(50));
        filterPanel.add(datelabel);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(new JTextField());
        filterPanel.add(Box.createHorizontalStrut(10));

        customerDetailsPanel.add(Box.createVerticalStrut(20));
        customerDetailsPanel.add(filterPanel);

        add(Box.createVerticalStrut(50));
        add(addPanel);
        add(Box.createVerticalStrut(50));
        add(customerDetailsPanel);
    }

}
