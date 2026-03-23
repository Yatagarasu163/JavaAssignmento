package panes;
import javax.swing.*;
import java.awt.*;
import components.FloatingToggleButton;
import components.TextLabel;

public class SidebarPanel extends JPanel {

    // --- MANAGER BUTTONS ---
    private FloatingToggleButton accountBtn;
    private FloatingToggleButton pricingBtn;
    private FloatingToggleButton editBtn;
    private FloatingToggleButton feedbackBtn;

    // --- CUSTOMER BUTTONS ---
    // Perfect for navigating to the nuggets, fries, and tarts!
    private FloatingToggleButton profileBtn;
    private FloatingToggleButton historyBtn;

    // --- SHARED BUTTONS ---
    private FloatingToggleButton exitBtn = new FloatingToggleButton("Log Out", 20);

    // CONSTRUCTOR 1: The Default (Keeps your teammate's code from breaking)
    public SidebarPanel() {
        this("Manager"); // If no role is passed, assume it's the Manager
    }

    // CONSTRUCTOR 2: The Dynamic Role Selector
    public SidebarPanel(String role) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(128, 128, 255));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        TextLabel appLabel = new TextLabel("APU- ASC");
        int componentSpace = 50;
        ButtonGroup grp = new ButtonGroup();

        add(Box.createVerticalStrut(componentSpace));
        add(appLabel);
        add(Box.createVerticalStrut(componentSpace));

        // --- THE IF-ELSE ROLE LOGIC ---
        if (role.equalsIgnoreCase("Manager")) {
            accountBtn = new FloatingToggleButton("Account Creation", 20);
            pricingBtn = new FloatingToggleButton("Profile", 20);
            editBtn = new FloatingToggleButton("Edit Profile", 20);
            feedbackBtn = new FloatingToggleButton("Feedback and Comments", 20);

            grp.add(accountBtn);
            grp.add(pricingBtn);
            grp.add(editBtn);
            grp.add(feedbackBtn);

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

            grp.add(profileBtn);
            grp.add(historyBtn);

            add(profileBtn);
            add(Box.createVerticalStrut(componentSpace));
            add(historyBtn);
            add(Box.createVerticalStrut(componentSpace));
        }

        // Add the shared Exit button at the bottom for everyone
        grp.add(exitBtn);
        add(exitBtn);
        add(Box.createVerticalStrut(componentSpace));
    }

    // --- GETTERS FOR MANAGER ---
    public JToggleButton getPricingBtn() { return this.pricingBtn; }
    public JToggleButton getAccountBtn() { return this.accountBtn; }
    public JToggleButton getEditBtn() { return this.editBtn; }
    public JToggleButton getFeedbackBtn() { return this.feedbackBtn; }

    // --- GETTERS FOR CUSTOMER ---
    public JToggleButton getProfileBtn() { return this.profileBtn; }
    public JToggleButton getHistoryBtn() { return this.historyBtn; }

    // --- SHARED GETTERS ---
    public JToggleButton getExitBtn() { return this.exitBtn; }
}

