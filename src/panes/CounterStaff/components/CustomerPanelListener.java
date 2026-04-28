package panes.CounterStaff.components;

public interface CustomerPanelListener {
    void onViewCustomerDetails(String customerID);
    void onBackToList();
    void onCreateCustomer();
    void onAddVehicle(String customerID);
}
