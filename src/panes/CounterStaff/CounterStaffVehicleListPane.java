package panes.CounterStaff;

import java.util.List;
import java.util.ArrayList;
import panes.CounterStaff.components.CustomerPanelListener;
import panes.CounterStaff.components.AbstractListPane;
import IO.FileHandler;
import javax.swing.Timer;

public class CounterStaffVehicleListPane extends AbstractListPane {

    private CustomerPanelListener listener;
    private static final String filename = "Vehicle.txt";
    private String lastStateHash = "";
    private Timer timer;

    public CounterStaffVehicleListPane(CustomerPanelListener listener) {
        super("");
        this.listener = listener;

        timer = new Timer(3000, e -> {
            List<String[]> vehicles = FileHandler.read(filename);
            StringBuilder currentState = new StringBuilder();

            for (String[] v : vehicles) {
                if (v.length > 3) {
                    currentState.append(v[0]).append(v[1]).append(v[2]).append(v[3]);
                }
            }

            if (!currentState.toString().equals(lastStateHash)) {
                lastStateHash = currentState.toString();
                refreshTable();
            }
        });
        timer.start();
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Vehicle ID", "Car Plate", "Model", "Owner ID", "Details"};
    }

    @Override
    protected String[][] getTableData() {
        List<String[]> vehicles = FileHandler.read(filename);
        List<String[]> cleanedVehicles = new ArrayList<>();

        if (vehicles.size() > 0) {
            for(String[] v : vehicles){
                String[] arr = {v[0], v[1], v[2], v[4], "Edit"};
                cleanedVehicles.add(arr);
            }
        }
        return cleanedVehicles.toArray(new String[0][]);
    }

    @Override
    protected void onAddButtonClicked() {

    }

    @Override
    protected void onRowButtonClicked(String vehicleID) {
        if (listener != null) {
            listener.onViewVehicleDetails(vehicleID);
        }
    }
}