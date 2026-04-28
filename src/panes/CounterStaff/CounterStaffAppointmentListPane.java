package panes.CounterStaff;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import components.TextLabel;
import components.FloatingButton;
import config.UIConfig;
import panes.CounterStaff.components.AppointmentBox;
import panes.CounterStaff.components.AppointmentPanelListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import IO.FileHandler;

public class CounterStaffAppointmentListPane extends JPanel{
    private AppointmentPanelListener listener;
    private List<String[]> appointments = FileHandler.read(FileHandler.appointments);
    private List<String[]> users = FileHandler.read(FileHandler.users);


    public CounterStaffAppointmentListPane(AppointmentPanelListener listener) {

        List<String[]> technicians = new ArrayList<>();
        for (String[] user : users){
            if (user[0].startsWith("T")){
                technicians.add(user);
            }
        }

        Set<String> selectedTechnicians = new HashSet<>();
        for (String[] appointment : appointments){
            selectedTechnicians.add(appointment[6]);
        } 

        this.listener = listener;

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
        //Loop through the amount of technicians and make the appointment boxes here.        
        for (String technicianID : selectedTechnicians){
            AppointmentBox appointmentBox = new AppointmentBox(technicianID);
            middlePanel.add(appointmentBox);
            middlePanel.add(Box.createVerticalStrut(30));
        }


        JScrollPane scrollPane = new JScrollPane(middlePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


        addAppointmentBtn.addActionListener(e -> {
            listener.onCreateAppointment();
        });
    }   
}