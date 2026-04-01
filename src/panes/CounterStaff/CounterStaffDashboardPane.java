package panes.CounterStaff;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

import components.FloatingButton;
import config.UIConfig;

public class CounterStaffDashboardPane extends JPanel{

    private int componentSpace = 10;

    private JPanel buttonPanel = new JPanel();
    private JTextField nameField, phoneField, addressField; // Editable
    private JTextField idField, emailField, dateField;                 // Non-editable
    private boolean isEditing = false;
    private CardLayout cardLayout = new CardLayout();


    private FloatingButton updateBtn = new FloatingButton("Update", 20);
    private FloatingButton saveBtn = new FloatingButton("Save Changes", 20);
    private FloatingButton cancelBtn = new FloatingButton("Cancel", 20);

    public CounterStaffDashboardPane() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

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
                g2.setColor(UIConfig.mainForeground);
                g2.drawOval(10, 10, getWidth() - 20, getHeight() - 20); // Draw outer circle
            }
        };
        avatarContainer.setPreferredSize(new Dimension(150, 150));
        avatarContainer.setMaximumSize(new Dimension(150, 150));
        avatarContainer.setBackground(UIConfig.mainBackground);
        avatarContainer.setLayout(new BorderLayout());
        avatarContainer.add(avatarLabel, BorderLayout.CENTER);
        avatarContainer.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel detailsCard = new JPanel();
        detailsCard.setBackground(Color.WHITE);
        detailsCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIConfig.mainBackground, 2, true),
            new EmptyBorder(30, 40, 30, 40)
        ));
        detailsCard.setLayout(new BoxLayout(detailsCard, BoxLayout.Y_AXIS));


        idField = createReadOnlyField("CS123456");
        nameField = createReadOnlyField("Hao Ni Ma");
        emailField = createReadOnlyField("haonima@gmail.com");
        phoneField = createReadOnlyField("+6012234402");
        dateField = createReadOnlyField("5/5/2005");
        addressField = createReadOnlyField("5, Lasnf, 12342, Jln.Chao, Ni Ma, China");

        JPanel topDetailsPanel = new JPanel();
        topDetailsPanel.setOpaque(false);
        topDetailsPanel.setLayout(new GridLayout(3, 2, 10, 20));
        topDetailsPanel.add(createDetailsPanel("Name", nameField));
        topDetailsPanel.add(createDetailsPanel("ID", idField));
        topDetailsPanel.add(createDetailsPanel("Email", emailField));
        topDetailsPanel.add(createDetailsPanel("Date", dateField));
        topDetailsPanel.add(createDetailsPanel("Contact", phoneField));

        detailsCard.add(Box.createVerticalStrut(componentSpace));
        detailsCard.add(topDetailsPanel);
        detailsCard.add(Box.createVerticalStrut(10));
        detailsCard.add(createDetailsPanel("Address", addressField));
        detailsCard.add(Box.createVerticalStrut(componentSpace));


        buttonPanel.setLayout(cardLayout);
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));
        updatePanel.setBackground(Color.WHITE);
        updatePanel.add(updateBtn);
        buttonPanel.add(updatePanel, "UPDATE");
        JPanel savePanel = new JPanel();
        savePanel.setLayout(new GridBagLayout());
        savePanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        savePanel.add(cancelBtn, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        savePanel.add(saveBtn, gbc);
        buttonPanel.add(savePanel, "SAVE");

        add(Box.createVerticalStrut(componentSpace));
        add(avatarContainer);
        add(Box.createVerticalStrut(componentSpace));
        add(detailsCard);
        add(Box.createVerticalStrut(componentSpace));
        add(buttonPanel);
        add(Box.createVerticalStrut(componentSpace));


        updateBtn.addActionListener(e -> {
            toggleEditMode(false);
        });

        saveBtn.addActionListener(e -> {
            toggleEditMode(true);
        });

        cancelBtn.addActionListener(e -> {
            toggleEditMode(false);
        });
    }

    private JTextField createReadOnlyField(String text) {
        JTextField field = new JTextField(text);
        field.setFont(new Font("Serif", Font.BOLD, 16));
        field.setEditable(false);
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        field.setAlignmentX(CENTER_ALIGNMENT);
        return field;
    }

    private JLabel createLabel(String text) {
    JLabel lbl = new JLabel(text);
    lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
    lbl.setForeground(Color.GRAY);
    lbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
    return lbl;
    }

    private JPanel createDetailsPanel(String emoji, JTextField detail){
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(10, 10));
        JLabel emojiLabel = createLabel(emoji);
        panel.add(emojiLabel, BorderLayout.WEST);
        panel.add(detail);
        return panel;
    }

    private void toggleEditMode(boolean isSaving){
        if (!isEditing) {
            isEditing = true;
            cardLayout.show(buttonPanel, "SAVE");

            enableField(nameField);
            enableField(phoneField);
            enableField(addressField);

        } else {
            isEditing = false;
            cardLayout.show(buttonPanel, "UPDATE");

            if(isSaving){
                // Foo
            } else {
                // Un foo
            }

            disableField(nameField);
            disableField(phoneField);
            disableField(addressField);
        }
    }

    private void enableField(JTextField field){
        field.setEditable(true);
        field.setOpaque(true);
        field.setBackground(new Color(245, 245, 255));
        field.setBorder(BorderFactory.createLineBorder(UIConfig.mainBackground, 1, true));
    }

    private void disableField(JTextField field){
        field.setEditable(false);
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
}
