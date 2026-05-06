package panes.Customer;

import IO.FileHandler;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CustomerPaymentDetailsPane extends JPanel {

    private final Color primaryPurple = new Color(128, 128, 255);
    private final Color bgColor = new Color(250, 250, 255);
    private JButton backBtn;

    private String loggedInCustomerID;
    private String appointmentId;
    private String vehicleId;

    public CustomerPaymentDetailsPane(JPanel cardsContainer, CardLayout cardLayout, String CusID, String AppId, String VehId) {
        this.loggedInCustomerID = CusID;
        this.appointmentId = AppId;
        this.vehicleId = VehId;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(bgColor);
        setBorder(new EmptyBorder(30, 40, 40, 40));

        String invoiceNo = "N/A";
        String invoiceDate = "N/A";
        String custName = "Customer";
        String apptDate = "N/A";
        String plateNo = "N/A";
        String serviceType = "Unknown Service";
        String totalAmountStr = "RM 0.00";
        String serviceIDs = "";

        List<String[]> paymentList = FileHandler.read("Payment.txt");
        List<String[]> customerList = FileHandler.read("Users.txt");
        List<String[]> vehicleList = FileHandler.read("Vehicle.txt");
        List<String[]> apptList = FileHandler.read("Appointment.txt");
        List<String[]> priceList = FileHandler.read("Price.txt");

        for (String[] row : paymentList) {
            if (row.length >= 6 && row[5].equals(appointmentId)) {
                invoiceNo = row[0];
                totalAmountStr = "RM " + row[1];
                invoiceDate = row[2];
                break;
            }
        }

        for (String[] row : customerList) {
            if (row[0].equals(loggedInCustomerID)) {
                custName = row[1];
                break;
            }
        }

        for (String[] row : vehicleList) {
            if (row[0].equals(vehicleId)) {
                plateNo = row[1];
                break;
            }
        }

        for (String[] row : apptList) {
            if (row[0].equals(appointmentId)) {
                serviceType = row[2];
                if (row.length > 3) {
                    serviceIDs = row[3];
                }
                if (row.length > 5) {
                    apptDate = row[5];
                }
                break;
            }
        }

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setBackground(bgColor);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        backBtn = new JButton("←");
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 24));
        backBtn.setForeground(Color.DARK_GRAY);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
        backBtn.setPreferredSize(new Dimension(40, 40));

        topPanel.add(backBtn);

        JPanel receiptCard = new JPanel();
        receiptCard.setLayout(new BoxLayout(receiptCard, BoxLayout.Y_AXIS));
        receiptCard.setBackground(Color.WHITE);
        receiptCard.setMaximumSize(new Dimension(800, 500));
        receiptCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primaryPurple, 2, true),
                new EmptyBorder(30, 40, 30, 40)
        ));

        JPanel headerPanel = new JPanel(new GridLayout(3, 4, 15, 15));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        addHeaderData(headerPanel, "Invoice No:", invoiceNo);
        addHeaderData(headerPanel, "Invoice Date:", invoiceDate);
        addHeaderData(headerPanel, "Customer Name:", custName);
        addHeaderData(headerPanel, "Appointment Date:", apptDate);
        addHeaderData(headerPanel, "Car plate number:", plateNo);
        addHeaderData(headerPanel, "Service Type:", serviceType);

        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setForeground(Color.GRAY);
        separator1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        JLabel servicesTitle = new JLabel("<html><u>Services</u></html>");
        servicesTitle.setFont(new Font("Serif", Font.BOLD, 20));
        servicesTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleRow.setBackground(Color.WHITE);
        titleRow.add(servicesTitle);
        titleRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        contentPanel.add(titleRow);
        contentPanel.add(Box.createVerticalStrut(15));

        if (serviceIDs != null && !serviceIDs.isEmpty()) {
            String[] individualIds = serviceIDs.split(",");

            for (String id : individualIds) {
                String cleanId = id.trim();

                for (String[] priceRow : priceList) {
                    if (priceRow.length >= 4 && priceRow[0].equals(cleanId)) {
                        String itemName = priceRow[1];
                        double itemPrice = Double.parseDouble(priceRow[3]);
                        String formattedPrice = String.format("RM %.2f", itemPrice);

                        contentPanel.add(createServiceRow(itemName, formattedPrice));
                        contentPanel.add(Box.createVerticalStrut(10));
                        break; // Stop looping once we found the match for this ID
                    }
                }
            }
        } else {
            contentPanel.add(createServiceRow("No Services Listed", "RM 0.00"));
            contentPanel.add(Box.createVerticalStrut(10));
        }

        JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
        separator2.setForeground(Color.GRAY);
        separator2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel totalLabel = new JLabel("Total amount: ");
        totalLabel.setFont(new Font("Serif", Font.BOLD, 18));

        JLabel totalAmountLbl = new JLabel(totalAmountStr);
        totalAmountLbl.setFont(new Font("Serif", Font.BOLD, 18));

        footerPanel.add(totalLabel);
        footerPanel.add(totalAmountLbl);

        receiptCard.add(headerPanel);
        receiptCard.add(Box.createVerticalStrut(15));
        receiptCard.add(separator1);
        receiptCard.add(Box.createVerticalStrut(20));
        receiptCard.add(contentPanel);
        receiptCard.add(Box.createVerticalGlue());
        receiptCard.add(Box.createVerticalStrut(40));
        receiptCard.add(separator2);
        receiptCard.add(Box.createVerticalStrut(15));
        receiptCard.add(footerPanel);

        add(topPanel);
        add(Box.createVerticalStrut(20));
        add(receiptCard);

        backBtn.addActionListener(e -> {
            cardLayout.show(cardsContainer, "HISTORY");
        });
    }

    private void addHeaderData(JPanel panel, String fixedLabel, String dynamicValue) {
        JLabel lblFixed = new JLabel(fixedLabel);
        lblFixed.setFont(new Font("Serif", Font.BOLD, 14));

        JLabel lblDynamic = new JLabel(dynamicValue);
        lblDynamic.setFont(new Font("Serif", Font.PLAIN, 14));

        panel.add(lblFixed);
        panel.add(lblDynamic);
    }

    private JPanel createServiceRow(String serviceName, String price) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JLabel nameLabel = new JLabel(serviceName);
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 16));

        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("Serif", Font.BOLD, 16));

        row.add(nameLabel, BorderLayout.WEST);
        row.add(priceLabel, BorderLayout.EAST);

        return row;
    }
}