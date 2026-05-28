package panes.CounterStaff;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CounterStaffAppointmentListPane extends JPanel{
    private AppointmentPanelListener listener;
    private List<String[]> appointments = FileHandler.read(FileHandler.appointments);
    private List<String[]> users = FileHandler.read(FileHandler.users);
    private JPanel middlePanel;

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
    
        middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setOpaque(false);

        loadAppointments();

        Timer timer = new Timer(3000, e -> loadAppointments());

        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (isShowing()) {
                    timer.start();
                } else {
                    timer.stop();
                }
            }
        });


        JScrollPane scrollPane = new JScrollPane(middlePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(true);
        scrollPane.getViewport().setOpaque(true);
        scrollPane.setBorder(null);
        

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


        addAppointmentBtn.addActionListener(e -> {
            listener.onCreateAppointment();
        });
    }

    private void loadAppointments() {
        middlePanel.removeAll();

        List<String[]> allAppointments = FileHandler.read(FileHandler.appointments);
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Set<String> selectedTechnicians = new HashSet<>();

        for (String[] appointment : allAppointments){
            if (appointment.length > 6 && appointment[5].equals(todayDate)) {
                selectedTechnicians.add(appointment[6]);
            }
        }

        if (selectedTechnicians.isEmpty()) {
            JLabel emptyStateLbl = new JLabel("There are no appointments for today yet.", SwingConstants.CENTER);
            emptyStateLbl.setFont(new Font("SansSerif", Font.ITALIC, 18));
            emptyStateLbl.setForeground(Color.GRAY);
            emptyStateLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

            middlePanel.add(Box.createVerticalStrut(50));
            middlePanel.add(emptyStateLbl);
        } else {
            for (String technicianID : selectedTechnicians){
                AppointmentBox box = new AppointmentBox(technicianID);
                middlePanel.add(box);
                middlePanel.add(Box.createVerticalStrut(30));
            }
        }

        middlePanel.revalidate();
        middlePanel.repaint();
    }
}