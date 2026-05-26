package panes.Technician;
import IO.FileHandler;
import panes.Technician.TechnicianAppointmentDetailsPane;


import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TechnicianAppointmentPane extends JPanel {

    private panes.Technician.TechnicianAppointmentListPane listPanel;
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
        listPanel = new panes.Technician.TechnicianAppointmentListPane(appointments, this);
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
            //String time, String model, String plate, String name, String contact, String email
            AppointmentData app = new AppointmentData("Slot " + i, appointment[0], appointment[1], appointment[2],
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
                            String[] baseTasks = new String[]{
                                    "Fully Synthetic Engine Oil (4L)", "Oil Filter Replacement", "Drain Plug Washer",
                                    "Tire Rotation & Balancing", "Battery Health Check & Cleaning", "Windshield Wiper Fluid Top-up"
                            };
                            String[] extraServiceNames = additionalServices(details[3]);
                            List<String> combinedList = new ArrayList<>(Arrays.asList(baseTasks));
                            combinedList.addAll(Arrays.asList(extraServiceNames));
                            app.tasks = combinedList.toArray(new String[0]);
                            break;
                        case "Major Service":
                            String[] baseTask = new String[]{"Spark Plugs Replacement (Set of 4)", "Transmission Fluid Flush", "Brake Pad Replacement",
                                                    "Brake Fluid Flush & Bleed", "Cabin Air Filter (A/C) Replacement", "Engine Coolant System Flush"};
                            String[] extraServiceName = additionalServices(details[3]);
                            List<String> combinedLists = new ArrayList<>(Arrays.asList(baseTask));
                            combinedLists.addAll(Arrays.asList(extraServiceName));
                            app.tasks = combinedLists.toArray(new String[0]);
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
        List<String[]> vehicleList = FileHandler.read("Vehicle.txt");
        List<String[]> userList = FileHandler.read("Users.txt");
        List<String[]> todayAppointments = new ArrayList<>();

        for (String[] appointments: appointmentList){
            if (appointments[5].equals(date) && appointments[6].equals(TechnicianID)) {
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


        for (String[] vehicles: vehicleList){
            for (String[] appointment: todayAppointments){
                if (vehicles[0].trim().equals(appointment[0])){
                    appointment[0] = vehicles[2];
                    appointment[1] = vehicles[1];
                }
            }
        }

        for (String[] customers : userList){
            for (String[] appointment: todayAppointments) {
                if (customers[0].trim().equals(appointment[2])){
                    String fullName = customers[1] + customers[2];
                    appointment[2] = fullName;
                    appointment[3] = customers[6];
                    appointment[4] = customers[5];
                }
            }
        }


        return todayAppointments;
    }

    public String[] additionalServices(String addServiceIDs) {
        String[] addServices = addServiceIDs.split(",");

        List<String[]> serviceList = FileHandler.read("Price.txt");

        for (int i = 0; i < addServices.length; i++) {
            for (String[] service : serviceList) {
                if (addServices[i].equals(service[0])) {

                    addServices[i] = service[1];

                    break;
                }
            }
        }

        return addServices;
    }

    // navigate to specific appointment view
    public void openSpecificAppointment(String plateNumber) {
        for (AppointmentData data : appointments) {
            if (data.plate.equals(plateNumber)) {
                onAppointmentSelected(data);
                break;
            }
        }
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
