package components;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PricingPane extends JPanel{

    private double currentPrice = 67.69;

    public PricingPane() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        TextLabel title = new TextLabel("Pricing");

        //Add title text to the pane
        add(Box.createVerticalStrut(50));
        add(title);
        add(Box.createVerticalStrut(50));

        //Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 30, 10));
        formPanel.setOpaque(false);

        //Sets the input fields
        String[] options = {"Normal service type", "Major service type"};
        JComboBox<String> serviceTypeComboBox = new JComboBox<>(options);
        serviceTypeComboBox.setSelectedIndex(0);
        FloatingTextField newPriceTxtField = new FloatingTextField("New Price");

        //Adds content to the new formPanel
        formPanel.add(new TextLabel("Service type: "));
        formPanel.add(serviceTypeComboBox);
        formPanel.add(new TextLabel("Current Price: "));
        formPanel.add(new TextLabel(String.format("RM%.2f", currentPrice)));
        formPanel.add(new TextLabel("New Price: "));
        formPanel.add(newPriceTxtField);


        //Creates button for the window
        FloatingButton updateButton = new FloatingButton("Update", 20);
        FloatingButton cancelButton = new FloatingButton("Cancel", 20);

        //Adds the form panel to the pricing pane
        add(formPanel);
        add(Box.createVerticalStrut(30));
        add(updateButton);
        add(Box.createVerticalStrut(20));
        add(cancelButton);

    }
}
