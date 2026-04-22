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
//			CounterStaffMainPane testPane = new CounterStaffMainPane();
			 TechnicianMainPane testPane = new TechnicianMainPane();
			testPane.setVisible(true);
		});

		// calling class by ArrayList<String> <variable> = FileHandler.read(<File Name>);
//		ArrayList<String> fileRows = FileHandler.read("Technician.txt");
//      // get specific row by using <variable>.get()
//		System.out.println(fileRows);
//		String secondRow = fileRows.get(1);
//		System.out.println(secondRow);
//
//		ArrayList<String> logData = new ArrayList<>();
//		logData.add("VKA 1234, In Service, 10:00 AM");
//		logData.add("WYU 6469, Pending, 11:30 AM");
//
 /// /  writing data into file
//		FileHandler.write("Technician.txt", logData, true);

	}
}
