package panes.Manager;

import javax.swing.*;

import panes.SidebarPanel;

import java.awt.*;
public class ManagerMainPane extends JFrame{

	public ManagerMainPane() {
		setTitle("Testing Window");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		SidebarPanel sidebarPanel = new SidebarPanel("Manager");

		JPanel dashboardPane = new JPanel();
		CardLayout cardLayout = new CardLayout();
		dashboardPane.setLayout(cardLayout);

		ManagerMainAccountPage accountPane = new ManagerMainAccountPage();
		ManagerPricingPane pricingPane = new ManagerPricingPane();
		ManagerFeedbackPane feedbackPane = new ManagerFeedbackPane();
		ManagerMainDashboardPane mainDashboardPane = new ManagerMainDashboardPane();
		ManagerProfile profilePane = new ManagerProfile();
		

		JScrollPane sidePane = new JScrollPane(sidebarPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sidePane.setPreferredSize(new Dimension(350, Integer.MAX_VALUE));

		contentPane.add(sidePane, BorderLayout.WEST);
		contentPane.add(dashboardPane, BorderLayout.CENTER);

		dashboardPane.add(accountPane, "ACCOUNT");
		dashboardPane.add(pricingPane, "PRICING");
		dashboardPane.add(feedbackPane, "FEEDBACK");
		dashboardPane.add(mainDashboardPane, "DASHBOARD");
		dashboardPane.add(profilePane, "PROFILE");



		// To return to main dashboard
		sidebarPanel.getHomeBtn().addActionListener(e -> {
			// Change the "ACCOUNT" to "Dashboard" after you created a dashboard interface
			cardLayout.show(dashboardPane, "DASHBOARD");

			// Clear the other buttons "White Background"
			sidebarPanel.clearSelection();
		});

		sidebarPanel.getAccountBtn().addActionListener(e -> {
			cardLayout.show(dashboardPane, "ACCOUNT");
		});
		
		sidebarPanel.getPricingBtn().addActionListener(e -> {
			cardLayout.show(dashboardPane, "PRICING");
		});

		sidebarPanel.getFeedbackBtn().addActionListener(e -> {
			cardLayout.show(dashboardPane, "FEEDBACK");
		});

		sidebarPanel.getProfileBtn().addActionListener(e -> {
			cardLayout.show(dashboardPane, "PROFILE");
		});
	}


}
