package panes.CounterStaff;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import components.TextLabel;
import components.FloatingButton;
import config.UIConfig;
import panes.CounterStaff.components.AppointmentBox;

public class CounterStaffAppointmentPane extends JPanel{
    public CounterStaffAppointmentPane() {
        setLayout(new BorderLayout(20, 40));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        TextLabel labelText = new TextLabel("Today's Schedule");
        FloatingButton addAppointmentBtn = new FloatingButton("(+) Add New Appointment");
        addAppointmentBtn.setBackground(UIConfig.mainForeground);
        addAppointmentBtn.setForeground(UIConfig.mainBackground);
        addAppointmentBtn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIConfig.mainBackground, 2, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        addAppointmentBtn.setBorderPainted(true);
        topPanel.add(labelText, BorderLayout.WEST);
        topPanel.add(addAppointmentBtn, BorderLayout.EAST);
    
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBackground(UIConfig.mainForeground);
        AppointmentBox testBox = new AppointmentBox("Timmy");
        middlePanel.add(testBox);
        JScrollPane scrollPane = new JScrollPane(middlePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }   
}