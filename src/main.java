import javax.swing.*;
import java.awt.*;

import panes.CustomerMainPane;
import panes.ManagerMainPane;

public class main{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
//			ManagerMainPane testPane = new ManagerMainPane();
			CustomerMainPane testPane = new CustomerMainPane();
			testPane.setVisible(true);
		});
	}
}
