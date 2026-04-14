package panes.CounterStaff;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.List;

import components.FloatingComboBox;
import components.TextLabel;
import config.UIConfig;
import panes.CounterStaff.components.AppointmentPanelListener;
import components.FloatingButton;
import components.FloatingCheckBox;

public class CounterStaffCreateAppointmentPane extends JPanel{

    private AppointmentPanelListener listener;
    private int componentSpace = 20;

    private String customerName = "Sum Ting Wong"; // CHANGE THIS VALUE LATER
    private String[] customerIDList = {"CT123456", "CT123457", "CT000001"};  //CHANGE THIS TO ACTUAL DATA
    private String contactNumber = "012-345-6789"; // CHANGE THIS VALUE LATER
    private String email = "tingwong@gmail.com"; //CHANGE THIS VALUE LATER
    private String vehicleModel = "Perodua Myvi"; //CHANGE THIS VALUE LATER
    private String[] vehiclePlateOptions = {"ABC 1234"}; //CHANGE THIS VALUE LATER


    public CounterStaffCreateAppointmentPane(AppointmentPanelListener listener){
        this.listener = listener;

        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        TextLabel appointmentDateTxt = new TextLabel("Appointment Date");
        appointmentDateTxt.setFontSize(30);
        appointmentDateTxt.setForeground(UIConfig.mainBackground);
        add(appointmentDateTxt);
        add(Box.createVerticalStrut(componentSpace));


        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(editor);
        dateSpinner.setMaximumSize(new Dimension(200, 100));
        add(dateSpinner);
        add(Box.createVerticalStrut(componentSpace));

        TextLabel customerInfoTxt = new TextLabel("Customer Information");
        customerInfoTxt.setFontSize(30);
        customerInfoTxt.setForeground(UIConfig.mainBackground);
        add(customerInfoTxt);
        add(Box.createVerticalStrut(componentSpace));

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

        add(customerDetailsPanel);
        add(Box.createVerticalStrut(componentSpace));
        
        TextLabel serviceTypeTxt = new TextLabel("Service Type");
        serviceTypeTxt.setFontSize(30);
        add(serviceTypeTxt);
        add(Box.createVerticalStrut(componentSpace));

        JPanel typePanel = new JPanel();
        typePanel.setOpaque(false);
        typePanel.setLayout(new BorderLayout(10, 10));
        String[] serviceTypes = {"Normal Service", "Major Service"};
        FloatingComboBox<String> typeComboBox = new FloatingComboBox<>(serviceTypes);
        typeComboBox.setPreferredSize(new Dimension(250, 40));
        typeComboBox.setMaximumSize(new Dimension(300, 40));
        typePanel.add(typeComboBox, BorderLayout.CENTER);
        add(typePanel);
        add(Box.createVerticalStrut(componentSpace));

        TextLabel serviceRequestTxt = new TextLabel("Service Request");
        serviceRequestTxt.setFontSize(30);
        add(serviceRequestTxt);
        add(Box.createVerticalStrut(componentSpace));

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
        addCheckBox(normalList, normalServPanel, "Brake Inspection and Adjustment");
        addCheckBox(normalList, normalServPanel, "Chassis Lubrication");
        addCheckBox(normalList, normalServPanel, "Carburetor Tuning");
        addCheckBox(normalList, normalServPanel, "Engine Oil and Filter Change");
        addCheckBox(normalList, normalServPanel, "Ignition Tune-Up");
        addCheckBox(normalList, normalServPanel, "Comprehensive Fluid Check");

        JPanel majorServPanel = new JPanel();
        majorServPanel.setOpaque(false);
        majorServPanel.setLayout(new GridLayout(3, 2, 10, 10));
        majorServPanel.setMaximumSize(new Dimension(700, 100));
        addCheckBox(majorList, majorServPanel, "Buff out body");
        addCheckBox(majorList, majorServPanel, "Fix broken windshield");
        addCheckBox(majorList, majorServPanel, "Wheel Replacement");
        addCheckBox(majorList, majorServPanel, "Polishing");
        addCheckBox(majorList, majorServPanel, "Recalibrate steering");
        addCheckBox(majorList, majorServPanel, "Clean exhaust");


        requestsPanel.add(normalServPanel, "NORMAL");
        requestsPanel.add(majorServPanel, "MAJOR");
        add(requestsPanel);



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
        add(techniciansTxt);

        JPanel techSelectPanel = new JPanel();
        techSelectPanel.setOpaque(false);
        techSelectPanel.setLayout(new BorderLayout());
        String[] options = {"Ali", "Muthu", "Ah Hock"};
        FloatingComboBox<String> techComboBox = new FloatingComboBox<>(options);
        techComboBox.setPreferredSize(new Dimension(250, 40));
        techComboBox.setMaximumSize(new Dimension(300, 40));
        techSelectPanel.add(techComboBox, BorderLayout.CENTER);
        add(techSelectPanel);
        add(Box.createVerticalStrut(componentSpace));

        JPanel createAppointmentPanel = new JPanel();
        createAppointmentPanel.setOpaque(false);
        createAppointmentPanel.setLayout(new BorderLayout());
        createAppointmentPanel.setMaximumSize(new Dimension(900, 60));
        FloatingButton createAppointment = new FloatingButton("Create Appointment");
        createAppointment.setPreferredSize(new Dimension(300, 50));
        createAppointmentPanel.add(createAppointment, BorderLayout.EAST);
        add(createAppointmentPanel);
    

        createAppointment.addActionListener(e -> {
            listener.onBackToList();
        });
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
