package panes.Customer;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CustomerPaymentDetailsPane extends JPanel {

    private final Color primaryPurple = new Color(128, 128, 255);
    private final Color bgColor = new Color(250, 250, 255);
    private JButton backBtn;

    public CustomerPaymentDetailsPane() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(bgColor);
        setBorder(new EmptyBorder(30, 40, 40, 40));

        // --- 1. BACK BUTTON ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setBackground(bgColor);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Using a Unicode arrow for the icon
        backBtn = new JButton("\u2190");
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 24));
        backBtn.setForeground(Color.DARK_GRAY);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);

        // Draw a circle around the arrow (like your mockup)
        backBtn.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
        backBtn.setPreferredSize(new Dimension(40, 40));

        topPanel.add(backBtn);

        // --- 2. RECEIPT CARD CONTAINER ---
        JPanel receiptCard = new JPanel();
        receiptCard.setLayout(new BoxLayout(receiptCard, BoxLayout.Y_AXIS));
        receiptCard.setBackground(Color.WHITE);
        receiptCard.setMaximumSize(new Dimension(800, 500)); // Keeps the receipt from stretching too tall
        receiptCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primaryPurple, 2, true),
                new EmptyBorder(30, 40, 30, 40) // Internal padding of the receipt
        ));

        // --- 2i. RECEIPT HEADER ---
        JPanel headerPanel = new JPanel(new GridLayout(3, 4, 15, 15));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Hardcoded data based on your mockup
        addHeaderData(headerPanel, "Invoice No:", "IN245425");
        addHeaderData(headerPanel, "Invoice Date:", "5 March 2026");
        addHeaderData(headerPanel, "Customer Name:", "Sum Ting Wong");
        addHeaderData(headerPanel, "Appointment Date:", "23 Feb 2026");
        addHeaderData(headerPanel, "Car plate number:", "ABC 1234");
        addHeaderData(headerPanel, "Service Type:", "Major Service");

        // --- DIVIDER 1 ---
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setForeground(Color.GRAY);
        separator1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        // --- 2ii. RECEIPT CONTENT (Services) ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        JLabel servicesTitle = new JLabel("<html><u>Services</u></html>"); // HTML trick to underline text
        servicesTitle.setFont(new Font("Serif", Font.BOLD, 20));
        servicesTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleRow.setBackground(Color.WHITE);
        titleRow.add(servicesTitle);

        contentPanel.add(titleRow);
        contentPanel.add(Box.createVerticalStrut(15));

        // Hardcoded service list
        contentPanel.add(createServiceRow("Brake Pad Replacement", "RM 3,500.00"));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createServiceRow("Fluid Check", "RM 9,500.00"));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createServiceRow("Oil Filter Replacement", "RM 1,500.00"));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createServiceRow("Balancing Checking", "RM 1,500.00"));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createServiceRow("Air-con Filter Replacement", "RM 3,200.00"));

        // --- DIVIDER 2 ---
        JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
        separator2.setForeground(Color.GRAY);
        separator2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        // --- 2iii. RECEIPT FOOTER ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel totalLabel = new JLabel("Total amount: ");
        totalLabel.setFont(new Font("Serif", Font.BOLD, 18));

        JLabel totalAmount = new JLabel("RM 16,200.00");
        totalAmount.setFont(new Font("Serif", Font.BOLD, 18));

        footerPanel.add(totalLabel);
        footerPanel.add(totalAmount);

        // --- ASSEMBLE RECEIPT CARD ---
        receiptCard.add(headerPanel);
        receiptCard.add(Box.createVerticalStrut(15));
        receiptCard.add(separator1);
        receiptCard.add(Box.createVerticalStrut(20));
        receiptCard.add(contentPanel);
        receiptCard.add(Box.createVerticalStrut(40)); // Push footer down
        receiptCard.add(separator2);
        receiptCard.add(Box.createVerticalStrut(15));
        receiptCard.add(footerPanel);

        // --- ASSEMBLE ENTIRE PAGE ---
        add(topPanel);
        add(Box.createVerticalStrut(20));
        add(receiptCard);
    }

    // --- HELPER: Adds fixed label and dynamic value to the header Grid ---
    private void addHeaderData(JPanel panel, String fixedLabel, String dynamicValue) {
        JLabel lblFixed = new JLabel(fixedLabel);
        lblFixed.setFont(new Font("Serif", Font.BOLD, 14));

        JLabel lblDynamic = new JLabel(dynamicValue);
        lblDynamic.setFont(new Font("Serif", Font.PLAIN, 14));

        panel.add(lblFixed);
        panel.add(lblDynamic);
    }

    // --- HELPER: Creates a row with Name on Left, Price on Right ---
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

    // Getter so the MainPane can attach an ActionListener to switch screens
    public JButton getBackBtn() {
        return backBtn;
    }
}