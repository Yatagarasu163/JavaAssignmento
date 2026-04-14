package components;

import javax.swing.*;
import java.awt.*;


public class TextLabel extends JLabel{

	private String fontFamily = "SansSerif";
	private int fontType = Font.BOLD; 
	private int fontSize = 28;

		public TextLabel(String label) {
			setText(label);
			setFont(new Font(fontFamily, fontType, fontSize));
			setAlignmentX(Component.CENTER_ALIGNMENT);
		}

		public void setFontFamily(String fontFamily){
			this.fontFamily = fontFamily;
			setFont(new Font(this.fontFamily, fontType, fontSize));
		}

		public void setFontType(int fontType){
			this.fontType = fontType;
			setFont(new Font(fontFamily, this.fontType, fontSize));
		}

		public void setFontSize(int fontSize){
			this.fontSize = fontSize;
			setFont(new Font(fontFamily, fontType, this.fontSize));
		}

}