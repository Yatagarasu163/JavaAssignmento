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
    private String cusName = "";
    private String carPlate = "";
    private String appointmentDate = "";
    private String serviceType = "";
    private List<String[]> services = new ArrayList<>(); //{service + price}
    private JPanel servicesPanel;
    private String totalAmount = "";
    private String payAmount = "";
    private TextLabel cusNameLbl;
    private TextLabel appointmentDateLbl;
    private TextLabel plateLbl;
    private TextLabel typeLbl;
    private TextLabel totalLbl;
    private TextLabel payLbl;
    private CardLayout cardLayout;
    private JPanel bottomPanel;

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
            topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
            add(topPanel);
            add(Box.createVerticalStrut(50));

            backButton.addActionListener(e -> {
                listener.onBackToList();
            });

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
            cusNameLbl = new TextLabel(cusName);
            middleTopPanel.add(cusNameTxt);
            middleTopPanel.add(cusNameLbl);

            TextLabel appointmentDateTxt = new TextLabel("Appoinment Date: ");
            appointmentDateLbl = new TextLabel(appointmentDate);
            middleTopPanel.add(appointmentDateTxt);
            middleTopPanel.add(appointmentDateLbl);

            TextLabel plateTxt = new TextLabel("Car Plate Number: ");
            plateLbl = new TextLabel(carPlate);
            middleTopPanel.add(plateTxt);
            middleTopPanel.add(plateLbl);

            TextLabel typeTxt = new TextLabel("Service Type: ");
            typeLbl = new TextLabel(serviceType);
            middleTopPanel.add(typeTxt);
            middleTopPanel.add(typeLbl);
            middlePanel.add(middleTopPanel);

            middlePanel.add(Box.createVerticalStrut(10));
            middlePanel.add(new JSeparator());
            middlePanel.add(Box.createVerticalStrut(10));

            TextLabel servicesTitle = new TextLabel("Services");
            servicesTitle.setFontSize(25);
            servicesTitle.setFontType(Font.BOLD);
            middlePanel.add(servicesTitle);
            middlePanel.add(Box.createVerticalStrut(10));
            
            servicesPanel = new JPanel();
            servicesPanel.setLayout(new BoxLayout(servicesPanel, BoxLayout.Y_AXIS));
            servicesPanel.setOpaque(false);
            middlePanel.add(servicesPanel);

            middlePanel.add(Box.createVerticalStrut(20));

            totalLbl = new TextLabel("Total Amount: " + totalAmount);
            totalLbl.setFontType(Font.BOLD);
            totalLbl.setFontSize(20);
            totalLbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
            middlePanel.add(totalLbl);

            middlePanel.add(Box.createVerticalStrut(10));
            middlePanel.add(new JSeparator());
            middlePanel.add(Box.createVerticalStrut(10));

            payLbl = new TextLabel("Pay: " + payAmount);
            payLbl.setFontType(Font.BOLD);
            payLbl.setFontSize(20);
            payLbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
            middlePanel.add(payLbl);

            add(middlePanel);

            bottomPanel = new JPanel();
            cardLayout = new CardLayout();
            bottomPanel.setOpaque(false);
            bottomPanel.setLayout(cardLayout);
            

            JPanel makePaymentPanel = new JPanel();
            makePaymentPanel.setOpaque(false);
            makePaymentPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            FloatingButton payBtn = new FloatingButton("Make Payment", 20);
            payBtn.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Payment Successful!", "Successfully made payment!", JOptionPane.INFORMATION_MESSAGE);
                listener.onBackToList();
            });
            makePaymentPanel.add(payBtn);
            bottomPanel.add(makePaymentPanel, "PAYMENT");

            JPanel printReceiptPanel = new JPanel();
            printReceiptPanel.setOpaque(false);
            printReceiptPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            FloatingButton receiptBtn = new FloatingButton("Print Receipt", 20);
            receiptBtn.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Receipt Successful!", "Printed the receipt!", JOptionPane.INFORMATION_MESSAGE);
            });
            printReceiptPanel.add(receiptBtn);
            bottomPanel.add(printReceiptPanel, "RECEIPT");
            add(bottomPanel);

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

    public void loadPayment(String paymentID, boolean hasPaid){
        if(paymentID != null){
            System.out.println("Opened!");
            //Read data from file handler here and handle the output
            cusName = "Sum Ting Wong";
            cusNameLbl.setText(cusName);
            carPlate = "ABC 1234";
            plateLbl.setText(carPlate);
            appointmentDate = "23 Feb 2026";
            appointmentDateLbl.setText(appointmentDate);
            serviceType = "Major Service";
            typeLbl.setText(serviceType);


            servicesPanel.removeAll();
            services = new ArrayList<>(List.of(new String[]{"Brake Plate Replacement", "RM3,500.00"}, new String[]{"Fluid Check", "RM9,500.00"}, new String[]{"Oil Filter Replacement", "RM 1,500.00"}));
            for (String[] service : services){
                servicesPanel.add(createServiceRow(service[0], service[1]));
                servicesPanel.add(Box.createVerticalStrut(5));
            }

            servicesPanel.revalidate();
            servicesPanel.repaint();           
            
            
            totalAmount = "RM16,200.00";
            totalLbl.setText("Total Amount: " + totalAmount);
            payAmount = totalAmount;
            payLbl.setText("Pay: " + payAmount);
        } else{
            listener.onBackToList();
        }

        if(hasPaid){
            cardLayout.show(bottomPanel, "RECEIPT");
        } else{
            cardLayout.show(bottomPanel, "PAYMENT");
        }
    }
}
