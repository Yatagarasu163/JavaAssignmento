package panes.Techinician;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TechnicianAppointmentPane extends JPanel {

    private TechnicianAppointmentListPane listPanel;
    private TechnicianAppointmentDetailsPane detailsPanel;
    private List<AppointmentData> appointments;

    public TechnicianAppointmentPane() {
        setLayout(new BorderLayout(30, 0));
        setBackground(new Color(248, 248, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // 1. Initialize our dummy data
        initData();

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
    }

    // Called by the List Panel when a card is clicked
    public void onAppointmentSelected(AppointmentData data) {
        detailsPanel.loadAppointment(data);
    }

    private void initData() {
        appointments = new ArrayList<>();

        // Appointment 1
        AppointmentData app1 = new AppointmentData("9.00 AM", "Perodua Myvi", "VKA 1234", "Ali Bin Supaman", "0162203974", "AliSupaman@gmail.com");
        app1.description = "Engine feels slightly rough during idle. Squeaking sound when braking at low speeds. A/C takes longer to get cold. Car pulls left.";
        app1.tasks = new String[]{"10,000 km schedule service", "Replace Oil Filter", "Rotate Tires", "Add aerospace oil", "Nasi Goreng Ayam"};
        app1.taskStates = new boolean[5];
        app1.chatHistory.add(new String[]{"CUSTOMER", "Hearing a grinding voice from left wheels. Me feel cooked..."});
        app1.chatHistory.add(new String[]{"TECHNICIAN", "Gud Luck :)"});
        appointments.add(app1);

        // Appointment 2
        AppointmentData app2 = new AppointmentData("11.30 AM", "Proton Saga", "ABC 9876", "Siti Nurhaliza", "0123456789", "siti@gmail.com");
        app2.description = "Customer requested a standard oil change and battery check. Car struggles to start in the morning.";
        app2.tasks = new String[]{"Change Engine Oil", "Check Battery Health", "Top up wiper fluid"};
        app2.taskStates = new boolean[3];
        app2.chatHistory.add(new String[]{"CUSTOMER", "Please check the battery carefully, it died twice this week."});
        appointments.add(app2);
    }

    // --- DATA CLASS TO HOLD STATE ---
    public static class AppointmentData {
        public String time, model, plate, name, contact, email, description;
        public String status = "In Queue";
        public String[] tasks;
        public boolean[] taskStates; // Remembers checked boxes
        public List<String[]> chatHistory = new ArrayList<>(); // [Sender, Message]

        public AppointmentData(String time, String model, String plate, String name, String contact, String email) {
            this.time = time; this.model = model; this.plate = plate;
            this.name = name; this.contact = contact; this.email = email;
        }
    }
}
