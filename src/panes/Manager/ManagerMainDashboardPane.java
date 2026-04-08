package panes.Manager;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import components.TextLabel;
import panes.Manager.components.DashboardPanel;

public class ManagerMainDashboardPane extends JPanel{
	public ManagerMainDashboardPane(){
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

		DashboardPanel requestsPanel = new DashboardPanel("Unassigned Requests", "50");
		DashboardPanel pendingServicesPanel = new DashboardPanel("Pending Services", "20");
		DashboardPanel completedServicesPanel = new DashboardPanel("Completed Services", " 67");

		topPanel.add(requestsPanel);
		topPanel.add(Box.createHorizontalStrut(50));
		topPanel.add(pendingServicesPanel);
		topPanel.add(Box.createHorizontalStrut(50));
		topPanel.add(completedServicesPanel);
		
		add(topPanel);
		add(Box.createVerticalStrut(50));

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		DashboardPanel pendingNormalServicePanel = new DashboardPanel("Pending Normal Services", "20");
		pendingNormalServicePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		DashboardPanel pendingMajorServicePanel = new DashboardPanel("Pending Major Services", "34");
		pendingMajorServicePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		bottomPanel.add(pendingNormalServicePanel);
		bottomPanel.add(Box.createHorizontalStrut(50));
		bottomPanel.add(pendingMajorServicePanel);

		add(bottomPanel);
		add(Box.createVerticalStrut(50));
		
		
	}
}
