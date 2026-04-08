package panes.Manager.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import components.TextLabel;
import config.UIConfig;


public class DashboardPanel extends JPanel{

    private int radius = 20;

    public DashboardPanel(String title, String value){
        setLayout(new BorderLayout());
        setBackground(UIConfig.mainBackground);
        setForeground(UIConfig.mainForeground);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        TextLabel panelTitle = new TextLabel(title);
        panelTitle.setForeground(Color.WHITE);
        panelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        panelTitle.setBorder(new EmptyBorder(20, 20, 20, 20));


        TextLabel panelValue = new TextLabel(value);
        panelValue.setForeground(Color.WHITE);
        panelValue.setHorizontalAlignment(SwingConstants.CENTER);
        panelValue.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(panelTitle, BorderLayout.NORTH);
        add(panelValue, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(UIConfig.mainBackground);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();
    }
}