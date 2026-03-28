package panes;
import javax.swing.*;
import java.awt.*;
import components.FloatingToggleButton;
// You can remove the TextLabel import if you aren't using it anywhere else!

public class SidebarPanel extends JPanel {

    private ButtonGroup navGroup;

    // --- SHARED BUTTONS ---
    // Added the home button (logo) here so we can access it later
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

    // --- TECHNICIAN BUTTONS--//
    private FloatingToggleButton myProfileBtn;
    private FloatingToggleButton appointmentBtn;


    // CONSTRUCTOR 1: The Default (Keeps your teammate's code from breaking)
    public SidebarPanel() {
        this("Manager"); // If no role is passed, assume it's the Manager
    }

    // CONSTRUCTOR 2: The Dynamic Role Selector
    public SidebarPanel(String role) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(128, 128, 255));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- CREATE THE LOGO / HOME BUTTON ---
        homeBtn = new JButton("APU - ASC");
        homeBtn.setForeground(Color.BLACK);
        homeBtn.setFont(new Font("SansSerif", Font.BOLD, 28)); // Match your TextLabel size
        homeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Show the pointing finger

        // These 3 lines make it look like text instead of a chunky button
        homeBtn.setContentAreaFilled(false);
        homeBtn.setBorderPainted(false);
        homeBtn.setFocusPainted(false);

        int componentSpace = 50;
        navGroup = new ButtonGroup();

        add(Box.createVerticalStrut(componentSpace));

        // Add the new clickable logo instead of the TextLabel
        add(homeBtn);

        add(Box.createVerticalStrut(componentSpace));

        // --- THE IF-ELSE ROLE LOGIC ---
        if (role.equalsIgnoreCase("Manager")) {
            accountBtn = new FloatingToggleButton("Account Creation", 20);
            pricingBtn = new FloatingToggleButton("Profile", 20);
            editBtn = new FloatingToggleButton("Edit Profile", 20);
            feedbackBtn = new FloatingToggleButton("Feedback and Comments", 20);

            navGroup.add(accountBtn);
            navGroup.add(pricingBtn);
            navGroup.add(editBtn);
            navGroup.add(feedbackBtn);

            add(accountBtn);
            add(Box.createVerticalStrut(componentSpace));
            add(pricingBtn);
            add(Box.createVerticalStrut(componentSpace));
            add(editBtn);
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

        // Add the shared Exit button at the bottom for everyone
        navGroup.add(exitBtn);
        add(exitBtn);
        add(Box.createVerticalStrut(componentSpace));
    }

    public void clearSelection() {
        if (navGroup != null) {
            navGroup.clearSelection();
        }
    }

    // --- SHARED GETTERS ---
    public JButton getHomeBtn() { return this.homeBtn; } // New getter for the logo!
    public JToggleButton getExitBtn() { return this.exitBtn; }

    // --- GETTERS FOR MANAGER ---
    public JToggleButton getPricingBtn() { return this.pricingBtn; }
    public JToggleButton getAccountBtn() { return this.accountBtn; }
    public JToggleButton getEditBtn() { return this.editBtn; }
    public JToggleButton getFeedbackBtn() { return this.feedbackBtn; }

    // --- GETTERS FOR CUSTOMER ---
    public JToggleButton getProfileBtn() { return this.profileBtn; }
    public JToggleButton getHistoryBtn() { return this.historyBtn; }
}