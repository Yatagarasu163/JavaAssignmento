package panes.Technician;

import IO.FileHandler;
import components.FloatingButton;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.util.List;

public class TechnicianProfilePane extends JPanel {

    private final Color primaryPurple = new Color(128, 128, 255);
    private final Color bgColor = new Color(245, 245, 250);

    private JTextField nameField, phoneField, addressField, idField; // Editable
    private JTextField emailField;
    private JTextField dateField;      // Non-editable
    private record parsedName(String firstName, String lastName){};

    private boolean isEditing = false;
    private FloatingButton updateBtn;

    public TechnicianProfilePane(String name, String id, String email, String date, String phone, String address) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(bgColor);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        ImageIcon originalIcon = new ImageIcon("src/images/UserProfile.png");

        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon avatarIcon = new ImageIcon(scaledImage);

        JLabel avatarLabel = new JLabel(avatarIcon, SwingConstants.CENTER);
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel avatarContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(primaryPurple);
                g2.drawOval(10, 10, getWidth() - 20, getHeight() - 20);
            }
        };
        avatarContainer.setPreferredSize(new Dimension(150, 150));
        avatarContainer.setMaximumSize(new Dimension(150, 150));
        avatarContainer.setBackground(bgColor);
        avatarContainer.setLayout(new BorderLayout());
        avatarContainer.add(avatarLabel, BorderLayout.CENTER);

        JPanel detailsCard = new JPanel(new GridBagLayout());
        detailsCard.setBackground(Color.WHITE);
        detailsCard.setMaximumSize(new Dimension(800, 300));
        detailsCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primaryPurple, 2, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        nameField = createReadOnlyField(name);
        idField = createReadOnlyField(id);
        emailField = createReadOnlyField(email);
        dateField = createReadOnlyField(date);
        phoneField = createReadOnlyField(phone);
        addressField = createReadOnlyField(address);

        addFormField(detailsCard, "src/images/User.png", nameField, 0, 0, 1);
        addFormField(detailsCard, "src/images/ID.png", idField, 2, 0, 1);
        addFormField(detailsCard, "src/images/Email.png", emailField, 0, 1, 1);
        addFormField(detailsCard, "src/images/Calendar.png", dateField, 2, 1, 1);
        addFormField(detailsCard, "src/images/Contact.png", phoneField, 0, 2, 3);
        addFormField(detailsCard, "src/images/Address.png", addressField, 0, 3, 3);

        updateBtn = new FloatingButton("Update", 20);
        updateBtn.setPreferredSize(new Dimension(150, 40));
        updateBtn.setMaximumSize(new Dimension(150, 40));

        updateBtn.addActionListener(e -> toggleEditMode());

        add(Box.createVerticalStrut(20));
        add(avatarContainer);
        add(Box.createVerticalStrut(30));
        add(detailsCard);
        add(Box.createVerticalStrut(30));
        add(updateBtn);
    }

    private JTextField createReadOnlyField(String text) {
        JTextField field = new JTextField(text);
        field.setFont(new Font("Serif", Font.BOLD, 16));
        field.setEditable(false);
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return field;
    }

    private void addFormField(JPanel panel, String imagePath, JTextField field, int x, int y, int width) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x; gbc.gridy = y;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(15, 15, 15, 5);

        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(scaledImage);

        JLabel iconLabel = new JLabel(finalIcon, SwingConstants.LEFT);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        iconLabel.setIconTextGap(10);

        panel.add(iconLabel, gbc);

        gbc.gridx = x + 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = width;
        panel.add(field, gbc);
    }

    private void toggleEditMode() {
        if (!isEditing) {
            isEditing = true;
            updateBtn.setText("Save Changes");
            updateBtn.setBackground(new Color(100, 200, 100));

            enableField(nameField);
            enableField(phoneField);
            enableField(addressField);

        } else {

            isEditing = false;
            updateBtn.setText("Update");
            updateBtn.setBackground(primaryPurple);

            disableField(nameField);
            disableField(phoneField);
            disableField(addressField);

            updateTechnicianInfo();

        }
    }

    private void enableField(JTextField field) {
        field.setEditable(true);
        field.setOpaque(true);
        field.setBackground(new Color(245, 245, 255));
        field.setBorder(BorderFactory.createLineBorder(primaryPurple, 1, true));
    }

    private void disableField(JTextField field) {
        field.setEditable(false);
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public static parsedName splitName(String fullName){
        String[] parts = fullName.trim().split(" ", 2);

        String first = parts[0];
        String last = (parts.length > 1) ? parts[1] : "";

        return new parsedName(first, last);
    }

    public void updateTechnicianInfo() {

        List<String[]> userList = FileHandler.read("Users.txt");
        List<String[]> currentUser = FileHandler.read("CurrentUser.txt");

        String[] updatedInfo = null;
        boolean found = false;

        parsedName userName = splitName(nameField.getText());

        for (String[] user: currentUser) {
            if (user[0].equals(idField.getText())) {
                user[1] = userName.firstName;
                user[2] = userName.lastName;
                user[6] = phoneField.getText();
                user[8] = addressField.getText();

                updatedInfo = user;
                break;
            }
        }

        if (updatedInfo != null) {
            for (int i = 0; i < userList.size(); i++) {
                String[] targetUser = userList.get(i);
                if (targetUser[0].equals(updatedInfo[0])) {
                    userList.set(i, updatedInfo);
                    found = true;
                    break;
                }
            }
        }
        if (found) {
            FileHandler.write("Users.txt", userList, false);
            FileHandler.write("CurrentUser.txt", currentUser, false);
            System.out.println("Update successful.");
        } else {
            System.out.println("Technician ID not found.");
        }
    }
}