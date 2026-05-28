package panes;

import javax.swing.*;
import components.FloatingPasswordField;
import components.FloatingTextField;
import java.awt.*;
import java.awt.event.ItemEvent;
import IO.FileHandler;
import java.util.List;
import java.util.ArrayList;

import config.UIConfig;

public class LoginPage extends JFrame {

    private FloatingTextField txtUsername;
    private FloatingPasswordField txtPassword;
    private JButton btnSignIn;

    public LoginPage() {
        setTitle("APU-ASC");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIConfig.whiteBackground);
        mainPanel.setLayout(new GridBagLayout());
        setContentPane(mainPanel);

        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JLabel lblLogo = new JLabel("APU - ASC");
        lblLogo.setFont(new Font("Serif", Font.PLAIN, 36));
        lblLogo.setForeground(UIConfig.mainBackground);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUsername = new FloatingTextField("User ID");
        txtPassword = new FloatingPasswordField("Password");

        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setOpaque(false);
        optionsPanel.setMaximumSize(new Dimension(300, 30));

        JCheckBox chkShowPassword = new JCheckBox("Show Password");
        chkShowPassword.setOpaque(false);
        chkShowPassword.setFont(new Font("SansSerif", Font.BOLD, 11));
        chkShowPassword.setFocusPainted(false);
        chkShowPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));

        chkShowPassword.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });

        JButton forgotPassBtn = new JButton("Forgot Password?");
        forgotPassBtn.setFont(new Font("SansSerif", Font.BOLD, 11));
        forgotPassBtn.setForeground(UIConfig.mainBackground);
        forgotPassBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPassBtn.setContentAreaFilled(false);
        forgotPassBtn.setBorderPainted(false);
        forgotPassBtn.setFocusPainted(false);
        forgotPassBtn.setMargin(new Insets(0, 0, 0, 0));

        forgotPassBtn.addActionListener(e -> {
            this.dispose();
            new ForgotPasswordPage().setVisible(true);
        });

        optionsPanel.add(chkShowPassword, BorderLayout.WEST);
        optionsPanel.add(forgotPassBtn, BorderLayout.EAST);

        btnSignIn = new JButton("Sign In") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                super.paintComponent(g);
                g2.dispose();
            }
        };

        btnSignIn.addActionListener(e -> {
            String inputId = txtUsername.getText().trim();
            String inputPassword = new String(txtPassword.getPassword());

            if (inputId.isEmpty() || inputPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both User ID and Password.", "Missing Input", JOptionPane.WARNING_MESSAGE);
                return;
            }

            java.util.List<String[]> userList = FileHandler.read("Users.txt");
            boolean userFound = false;

            for (String[] row : userList) {
                if (row.length >= 10) {
                    String dbId = row[0].trim();
                    String dbFirstName = row[1].trim();
                    String dbLastName = row[2].trim();
                    String dbUserName = row[3].trim();
                    String dbRole = row[4].trim();
                    String dbPassword = row[7].trim();

                    if (dbId.equalsIgnoreCase(inputId)) {
                        userFound = true;
                        if (dbPassword.equals(inputPassword)) {
                            String defaultPass = components.DefaultPasswordGenerator.generate(dbFirstName, dbId);

                            if (dbPassword.equals(defaultPass)) {
                                JOptionPane.showMessageDialog(this,
                                        "For security reasons, you must change your default password before continuing.",
                                        "First Time Login",
                                        JOptionPane.WARNING_MESSAGE);

                                this.dispose();
                                new ForcePasswordChangePage(row).setVisible(true);
                                return;
                            }

                            try{
                                List<String[]> inputRow = new ArrayList<>();
                                inputRow.add(row);
                                FileHandler.write("CurrentUser.txt", inputRow, false);
                            } catch (Exception ex) {
                                System.out.println("Error saving session: " + ex.getMessage());
                            }

                            JOptionPane.showMessageDialog(this, "Login Successful! Welcome, " + dbUserName, "Success", JOptionPane.INFORMATION_MESSAGE);

                            if (dbRole.equalsIgnoreCase("Customer")) {
                                new panes.Customer.CustomerMainPane().setVisible(true);
                                this.dispose();

                            } else if (dbRole.equalsIgnoreCase("Manager")) {
                                new panes.Manager.ManagerMainPane().setVisible(true);
                                this.dispose();

                            } else if (dbRole.equalsIgnoreCase("Counter Staff") || dbRole.equalsIgnoreCase("CounterStaff")) {
                                new panes.CounterStaff.CounterStaffMainPane().setVisible(true);
                                this.dispose();

                            } else if (dbRole.equalsIgnoreCase("Technician")) {
                                new panes.Technician.TechnicianMainPane(dbId).setVisible(true);
                                this.dispose();

                            } else {
                                JOptionPane.showMessageDialog(this, "Error: Unknown role '" + dbRole + "' found in database.", "Routing Error", JOptionPane.ERROR_MESSAGE);
                            }

                            return;

                        } else {
                            JOptionPane.showMessageDialog(this, "Incorrect Password! Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
            }

            if (!userFound) {
                JOptionPane.showMessageDialog(this, "User ID not found! Please check your ID.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSignIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSignIn.setMaximumSize(new Dimension(300, 45));
        btnSignIn.setPreferredSize(new Dimension(300, 45));
        btnSignIn.setBackground(UIConfig.mainBackground);
        btnSignIn.setForeground(Color.WHITE);
        btnSignIn.setFont(new Font("SansSerif", Font.PLAIN, 18));

        btnSignIn.setContentAreaFilled(false);
        btnSignIn.setFocusPainted(false);
        btnSignIn.setBorderPainted(false);
        btnSignIn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        formPanel.add(lblLogo);
        formPanel.add(Box.createVerticalStrut(40));
        formPanel.add(txtUsername);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(txtPassword);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(optionsPanel);
        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(btnSignIn);

        mainPanel.add(formPanel);
    }
}