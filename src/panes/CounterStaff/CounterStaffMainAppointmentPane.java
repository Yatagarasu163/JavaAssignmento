package panes.CounterStaff;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import panes.CounterStaff.components.AppointmentPanelListener;

public class CounterStaffMainAppointmentPane extends JPanel implements AppointmentPanelListener{
    private AppointmentPanelListener listener;
    private CardLayout cardLayout = new CardLayout();
    private CounterStaffAppointmentListPane listPane;
    private CounterStaffCreateAppointmentPane createPane;

    public CounterStaffMainAppointmentPane(){
        setBackground(Color.WHITE);
        setLayout(cardLayout);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        listPane = new CounterStaffAppointmentListPane(this);
        createPane = new CounterStaffCreateAppointmentPane(this);

        add(listPane, "LIST");
        add(createPane, "CREATE");

        cardLayout.show(this, "LIST");
    }

    public void onCreateAppointment(){
        cardLayout.show(this, "CREATE");
    }

    public void onBackToList(){
        cardLayout.show(this, "LIST");
    }
}
