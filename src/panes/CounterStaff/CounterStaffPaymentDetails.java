package panes.CounterStaff;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.border.*;
import java.io.IOException;
import panes.CounterStaff.components.PaymentListener;
import components.FloatingButton;
import config.UIConfig;
import components.TextLabel;
import IO.FileHandler;

// PDF Java Jar Import
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;

public class CounterStaffPaymentDetails extends JPanel{

    private PaymentListener listener;
    private String cusName;
    private String carPlate;
    private String appointmentDate;
    private String serviceType;
    private List<String[]> services = new ArrayList<>();
    private String totalAmount;
    private String payAmount; 
    private TextLabel cusNameLbl;
    private TextLabel appointmentDateLbl;
    private TextLabel plateLbl;
    private TextLabel typeLbl;
    private TextLabel totalLbl;
    private TextLabel payLbl;
    private JPanel servicesPanel;
    private String paymentID;
    private FloatingButton payBtn;

    public CounterStaffPaymentDetails(PaymentListener listener) {
        
            this.listener = listener;
           
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.WHITE);
            setBorder(new EmptyBorder(20, 20, 20, 20));
            
            JPanel topPanel = new JPanel();
            topPanel.setOpaque(false);
            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            FloatingButton backButton = new FloatingButton("Back", 20);
            topPanel.add(backButton);
            add(topPanel);
            add(Box.createVerticalStrut(10));

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
            servicesTitle.setFontSize(18);
            servicesTitle.setFontType(Font.BOLD);
            middlePanel.add(servicesTitle);
            middlePanel.add(Box.createVerticalStrut(10));

            servicesPanel = new JPanel();
            servicesPanel.setOpaque(false);
            servicesPanel.setLayout(new BoxLayout(servicesPanel, BoxLayout.Y_AXIS));
            middlePanel.add(servicesPanel);

            for (String[] service: services) {
                middlePanel.add(createServiceRow(service[0], service[1]));
                middlePanel.add(Box.createVerticalStrut(2));
            }

            middlePanel.add(Box.createVerticalStrut(10));

            totalLbl = new TextLabel("Total Amount: " + totalAmount);
            totalLbl.setFontType(Font.BOLD);
            totalLbl.setFontSize(16);
            totalLbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
            middlePanel.add(totalLbl);

            middlePanel.add(Box.createVerticalStrut(10));
            middlePanel.add(new JSeparator());
            middlePanel.add(Box.createVerticalStrut(10));

            payLbl = new TextLabel("Pay: " + payAmount);
            payLbl.setFontType(Font.BOLD);
            payLbl.setFontSize(16);
            payLbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
            middlePanel.add(payLbl);

            add(middlePanel);

            JPanel bottomPanel = new JPanel();
            bottomPanel.setOpaque(false);
            bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            payBtn = new FloatingButton("Make Payment", 20);
            payBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
            bottomPanel.add(payBtn);
            add(bottomPanel);

            setPaymentButtonAction();

            backButton.addActionListener(e -> {
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
            setPaymentButtonAction();

            this.paymentID = paymentID;

            List<String[]> payments = FileHandler.read(FileHandler.payment);
            String[] selectedPayment = null;
            for (String[] payment : payments){
                if (payment[0].equalsIgnoreCase(paymentID)){
                    selectedPayment = payment;
                    break;
                }
            } 
            String customerID = "";
            String vehicleID = selectedPayment[4];
            String appointmentID = selectedPayment[5];
            if(selectedPayment.length > 0){
                customerID = selectedPayment[3];
            } else{
               listener.onBackToList(); 
            }

            List<String[]> users = FileHandler.read(FileHandler.users);
            String[] selectedUser = null;
            for (String[] user : users){
                if (user[0].equalsIgnoreCase(customerID)){
                    selectedUser = user;
                    break;
                }
            }
            
            List<String[]> vehicles = FileHandler.read(FileHandler.vehicles);
            String[] selectedVehicle = null;
            for(String[] vehicle : vehicles) {
                if(vehicle[0].equalsIgnoreCase(vehicleID)){
                    selectedVehicle = vehicle;
                    break;
                }
            }
            
            List<String[]> appointments = FileHandler.read(FileHandler.appointments);
            String[] selectedAppointment = null;
            for(String[] appointment : appointments){
                if(appointment[0].equalsIgnoreCase(appointmentID)){
                    selectedAppointment = appointment;
                    break;
                }
            }
            String[] selectedServices = selectedAppointment[3].split(",");
            List<String[]> prices = FileHandler.read(FileHandler.prices);
            List<String[]> compiledServices = new ArrayList<>();
            double total = 0.00;
            for(String service : selectedServices){
                for (String[] price : prices){
                    if (price[0].equalsIgnoreCase(service)){
                        double priceValue = Double.parseDouble(price[3]);
                        compiledServices.add(new String[]{service, String.format("%.2f", priceValue), price[1]});
                        total += priceValue;
                        break;
                    }
                }
            }
            
            cusName = selectedUser[1] + " " + selectedUser[2];
            carPlate = selectedVehicle[1];
            appointmentDate = selectedAppointment[5];
            serviceType = selectedAppointment[2];
            services = compiledServices;
            totalAmount = String.format("%.2f", total);
            payAmount = totalAmount;

            cusNameLbl.setText(cusName);
            appointmentDateLbl.setText(appointmentDate);
            plateLbl.setText(carPlate);
            typeLbl.setText(serviceType);

            totalLbl.setText("Total Amount: RM" + totalAmount);
            payLbl.setText("Pay: RM" + payAmount);

            servicesPanel.removeAll();

            for (String[] service : services) {
                servicesPanel.add(createServiceRow(service[2], "RM" + service[1]));
                servicesPanel.add(Box.createVerticalStrut(5));
            }

            servicesPanel.revalidate();
            servicesPanel.repaint();
        } else{
            listener.onBackToList();
        }
    }

    public void loadReceipt(String paymentID){
        if(paymentID != null){
            setReceiptButtonAction();

            this.paymentID = paymentID;

            List<String[]> payments = FileHandler.read(FileHandler.payment);
            String[] selectedPayment = null;
            for (String[] payment : payments){
                if (payment[0].equalsIgnoreCase(paymentID)){
                    selectedPayment = payment;
                    break;
                }
            } 
            String customerID = "";
            String vehicleID = selectedPayment[4];
            String appointmentID = selectedPayment[5];
            if(selectedPayment.length > 0){
                customerID = selectedPayment[3];
            } else{
               listener.onBackToList(); 
            }

            List<String[]> users = FileHandler.read(FileHandler.users);
            String[] selectedUser = null;
            for (String[] user : users){
                if (user[0].equalsIgnoreCase(customerID)){
                    selectedUser = user;
                    break;
                }
            }
            
            List<String[]> vehicles = FileHandler.read(FileHandler.vehicles);
            String[] selectedVehicle = null;
            for(String[] vehicle : vehicles) {
                if(vehicle[0].equalsIgnoreCase(vehicleID)){
                    selectedVehicle = vehicle;
                    break;
                }
            }
            
            List<String[]> appointments = FileHandler.read(FileHandler.appointments);
            String[] selectedAppointment = null;
            for(String[] appointment : appointments){
                if(appointment[0].equalsIgnoreCase(appointmentID)){
                    selectedAppointment = appointment;
                    break;
                }
            }
            String[] selectedServices = selectedAppointment[3].split(",");
            List<String[]> prices = FileHandler.read(FileHandler.prices);
            List<String[]> compiledServices = new ArrayList<>();
            double total = 0.00;
            for(String service : selectedServices){
                for (String[] price : prices){
                    if (price[0].equalsIgnoreCase(service)){
                        double priceValue = Double.parseDouble(price[3]);
                        compiledServices.add(new String[]{service, String.format("%.2f", priceValue), price[1]});
                        total += priceValue;
                        break;
                    }
                }
            }
            
            cusName = selectedUser[1] + " " + selectedUser[2];
            carPlate = selectedVehicle[1];
            appointmentDate = selectedAppointment[5];
            serviceType = selectedAppointment[2];
            services = compiledServices;
            totalAmount = String.format("%.2f", total);
            payAmount = totalAmount;

            cusNameLbl.setText(cusName);
            appointmentDateLbl.setText(appointmentDate);
            plateLbl.setText(carPlate);
            typeLbl.setText(serviceType);

            totalLbl.setText("Total Amount: RM" + totalAmount);
            payLbl.setText("Pay: RM" + payAmount);

            servicesPanel.removeAll();

            for (String[] service : services) {
                servicesPanel.add(createServiceRow(service[2], "RM" + service[1]));
                servicesPanel.add(Box.createVerticalStrut(5));
            }
            servicesPanel.revalidate();
            servicesPanel.repaint();
            
        } else{
            listener.onBackToList();
        }
    }

    private void setPaymentButtonAction() {
        for (java.awt.event.ActionListener al : payBtn.getActionListeners()) {
            payBtn.removeActionListener(al);
        }

        payBtn.setText("Make Payment");

        payBtn.addActionListener(e -> {

            List<String[]> payments = FileHandler.read(FileHandler.payment);

            for (String[] payment : payments) {
                if(payment[0].equalsIgnoreCase(paymentID)) {
                    payment[6] = "Paid";
                    break;
                }
            }

            FileHandler.write(FileHandler.payment, payments, false);

            JOptionPane.showMessageDialog(
                this,
                "Payment made successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );

            listener.onBackToList();
        });
    }

    private void setReceiptButtonAction() {

        for (java.awt.event.ActionListener al : payBtn.getActionListeners()) {
            payBtn.removeActionListener(al);
        }

        payBtn.setText("Print Receipt");

        payBtn.addActionListener(e -> {

            writeReceiptToPDF(paymentID, cusName, totalAmount);

            JOptionPane.showMessageDialog(
                this,
                "Receipt printed!",
                "Receipt",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    public void writeReceiptToPDF(String paymentID, String customerName, String amount) {
        String filePath = "src/receipts/" + paymentID + "_Receipt.pdf";
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            document.open();
            document.add(new Paragraph("====================================="));
            document.add(new Paragraph("         APU - ASC RECEIPT           "));
            document.add(new Paragraph("====================================="));
            document.add(new Paragraph("Payment ID: " + paymentID));
            document.add(new Paragraph("Customer: " + customerName));
            document.add(new Paragraph("Total Paid: RM " + amount));
            document.add(new Paragraph("Status: PAID"));
            document.add(new Paragraph("====================================="));
            document.add(new Paragraph("Thank you for choosing APU-ASC!"));

            System.out.println("PDF Receipt generated successfully at: " + filePath);

        } catch (DocumentException | IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }
}
