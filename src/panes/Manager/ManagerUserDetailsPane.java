package panes.Manager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.border.LineBorder;
import java.util.List;
import java.util.ArrayList;

import components.FloatingButton;
import panes.Manager.components.AccountsPanelListener;
import config.UIConfig;
import IO.FileHandler;


public class ManagerUserDetailsPane extends JPanel{
    private int componentSpace = 10;
    private AccountsPanelListener listener;
    private static final String filename = "Users.txt";

    private JPanel buttonPanel = new JPanel();
    private JTextField nameField, phoneField, addressField;
    private JTextField idField, emailField, dateField;
    private boolean isEditing = false;
    private CardLayout cardLayout = new CardLayout();

    private FloatingButton updateBtn = new FloatingButton("Update", 2);
    private FloatingButton deleteBtn = new FloatingButton("Delete", 2);
    private FloatingButton backBtn = new FloatingButton("Back", 2);
    private FloatingButton saveBtn = new FloatingButton("Save", 2);
    private FloatingButton cancelBtn = new FloatingButton("Cancel", 2);


   public ManagerUserDetailsPane(AccountsPanelListener listener) {
        this.listener = listener;

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
            new LineBorder(UIConfig.mainBackground, 3, true),
            new EmptyBorder(30, 40, 30, 40)
        ));
        detailsCard.setLayout(new BoxLayout(detailsCard, BoxLayout.Y_AXIS));

        idField = createReadOnlyField(null);
        nameField = createReadOnlyField("Manager's Cuck");
        emailField = createReadOnlyField("managerscuck@cuckmail.com");
        phoneField = createReadOnlyField("+601212121212");
        dateField = createReadOnlyField("11/9/2001");
        addressField = createReadOnlyField("5, pentagons, 123, Jln. Pook, Kie Mah, Taiwan");

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
        detailsCard.add(Box.createVerticalStrut(componentSpace));
        detailsCard.add(createDetailsPanel("Address", addressField));
        detailsCard.add(Box.createVerticalStrut(componentSpace));

        //Update and back button panel
        buttonPanel.setLayout(cardLayout);
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new GridBagLayout());
        updatePanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        //Add Update Button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        updatePanel.add(updateBtn, gbc);
        
        //Add Delete Button
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        updatePanel.add(deleteBtn, gbc);
        
        //Add Back Button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        updatePanel.add(backBtn, gbc);
        buttonPanel.add(updatePanel, "UPDATE");



        // Save and cancel button panel
        JPanel savePanel = new JPanel();
        savePanel.setLayout(new GridBagLayout());
        savePanel.setBackground(Color.WHITE);
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

        deleteBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this account?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION
            );


            if (result == JOptionPane.YES_OPTION){
                // Add delete logic here

                String id = idField.getText();

                List<String[]> accounts = FileHandler.read(filename);
                for (int i = 0; i < accounts.size(); i++){
                    if (accounts.get(i)[0].equalsIgnoreCase(id)){
                        accounts.remove(i);
                        break;
                    }
                }

                FileHandler.write(filename, accounts, false);

                JOptionPane.showMessageDialog(this, "Account has been successfully deleted!", "Success!", JOptionPane.INFORMATION_MESSAGE);

                isEditing = true;
                toggleEditMode(false);
                listener.onBackToList();
            }
        });

        backBtn.addActionListener(e -> {
            isEditing = true;
            toggleEditMode(false);
            listener.onBackToList();
        });

        saveBtn.addActionListener(e -> {
            toggleEditMode(true);
        });

        cancelBtn.addActionListener(e -> {
            toggleEditMode(false);
        });
   } 

    private JTextField createReadOnlyField(String text){
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

            String id = idField.getText();
            String name = nameField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();


            cardLayout.show(buttonPanel, "UPDATE");

            if(isSaving){
                // Foo
                List<String[]> accounts = FileHandler.read(filename);
                for (String[] account : accounts){
                    if (account[0].equalsIgnoreCase(id)){
                        account[3] = name;
                        account[6] = phone;
                        account[8] = address;
                        JOptionPane.showMessageDialog(this, "Details saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                }

                FileHandler.write(filename, accounts, false);
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

    public void loadUser(String userID) {
        System.out.println("Load User: " + userID);
        List<String[]> accounts = FileHandler.read(filename);
        for (String[] account : accounts){
            if (account[0].equalsIgnoreCase(userID)){
                idField.setText(account[0]);
                nameField.setText(account[3]);
                phoneField.setText(account[6]);
                addressField.setText(account[8]);
                emailField.setText(account[5]);
                dateField.setText(account[9]);
            }
        }
        idField.setText(userID);
    }
}
