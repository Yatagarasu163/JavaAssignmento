package components;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import config.UIConfig;


public class FloatingTextField extends JTextField {
    private String placeholder;
    private int textSize = 20;
    private Color placeholderColor = Color.GRAY;
    private Color activeColor = new Color(128, 128, 255);

    public FloatingTextField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(false);
        setMaximumSize(new Dimension(300, 50));
        setPreferredSize(new Dimension(300, 50));
        setFont(new Font("SansSerif", Font.PLAIN, textSize));
        setForeground(new Color(128, 128, 255));
        setCaretColor(new Color(128, 128, 255));

        setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, placeholderColor),
                new EmptyBorder(20, 0, 5, 0)
        ));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                        new MatteBorder(0, 0, 2, 0, activeColor),
                        new EmptyBorder(20, 0, 5, 0)
                ));
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                        new MatteBorder(0, 0, 2, 0, placeholderColor),
                        new EmptyBorder(20, 0, 5, 0)
                ));
                repaint();
            }
        });
    }

    public void setPlaceHolderColor(Color placeholderColor){
        this.placeholderColor = placeholderColor;
        repaint();
    }

    public void setActiveColor(Color activeColor){
        this.activeColor = activeColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getText().isEmpty() && !hasFocus()) {
            g2d.setFont(new Font("SansSerif", Font.PLAIN, textSize));
            g2d.setColor(placeholderColor);
            g2d.drawString(placeholder, 0, 32);
        } else {
            g2d.setFont(new Font("SansSerif", Font.BOLD, textSize - 5));
            g2d.setColor(activeColor);
            g2d.drawString(placeholder, 0, 15);
        }
    }
}