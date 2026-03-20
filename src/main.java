import javax.swing.*;
import java.awt.*;
import panes.ManagerMainPane;

public class main{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ManagerMainPane testPane = new ManagerMainPane();
			testPane.setVisible(true);
		});
	}
}
