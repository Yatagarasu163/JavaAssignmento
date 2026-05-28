package panes;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;

import components.FloatingPasswordField;
import components.FloatingButton;
import components.TextLabel;
import IO.FileHandler;

public class ForcePasswordChangePage extends JFrame {

    private FloatingPasswordField newPassTxt;
    private FloatingPasswordField confirmPassTxt;
    private FloatingButton updateBtn;
    private JCheckBox showPassBox;
    private String[] trappedUserRow;

    public ForcePasswordChangePage(String[] userData) {
        this.trappedUserRow = userData;

        setTitle("APU-ASC");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(Color.WHITE);
        setContentPane(wrapperPanel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(40, 50, 40, 50));

        TextLabel title = new TextLabel("Welcome, " + trappedUserRow[1] + "!");
        title.setFontSize(28);
        title.setFontType(Font.BOLD);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);

        TextLabel subtitle = new TextLabel("Please set a new secure password to continue.");
        subtitle.setFontSize(14);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(subtitle);
        mainPanel.add(Box.createVerticalStrut(40));

        newPassTxt = new FloatingPasswordField("New Password");
        newPassTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(newPassTxt);
        mainPanel.add(Box.createVerticalStrut(20));

        confirmPassTxt = new FloatingPasswordField("Confirm New Password");
        confirmPassTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(confirmPassTxt);
        mainPanel.add(Box.createVerticalStrut(10));

        showPassBox = new JCheckBox("Show Password");
        showPassBox.setBackground(Color.WHITE);
        showPassBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(showPassBox);
        mainPanel.add(Box.createVerticalStrut(30));

        updateBtn = new FloatingButton("Update & Login", 20);
        updateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(updateBtn);

        wrapperPanel.add(mainPanel);

        showPassBox.addActionListener(e -> {
            if (showPassBox.isSelected()) {
                newPassTxt.setEchoChar((char) 0);
                confirmPassTxt.setEchoChar((char) 0);
            } else {
                newPassTxt.setEchoChar('•');
                confirmPassTxt.setEchoChar('•');
            }
        });

        updateBtn.addActionListener(e -> {
            String newPass = new String(newPassTxt.getPassword());
            String confirmPass = new String(confirmPassTxt.getPassword());

            if (newPass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidPassword(newPass)) {
                JOptionPane.showMessageDialog(this,
                        "Password must be 8-20 characters long, and include at least one uppercase letter, one lowercase letter, and one number (1-9).",
                        "Invalid Password Format",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String defaultPass = components.DefaultPasswordGenerator.generate(trappedUserRow[1], trappedUserRow[0]);
            if (newPass.equals(defaultPass)) {
                JOptionPane.showMessageDialog(this, "You cannot use the default generated password.", "Security Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newPass.equals(trappedUserRow[7])) {
                JOptionPane.showMessageDialog(this, "New password cannot be the same as your old password.", "Security Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<String[]> allUsers = FileHandler.read("Users.txt");
            for (String[] user : allUsers) {
                if (user[0].equals(trappedUserRow[0])) {
                    user[7] = newPass;
                    break;
                }
            }
            FileHandler.write("Users.txt", allUsers, false);

            JOptionPane.showMessageDialog(this, "Password updated successfully! Please log in with your new password.", "Success", JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
            new LoginPage().setVisible(true);
        });
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[1-9]).{8,20}$";
        return password != null && password.matches(regex);
    }
}