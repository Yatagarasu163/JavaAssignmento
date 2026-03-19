package components;

import java.awt.*;
import javax.swing.*;

public class FloatingButton extends JButton{

    private int radius = 0;

    public FloatingButton(String label){
        setText(label);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(new Color(128, 128, 225));
        setForeground(Color.WHITE);
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public FloatingButton(String label, int radius){
        setText(label);
        this.radius = radius;
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(new Color(128, 128, 225));
        setForeground(Color.WHITE);
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
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
