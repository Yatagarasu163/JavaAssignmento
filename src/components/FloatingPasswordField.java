package components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import config.UIConfig;

public class FloatingPasswordField extends JPasswordField {
    private String placeholder;
    private char defaultEchoChar = '•';

    public FloatingPasswordField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(false);
        setMaximumSize(new Dimension(300, 50));
        setPreferredSize(new Dimension(300, 50));
        setFont(new Font("SansSerif", Font.PLAIN, 16));
        setForeground(UIConfig.passwordFieldFont);
        setCaretColor(UIConfig.caretColor);
        setEchoChar(defaultEchoChar);

        setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY),
                new EmptyBorder(20, 0, 5, 0)
        ));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                        new MatteBorder(0, 0, 2, 0, new Color(128, 128, 255)),
                        new EmptyBorder(20, 0, 5, 0) // Padding updated here too
                ));
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                        new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY),
                        new EmptyBorder(20, 0, 5, 0) // Padding updated here too
                ));
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getPassword().length == 0 && !hasFocus()) {
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 16));
            g2d.setColor(Color.GRAY);
            g2d.drawString(placeholder, 0, 32);
        } else {
            g2d.setFont(new Font("SansSerif", Font.BOLD, 11));
            g2d.setColor(new Color(128, 128, 255));
            g2d.drawString(placeholder, 0, 15);
        }
    }
}