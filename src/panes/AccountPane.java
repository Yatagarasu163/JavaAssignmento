package panes;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import components.TextLabel;
import components.FloatingTextField;
import components.FloatingPasswordField;
import components.FloatingButton;
import components.FloatingComboBox;


public class AccountPane extends JPanel {
    public AccountPane() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));



        TextLabel title = new TextLabel("Account Creation");
        
        //Adding the title text to the pane
        add(Box.createVerticalStrut(30));
        add(title);
        add(Box.createVerticalStrut(30));

        //Create the form panel
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        formPanel.setOpaque(false);

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
        FloatingTextField userIDTxtField = new FloatingTextField("User ID");
        FloatingTextField firstNameTxtField = new FloatingTextField("First name");
        FloatingTextField lastNameTxtField = new FloatingTextField("Last name");
        FloatingTextField usernameTxtField = new FloatingTextField("Username"); 


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
        FloatingComboBox<String> titleComboBox = new FloatingComboBox<>(options, 20);
        FloatingTextField emailTxtField = new FloatingTextField("Email");
        FloatingTextField contactNumberTxtField = new FloatingTextField("Contact Number");
        FloatingPasswordField passwordTxtField = new FloatingPasswordField("Auto-generated password");

        //Adds the text fields with their label text to the right column.
        rightColumn.add(createField("Title", titleComboBox));
        rightColumn.add(Box.createVerticalStrut(vgap));

        rightColumn.add(createField("Email", emailTxtField));
        rightColumn.add(Box.createVerticalStrut(vgap));

        rightColumn.add(createField("Contact Number", contactNumberTxtField));
        rightColumn.add(Box.createVerticalStrut(vgap));

        rightColumn.add(createField("Password", passwordTxtField));

        //Adds both the left and the right column to the form panel
        formPanel.add(leftColumn);
        formPanel.add(rightColumn);

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
