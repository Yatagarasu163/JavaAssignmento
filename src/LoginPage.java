import javax.swing.*;

import components.FloatingPasswordField;
import components.FloatingTextField;

import java.awt.*;


public class LoginPage extends JFrame {

    private FloatingTextField txtUsername;
    private FloatingPasswordField txtPassword;
    private JButton btnSignIn;

    public LoginPage() {
        setTitle("Login Page");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // 1. Setup the Main Background Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(248, 248, 250));
        mainPanel.setLayout(new GridBagLayout());
        setContentPane(mainPanel);

        // 2. Setup the Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false); //Set the background to transparent
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // 3. Add Logo / Title
        JLabel lblLogo = new JLabel("🚗 APU - ASC");
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblLogo.setForeground(new Color(100, 100, 255));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 4. Initialize Custom Floating Fields
        txtUsername = new FloatingTextField("User Name");
        txtPassword = new FloatingPasswordField("Password");

        // 5. Initialize Sign In Button
        btnSignIn = new JButton("Sign In");
        btnSignIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSignIn.setMaximumSize(new Dimension(300, 45));
        btnSignIn.setPreferredSize(new Dimension(300, 45));
        btnSignIn.setBackground(new Color(128, 128, 255));
        btnSignIn.setForeground(Color.WHITE);
        btnSignIn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnSignIn.setFocusPainted(false);
        btnSignIn.setBorderPainted(false);
        btnSignIn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 6. Assemble the components with spacing
        formPanel.add(lblLogo);
        formPanel.add(Box.createVerticalStrut(50));
        formPanel.add(txtUsername);
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(txtPassword);
        formPanel.add(Box.createVerticalStrut(40));
        formPanel.add(btnSignIn);

        mainPanel.add(formPanel);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            new LoginPage().setVisible(true);
        });
    }
}
