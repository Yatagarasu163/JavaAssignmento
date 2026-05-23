package panes;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;

import components.FloatingTextField;
import components.FloatingPasswordField;
import components.FloatingButton;
import components.TextLabel;
import IO.FileHandler;

public class ForgotPasswordPage extends JFrame {

    // Verification Fields
    private FloatingTextField userIDTxt;
    private FloatingTextField emailTxt;
    private FloatingButton verifyBtn;

    // Reset Fields (Hidden at first)
    private JPanel resetPanel;
    private FloatingPasswordField newPassTxt;
    private FloatingPasswordField confirmPassTxt;
    private JCheckBox showPassBox;
    private FloatingButton resetBtn;

    // To store the verified user's row data temporarily
    private String[] verifiedUserRow = null;

    public ForgotPasswordPage() {
        setTitle("APU-ASC: Forgot Password");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        // Main Container
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(40, 50, 40, 50));

        // Title
        TextLabel title = new TextLabel("Reset Password");
        title.setFontSize(28);
        title.setFontType(Font.BOLD);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(40));

        // --- STEP 1: VERIFICATION SECTION ---
        userIDTxt = new FloatingTextField("User ID");
        userIDTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(userIDTxt);
        mainPanel.add(Box.createVerticalStrut(20));

        emailTxt = new FloatingTextField("Registered Email");
        emailTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(emailTxt);
        mainPanel.add(Box.createVerticalStrut(30));

        verifyBtn = new FloatingButton("Verify Account", 20);
        verifyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(verifyBtn);

        // --- STEP 2: RESET SECTION (HIDDEN BY DEFAULT) ---
        resetPanel = new JPanel();
        resetPanel.setLayout(new BoxLayout(resetPanel, BoxLayout.Y_AXIS));
        resetPanel.setBackground(Color.WHITE);
        resetPanel.setVisible(false); // Hide it initially!

        resetPanel.add(Box.createVerticalStrut(30));
        resetPanel.add(new JSeparator());
        resetPanel.add(Box.createVerticalStrut(30));

        newPassTxt = new FloatingPasswordField("New Password");
        newPassTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetPanel.add(newPassTxt);
        resetPanel.add(Box.createVerticalStrut(20));

        confirmPassTxt = new FloatingPasswordField("Confirm New Password");
        confirmPassTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetPanel.add(confirmPassTxt);
        resetPanel.add(Box.createVerticalStrut(10));

        showPassBox = new JCheckBox("Show Password");
        showPassBox.setBackground(Color.WHITE);
        showPassBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetPanel.add(showPassBox);
        resetPanel.add(Box.createVerticalStrut(30));

        resetBtn = new FloatingButton("Update Password", 20);
        resetBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetPanel.add(resetBtn);

        mainPanel.add(resetPanel);

        // Back to Login Button
        mainPanel.add(Box.createVerticalStrut(40));
        JButton backBtn = new JButton("Back to Login");
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setForeground(Color.GRAY);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(backBtn);

        add(mainPanel);

        // ==========================================
        // ADDING THE LOGIC & ACTIONS
        // ==========================================

        // 1. Back Button Logic
        backBtn.addActionListener(e -> {
            this.dispose();
            new LoginPage().setVisible(true);
        });

        // 2. Show/Hide Password Toggle
        showPassBox.addActionListener(e -> {
            if (showPassBox.isSelected()) {
                newPassTxt.setEchoChar((char) 0);
                confirmPassTxt.setEchoChar((char) 0);
            } else {
                newPassTxt.setEchoChar('•');
                confirmPassTxt.setEchoChar('•');
            }
        });

        // 3. Verify Button Logic
        verifyBtn.addActionListener(e -> {
            String inputID = userIDTxt.getText().trim();
            String inputEmail = emailTxt.getText().trim();

            List<String[]> users = FileHandler.read("Users.txt");
            boolean found = false;

            for (String[] user : users) {
                // Assuming Index 0 is ID and Index 5 is Email based on your User class
                if (user.length > 5 && user[0].equalsIgnoreCase(inputID) && user[5].equalsIgnoreCase(inputEmail)) {
                    verifiedUserRow = user; // Save the user data
                    found = true;
                    break;
                }
            }

            if (found) {
                // Lock the top fields so they can't change them after verifying
                userIDTxt.setEditable(false);
                emailTxt.setEditable(false);
                verifyBtn.setEnabled(false);

                // Reveal the bottom panel!
                resetPanel.setVisible(true);
                this.revalidate(); // Tells Java to redraw the window
                this.repaint();

                JOptionPane.showMessageDialog(this, "Account verified! You may now enter a new password.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No matching account found. Please check your ID and Email.", "Verification Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 4. Reset Password Button Logic
        resetBtn.addActionListener(e -> {
            String newPass = new String(newPassTxt.getPassword());
            String confirmPass = new String(confirmPassTxt.getPassword());

            if (newPass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update the specific user's password in the database
            List<String[]> allUsers = FileHandler.read("Users.txt");

            for (String[] user : allUsers) {
                if (user[0].equals(verifiedUserRow[0])) {
                    // Assuming password is at index 7 based on your User class structure
                    user[7] = newPass;
                    break;
                }
            }

            // Overwrite the file with the new updated list
            FileHandler.write("Users.txt", allUsers, false);

            JOptionPane.showMessageDialog(this, "Password successfully updated! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
            new LoginPage().setVisible(true);
        });
    }
}