package panes.Manager;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import java.util.ArrayList;
import components.TextLabel;
import panes.Manager.components.AccountsPanelListener;
import components.FloatingTextField;
import components.FloatingPasswordField;
import components.FloatingButton;
import components.FloatingComboBox;
import userClass.User;
import IO.FileHandler;

public class ManagerCreateAccountPane extends JPanel {

    private AccountsPanelListener listener;
    private FloatingTextField userIDTxtField;
    private FloatingTextField firstNameTxtField;
    private FloatingTextField lastNameTxtField;
    private FloatingTextField usernameTxtField; 
    private FloatingComboBox<String> titleComboBox;
    private FloatingTextField emailTxtField;
    private FloatingTextField contactNumberTxtField;
    private FloatingPasswordField passwordTxtField;
    private FloatingTextField addressTxtField;
    private String filename = "Users.txt";

    public ManagerCreateAccountPane(AccountsPanelListener listener) {
        this.listener = listener; 

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        TextLabel title = new TextLabel("Account Creation");
        
        //Adding the title text to the pane
        add(Box.createVerticalStrut(30));
        add(title);
        add(Box.createVerticalStrut(30));

        //Create the form panel
        JPanel formPanel = new JPanel(new BorderLayout(50, 50));
        formPanel.setOpaque(false);

        
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        

        //Handle layout for left and right columns;
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        leftColumn.setOpaque(false);

        JPanel rightColumn = new JPanel();
        rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
        rightColumn.setOpaque(false);


        //Declares the vertical gap between the components
        int vgap = 15;

        //Declares floating text fields for left columns
        userIDTxtField = new FloatingTextField("Auto Generated User ID");
        userIDTxtField.setEditable(false);
        firstNameTxtField = new FloatingTextField("First Name");
        lastNameTxtField = new FloatingTextField("Last Name");
        usernameTxtField = new FloatingTextField("Username");
        


        //Adds the text fields with their label text to the left column.
        leftColumn.add(createField("User ID", userIDTxtField));
        leftColumn.add(Box.createVerticalStrut(vgap));

        leftColumn.add(createField("First Name", firstNameTxtField));
        leftColumn.add(Box.createVerticalStrut(vgap));

        leftColumn.add(createField("Last Name", lastNameTxtField));
        leftColumn.add(Box.createVerticalStrut(vgap));

        leftColumn.add(createField("User Name", usernameTxtField));


        //Declares input fields for the right columns
        String[] options = {"Manager", "Counter Staff", "Technician", "Customer"};
        titleComboBox = new FloatingComboBox<>(options, 2);
        titleComboBox.setSelectedIndex(0);
        emailTxtField = new FloatingTextField("Email");
        contactNumberTxtField = new FloatingTextField("Contact Number");
        passwordTxtField = new FloatingPasswordField("Password");

        //Adds the text fields with their label text to the right column.
        rightColumn.add(createField("Title", titleComboBox));
        rightColumn.add(Box.createVerticalStrut(vgap));

        rightColumn.add(createField("Email", emailTxtField));
        rightColumn.add(Box.createVerticalStrut(vgap));

        rightColumn.add(createField("Contact Number", contactNumberTxtField));
        rightColumn.add(Box.createVerticalStrut(vgap));

        rightColumn.add(createField("Password", passwordTxtField));

        //Adds both the left and the right column to the form panel
        topPanel.add(leftColumn);
        topPanel.add(rightColumn);

        formPanel.add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BorderLayout(20, 20));
        TextLabel addressTextLabel = new TextLabel("Address");
        bottomPanel.add(addressTextLabel, BorderLayout.NORTH);

        addressTxtField = new FloatingTextField("Address");
        bottomPanel.add(addressTxtField, BorderLayout.CENTER);

        formPanel.add(bottomPanel, BorderLayout.SOUTH);

        //Adds the formPanel to the main account pane.
        add(formPanel);

        //Creates the buttons to create or cancel
        add(Box.createVerticalStrut(40));

        FloatingButton createBtn = new FloatingButton("Create", 20);
        createBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        FloatingButton  cancelBtn = new FloatingButton("Cancel", 20);
        cancelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Adds the buttons to the account pane
        add(createBtn);
        add(Box.createVerticalStrut(20));
        add(cancelBtn);


        createBtn.addActionListener(e -> {
            User user = new User();
            int roleIndex = titleComboBox.getSelectedIndex();
            String[] rolesPrefix = {"M", "CS", "T", "C"}; 

            user.id = userIDTxtField.getText();
            user.firstName = firstNameTxtField.getText();
            user.lastName = lastNameTxtField.getText();
            user.username = usernameTxtField.getText();
            user.role = (String) titleComboBox.getSelectedItem();
            user.email = emailTxtField.getText();
            user.contact = contactNumberTxtField.getText();
            user.password = new String(passwordTxtField.getPassword());

            String newID = generateNewID(filename, rolesPrefix[roleIndex]);

            List<String> inputUser = new ArrayList<>();
            inputUser.add(user.id);
            inputUser.add(user.firstName);
            inputUser.add(user.lastName);
            inputUser.add(user.username);
            inputUser.add(user.role);
            inputUser.add(user.email);
            inputUser.add(user.contact);
            inputUser.add(user.password);

            System.out.println(inputUser);

            FileHandler.write("Users.txt", inputUser);

            JOptionPane.showMessageDialog(this, "Saved!", "Saved the user details", JOptionPane.INFORMATION_MESSAGE);
        });

        cancelBtn.addActionListener(e -> {
            listener.onBackToList();
        });

        titleComboBox.addActionListener(e -> {
            int roleIndex = titleComboBox.getSelectedIndex();
            String[] rolesPrefix = {"M", "CS", "T", "C"};

            userIDTxtField.setText(generateNewID(filename, rolesPrefix[roleIndex]));
        });

    }

    public static int getLatestNumber(String filename, String prefix) {
        int max = 0;

        List<String[]> data = FileHandler.read(filename);

        for (String[] row : data) {
            if (row.length > 0 && row[0].startsWith(prefix)) {
                try {
                    String numberPart = row[0].substring(prefix.length());
                    int num = Integer.parseInt(numberPart);

                    if (num > max) {
                        max = num;
                    }
                } catch (NumberFormatException e) {
                    // skip bad data instead of crashing
                    System.err.println("Invalid ID format: " + row[0]);
                }
            }
        }

        return max;
    }

    public static String generateNewID(String filename, String prefix) {
        int next = getLatestNumber(filename, prefix) + 1;
        return prefix + String.format("%06d", next);
    }



    private JPanel createField(String labelText, JComponent field){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        TextLabel label = new TextLabel(labelText);
        
        panel.add(label);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(field);

        return panel;
    }
}
