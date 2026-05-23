package panes.CounterStaff;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import components.FloatingComboBox;
import components.TextLabel;
import config.UIConfig;
import panes.CounterStaff.components.AppointmentPanelListener;
import components.FloatingButton;
import components.FloatingCheckBox;
import IO.FileHandler;

public class CounterStaffCreateAppointmentPane extends JPanel {

    private AppointmentPanelListener listener;
    private int componentSpace = 20;

    private List<String[]> accounts = FileHandler.read("Users.txt");
    private List<String[]> customers = new ArrayList<>();

    private String customerName = "Unknown"; // CHANGE THIS VALUE LATER
    private String[] customerIDList = {"CT123456", "CT123457", "CT000001"};  //CHANGE THIS TO ACTUAL DATA
    private String contactNumber = "Unknown"; // CHANGE THIS VALUE LATER
    private String email = "Unknown"; //CHANGE THIS VALUE LATER
    private String vehicleModel = "Unknown"; //CHANGE THIS VALUE LATER
    private String[] vehiclePlateOptions = {"Unknown"}; //CHANGE THIS VALUE LATER
    private String[] technicianList = {"Ali", "Muthu", "Ah Hock"};
    private List<String[]> prices = FileHandler.read("Price.txt");
    private List<String[]> normalPriceList;
    private List<String[]> majorPriceList;

    public CounterStaffCreateAppointmentPane(AppointmentPanelListener listener) {
        this.listener = listener;

        normalPriceList = new ArrayList<>();
        majorPriceList = new ArrayList<>();

        for(String[] price : prices){
            if(price[2].equalsIgnoreCase("N")){
                normalPriceList.add(price);
            } else if (price[2].equalsIgnoreCase("M")){
                majorPriceList.add(price);
            }
        }

        List<String> customerIDs = new ArrayList<>();
        for(String[] account : accounts){
            if (account[0].startsWith("CT")){
                customers.add(account);
                customerIDs.add(account[0]);
            }
        }

        List<String[]> technicians = new ArrayList<>();
        List<String> technicianNames = new ArrayList<>();
        for(String[] account: accounts){
            if(account[0].startsWith("T")){
                technicians.add(account);
                technicianNames.add(account[1] + " " + account[2]);
            }
        }
        technicianList = technicianNames.toArray(new String[0]);
        if(technicianList.length <= 0){
            technicianList = new String[]{"Null"};
        }

        customerIDList = customerIDs.toArray(new String[0]);
        if (customerIDList.length <= 0){
            customerIDList = new String[]{"Null"};
        }

        // ==========================================
        // 1. SET UP THE MAIN PANE LAYOUT
        // ==========================================
        setBackground(Color.WHITE);
        setLayout(new BorderLayout()); // Changed to BorderLayout to hold the scroll pane

        // ==========================================
        // 2. CREATE THE INNER CONTENT PANEL
        // ==========================================
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        TextLabel appointmentDateTxt = new TextLabel("Appointment Date");
        appointmentDateTxt.setFontSize(30);
        appointmentDateTxt.setForeground(UIConfig.mainBackground);
        contentPanel.add(appointmentDateTxt);
        contentPanel.add(Box.createVerticalStrut(componentSpace));

        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(editor);
        dateSpinner.setMaximumSize(new Dimension(200, 100));
        contentPanel.add(dateSpinner);
        contentPanel.add(Box.createVerticalStrut(componentSpace));

        TextLabel customerInfoTxt = new TextLabel("Customer Information");
        customerInfoTxt.setFontSize(30);
        customerInfoTxt.setForeground(UIConfig.mainBackground);
        contentPanel.add(customerInfoTxt);
        contentPanel.add(Box.createVerticalStrut(componentSpace));

        JPanel customerDetailsPanel = new JPanel();
        customerDetailsPanel.setOpaque(false);
        customerDetailsPanel.setLayout(new GridLayout(3,2, 40, 15));
        customerDetailsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        //Customer ID Panel
        JPanel customerIDPanel = createCustomerDetailsPanel();
        TextLabel customerIDTxt = createTxtLabel("Customer ID: ");
        customerIDPanel.add(customerIDTxt, BorderLayout.WEST);
        FloatingComboBox<String> customerIDComboBox = new FloatingComboBox<>(customerIDList);
        customerIDComboBox.setPreferredSize(new Dimension(250, 40));
        customerIDComboBox.setMaximumSize(new Dimension(300, 40));
        customerIDPanel.add(customerIDComboBox, BorderLayout.CENTER);
        customerDetailsPanel.add(customerIDPanel);

        //Customer Email Panel
        JPanel emailPanel = createCustomerDetailsPanel();
        TextLabel emailTxt = createTxtLabel("Email: ");
        emailPanel.add(emailTxt, BorderLayout.WEST);
        TextLabel emailValue = createDetailsTxt(email);
        emailPanel.add(emailValue, BorderLayout.CENTER);
        customerDetailsPanel.add(emailPanel);

        //Customer Name Panel
        JPanel customerNamePanel = createCustomerDetailsPanel();
        TextLabel customerNameTxt = createTxtLabel("Customer Name: ");
        customerNamePanel.add(customerNameTxt, BorderLayout.WEST);
        TextLabel customerNameValue = createDetailsTxt(customerName);
        customerNamePanel.add(customerNameValue, BorderLayout.CENTER);
        customerDetailsPanel.add(customerNamePanel);

        //Vehicle Model Panel
        JPanel modelPanel = createCustomerDetailsPanel();
        TextLabel modelTxt = createTxtLabel("Vehicle Model: ");
        modelPanel.add(modelTxt, BorderLayout.WEST);
        TextLabel modelValue = createDetailsTxt(vehicleModel);
        modelPanel.add(modelValue, BorderLayout.CENTER);
        customerDetailsPanel.add(modelPanel);

        // Contact Number Panel
        JPanel customerNumberPanel = createCustomerDetailsPanel();
        TextLabel contactNumTxt = createTxtLabel("Contact Number: ");
        customerNumberPanel.add(contactNumTxt, BorderLayout.WEST);
        TextLabel contactNumValue = createDetailsTxt(contactNumber);
        customerNumberPanel.add(contactNumValue, BorderLayout.CENTER);
        customerDetailsPanel.add(customerNumberPanel);

        // Vehicle Plate Panel
        JPanel platePanel = createCustomerDetailsPanel();
        TextLabel plateTxt = createTxtLabel("Vehicle Plate: ");
        platePanel.add(plateTxt, BorderLayout.WEST);
        FloatingComboBox<String> plateComboBox = new FloatingComboBox<>(vehiclePlateOptions);
        platePanel.setPreferredSize(new Dimension(250, 40));
        platePanel.setMaximumSize(new Dimension(300, 40));
        platePanel.add(plateComboBox, BorderLayout.CENTER);
        customerDetailsPanel.add(platePanel);

        contentPanel.add(customerDetailsPanel);
        contentPanel.add(Box.createVerticalStrut(componentSpace));

        TextLabel serviceTypeTxt = new TextLabel("Service Type");
        serviceTypeTxt.setFontSize(30);
        contentPanel.add(serviceTypeTxt);
        contentPanel.add(Box.createVerticalStrut(componentSpace));

        JPanel typePanel = new JPanel();
        typePanel.setOpaque(false);
        typePanel.setLayout(new BorderLayout(10, 10));
        String[] serviceTypes = {"Normal Service", "Major Service"};
        FloatingComboBox<String> typeComboBox = new FloatingComboBox<>(serviceTypes);
        typeComboBox.setPreferredSize(new Dimension(250, 40));
        typeComboBox.setMaximumSize(new Dimension(300, 40));
        typePanel.add(typeComboBox, BorderLayout.CENTER);
        contentPanel.add(typePanel);
        contentPanel.add(Box.createVerticalStrut(componentSpace));

        TextLabel serviceRequestTxt = new TextLabel("Service Request");
        serviceRequestTxt.setFontSize(30);
        contentPanel.add(serviceRequestTxt);
        contentPanel.add(Box.createVerticalStrut(componentSpace));

        JPanel requestsPanel = new JPanel();
        requestsPanel.setOpaque(false);
        CardLayout cardLayout = new CardLayout();
        requestsPanel.setLayout(cardLayout);

        List<FloatingCheckBox> normalList = new ArrayList<>();
        List<FloatingCheckBox> majorList = new ArrayList<>();

        JPanel normalServPanel = new JPanel();
        normalServPanel.setOpaque(false);
        normalServPanel.setLayout(new GridLayout(3, 2, 10, 10));
        normalServPanel.setMaximumSize(new Dimension(700, 100));
        for (String[] price : normalPriceList){
            addCheckBox(normalList, normalServPanel, price[1]);
        }

        JPanel majorServPanel = new JPanel();
        majorServPanel.setOpaque(false);
        majorServPanel.setLayout(new GridLayout(3, 2, 10, 10));
        majorServPanel.setMaximumSize(new Dimension(700, 100));
        for (String[] price : majorPriceList){
            addCheckBox(majorList, majorServPanel, price[1]);
        }

        requestsPanel.add(normalServPanel, "NORMAL");
        requestsPanel.add(majorServPanel, "MAJOR");
        contentPanel.add(requestsPanel);

        typeComboBox.addActionListener(e -> {
            int selectedIndex = typeComboBox.getSelectedIndex();
            if(selectedIndex == 0){
                cardLayout.show(requestsPanel, "NORMAL");
            } else {
                cardLayout.show(requestsPanel, "MAJOR");
            }
        });

        JPanel techniciansTxt = new JPanel();
        techniciansTxt.setOpaque(false);
        TextLabel techTxt = new TextLabel("Assigned Technician");
        techTxt.setFontSize(30);
        techniciansTxt.add(techTxt);
        contentPanel.add(techniciansTxt);

        JPanel techSelectPanel = new JPanel();
        techSelectPanel.setOpaque(false);
        techSelectPanel.setLayout(new BorderLayout());
        FloatingComboBox<String> techComboBox = new FloatingComboBox<>(technicianList);
        techComboBox.setPreferredSize(new Dimension(250, 40));
        techComboBox.setMaximumSize(new Dimension(300, 40));
        techSelectPanel.add(techComboBox, BorderLayout.CENTER);
        contentPanel.add(techSelectPanel);
        contentPanel.add(Box.createVerticalStrut(componentSpace));

        JPanel createAppointmentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        createAppointmentPanel.setOpaque(false);
        createAppointmentPanel.setMaximumSize(new Dimension(900, 60));

        FloatingButton createAppointment = new FloatingButton("Create Appointment");
        createAppointment.setPreferredSize(new Dimension(300, 50));

        createAppointmentPanel.add(createAppointment);
        contentPanel.add(createAppointmentPanel);

        // ==========================================
        // 3. WRAP CONTENT IN SCROLL PANE & ADD TO MAIN
        // ==========================================
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null); // Removes the default border for a cleaner look
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Prevents side-to-side scrolling
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Makes mouse-wheel scrolling smooth and fast

        add(scrollPane, BorderLayout.CENTER); // Add the scrollable window to the main pane!

        // ==========================================
        // ACTION LISTENERS
        // ==========================================
        createAppointment.addActionListener(e -> {
            String appointmentID = generateNewAppointmentID("Appointment.txt");
            Date selectedDate = (Date) dateSpinner.getValue();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = sdf.format(selectedDate);
            String customerID = (String) customerIDComboBox.getSelectedItem();
            String plate = (String) plateComboBox.getSelectedItem();
            String serviceType = (String) typeComboBox.getSelectedItem();
            List<String> selectedServices = new ArrayList<>();

            List<FloatingCheckBox> activeList = typeComboBox.getSelectedIndex() == 0 ? normalList : majorList;

            for (FloatingCheckBox cb : activeList) {
                if(cb.isSelected()){
                    selectedServices.add(cb.getText());
                }
            }
            List<String[]> selectedServicesArray = new ArrayList<>();
            List<String> selectedServicesStrings = new ArrayList<>();
            for(String service : selectedServices){
                for(String[] price : normalPriceList){
                    if (price[1].equalsIgnoreCase(service)){
                        selectedServicesArray.add(price);
                        selectedServicesStrings.add(price[0]);
                    }
                }
            }
            String servicesString = String.join(",", selectedServicesStrings);

            int selectedTechnician = techComboBox.getSelectedIndex();
            String technicianID = technicians.get(selectedTechnician)[0];

            String selectedServiceType = (String) typeComboBox.getSelectedItem();
            List<String[]> user = FileHandler.read("CurrentUser.txt");
            String staffID = user.get(0)[0];

            List<String[]> vehicles = FileHandler.read("Vehicle.txt");
            String selectedPlate = (String) plateComboBox.getSelectedItem();
            String selectedVehicle = "";
            for(String[] vehicle : vehicles){
                if(vehicle[1].equalsIgnoreCase(selectedPlate)){
                    selectedVehicle = vehicle[0];
                    break;
                }
            }

            String[] appointmentArray = {appointmentID, "", selectedServiceType, servicesString, "In Queue", dateString, technicianID, customerID, staffID, selectedVehicle};
            List<String[]> appointmentList = new ArrayList<>();

            appointmentList.add(appointmentArray);
            FileHandler.write("Appointment.txt", appointmentList, true);

            String paymentID = generateNewPaymentID(FileHandler.payment);
            String totalAmount = "";
            double total = 0;
            for (String[] service : selectedServicesArray){
                total += Double.parseDouble(service[3]);
            }
            totalAmount = String.format("%.2f", total);
            String[] paymentArray = {paymentID, totalAmount, dateString, customerID, selectedVehicle, appointmentID, "Not Paid"};
            List<String[]> paymentList = new ArrayList<>();
            paymentList.add(paymentArray);
            FileHandler.write("Payment.txt", paymentList, true);

            JOptionPane.showMessageDialog(this, "Appointment added!", "Success", JOptionPane.INFORMATION_MESSAGE);

            listener.onBackToList();
        });

// ==========================================
        // DYNAMIC UPDATES & ACTION LISTENERS
        // ==========================================

        // 1. Vehicle Plate Listener (Updates the Vehicle Model when a new plate is chosen)
        plateComboBox.addActionListener(e -> {
            String selectedPlate = (String) plateComboBox.getSelectedItem();
            if (selectedPlate != null && !selectedPlate.equals("No Vehicles Found")) {
                List<String[]> vehicles = FileHandler.read("Vehicle.txt");
                for (String[] vehicle : vehicles) {
                    if (vehicle.length > 2 && vehicle[1].equalsIgnoreCase(selectedPlate)) {
                        // Assuming index 2 is Brand and 3 is Model.
                        // If your text file is different, just change these numbers!
                        modelValue.setText(vehicle[2] + " " + vehicle[3]);
                        return;
                    }
                }
            }
            modelValue.setText("N/A");
        });

        // 2. Customer ID Listener (Updates Customer Info AND populates the Plate Dropdown)
        customerIDComboBox.addActionListener(e -> {
            String customerID = (String) customerIDComboBox.getSelectedItem();
            if (customerID == null) return;

            // A. Update Customer Text Labels
            for (String[] account : customers){
                if(account[0].equals(customerID)){
                    customerNameValue.setText(account[1] + " " + account[2]);
                    emailValue.setText(account[5]);
                    contactNumValue.setText(account[6]);
                    break;
                }
            }

            // B. Find all vehicles belonging to this customer
            List<String[]> vehicles = FileHandler.read("Vehicle.txt");
            plateComboBox.removeAllItems(); // Clear the old plates out

            boolean hasVehicle = false;
            for (String[] vehicle : vehicles){
                // vehicle[4] is the Customer ID based on your file structure
                if (vehicle.length > 4 && vehicle[4].equals(customerID)){
                    plateComboBox.addItem(vehicle[1]);
                    hasVehicle = true;
                }
            }

            // C. Fallback if they have no registered cars
            if (!hasVehicle) {
                plateComboBox.addItem("No Vehicles Found");
            }
        });

        // 3. THE MAGIC TRICK: Trigger the listener once immediately so it loads
        // the first customer's data on startup instead of showing "Unknown"!
        if (customerIDComboBox.getItemCount() > 0) {
            customerIDComboBox.setSelectedIndex(-1); // Resets it
            customerIDComboBox.setSelectedIndex(0);  // Forces the listeners to run!
        }
    }

    public static int getLatestAppointmentNumber(String filename){
        int max = 0;
        List<String[]> data = FileHandler.read(filename);
        for(String[] row : data){
            if(row.length > 0 && row[0].startsWith("A")){
                try{
                    String numberPart = row[0].substring(1);
                    int num = Integer.parseInt(numberPart);
                    if (num > max){
                        max = num;
                    }
                } catch(NumberFormatException e){
                    System.err.println("Invalid vehicle ID: " + row[0]);
                }
            }
        }
        return max;
    }

    public static String generateNewAppointmentID(String filename) {
        int next = getLatestAppointmentNumber(filename) + 1;
        return "A" + String.format("%06d", next);
    }

    public static int getLatestPaymentNumber(String filename){
        int max = 0;
        List<String[]> data = FileHandler.read(filename);
        for(String[] row : data){
            if(row.length > 0 && row[0].startsWith("PY")){
                try{
                    String numberPart = row[0].substring(2);
                    int num = Integer.parseInt(numberPart);
                    if (num > max){
                        max = num;
                    }
                } catch(NumberFormatException e){
                    System.err.println("Invalid payment ID: " + row[0]);
                }
            }
        }
        return max;
    }

    public static String generateNewPaymentID(String filename) {
        int next = getLatestPaymentNumber(filename) + 1;
        return "PY" + String.format("%06d", next);
    }

    private TextLabel createTxtLabel(String label){
        TextLabel txtLbl = new TextLabel(label);
        txtLbl.setFontType(Font.PLAIN);
        txtLbl.setFontSize(25);
        txtLbl.setForeground(new Color(150, 150, 255));
        return txtLbl;
    }

    private TextLabel createDetailsTxt(String label){
        TextLabel txt = new TextLabel(label);
        txt.setFontType(Font.PLAIN);
        txt.setFontSize(25);
        txt.setForeground(Color.LIGHT_GRAY);
        return txt;
    }

    private JPanel createCustomerDetailsPanel(){
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(10, 0));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return panel;
    }

    private void addCheckBox(List<FloatingCheckBox> list, JPanel target, String label){
        FloatingCheckBox cb = new FloatingCheckBox(label);
        list.add(cb);
        target.add(cb);
    }
}