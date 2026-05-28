package panes.Manager;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.List;

import components.TextLabel;
import panes.Manager.components.DashboardPanel;
import IO.FileHandler;

public class ManagerMainDashboardPane extends JPanel{
	public ManagerMainDashboardPane(){

		List<String[]> appointments = FileHandler.read("Appointment.txt");
		int pendingServicesCount = 0;
		int completedServicesCount = 0;
		int pendingNServicesCount = 0;
		int pendingMServicesCount = 0;

		for (String[] appointment : appointments){
			if(appointment[4].equalsIgnoreCase("In Queue")){
				pendingServicesCount += 1;
				if (appointment[2].equalsIgnoreCase("Normal Service")){
					pendingNServicesCount += 1;
				} else{
					pendingMServicesCount += 1;
				}
			} else if (appointment[4].equalsIgnoreCase("Completed")){
				completedServicesCount += 1;
			}
		}

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(20, 20, 20, 20));

		TextLabel title = new TextLabel("Dashboard");

		add(Box.createVerticalStrut(50));
		add(title);
		add(Box.createVerticalStrut(50));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setBackground(Color.WHITE);
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		DashboardPanel pendingServicesPanel = new DashboardPanel("Pending Services", Integer.toString(pendingServicesCount));
		DashboardPanel completedServicesPanel = new DashboardPanel("Completed Services", Integer.toString(completedServicesCount));

		topPanel.add(pendingServicesPanel);
		topPanel.add(Box.createHorizontalStrut(50));
		topPanel.add(completedServicesPanel);
		
		add(topPanel);
		add(Box.createVerticalStrut(50));

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		DashboardPanel pendingNormalServicePanel = new DashboardPanel("Pending Normal Services", Integer.toString(pendingNServicesCount));
		pendingNormalServicePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		DashboardPanel pendingMajorServicePanel = new DashboardPanel("Pending Major Services", Integer.toString(pendingMServicesCount));
		pendingMajorServicePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		bottomPanel.add(pendingNormalServicePanel);
		bottomPanel.add(Box.createHorizontalStrut(50));
		bottomPanel.add(pendingMajorServicePanel);

		add(bottomPanel);
		add(Box.createVerticalStrut(50));
	}
}
