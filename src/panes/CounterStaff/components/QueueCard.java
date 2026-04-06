package panes.CounterStaff.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class QueueCard extends JPanel{
	private int radius = 30;
	private boolean highlighted;

	public QueueCard(String time, String car, String plate, String status, boolean highlighted) {
		this.highlighted = highlighted;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setPreferredSize(new Dimension(180, 200));

		add(centerLabel(time, 18, true));
		add(Box.createVerticalStrut(10));
		add(centerLabel(car, 16, true));
		add(Box.createVerticalStrut(5));
		add(centerLabel(plate, 14, false));
		add(Box.createVerticalStrut(15));

		add(createStatusButton(status));

	}

	private JLabel centerLabel(String text, int size, boolean bold) {
		JLabel lbl = new JLabel(text);
		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl.setForeground(Color.WHITE);
		lbl.setFont(new Font("SansSerif", bold ? Font.BOLD : Font.PLAIN, size));
		return lbl;
	}

	private JButton createStatusButton(String text){

		JButton btn = new JButton(text);
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);

		btn.setForeground(Color.WHITE);
		btn.setBackground(new Color(100, 100, 255));
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);

		btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

		return btn;
	}


	@Override
	protected void paintComponent(Graphics g){

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (highlighted) {
			g2.setColor(new Color(150, 150, 255));
		} else {
			g2.setColor(new Color(180, 180, 255, 120));
		}

		g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

		if(highlighted) {
			g2.setColor(Color.WHITE);
			g2.setStroke(new BasicStroke(2));
			g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
		}
	}
}
