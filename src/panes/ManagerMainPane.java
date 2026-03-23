package panes;

import javax.swing.*;

import panes.AccountPane;
import panes.SidebarPanel;
import panes.PricingPane;
import panes.FeedbackPane;

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

		AccountPane accountPane = new AccountPane();
		PricingPane pricingPane = new PricingPane();
		FeedbackPane feedbackPane = new FeedbackPane();
		

		JScrollPane sidePane = new JScrollPane(sidebarPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sidePane.setPreferredSize(new Dimension(350, Integer.MAX_VALUE));

		contentPane.add(sidePane, BorderLayout.WEST);
		contentPane.add(dashboardPane, BorderLayout.CENTER);

		dashboardPane.add(accountPane, "ACCOUNT");
		dashboardPane.add(pricingPane, "PRICING");
		dashboardPane.add(feedbackPane, "FEEDBACK");

		

		sidebarPanel.getAccountBtn().addActionListener(e -> {
			cardLayout.show(dashboardPane, "ACCOUNT");
		});
		
		sidebarPanel.getPricingBtn().addActionListener(e -> {
			cardLayout.show(dashboardPane, "PRICING");
		});

		sidebarPanel.getFeedbackBtn().addActionListener(e -> {
			cardLayout.show(dashboardPane, "FEEDBACK");
		});
	}


}
