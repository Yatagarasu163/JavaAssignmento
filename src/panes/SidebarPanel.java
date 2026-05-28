package panes;
import javax.swing.*;

import java.awt.*;
import components.FloatingToggleButton;
import IO.FileHandler;
import java.util.List;
import java.util.ArrayList;

public class SidebarPanel extends JPanel {

    private ButtonGroup navGroup;

    // --- SHARED BUTTONS ---
    private JButton homeBtn;
    private FloatingToggleButton exitBtn = new FloatingToggleButton("Log Out", 20);

    // --- MANAGER BUTTONS ---
    private FloatingToggleButton accountBtn;
    private FloatingToggleButton pricingBtn;
    private FloatingToggleButton editBtn;
    private FloatingToggleButton feedbackBtn;

    // --- CUSTOMER BUTTONS ---
    private FloatingToggleButton profileBtn;
    private FloatingToggleButton historyBtn;

    // --- COUNTER STAFF BUTTONS ---
    private FloatingToggleButton customerListBtn;
    private FloatingToggleButton appointmentBtn;
    private FloatingToggleButton paymentBtn;
    private FloatingToggleButton vehicleListBtn;

    // --- TECHNICIAN BUTTONS ---
    private FloatingToggleButton myProfileBtn;

    public SidebarPanel() {
        this("Manager");
    }

    public SidebarPanel(String role) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(128, 128, 255));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        homeBtn = new JButton("APU - ASC");
        homeBtn.setForeground(Color.BLACK);
        homeBtn.setFont(new Font("SansSerif", Font.BOLD, 28));
        homeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        homeBtn.setContentAreaFilled(false);
        homeBtn.setBorderPainted(false);
        homeBtn.setFocusPainted(false);

        int componentSpace = 50;
        navGroup = new ButtonGroup();

        add(Box.createVerticalStrut(componentSpace));
        add(homeBtn);
        add(Box.createVerticalStrut(componentSpace));

        if (role.equalsIgnoreCase("Manager")) {
            accountBtn = new FloatingToggleButton("Accounts", 20);
            pricingBtn = new FloatingToggleButton("Pricing", 20);
            profileBtn = new FloatingToggleButton("Profile", 20);
            feedbackBtn = new FloatingToggleButton("Feedback and Comments", 20);

            navGroup.add(accountBtn);
            navGroup.add(pricingBtn);
            navGroup.add(profileBtn);
            navGroup.add(feedbackBtn);

            add(accountBtn);
            add(Box.createVerticalStrut(componentSpace));
            add(pricingBtn);
            add(Box.createVerticalStrut(componentSpace));
            add(profileBtn);
            add(Box.createVerticalStrut(componentSpace));
            add(feedbackBtn);
            add(Box.createVerticalStrut(componentSpace));

        } else if (role.equalsIgnoreCase("Customer")) {
            profileBtn = new FloatingToggleButton("Profile", 20);
            historyBtn = new FloatingToggleButton("History", 20);

            navGroup.add(profileBtn);
            navGroup.add(historyBtn);

            add(profileBtn);
            add(Box.createVerticalStrut(componentSpace));
            add(historyBtn);
            add(Box.createVerticalStrut(componentSpace));

        } else if (role.equalsIgnoreCase("Counter Staff")) {
            profileBtn = new FloatingToggleButton("Profile", 20);
            customerListBtn = new FloatingToggleButton("Customer List", 20);
            appointmentBtn = new FloatingToggleButton("Appointment", 20);
            paymentBtn = new FloatingToggleButton("Payment", 20);
            vehicleListBtn = new FloatingToggleButton("Vehicle List", 20);

            navGroup.add(profileBtn);
            navGroup.add(customerListBtn);
            navGroup.add(appointmentBtn);
            navGroup.add(paymentBtn);
            navGroup.add(vehicleListBtn);

            add(profileBtn);
            add(Box.createVerticalStrut(componentSpace));
            add(customerListBtn);
            add(Box.createVerticalStrut(componentSpace));
            add(appointmentBtn);
            add(Box.createVerticalStrut(componentSpace));
            add(paymentBtn);
            add(Box.createVerticalStrut(componentSpace));
            add(vehicleListBtn);
            add(Box.createVerticalStrut(componentSpace));

        } else if (role.equalsIgnoreCase("Technician")) {
            myProfileBtn = new FloatingToggleButton("My Profile", 20);
            appointmentBtn = new FloatingToggleButton("Appointments", 20);

            navGroup.add(myProfileBtn);
            navGroup.add(appointmentBtn);

            add(myProfileBtn);
            add(Box.createVerticalStrut(componentSpace));
            add(appointmentBtn);
            add(Box.createVerticalStrut(componentSpace));
        }

        navGroup.add(exitBtn);
        add(exitBtn);
        add(Box.createVerticalStrut(componentSpace));

        exitBtn.addActionListener(e -> {
            Window currentWindow = SwingUtilities.getWindowAncestor(this);

            int confirm = JOptionPane.showConfirmDialog(currentWindow,
                    "Are you sure you want to log out?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try{
                    List<String[]> empty = new ArrayList<>();
                    FileHandler.write("CurrentUser.txt", empty, false);
                } catch (Exception ex) {
                    System.out.println("Error clearing session: " + ex.getMessage());
                }

                if (currentWindow != null) {
                    currentWindow.dispose();
                }

                new LoginPage().setVisible(true);

            } else {
                clearSelection();
            }
        });
    }

    public void clearSelection() {
        if (navGroup != null) {
            navGroup.clearSelection();
        }
    }

    public JButton getHomeBtn() {
        return this.homeBtn;
    }

    public JToggleButton getExitBtn() {
        return this.exitBtn;
    }

    public JToggleButton getPricingBtn() {
        return this.pricingBtn;
    }

    public JToggleButton getAccountBtn() {
        return this.accountBtn;
    }

    public JToggleButton getEditBtn() {
        return this.editBtn;
    }

    public JToggleButton getFeedbackBtn() {
        return this.feedbackBtn;
    }

    // --- GETTERS FOR CUSTOMER ---
    public JToggleButton getProfileBtn() {
        return this.profileBtn;
    }

    public JToggleButton getHistoryBtn() {
        return this.historyBtn;
    }

    // --- GETTERS FOR COUNTER STAFF ---
    public JToggleButton getCustomerListBtn() { return this.customerListBtn; }
    public JToggleButton getAppointmentBtn() { return this.appointmentBtn; }
    public JToggleButton getPaymentBtn() { return this.paymentBtn; }
    public JToggleButton getVehicleListBtn() { return this.vehicleListBtn; }

    // --- GETTERS FOR TECHNICIAN ---
    public JToggleButton getTechProfileBtn() {return this.myProfileBtn;}
}