package panes.Manager.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import components.TextLabel;
import config.UIConfig;

public class DashboardPanel extends JPanel {

    private int radius = 20;

    public DashboardPanel(String title, String value) {
        this(title, value, "");
    }

    public DashboardPanel(String title, String value, String subtext) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        TextLabel panelTitle = new TextLabel(title);
        panelTitle.setForeground(Color.WHITE);
        panelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        panelTitle.setBorder(new EmptyBorder(15, 20, 5, 20));

        TextLabel panelValue = new TextLabel(value);
        panelValue.setForeground(Color.WHITE);
        panelValue.setHorizontalAlignment(SwingConstants.CENTER);

        if (subtext.isEmpty()) {
            panelValue.setBorder(new EmptyBorder(10, 20, 20, 20));
        } else {
            panelValue.setBorder(new EmptyBorder(5, 20, 0, 20));
        }

        add(panelTitle, BorderLayout.NORTH);
        add(panelValue, BorderLayout.CENTER);

        if (!subtext.isEmpty()) {
            TextLabel panelSubtext = new TextLabel(subtext);
            panelSubtext.setForeground(Color.LIGHT_GRAY);
            panelSubtext.setFontSize(14);
            panelSubtext.setHorizontalAlignment(SwingConstants.CENTER);
            panelSubtext.setBorder(new EmptyBorder(5, 20, 15, 20));

            add(panelSubtext, BorderLayout.SOUTH);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(UIConfig.mainBackground);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }
}