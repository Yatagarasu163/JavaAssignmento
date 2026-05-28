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
    private List<String[]> normalPrices;
    private List<String[]> majorPrices;
    private String[] options; 

    public ManagerPricingPane() {

        normalPrices = new ArrayList<>();
        majorPrices = new ArrayList<>();

        for(String[] price : prices){
            if (price[2].equalsIgnoreCase("N")){
                normalPrices.add(price);
            } else if (price[2].equalsIgnoreCase("M")){
                majorPrices.add(price);
            }
        }

        List<String> normalPricesList = new ArrayList<>();
        for(String[] price : normalPrices){
            normalPricesList.add(price[1]);
        }
        String[] normalPricesStrings = normalPricesList.toArray(new String[0]);

        List<String> majorPricesList = new ArrayList<>();
        for(String[] price : majorPrices){
            majorPricesList.add(price[1]);
        }
        String[] majorPricesStrings = majorPricesList.toArray(new String[0]);

        options = normalPricesStrings;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        TextLabel title = new TextLabel("Pricing");

        add(Box.createVerticalStrut(50));
        add(title);
        add(Box.createVerticalStrut(50));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 30, 10));
        formPanel.setOpaque(false);

        String[] priceOptions = {"Normal service type", "Major service type"};
        FloatingComboBox<String> serviceTypeComboBox = new FloatingComboBox<>(priceOptions);
        FloatingComboBox<String> priceTypeComboBox = new FloatingComboBox<>(options);
        newPriceField = new FloatingTextField("New Price");
        currentPriceLabel = new TextLabel(String.format("RM%.2f", currentPrice));

        formPanel.add(new TextLabel("Service type: "));
        formPanel.add(serviceTypeComboBox);
        formPanel.add(new TextLabel("Service: "));
        formPanel.add(priceTypeComboBox);
        formPanel.add(new TextLabel("Current Price: "));
        formPanel.add(currentPriceLabel);
        formPanel.add(new TextLabel("New Price: "));
        formPanel.add(newPriceField);

        FloatingButton updateButton = new FloatingButton("Update", 20);
        FloatingButton cancelButton = new FloatingButton("Cancel", 20);

        add(formPanel);
        add(Box.createVerticalStrut(30));
        add(updateButton);
        add(Box.createVerticalStrut(20));
        add(cancelButton);

        serviceTypeComboBox.addActionListener(e -> {
            Integer selectedIndex = (int) serviceTypeComboBox.getSelectedIndex();

            if(selectedIndex == 0){
                priceTypeComboBox.removeAllItems();
                for(String name : normalPricesStrings){
                    priceTypeComboBox.addItem(name);
                }
            } else {
                priceTypeComboBox.removeAllItems();
                for(String name : majorPricesStrings) {
                    priceTypeComboBox.addItem(name);
                }
            }
            
            newPriceField.setText("");
        });

        priceTypeComboBox.addActionListener(e -> {
            Integer selectedIndex = (int) serviceTypeComboBox.getSelectedIndex();
            Integer selectedPriceType = (int) priceTypeComboBox.getSelectedIndex();
            if(selectedIndex == 0){
                double selectedPrice = Double.parseDouble(prices.get(selectedPriceType)[3]);
                currentPriceLabel.setText(String.format("RM%.2f", selectedPrice));
            } else{
                double selectedPrice = Double.parseDouble(prices.get(selectedPriceType + 6)[3]);
                currentPriceLabel.setText(String.format("RM%.2f", selectedPrice));
            }

            newPriceField.setText("");
        });

        updateButton.addActionListener(e -> {
            double newPrice = parsePrice(newPriceField.getText());
            Integer serviceTypeIndex = serviceTypeComboBox.getSelectedIndex();
            Integer priceTypeIndex = priceTypeComboBox.getSelectedIndex();

            if(newPrice <= 0){
                JOptionPane.showMessageDialog(this, "Invalid price input!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(serviceTypeIndex == 0){
                prices.get(priceTypeIndex)[3] = String.format("%.2f", newPrice);
            } else{
                prices.get(priceTypeIndex + normalPricesList.size())[3] = String.format("%.2f", newPrice);
            }

            currentPriceLabel.setText(String.format("RM%.2f", newPrice));

            FileHandler.write(filename, prices, false);

            JOptionPane.showMessageDialog(this, "New price saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        cancelButton.addActionListener(e -> {
            newPriceField.setText("0.00");
        });

    }

    private static double parsePrice(String input) {
    if (input == null || input.isEmpty()) return 0.0;

    String cleaned = input.replaceAll("[^0-9.]", "");

    try {
        return Double.parseDouble(cleaned);
    } catch (NumberFormatException e) {
        return 0.0;
    }
}
}
