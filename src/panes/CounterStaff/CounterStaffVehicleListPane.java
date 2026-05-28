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

    // Pass an empty string to completely hide the "Add" button row
    public CounterStaffVehicleListPane(CustomerPanelListener listener) {
        super("");
        this.listener = listener;
        // 3. REAL-TIME WATCHER TIMER
        // Checks the database every 3 seconds (3000ms)
        timer = new Timer(3000, e -> {
            List<String[]> vehicles = FileHandler.read(filename);
            StringBuilder currentState = new StringBuilder();

            for (String[] v : vehicles) {
                // Combine ID, Plate, Model, and Color into one long string to detect ANY changes
                if (v.length > 3) {
                    currentState.append(v[0]).append(v[1]).append(v[2]).append(v[3]);
                }
            }

            // If the database data doesn't match our last saved state, refresh!
            if (!currentState.toString().equals(lastStateHash)) {
                lastStateHash = currentState.toString();
                refreshTable(); // Inherited from AbstractListPane!
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
                // Read all vehicles directly, no filtering needed
                String[] arr = {v[0], v[1], v[2], v[4], "Edit"};
                cleanedVehicles.add(arr);
            }
        }
        return cleanedVehicles.toArray(new String[0][]);
    }

    @Override
    protected void onAddButtonClicked() {
        // Leave empty, button is hidden
    }

    @Override
    protected void onRowButtonClicked(String vehicleID) {
        if (listener != null) {
            listener.onViewVehicleDetails(vehicleID);
        }
    }
}