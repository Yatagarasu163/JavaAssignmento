package panes.Customer;

import components.FloatingButton;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import config.UIConfig;
import IO.FileHandler;

public class CustomerProfilePane extends JPanel {

    private final Color primaryPurple = UIConfig.mainBackground;
    private final Color bgColor = UIConfig.whiteBackground;

    //Editable Fields
    private JTextField nameField, phoneField, addressField;
    //Non-editable Fields
    private JTextField idField, emailField, dateField;

    private boolean isEditing = false;
    private FloatingButton updateBtn;

    private String loggedInCustomerID;

    public CustomerProfilePane(String id) {
        this.loggedInCustomerID = id;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(bgColor);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        String name = "Not found", email = "Not found", date = "Not found", phone = "Not found", address = "Not found";

        List<String[]> currentUserData = FileHandler.read("CurrentUser.txt");

        if (!currentUserData.isEmpty() && currentUserData.get(0).length >= 10) {
            String[] row = currentUserData.get(0);
            name = row[1].trim() + " " + row[2].trim();
            email = row[5].trim();
            phone = row[6].trim();
            address = row[8].trim();
            date = row[9].trim();
        }

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
        idField = createReadOnlyField(loggedInCustomerID);
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
            updateBtn.setBackground(UIConfig.saveBtn);

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

            saveUpdatedProfileData();
        }
    }

    private void saveUpdatedProfileData() {
        try {
            List<String[]> usersList = FileHandler.read("Users.txt");
            boolean dataUpdated = false;
            String[] updatedRow = null;

            for (int i = 0; i < usersList.size(); i++) {
                String[] row = usersList.get(i);

                if (row[0].equals(loggedInCustomerID)) {
                    String fullName = nameField.getText().trim();
                    String firstName = fullName;
                    String lastName = "";

                    if (fullName.contains(" ")) {
                        firstName = fullName.substring(0, fullName.indexOf(" "));
                        lastName = fullName.substring(fullName.indexOf(" ") + 1);
                    }

                    row[1] = firstName;
                    row[2] = lastName;
                    row[6] = phoneField.getText().trim();
                    row[8] = addressField.getText().trim();

                    updatedRow = row;
                    dataUpdated = true;
                    break;
                }
            }

            if (dataUpdated) {
                FileHandler.write("Users.txt", usersList, false);

                try (java.io.FileWriter writer = new java.io.FileWriter("src/database/CurrentUser.txt", false)) {
                    writer.write(String.join("/></", updatedRow));
                }

                JOptionPane.showMessageDialog(this,
                        "Profile successfully updated!",
                        "Update Complete",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            System.err.println("Failed to save profile updates.");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "An error occurred while saving your profile.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enableField(JTextField field) {
        field.setEditable(true);
        field.setOpaque(true);
        field.setBackground(UIConfig.whiteBackground);
        field.setBorder(BorderFactory.createLineBorder(primaryPurple, 1, true));
    }

    private void disableField(JTextField field) {
        field.setEditable(false);
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
}