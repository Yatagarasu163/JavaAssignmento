package panes.CounterStaff.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.border.*;
import java.awt.event.HierarchyEvent;
import config.UIConfig;
import components.TextLabel;
import IO.FileHandler;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AppointmentBox extends JPanel{
    private int radius = 30; 
    private Timer timer;
    private String technicianID;
    private int lastCount = -1;
    
    public AppointmentBox(String technicianID){
        this.technicianID = technicianID;

        setLayout(new BorderLayout(20, 10));
        setBackground(UIConfig.mainBackground);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(0, 250));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        setOpaque(true);
    
        loadAppointments(this.technicianID);


        timer = new Timer(3000, e -> {
            List<String[]> appointments = FileHandler.read(FileHandler.appointments);

            if (appointments.size() != lastCount) {
                lastCount = appointments.size();
                loadAppointments(this.technicianID);
            }
        });
        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (isShowing()) {
                    timer.start();
                } else {
                    timer.stop();
                }
            }
        });
    }

    public void loadAppointments(String technicianID) {
        removeAll();

        List<String[]> users = FileHandler.read(FileHandler.users);
        List<String[]> appointments = FileHandler.read(FileHandler.appointments);
        List<String[]> vehicles = FileHandler.read(FileHandler.vehicles);

        List<String[]> technician = new ArrayList<>();
        for(String[] user : users){
            if(user[0].equalsIgnoreCase(technicianID)){
                technician.add(user);
                break;
            } 
        }

        if(technician.isEmpty()){
            System.out.println("No technician found for ID: " + technicianID);
            return;
        }
        
        String[] technicianArr = technician.get(0);

        List<String[]> selectedAppointments = new ArrayList<>();
        List<String> vehicleIDs = new ArrayList<>();

        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        for (String[] appointment : appointments){
            if(appointment.length > 9 &&
                    appointment[6].equalsIgnoreCase(technicianID) &&
                    appointment[5].equals(todayDate)){

                selectedAppointments.add(appointment);
                vehicleIDs.add(appointment[9]);
            }
        }

        List<String[]> selectedVehicles = new ArrayList<>();

        for(String[] vehicle : vehicles){
            for (String vehicleID : vehicleIDs){
                if(vehicle[0].equalsIgnoreCase(vehicleID)){
                    selectedVehicles.add(vehicle);
                    break;
                }
            }
        }

        List<String[]> queueData = new ArrayList<>();
        for (String[] appointment : selectedAppointments){
            for (String[] vehicle : selectedVehicles){
                if(appointment[9].equalsIgnoreCase(vehicle[0]) && !appointment[4].equalsIgnoreCase("Completed")){
                    if (appointment[2].equalsIgnoreCase("Normal Service")){
                        queueData.add(new String[]{
                            "1 Hour",
                            vehicle[2],
                            vehicle[1],
                            appointment[4]
                        });
                    } else{
                        queueData.add(new String[]{
                            "3 Hours",
                            vehicle[2],
                            vehicle[1],
                            appointment[4]
                        });
                    }
                }
            }
        }

        setLayout(new BorderLayout(20, 10));

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        TextLabel PICText = new TextLabel("Person-in-Charge: ");
        PICText.setForeground(Color.WHITE);

        PillLabel technicianNameLabel = new PillLabel(technicianArr[1] + " " + technicianArr[2]);
        technicianNameLabel.setForeground(UIConfig.mainBackground);
        technicianNameLabel.setBackground(Color.WHITE);
        technicianNameLabel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIConfig.mainForeground, 2, true),
            new EmptyBorder(5, 5, 5, 5)
        ));

        topPanel.add(PICText);
        topPanel.add(Box.createHorizontalStrut(5));
        topPanel.add(technicianNameLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel();
        middlePanel.setOpaque(false);
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));

        for(int i = 0; i < queueData.size(); i++){
            String[] queue = queueData.get(i);

            QueueCard card = new QueueCard(
                queue[0], queue[1], queue[2], queue[3],
                i == 0
            );

            middlePanel.add(card);
            if(i < queueData.size() - 1){
                middlePanel.add(Box.createHorizontalStrut(20));
            }
        }

        JScrollPane scrollPanel = new JScrollPane(
            middlePanel,
            JScrollPane.VERTICAL_SCROLLBAR_NEVER,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        scrollPanel.setOpaque(false);
        scrollPanel.getViewport().setOpaque(false);
        scrollPanel.setBorder(null);

        add(scrollPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(UIConfig.mainBackground);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.setColor(Color.DARK_GRAY);
        g2.drawRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.setColor(new Color(0, 0, 0, 30));
        g2.setStroke(new BasicStroke(1.5f));

        super.paintComponent(g);
        g2.dispose();
    }

}