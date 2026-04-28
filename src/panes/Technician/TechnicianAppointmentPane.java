package panes.Technician;

import IO.FileHandler;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TechnicianAppointmentPane extends JPanel {

    private TechnicianAppointmentListPane listPanel;
    private TechnicianAppointmentDetailsPane detailsPanel;
    private List<AppointmentData> appointments;

    public TechnicianAppointmentPane(String UserID) {
        setLayout(new BorderLayout(30, 0));
        setBackground(new Color(248, 248, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Initializing appointment information
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate =  today.format(formatter);;
        initData(UserID, formattedDate);

        // 2. Create the panels
        listPanel = new TechnicianAppointmentListPane(appointments, this);
        listPanel.setPreferredSize(new Dimension(320, 0));

        detailsPanel = new TechnicianAppointmentDetailsPane(this);

        // 3. Wrap right panel in a scroll pane for smaller screens
        JScrollPane rightScroll = new JScrollPane(detailsPanel);
        rightScroll.setBorder(null);
        rightScroll.getVerticalScrollBar().setUnitIncrement(16);
        rightScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightScroll.setOpaque(false);
        rightScroll.getViewport().setOpaque(false);

        // 4. Add them to the screen
        add(listPanel, BorderLayout.WEST);
        add(rightScroll, BorderLayout.CENTER);

        // 5. Load the first appointment by default
        if (!appointments.isEmpty()) {
            detailsPanel.loadAppointment(appointments.get(0));
        }
    }

    // Called by the Details Panel when tasks are checked/completed
    public void updateAppointmentStatus(AppointmentData data, String newStatus) {
        data.status = newStatus;
        listPanel.refreshList(); // Redraw the left panel to update the status pill

        List<String[]> AppointmentList = FileHandler.read("Appointment.txt");

        for (String[] appointments : AppointmentList){
            if (appointments[0].equals(data.appointmentID)){
                appointments[4] = newStatus;
            }
        }

        FileHandler.write("Appointment.txt", AppointmentList, false);
    }

    // Called by the List Panel when a card is clicked
    public void onAppointmentSelected(AppointmentData data) {
        detailsPanel.loadAppointment(data);
    }

    private void initData(String UserID, String appointmentDate) {
        appointments = new ArrayList<>();
        List<String[]> AppointmentList = AppointmentInfo(UserID, appointmentDate);
        List<String[]> AppointmentDetails = AppointmentDetails(UserID, appointmentDate);
        List<String[]> commentHistory = FileHandler.read("Comments.txt");

        System.out.println(AppointmentDetails);

        int i = 1;
        for (String[] appointment:AppointmentList){

            AppointmentData app = new AppointmentData("slot " + i, appointment[0], appointment[1], appointment[2],
                    appointment[3], appointment[4]);
            for (String[] details : AppointmentDetails){
                if (details[0].equals(appointment[5])){
                    // add description
                    app.description = details[1];
                    // add appointmentID for each appointment
                    app.appointmentID = details[0];
                    // add status
                    app.status = details[4];
                    // Add service task
                    switch (details[2]) {
                        case "Normal Service":
                            app.tasks = new String[]{"10,000 km schedule service", "Replace Oil Filter", "Rotate Tires"};
                            break;
                        case "Major Service":
                            app.tasks = new String[]{"10,000 km schedule service", "Replace Oil Filter", "Rotate Tires", "Additional Features"};
                            break;
                        default:
                            app.tasks = new String[]{"Unknown Service"};
                            break;


                    }
                }
            }

            // length of assigned task
            app.taskStates = new boolean [app.tasks.length];

            // fetching comment history for each appointment
            for (String[] comments : commentHistory) {
                if (app.appointmentID.equals(comments[4])) {
                    app.chatHistory.add(new String[]{comments[2], comments[1]});
                }
            }
            // add relevant components into interface
            appointments.add(app);
            i++;
        }
    }

    public List<String[]> AppointmentDetails (String TechnicianID, String date){
        List<String[]> appointmentList = FileHandler.read("Appointment.txt");
        List<String[]> appointmentDetails = new ArrayList<>();

        for (String[] appointments: appointmentList){
            if (appointments[5].equals(date) && appointments[6].equals(TechnicianID)) {
                appointmentDetails.add(appointments);
            }
        }

        return appointmentDetails;
    }

    public static List<String[]> AppointmentInfo(String TechnicianID, String date){
        List<String[]> appointmentList = FileHandler.read("Appointment.txt");
        List<String[]> VehicleList = FileHandler.read("Vehicle.txt");
        List<String[]> CustomerList = FileHandler.read("Customer.txt");
        List<String[]> todayAppointments = new ArrayList<>();

        for (String[] appointments: appointmentList){
            if (appointments[5].trim().equals(date) && appointments[6].trim().equals(TechnicianID)) {
                String[] appointment = new String[] {
                        appointments[9].trim(),
                        appointments[9].trim(),
                        appointments[7].trim(),
                        appointments[7].trim(),
                        appointments[7].trim(),
                        appointments[0].trim()
                };

                todayAppointments.add(appointment);
            }
        }

        for (String[] vehicles: VehicleList){
            for (String[] appointment: todayAppointments){
                if (vehicles[0].trim().equals(appointment[0])){
                    appointment[0] = vehicles[1].trim();
                    appointment[1] = vehicles[2].trim();
                }
            }
        }

        for (String[] customers : CustomerList){
            for (String[] appointment: todayAppointments) {
                if (customers[0].trim().equals(appointment[2])){
                    appointment[2] = customers[1].trim();
                    appointment[3] = customers[4].trim();
                    appointment[4] = customers[5].trim();
                }
            }
        }

        return todayAppointments;
    }

    // --- DATA CLASS TO HOLD STATE ---
    public static class AppointmentData {
        public String time, model, plate, name, contact, email, description, appointmentID;
        public String status;
        public String[] tasks;
        public boolean[] taskStates; // Remembers checked boxes
        public List<String[]> chatHistory = new ArrayList<>(); // [Sender, Message]

        public AppointmentData(String time, String model, String plate, String name, String contact, String email) {
            this.time = time; this.model = model; this.plate = plate;
            this.name = name; this.contact = contact; this.email = email;
        }

    }
}
