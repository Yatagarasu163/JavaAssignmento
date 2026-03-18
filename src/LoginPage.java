import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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

    // --- INNER CLASS: FLOATING TEXT FIELD ---
    class FloatingTextField extends JTextField {
        private String placeholder;

        public FloatingTextField(String placeholder) {
            this.placeholder = placeholder;
            setOpaque(false);
            setMaximumSize(new Dimension(300, 50));
            setPreferredSize(new Dimension(300, 50));
            setFont(new Font("SansSerif", Font.PLAIN, 16));
            setForeground(Color.DARK_GRAY);
            setCaretColor(new Color(128, 128, 255));

            setBorder(BorderFactory.createCompoundBorder(
                    new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY),
                    new EmptyBorder(20, 0, 5, 0)
            ));

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(
                            new MatteBorder(0, 0, 2, 0, new Color(128, 128, 255)),
                            new EmptyBorder(20, 0, 5, 0)
                    ));
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(
                            new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY),
                            new EmptyBorder(20, 0, 5, 0)
                    ));
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getText().isEmpty() && !hasFocus()) {
                g2d.setFont(new Font("SansSerif", Font.PLAIN, 16));
                g2d.setColor(Color.GRAY);
                g2d.drawString(placeholder, 0, 32);
            } else {
                g2d.setFont(new Font("SansSerif", Font.BOLD, 11));
                g2d.setColor(new Color(128, 128, 255));
                g2d.drawString(placeholder, 0, 15);
            }
        }
    }

    // --- INNER CLASS: FLOATING PASSWORD FIELD WITH TOGGLE ---
    class FloatingPasswordField extends JPasswordField {
        private String placeholder;
        private JButton toggleButton;
        private char defaultEchoChar = '•';

        public FloatingPasswordField(String placeholder) {
            this.placeholder = placeholder;
            setOpaque(false);
            setMaximumSize(new Dimension(300, 50));
            setPreferredSize(new Dimension(300, 50));
            setFont(new Font("SansSerif", Font.PLAIN, 16));
            setForeground(Color.DARK_GRAY);
            setCaretColor(new Color(128, 128, 255));
            setEchoChar(defaultEchoChar); // Mask by default

            // Notice the right padding is now 40 to prevent text from going under the button
            setBorder(BorderFactory.createCompoundBorder(
                    new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY),
                    new EmptyBorder(20, 0, 5, 40)
            ));

            // Set Absolute Positioning to place the button exactly where we want it inside the field
            setLayout(null);

            // Create the Toggle Button
            toggleButton = new JButton("👁");
            toggleButton.setFont(new Font("Dialog", Font.PLAIN, 16)); // Font that supports emojis well
            toggleButton.setForeground(Color.GRAY);
            toggleButton.setOpaque(false);
            toggleButton.setContentAreaFilled(false);
            toggleButton.setBorderPainted(false);
            toggleButton.setFocusPainted(false);
            toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Position the button on the far right: x=260, y=18, width=40, height=30
            toggleButton.setBounds(260, 18, 40, 30);

            // Add the Click Logic
            toggleButton.addActionListener(e -> {
                if (getEchoChar() == defaultEchoChar) {
                    // It is currently hidden, so reveal it
                    setEchoChar((char) 0);
                    toggleButton.setText("😊"); // Change to lock icon
                    toggleButton.setForeground(new Color(128, 128, 255)); // Highlight color
			repaint();
                } else {
                    // It is currently revealed, so hide it
                    setEchoChar(defaultEchoChar);
                    toggleButton.setText("👁"); // Change to eye icon
                    toggleButton.setForeground(Color.GRAY);
			repaint();
                }
            });

            add(toggleButton); // Add the button into the text field

            // Handle the bottom border color on focus
            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(
                            new MatteBorder(0, 0, 2, 0, new Color(128, 128, 255)),
                            new EmptyBorder(20, 0, 5, 40)
                    ));
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(
                            new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY),
                            new EmptyBorder(20, 0, 5, 40)
                    ));
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getPassword().length == 0 && !hasFocus()) {
                g2d.setFont(new Font("SansSerif", Font.PLAIN, 16));
                g2d.setColor(Color.GRAY);
                g2d.drawString(placeholder, 0, 32);
            } else {
                g2d.setFont(new Font("SansSerif", Font.BOLD, 11));
                g2d.setColor(new Color(128, 128, 255));
                g2d.drawString(placeholder, 0, 15);
            }
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            new LoginPage().setVisible(true);
        });
    }
}
