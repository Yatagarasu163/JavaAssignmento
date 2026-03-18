import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.CardLayout;

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

		TextLabel appLabel = new TextLabel("APU - ASC");

		JToggleButton profileBtn = createSidebarButton("Profile");
		JToggleButton homeBtn = createSidebarButton("Home");

		ButtonGroup sidebarGroup = new ButtonGroup();

		sidebarGroup.add(profileBtn);
		sidebarGroup.add(homeBtn);

		JPanel dashboardPane = new JPanel();
		CardLayout cardLayout = new CardLayout();
		dashboardPane.setLayout(cardLayout);

		JPanel profilePane = new JPanel();
		profilePane.setLayout(new BoxLayout(profilePane, BoxLayout.Y_AXIS));
		TextLabel profileLabel = new TextLabel("PROFILE");
		profilePane.add(profileLabel);

		JPanel homePane = new JPanel();
		homePane.setLayout(new BoxLayout(homePane, BoxLayout.Y_AXIS));
		TextLabel homeLabel = new TextLabel("HOME");
		homePane.add(homeLabel);


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

		dashboardPane.add(profilePane, "PROFILE");
		dashboardPane.add(homePane, "HOME");

		sidebarPanel.add(Box.createVerticalStrut(30));
		sidebarPanel.add(appLabel);
		sidebarPanel.add(Box.createVerticalStrut(50));
		sidebarPanel.add(profileBtn);
		sidebarPanel.add(Box.createVerticalStrut(50));
		sidebarPanel.add(homeBtn);

		profileBtn.addActionListener(e -> {
			cardLayout.show(dashboardPane, "PROFILE");
		});

		homeBtn.addActionListener(e -> {
			cardLayout.show(dashboardPane, "HOME");

		});
	}

	// Creates a new toggle sidebar button using this method
	public JToggleButton createSidebarButton(String name){
		JToggleButton btn = new JToggleButton(name);
		btn.setBackground(new Color(128, 128, 255));
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("SansSerif", Font.BOLD, 28));
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		btn.addItemListener(e -> {
			if(btn.isSelected()){
				btn.setBackground(Color.WHITE);
				btn.setForeground(Color.BLACK);
			} else {
				btn.setBackground(new Color(128, 128, 255));
				btn.setForeground(Color.WHITE);
			}
		});

		return btn;

	}


	class TextLabel extends JLabel{

		public TextLabel(String label) {
			setText(label);
			setFont(new Font("SansSerif", Font.BOLD, 28));
			setAlignmentX(Component.CENTER_ALIGNMENT);
		}

	}

	class sidebarPanel extends JPanel{

	}
}
