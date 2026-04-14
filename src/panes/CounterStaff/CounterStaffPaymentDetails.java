package panes.CounterStaff;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.border.*;

import panes.CounterStaff.components.PaymentListener;
import components.FloatingButton;
import config.UIConfig;
import components.TextLabel;

public class CounterStaffPaymentDetails extends JPanel{

    private PaymentListener listener;
    private String cusName;
    private String carPlate;
    private String appointmentDate;
    private String serviceType;
    private List<String[]> services = new ArrayList<>(); //{service + price}
    private String totalAmount;
    private String payAmount; 

    public CounterStaffPaymentDetails(PaymentListener listener) {
            this.listener = listener;
           
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.WHITE);
            setBorder(new EmptyBorder(20, 20, 20, 20));
            
            JPanel topPanel = new JPanel();
            topPanel.setOpaque(false);
            topPanel.setLayout(new BorderLayout(10, 10));
            FloatingButton backButton = new FloatingButton("Back");
            topPanel.add(backButton, BorderLayout.WEST);
            add(topPanel);
            add(Box.createVerticalStrut(50));

            JPanel middlePanel = new JPanel();
            middlePanel.setOpaque(false);
            middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
            middlePanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UIConfig.mainBackground, 4, true),
                new EmptyBorder(20, 20, 20, 20)
            ));
            JPanel middleTopPanel = new JPanel();
            middleTopPanel.setOpaque(false);
            middleTopPanel.setLayout(new GridLayout(2, 4));
            TextLabel cusNameTxt = new TextLabel("Customer Name: ");
            TextLabel cusNameLbl = new TextLabel(cusName);
            middleTopPanel.add(cusNameTxt);
            middleTopPanel.add(cusNameLbl);

            TextLabel appointmentDateTxt = new TextLabel("Appoinment Date: ");
            TextLabel appointmentDateLbl = new TextLabel(appointmentDate);
            middleTopPanel.add(appointmentDateTxt);
            middleTopPanel.add(appointmentDateLbl);

            TextLabel plateTxt = new TextLabel("Car Plate Number: ");
            TextLabel plateLbl = new TextLabel(carPlate);
            middleTopPanel.add(plateTxt);
            middleTopPanel.add(plateLbl);

            TextLabel typeTxt = new TextLabel("Service Type: ");
            TextLabel typeLbl = new TextLabel(serviceType);
            middleTopPanel.add(typeTxt);
            middleTopPanel.add(typeLbl);
            middlePanel.add(middleTopPanel);

            middlePanel.add(Box.createVerticalStrut(10));
            middlePanel.add(new JSeparator());
            middlePanel.add(Box.createVerticalStrut(10));

            TextLabel servicesTitle = new TextLabel("Services");
            servicesTitle.setFontSize(18);
            servicesTitle.setFontType(Font.BOLD);
            middlePanel.add(servicesTitle);
            middlePanel.add(Box.createVerticalStrut(10));

            for (String[] service: services) {
                middlePanel.add(createServiceRow(service[0], service[1]));
                middlePanel.add(Box.createVerticalStrut(5));
            }

            middlePanel.add(Box.createVerticalStrut(20));

            TextLabel totalLbl = new TextLabel("Total Amount: " + totalAmount);
            totalLbl.setFontType(Font.BOLD);
            totalLbl.setFontSize(16);
            totalLbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
            middlePanel.add(totalLbl);

            middlePanel.add(Box.createVerticalStrut(10));
            middlePanel.add(new JSeparator());
            middlePanel.add(Box.createVerticalStrut(10));

            TextLabel payLbl = new TextLabel("Pay: " + payAmount);
            payLbl.setFontType(Font.BOLD);
            payLbl.setFontSize(16);
            payLbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
            middlePanel.add(payLbl);

            add(middlePanel);

            JPanel bottomPanel = new JPanel();
            bottomPanel.setOpaque(false);
            bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            FloatingButton payBtn = new FloatingButton("Make Payment", 20);
            payBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
            bottomPanel.add(payBtn);
            add(bottomPanel);

            payBtn.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Payment Status", "Payment made successfully!", JOptionPane.INFORMATION_MESSAGE);
                listener.onBackToList();
            });




    }

    private JPanel createServiceRow(String service, String price){
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        TextLabel name = new TextLabel(service);
        TextLabel servPrice = new TextLabel(price);

        p.add(name, BorderLayout.WEST);
        p.add(servPrice, BorderLayout.EAST);

        return p;

    }

    public void loadPayment(String paymentID){
        if(paymentID != null){
            //Read data from file handler here and handle the output
            cusName = "Sum Ting Wong";
            carPlate = "ABC 1234";
            appointmentDate = "23 Feb 2026";
            serviceType = "Major Service";
            services = new ArrayList<>(List.of(new String[]{"Brake Plate Replacement", "RM3,500.00"}, new String[]{"Fluid Check", "RM9,500.00"}, new String[]{"Oil Filter Replacement", "RM 1,500.00"}));
            totalAmount = "RM16,200.00";
            payAmount = totalAmount;
        } else{
            listener.onBackToList();
        }
    }
}
