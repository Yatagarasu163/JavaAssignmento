package components;

import java.awt.*;
import javax.swing.*;
import config.UIConfig;

public class FloatingComboBox<E> extends JComboBox<E>{
    
    private int radius = 0;

    public FloatingComboBox(E[] options){
        super(options);
        setSelectedIndex(0);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(UIConfig.mainBackground);
        setForeground(UIConfig.mainForeground);
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setCursor(new Cursor(Cursor.HAND_CURSOR));     
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
    }

    public FloatingComboBox(E[] options, int radius){
        super(options);
        setSelectedIndex(0);
        this.radius = radius;
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(UIConfig.mainBackground);
        setForeground(UIConfig.mainForeground);
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));   
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Setting the background of the combo box
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), this.radius, this.radius);

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(200, 200 , 200));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, this.radius, this.radius);
    }

}
