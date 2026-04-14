package components;

import javax.swing.*;
import java.awt.*;

public class FloatingCheckBox extends JCheckBox{
    public FloatingCheckBox(String label){
        super(label);
        setBackground(Color.WHITE);
        setFont(new Font("SansSerif", Font.BOLD, 28));
        setForeground(Color.LIGHT_GRAY);
    }
}
