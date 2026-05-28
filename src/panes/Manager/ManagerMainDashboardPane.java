package panes.Manager;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import components.TextLabel;
import components.FeedbackTable;
import panes.Manager.components.DashboardPanel;
import IO.FileHandler;
import config.UIConfig;

public class ManagerMainDashboardPane extends JPanel {

	public ManagerMainDashboardPane() {
		List<String[]> appointments = FileHandler.read("Appointment.txt");
		List<String[]> payments = FileHandler.read("Payment.txt");

		int pendingServicesCount = 0;
		int completedServicesCount = 0;
		int pendingNServicesCount = 0;
		int pendingMServicesCount = 0;

		for (String[] appointment : appointments) {
			if (appointment.length > 4) {
				if (appointment[4].equalsIgnoreCase("In Queue") || appointment[4].equalsIgnoreCase("In Service")) {
					pendingServicesCount++;
					if (appointment[2].equalsIgnoreCase("Normal Service")) pendingNServicesCount++;
					else pendingMServicesCount++;
				} else if (appointment[4].equalsIgnoreCase("Completed")) {
					completedServicesCount++;
				}
			}
		}

		LocalDate today = LocalDate.now();
		String currentMonthYear = String.format("%02d/%d", today.getMonthValue(), today.getYear()); // e.g., "05/2026"

		double monthlyRevenue = 0.0;
		List<String[]> monthlyPaymentsList = new ArrayList<>();

		for (String[] payment : payments) {
			if (payment.length >= 7 && payment[2].contains(currentMonthYear) && payment[6].equalsIgnoreCase("Paid")) {
				try {
					monthlyRevenue += Double.parseDouble(payment[1]);
					monthlyPaymentsList.add(new String[]{payment[0], payment[2], "RM " + payment[1], payment[5]});
				} catch (NumberFormatException ignored) {}
			}
		}

		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(30, 40, 30, 40));

		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titlePanel.setOpaque(false);
		TextLabel title = new TextLabel("Manager Overview");
		title.setFont(new Font("SansSerif", Font.BOLD, 28));
		titlePanel.add(title);

		JPanel kpiGrid = new JPanel(new GridLayout(1, 3, 30, 0));
		kpiGrid.setOpaque(false);
		kpiGrid.setPreferredSize(new Dimension(Integer.MAX_VALUE, 150));
		kpiGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

		String workloadSubtitle = String.format("(%d Normal Service | %d Major Service)", pendingNServicesCount, pendingMServicesCount);
		DashboardPanel pendingPanel = new DashboardPanel("Active Workload", Integer.toString(pendingServicesCount), workloadSubtitle);
		DashboardPanel completedPanel = new DashboardPanel("Ready for Pickup", Integer.toString(completedServicesCount));
		DashboardPanel revenuePanel = new DashboardPanel("Monthly Revenue", String.format("RM %.2f", monthlyRevenue));

		kpiGrid.add(pendingPanel);
		kpiGrid.add(completedPanel);
		kpiGrid.add(revenuePanel);

		JPanel bottomSection = new JPanel(new BorderLayout(0, 15));
		bottomSection.setOpaque(false);
		bottomSection.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

		TextLabel bottomTitle = new TextLabel("Recent Activity: Payments (" + today.getMonth().toString() + " " + today.getYear() + ")");
		bottomTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
		bottomSection.add(bottomTitle, BorderLayout.NORTH);

		String[] columns = {"Payment ID", "Date", "Total Paid", "Appointment ID"};
		String[][] tableData = monthlyPaymentsList.toArray(new String[0][]);

		DefaultTableModel model = new DefaultTableModel(tableData, columns) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};

		FeedbackTable paymentTable = new FeedbackTable(model);
		paymentTable.setBackground(UIConfig.mainForeground);
		paymentTable.setForeground(UIConfig.mainBackground);

		JScrollPane scrollPane = new JScrollPane(paymentTable);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 230), 1, true));
		bottomSection.add(scrollPane, BorderLayout.CENTER);

		JPanel mainContent = new JPanel();
		mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
		mainContent.setOpaque(false);

		mainContent.add(titlePanel);
		mainContent.add(Box.createVerticalStrut(20));
		mainContent.add(kpiGrid);
		mainContent.add(bottomSection);

		add(mainContent, BorderLayout.CENTER);
	}
}