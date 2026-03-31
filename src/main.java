import javax.swing.*;
import java.awt.*;

import panes.CustomerMainPane;
import panes.ManagerMainPane;
import panes.TechnicianMainPane;

public class main{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
//			ManagerMainPane testPane = new ManagerMainPane();
//			CustomerMainPane testPane = new CustomerMainPane();
			TechnicianMainPane testPane = new TechnicianMainPane();
			testPane.setVisible(true);
		});
	}
}
