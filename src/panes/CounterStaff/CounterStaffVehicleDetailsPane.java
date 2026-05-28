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

public class CounterStaffVehicleDetailsPane extends JPanel {
    private CustomerPanelListener listener;
    private static final String filename = "Vehicle.txt";

    private String currentVehicleID;
    private FloatingTextField vehicleIDField;
    private FloatingTextField customerIDField;
    private FloatingTextField plateField;
    private FloatingTextField modelField;
    private FloatingTextField colorField;

    public CounterStaffVehicleDetailsPane(CustomerPanelListener listener) {
        this.listener = listener;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        FloatingButton backBtn = new FloatingButton("Back", 20);
        topPanel.add(backBtn);
        add(topPanel);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        vehicleIDField = new FloatingTextField("Vehicle ID");
        vehicleIDField.setEditable(false);
        customerIDField = new FloatingTextField("Customer ID");
        customerIDField.setEditable(false);
        plateField = new FloatingTextField("Car Plate");
        modelField = new FloatingTextField("Car Model");
        colorField = new FloatingTextField("Color");

        mainPanel.add(new TextLabel("Vehicle ID:"));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(vehicleIDField);
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(new TextLabel("Customer ID:"));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(customerIDField);
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(new TextLabel("Car Plate:"));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(plateField);
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(new TextLabel("Car Model:"));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(modelField);
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(new TextLabel("Color:"));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(colorField);

        add(mainPanel);
        add(Box.createVerticalStrut(40));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setOpaque(false);

        FloatingButton deleteBtn = new FloatingButton("Delete Vehicle", 20);
        deleteBtn.setBackground(new Color(220, 53, 69));
        deleteBtn.setForeground(Color.WHITE);

        FloatingButton updateBtn = new FloatingButton("Update Details", 20);

        buttonPanel.add(deleteBtn);
        buttonPanel.add(updateBtn);
        add(buttonPanel);

        backBtn.addActionListener(e -> listener.onBackToList());

        updateBtn.addActionListener(e -> {
            String plate = plateField.getText().trim();
            String model = modelField.getText().trim();
            String color = colorField.getText().trim();

            if (plate.isEmpty() || model.isEmpty() || color.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!plate.matches("^[a-zA-Z0-9 ]+$") || !model.matches("^[a-zA-Z0-9 ]+$")) {
                JOptionPane.showMessageDialog(this, "Plate and Model can only contain letters and numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!color.matches("^[a-zA-Z ]+$")) {
                JOptionPane.showMessageDialog(this, "Color can only contain letters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<String[]> vehicles = FileHandler.read(filename);
            for (String[] v : vehicles) {
                if (v[0].equalsIgnoreCase(currentVehicleID)) {
                    v[1] = plate;
                    v[2] = model;
                    v[3] = color;
                    break;
                }
            }
            FileHandler.write(filename, vehicles, false);
            JOptionPane.showMessageDialog(this, "Vehicle details updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
            listener.onBackToList();
        });

        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this vehicle?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                List<String[]> vehicles = FileHandler.read(filename);
                vehicles.removeIf(v -> v[0].equalsIgnoreCase(currentVehicleID));
                FileHandler.write(filename, vehicles, false);
                JOptionPane.showMessageDialog(this, "Vehicle deleted successfully.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                listener.onBackToList();
            }
        });
    }

    public void loadVehicle(String vehicleID) {
        this.currentVehicleID = vehicleID;
        List<String[]> vehicles = FileHandler.read(filename);
        for (String[] v : vehicles) {
            if (v[0].equalsIgnoreCase(vehicleID)) {
                vehicleIDField.setText(v[0]);
                plateField.setText(v[1]);
                modelField.setText(v[2]);
                colorField.setText(v[3]);
                customerIDField.setText(v[4]);
                break;
            }
        }
    }
}