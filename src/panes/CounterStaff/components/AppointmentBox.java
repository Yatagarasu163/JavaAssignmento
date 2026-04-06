package panes.CounterStaff.components;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import config.UIConfig;
import components.TextLabel;

public class AppointmentBox extends JPanel{

    private int radius = 30; 
    
    public AppointmentBox(String technicianName){
        setLayout(new BorderLayout(20, 10));
        setBackground(UIConfig.mainBackground);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(0, 250));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        setOpaque(false);
        
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        TextLabel PICText = new TextLabel("Person-in-Charge: ");
        PICText.setForeground(Color.WHITE);
        PillLabel technicianNameLabel = new PillLabel(technicianName);
        technicianNameLabel.setForeground(UIConfig.mainBackground);
        technicianNameLabel.setBackground(Color.WHITE);
        technicianNameLabel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIConfig.mainForeground, 2,  true),
            new EmptyBorder(5, 5, 5, 5)
        ));
        topPanel.add(PICText);
        topPanel.add(Box.createHorizontalStrut(5));
        topPanel.add(technicianNameLabel);

        add(topPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel();
        middlePanel.setOpaque(false);
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
        middlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        QueueCard queue1 = new QueueCard("10.00 p.m.", "Honda Jazz", "PLK3215", "Incomplete", true);
        QueueCard queue2 = new QueueCard("12.00 p.m.", "Perodua Myvi", "DED2315", "In Progress", false);
        middlePanel.add(queue1);
        middlePanel.add(Box.createHorizontalStrut(20));
        middlePanel.add(queue2);
        middlePanel.add(Box.createHorizontalStrut(20));

        JScrollPane scrollPanel = new JScrollPane(middlePanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setOpaque(false);
        scrollPanel.getViewport().setOpaque(false);
        scrollPanel.setBorder(null);
        scrollPanel.getHorizontalScrollBar().setUnitIncrement(16);
        add(scrollPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(UIConfig.mainBackground);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.setColor(Color.DARK_GRAY);
        g2.drawRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.setColor(new Color(0, 0, 0, 30));
        g2.setStroke(new BasicStroke(1.5f));

        super.paintComponent(g);
        g2.dispose();
    }
}