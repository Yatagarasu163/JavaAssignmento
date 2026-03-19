package components;

import javax.swing.*;
import java.awt.*;


public class TextLabel extends JLabel{

		public TextLabel(String label) {
			setText(label);
			setFont(new Font("SansSerif", Font.BOLD, 28));
			setAlignmentX(Component.CENTER_ALIGNMENT);
		}

}