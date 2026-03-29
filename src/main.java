import javax.swing.*;
import java.awt.*;

import panes.CustomerMainPane;
import panes.TechnicianProfilePane;
import panes.CounterStaff.CounterStaffMainPane;
import panes.Manager.ManagerMainPane;

public class main{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			// ManagerMainPane testPane = new ManagerMainPane();
			// CustomerMainPane testPane = new CustomerMainPane();
			CounterStaffMainPane testPane = new CounterStaffMainPane();
			testPane.setVisible(true);
		});
	}
}
