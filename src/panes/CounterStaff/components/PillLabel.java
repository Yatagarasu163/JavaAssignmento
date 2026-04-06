package panes.CounterStaff.components;

import javax.swing.*;
import java.awt.*;

public class PillLabel extends JLabel {

    private int radius = 20;

    public PillLabel(String text) {
        super(text);
        setForeground(new Color(128, 128, 255));
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setHorizontalAlignment(SwingConstants.CENTER);

        setOpaque(false); // we paint manually
        setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // background (white pill)
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // border
        g2.setColor(new Color(200, 200, 255));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);

        g2.dispose();

        super.paintComponent(g); // draw text
    }
}  