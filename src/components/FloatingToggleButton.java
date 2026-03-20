package components;

import java.awt.*;
import javax.swing.*;
import config.UIConfig;

public class FloatingToggleButton extends JToggleButton{

    private int radius = 0;
    private int height = 35;

    public FloatingToggleButton(String label){
        setText(label);
        setBackground(UIConfig.mainBackground);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setForeground(UIConfig.mainForeground);
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(150, height));

        addItemListener(e -> {
			if(isSelected()){
				setBackground(Color.WHITE);
				setForeground(Color.BLACK);
			} else {
				setBackground(UIConfig.mainBackground);
				setForeground(UIConfig.mainForeground);
			}
		});
    }

    public FloatingToggleButton(String label, int radius){
        setText(label);
        this.radius = radius;
		setBackground(UIConfig.mainBackground);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setForeground(UIConfig.mainForeground);
		setFont(new Font("SansSerif", Font.BOLD, 28));
        setContentAreaFilled(false);
		setFocusPainted(false);
		setBorderPainted(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(150, height));

		addItemListener(e -> {
			if(isSelected()){
				setBackground(Color.WHITE);
				setForeground(Color.BLACK);
			} else {
				setBackground(UIConfig.mainBackground);
				setForeground(UIConfig.mainForeground);
			}
		});
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }


        if(this.radius > 0){
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), this.radius, this.radius);
        } else {
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
        }

        g2.dispose();

        super.paintComponent(g);
    }
}
