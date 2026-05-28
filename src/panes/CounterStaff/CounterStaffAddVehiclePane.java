package panes.CounterStaff;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import IO.FileHandler;
import components.FloatingButton;
import components.FloatingTextField;
import components.TextLabel;
import panes.CounterStaff.components.CustomerPanelListener;

public class CounterStaffAddVehiclePane extends JPanel{
    private CustomerPanelListener listener;
    private String customerID;
    private static final String filename = "Vehicle.txt";
    private FloatingTextField customerIDField;

    public CounterStaffAddVehiclePane(CustomerPanelListener listener){
        this.listener = listener;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        TextLabel idTxt = new TextLabel("Customer ID:");
        customerIDField = new FloatingTextField("Customer ID");
        customerIDField.setEditable(false);
        mainPanel.add(idTxt);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(customerIDField);
        mainPanel.add(Box.createVerticalStrut(30));

        TextLabel plateTxt = new TextLabel("Car Plate:");
        FloatingTextField plateField = new FloatingTextField("Car Plate");
        mainPanel.add(plateTxt);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(plateField);
        mainPanel.add(Box.createVerticalStrut(30));

        TextLabel modelTxt = new TextLabel("Car Model:");
        FloatingTextField modelField = new FloatingTextField("Car Model");
        mainPanel.add(modelTxt);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(modelField);
        mainPanel.add(Box.createVerticalStrut(30));

        TextLabel colorTxt = new TextLabel("Color:");
        FloatingTextField colorField = new FloatingTextField("Color");
        mainPanel.add(colorTxt);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(colorField);
        mainPanel.add(Box.createVerticalStrut(30));

        add(mainPanel);
        add(Box.createVerticalStrut(30));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        FloatingButton cancelBtn = new FloatingButton("Cancel", 20);
        cancelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        FloatingButton addBtn = new FloatingButton("Add", 20);
        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(cancelBtn);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(addBtn);
        add(buttonPanel);

        cancelBtn.addActionListener(e -> {
            listener.onBackToList();
        });

        addBtn.addActionListener(e -> {
            String id = customerID;
            String plate = plateField.getText().trim();
            String model = modelField.getText().trim();
            String color = colorField.getText().trim();

            if (plate.isEmpty() || model.isEmpty() || color.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all vehicle details!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!plate.matches("^[a-zA-Z0-9 ]+$")) {
                JOptionPane.showMessageDialog(this, "Car Plate can only contain letters and numbers (no symbols).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!model.matches("^[a-zA-Z0-9 ]+$")) {
                JOptionPane.showMessageDialog(this, "Car Model can only contain letters and numbers (no symbols).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!color.matches("^[a-zA-Z ]+$")) {
                JOptionPane.showMessageDialog(this, "Car Color can only contain letters.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String vehicleID = generateNewVehicleID(filename);
            String[] vehicle = {vehicleID, plate, model, color, id};
            List<String[]> vehicleList = new ArrayList<>();

            vehicleList.add(vehicle);
            FileHandler.write(filename, vehicleList, true);

            plateField.setText("");
            modelField.setText("");
            colorField.setText("");

            JOptionPane.showMessageDialog(this, "Vehicle saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            listener.onBackToList();
        });
    }

    public static int getLatestVehicleNumber(String filename) {
        int max = 0;

        List<String[]> data = FileHandler.read(filename);

        for (String[] row : data) {
            if (row.length > 0 && row[0].startsWith("V")) {
                try {
                    String numberPart = row[0].substring(1);
                    int num = Integer.parseInt(numberPart);

                    if (num > max) {
                        max = num;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid vehicle ID: " + row[0]);
                }
            }
        }

        return max;
    } 
    
    public static String generateNewVehicleID(String filename) {
        int next = getLatestVehicleNumber(filename) + 1;
        return "V" + String.format("%06d", next);
    }
    
    public void loadCustomer(String customerID){
        this.customerID = customerID;
        customerIDField.setText(this.customerID);
    }
}
