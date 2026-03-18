import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Test extends JFrame{

	public static void main(String[] args){
		
		SwingUtilities.invokeLater(() -> {
			new Test().setVisible(true);
		});
		
	}


	public Test() {
		setTitle("Testing Window");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new GridBagLayout());

		JPanel sidebarPanel = new JPanel();
		sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
		sidebarPanel.setBackground(new Color(128, 128, 255));

		JLabel appLabel = new JLabel("APU - ASC");
		appLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
		appLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		Button testBtn = new Button("Profile");
		Button newBtn = new Button("Haha");

		JPanel dashboardPane = new JPanel();
		dashboardPane.setLayout(new BoxLayout(dashboardPane, BoxLayout.Y_AXIS));
		JLabel testLbl = new JLabel("Hmm");
		testLbl.setFont(new Font("SansSerif", Font.BOLD, 28));
		testLbl.setForeground(Color.BLACK);
		testLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel notProfileLbl = new JLabel("I'm New! and on top, apparently");
		notProfileLbl.setFont(new Font("SansSerif", Font.PLAIN, 28));
		notProfileLbl.setForeground(Color.RED);
		notProfileLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

		
		//Adding sidebarPanel and dashboardPane using gridbag constraints

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1.0;
		gbc.weightx = 0.5;
		contentPane.add(sidebarPanel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.5;
		gbc.weighty = 1.0;
		contentPane.add(dashboardPane, gbc);


		dashboardPane.add(testLbl);
		sidebarPanel.add(Box.createVerticalStrut(30));
		sidebarPanel.add(appLabel);
		sidebarPanel.add(Box.createVerticalStrut(50));
		sidebarPanel.add(testBtn);
		sidebarPanel.add(Box.createVerticalStrut(50));
		sidebarPanel.add(newBtn);

		testBtn.addActionListener(e -> {
			testBtn.setActive(true);
			newBtn.setActive(false);
			testLbl.setText("Profile!");
			dashboardPane.removeAll();
			dashboardPane.add(notProfileLbl);
			dashboardPane.add(Box.createVerticalStrut(20));
			dashboardPane.add(testLbl);
			dashboardPane.revalidate();
			dashboardPane.repaint();
			repaint();
		});

		newBtn.addActionListener(e -> {
			newBtn.setActive(true);
			testBtn.setActive(false);
			testLbl.setText("Testing!!");
			dashboardPane.removeAll();
			dashboardPane.add(notProfileLbl);
			dashboardPane.add(Box.createVerticalStrut(20));
			dashboardPane.add(testLbl);
			dashboardPane.revalidate();
			dashboardPane.repaint();
			repaint();

		});
	}

	class Button extends JButton{

		public Button(String label) {
		setText(label);
		setBackground(new Color(128, 128, 255));
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setForeground(Color.WHITE);
		setFont(new Font("SansSerif", Font.BOLD, 28));
		setFocusPainted(false);
		setBorderPainted(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		public void setActive(boolean isActive) {
			if(isActive){
				setBackground(Color.WHITE);
				setForeground(Color.BLACK);
			} else{
				setBackground(new Color(128, 128, 255));
				setForeground(Color.WHITE);
			}
		}
	}
}
