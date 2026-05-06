package panes.Customer;

import IO.FileHandler;
import java.awt.*;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import config.UIConfig;

public class CustomerHistoryPane extends JPanel {

    private final Color primaryPurple = UIConfig.mainBackground;
    private final Color bgColor = UIConfig.whiteBackground2;
    private JTable historyTable;
    private DefaultTableModel tableModel;

    private JPanel cardsContainer;
    private CardLayout cardLayout;
    private String loggedInCustomerID;

    public CustomerHistoryPane(JPanel cardsContainer, CardLayout cardLayout, String cusID) {
        this.cardsContainer = cardsContainer;
        this.cardLayout = cardLayout;
        this.loggedInCustomerID = cusID;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(bgColor);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("History");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 42));
        titleLabel.setForeground(primaryPurple);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel tableContainer = new JPanel();
        tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
        tableContainer.setBackground(primaryPurple);
        tableContainer.setBorder(new EmptyBorder(15, 20, 20, 20));
        tableContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel filterRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterRow.setBackground(primaryPurple);

        JLabel serviceLabel = new JLabel("Service Type:");
        serviceLabel.setForeground(Color.WHITE);
        serviceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        JComboBox<String> serviceCombo = new JComboBox<>(new String[]{"All", "Normal Service", "Major Service"});
        serviceCombo.setPreferredSize(new Dimension(150, 30));

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        String[] days = new String[32]; days[0] = "Day";
        for (int i = 1; i <= 31; i++) days[i] = String.format("%02d", i);

        String[] months = {"Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String[] years = new String[(currentYear - 2020) + 2]; years[0] = "Year";
        for (int i = 0; i <= (currentYear - 2020); i++) years[i + 1] = String.valueOf(currentYear - i);

        JComboBox<String> dayCombo = new JComboBox<>(days);
        JComboBox<String> monthCombo = new JComboBox<>(months);
        JComboBox<String> yearCombo = new JComboBox<>(years);

        JPanel dateComboGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dateComboGroup.setBackground(primaryPurple);
        dateComboGroup.add(dayCombo);
        dateComboGroup.add(monthCombo);
        dateComboGroup.add(yearCombo);

        filterRow.add(serviceLabel);
        filterRow.add(serviceCombo);
        filterRow.add(Box.createHorizontalStrut(20));
        filterRow.add(dateLabel);
        filterRow.add(dateComboGroup);

        String[] columns = {"Date", "Car Plate No.", "Service Type", "Payment", "AppID", "VehID"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        historyTable = new JTable(tableModel);
        historyTable.setRowHeight(35);
        historyTable.setFont(new Font("Serif", Font.PLAIN, 14));
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        historyTable.setGridColor(Color.GRAY);
        historyTable.setShowGrid(true);

        JTableHeader header = historyTable.getTableHeader();
        header.setFont(new Font("Serif", Font.BOLD, 16));
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        historyTable.removeColumn(historyTable.getColumnModel().getColumn(5));
        historyTable.removeColumn(historyTable.getColumnModel().getColumn(4));

        Runnable loadDataFromDatabase = () -> {
            tableModel.setRowCount(0);

            String selectedService = (String) serviceCombo.getSelectedItem();
            String selectedDay = (String) dayCombo.getSelectedItem();
            String selectedMonth = (String) monthCombo.getSelectedItem();
            String selectedYear = (String) yearCombo.getSelectedItem();

            List<String[]> paymentList = FileHandler.read("Payment.txt");
            List<String[]> vehicleList = FileHandler.read("Vehicle.txt");
            List<String[]> apptList = FileHandler.read("Appointment.txt");

            for (String[] payRow : paymentList) {
                if (payRow.length >= 7) {
                    String custId = payRow[3].trim();
                    String paymentStatus = payRow[6].trim();

                    if (custId.equals(loggedInCustomerID) && paymentStatus.equalsIgnoreCase("Paid")) {
                        String date = payRow[2];
                        String vehId = payRow[4];
                        String appId = payRow[5];

                        String serviceType = "Unknown";
                        for (String[] apptRow : apptList) {
                            if (apptRow.length >= 3 && apptRow[0].equals(appId)) {
                                serviceType = apptRow[2];
                                break;
                            }
                        }

                        if (!"All".equals(selectedService) && !selectedService.equals(serviceType)) {
                            continue;
                        }

                        String[] dateParts = date.split("/");
                        if (dateParts.length == 3) {
                            String d = dateParts[0];
                            String m = dateParts[1];
                            String y = dateParts[2];

                            if (!"Year".equals(selectedYear) && !selectedYear.equals(y)) continue;
                            if (!"Month".equals(selectedMonth) && monthCombo.getSelectedIndex() != Integer.parseInt(m)) continue;
                            if (!"Day".equals(selectedDay) && Integer.parseInt(selectedDay) != Integer.parseInt(d)) continue;
                        }

                        String plate = vehId;
                        for (String[] vRow : vehicleList) {
                            if (vRow.length >= 2 && vRow[0].equals(vehId)) {
                                plate = vRow[1];
                                break;
                            }
                        }

                        String totalAmount = "RM " + payRow[1] + " (Paid)";

                        tableModel.addRow(new Object[]{date, plate, serviceType, totalAmount, appId, vehId});
                    }
                }
            }
        };

        serviceCombo.addActionListener(e -> loadDataFromDatabase.run());
        dayCombo.addActionListener(e -> loadDataFromDatabase.run());
        monthCombo.addActionListener(e -> loadDataFromDatabase.run());
        yearCombo.addActionListener(e -> loadDataFromDatabase.run());

        loadDataFromDatabase.run();

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableContainer.add(filterRow);
        tableContainer.add(Box.createVerticalStrut(10));
        tableContainer.add(scrollPane);

        JButton viewDetailsBtn = new JButton("View Details");
        viewDetailsBtn.setFont(new Font("Serif", Font.BOLD, 16));
        viewDetailsBtn.setForeground(primaryPurple);
        viewDetailsBtn.setBackground(Color.WHITE);
        viewDetailsBtn.setFocusPainted(false);
        viewDetailsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewDetailsBtn.setPreferredSize(new Dimension(180, 40));

        viewDetailsBtn.addActionListener(e -> {
            int viewRow = historyTable.getSelectedRow();

            if (viewRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an appointment from the table first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            } else {

                String selectedAppId = (String) tableModel.getValueAt(viewRow, 4);
                String selectedVehId = (String) tableModel.getValueAt(viewRow, 5);

                panes.Customer.CustomerPaymentDetailsPane customPaymentPane =
                        new panes.Customer.CustomerPaymentDetailsPane(cardsContainer, cardLayout, loggedInCustomerID, selectedAppId, selectedVehId);

                cardsContainer.add(customPaymentPane, "DYNAMIC_PAYMENT");
                cardLayout.show(cardsContainer, "DYNAMIC_PAYMENT");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(bgColor);
        buttonPanel.add(viewDetailsBtn);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(titleLabel);
        add(Box.createVerticalStrut(20));
        add(tableContainer);
        add(Box.createVerticalStrut(20));
        add(buttonPanel);
    }
}