package src.panes.Technician;

import IO.FileHandler;
import components.FloatingButton;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.util.List;

public class TechnicianProfilePane extends JPanel {

    private final Color primaryPurple = new Color(128, 128, 255);
    private final Color bgColor = new Color(245, 245, 250);

    // Form Fields
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

        // --- PART 1: USER PROFILE ICON ---
        // Using a large Unicode character as a placeholder for the avatar
        JLabel avatarLabel = new JLabel("\uD83D\uDC64", SwingConstants.CENTER);
        avatarLabel.setFont(new Font("SansSerif", Font.PLAIN, 80));
        avatarLabel.setForeground(Color.LIGHT_GRAY);
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Wrap it in a panel to draw the circle around it
        JPanel avatarContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(primaryPurple);
                g2.drawOval(10, 10, getWidth() - 20, getHeight() - 20); // Draw outer circle
            }
        };
        avatarContainer.setPreferredSize(new Dimension(150, 150));
        avatarContainer.setMaximumSize(new Dimension(150, 150));
        avatarContainer.setBackground(bgColor);
        avatarContainer.setLayout(new BorderLayout());
        avatarContainer.add(avatarLabel, BorderLayout.CENTER);

        // --- PART 2: USER DETAILS CARD ---
        JPanel detailsCard = new JPanel(new GridBagLayout());
        detailsCard.setBackground(Color.WHITE);
        detailsCard.setMaximumSize(new Dimension(800, 300));
        detailsCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primaryPurple, 2, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        // Initialize all fields (using our helper method to make them look like labels initially)
        nameField = createReadOnlyField(name);
        idField = createReadOnlyField(id);
        emailField = createReadOnlyField(email);
        dateField = createReadOnlyField(date);
        phoneField = createReadOnlyField(phone);
        addressField = createReadOnlyField(address);

        // Add fields to the GridBagLayout (Icon, Field, X, Y, Width)
        addFormField(detailsCard, "👤", nameField, 0, 0, 1);
        addFormField(detailsCard, "🪪", idField, 2, 0, 1);
        addFormField(detailsCard, "✉️", emailField, 0, 1, 1);
        addFormField(detailsCard, "📅", dateField, 2, 1, 1);
        addFormField(detailsCard, "📞", phoneField, 0, 2, 3); // Spans across
        addFormField(detailsCard, "📍", addressField, 0, 3, 3); // Spans across


        // --- PART 3: THE UPDATE BUTTON ---
        updateBtn = new FloatingButton("Update", 20);
        updateBtn.setPreferredSize(new Dimension(150, 40));
        updateBtn.setMaximumSize(new Dimension(150, 40));

        // The Toggle Logic
        updateBtn.addActionListener(e -> toggleEditMode());

        // --- ASSEMBLE EVERYTHING ---
        add(Box.createVerticalStrut(20));
        add(avatarContainer);
        add(Box.createVerticalStrut(30));
        add(detailsCard);
        add(Box.createVerticalStrut(30));
        add(updateBtn);
    }

    // --- HELPER: Formats a TextField to look like a plain Label ---
    private JTextField createReadOnlyField(String text) {
        JTextField field = new JTextField(text);
        field.setFont(new Font("Serif", Font.BOLD, 16));
        field.setEditable(false);
        field.setOpaque(false); // Transparent background
        field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // No visible border
        return field;
    }

    // --- HELPER: Adds a row to the GridBagLayout ---
    private void addFormField(JPanel panel, String icon, JTextField field, int x, int y, int width) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x; gbc.gridy = y;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(15, 15, 15, 5); // Padding around items

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        panel.add(iconLabel, gbc);

        gbc.gridx = x + 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = width;
        panel.add(field, gbc);
    }

    // --- THE MAGIC: Toggles between reading and editing ---
    private void toggleEditMode() {
        if (!isEditing) {
            // ENTER EDIT MODE
            isEditing = true;
            updateBtn.setText("Save Changes");
            updateBtn.setBackground(new Color(100, 200, 100)); // Change button to green

            // Turn on specific fields
            enableField(nameField);
            enableField(phoneField);
            enableField(addressField);

        } else {
            // SAVE AND EXIT EDIT MODE
            isEditing = false;
            updateBtn.setText("Update");
            updateBtn.setBackground(primaryPurple); // Change back to purple

            // Turn off fields
            disableField(nameField);
            disableField(phoneField);
            disableField(addressField);

            updateTechnicianInfo();

        }
    }

    // Turns a "label" back into a real text box
    private void enableField(JTextField field) {
        field.setEditable(true);
        field.setOpaque(true);
        field.setBackground(new Color(245, 245, 255)); // Light blue editing background
        field.setBorder(BorderFactory.createLineBorder(primaryPurple, 1, true));
    }

    // Turns a text box back into a "label"
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

        // Set to null initially so we can easily check if we found someone
        String[] updatedInfo = null;
        boolean found = false;

        // Assuming splitName() returns your parsedName object correctly
        parsedName userName = splitName(nameField.getText());

        // 1. Update the Current User
        for (String[] user: currentUser) {
            if (user[0].equals(idField.getText())) {
                user[1] = userName.firstName;
                user[2] = userName.lastName;
                user[6] = phoneField.getText();
                user[8] = addressField.getText();

                updatedInfo = user; // Now safely INSIDE the if-statement
                break; // Stop looping since we found the user
            }
        }

        // If we successfully found and updated the user in the first step...
        if (updatedInfo != null) {

            // 2. Update the Main User List (Using a standard for-loop to use .set)
            for (int i = 0; i < userList.size(); i++) {
                String[] targetUser = userList.get(i);

                if (targetUser[0].equals(updatedInfo[0])) {
                    // Replace the old array with the newly updated array
                    userList.set(i, updatedInfo);
                    found = true;
                    break; // Stop looping since we found the user
                }
            }
        }

        // 3. Save the results
        if (found) {

            FileHandler.write("Users.txt", userList, false);

            // Don't forget to save the changes to the CurrentUser file too!
            FileHandler.write("CurrentUser.txt", currentUser, false);

            System.out.println("Update successful.");
        } else {
            System.out.println("Technician ID not found.");
        }
    }
}