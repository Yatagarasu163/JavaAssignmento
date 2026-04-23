import javax.swing.*;

import panes.CounterStaff.CounterStaffMainPane;
import panes.Customer.CustomerMainPane;
import panes.Manager.ManagerMainPane;
import panes.Technician.TechnicianMainPane;
import IO.FileHandler;

import java.util.ArrayList;

public class main{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			//ManagerMainPane testPane = new ManagerMainPane();
//			 CustomerMainPane testPane = new CustomerMainPane();
			CounterStaffMainPane testPane = new CounterStaffMainPane();
			//  TechnicianMainPane testPane = new TechnicianMainPane("TC123456");
			testPane.setVisible(true);
		});

	}
}
