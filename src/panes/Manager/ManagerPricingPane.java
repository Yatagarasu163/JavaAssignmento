package panes.Manager;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import java.util.ArrayList;
import components.FloatingButton;
import components.FloatingComboBox;
import components.TextLabel;
import components.FloatingTextField;
import IO.FileHandler;

public class ManagerPricingPane extends JPanel{

    private double currentPrice = 0.00;
    private static final String filename = "Price.txt";
    private TextLabel currentPriceLabel;
    private FloatingTextField newPriceField;
    private List<String[]> prices = FileHandler.read(filename);
    private String normalPrice = prices.get(0)[0];
    private String majorPrice = prices.get(1)[0];

    public ManagerPricingPane() {
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
        FloatingComboBox<String> serviceTypeComboBox = new FloatingComboBox<>(options);
        newPriceField = new FloatingTextField("New Price");
        currentPriceLabel = new TextLabel(String.format("RM%.2f", currentPrice));

        //Adds content to the new formPanel
        formPanel.add(new TextLabel("Service type: "));
        formPanel.add(serviceTypeComboBox);
        formPanel.add(new TextLabel("Current Price: "));
        formPanel.add(currentPriceLabel);
        formPanel.add(new TextLabel("New Price: "));
        formPanel.add(newPriceField);


        //Creates button for the window
        FloatingButton updateButton = new FloatingButton("Update", 20);
        FloatingButton cancelButton = new FloatingButton("Cancel", 20);

        //Adds the form panel to the pricing pane
        add(formPanel);
        add(Box.createVerticalStrut(30));
        add(updateButton);
        add(Box.createVerticalStrut(20));
        add(cancelButton);

        serviceTypeComboBox.addActionListener(e -> {
            prices = FileHandler.read(filename);
            normalPrice = prices.get(0)[0];
            majorPrice = prices.get(1)[0];


            if (serviceTypeComboBox.getSelectedIndex() == 0){
                currentPrice = Double.parseDouble(normalPrice);
            } else{
                currentPrice = Double.parseDouble(majorPrice);
            }
            currentPriceLabel.setText(String.format("RM%.2f", currentPrice));
        });

        updateButton.addActionListener(e -> {
            if (serviceTypeComboBox.getSelectedIndex() == 0){
                prices.get(0)[0] = newPriceField.getText();
                currentPriceLabel.setText(prices.get(0)[0]);
            } else{
                prices.get(1)[0] = newPriceField.getText();
                currentPriceLabel.setText(prices.get(1)[0]);
            }

            FileHandler.write(filename, prices, false);

            JOptionPane.showMessageDialog(this, "New price saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        cancelButton.addActionListener(e -> {
            newPriceField.setText("0.00");
        });

    }
}
