import javax.swing.*;

import components.AccountPane;
import components.SidebarPanel;
import components.PricingPane;

import java.awt.*;
public class Main extends JFrame{

	

	public static void main(String[] args){
		
		SwingUtilities.invokeLater(() -> {
			new Main().setVisible(true);
		});
		
	}


	public Main() {
		setTitle("Testing Window");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new GridBagLayout());

		SidebarPanel sidebarPanel = new SidebarPanel();

		JPanel dashboardPane = new JPanel();
		CardLayout cardLayout = new CardLayout();
		dashboardPane.setLayout(cardLayout);

		AccountPane accountPane = new AccountPane();
		PricingPane pricingPane = new PricingPane();

		//Adding sidebarPanel and dashboardPane using gridbag constraints
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1.0;
		gbc.weightx = 0.3;
		sidebarPanel.setPreferredSize(new Dimension(200, 0));
		contentPane.add(sidebarPanel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.5;
		gbc.weighty = 1.0;
		contentPane.add(dashboardPane, gbc);


		dashboardPane.add(accountPane, "ACCOUNT");
		dashboardPane.add(pricingPane, "PRICING");

		

		sidebarPanel.getAccountBtn().addActionListener(e -> {
			cardLayout.show(dashboardPane, "ACCOUNT");
		});
		
		sidebarPanel.getPricingBtn().addActionListener(e -> {
			cardLayout.show(dashboardPane, "PRICING");
		});
	}


}
