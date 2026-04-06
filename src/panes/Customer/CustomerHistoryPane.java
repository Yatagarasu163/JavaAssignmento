package panes.Customer;

import java.awt.*;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

public class CustomerHistoryPane extends JPanel {

    private final Color primaryPurple = new Color(128, 128, 255);
    private final Color bgColor = new Color(250, 250, 255);
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;

    // 1. ADD THESE TWO NEW VARIABLES
    private JPanel cardsContainer;
    private CardLayout cardLayout;

    // 2. UPDATE THE CONSTRUCTOR TO ACCEPT THEM
    public CustomerHistoryPane(JPanel cardsContainer, CardLayout cardLayout) {
        // 3. SAVE THEM TO THE CLASS VARIABLES
        this.cardsContainer = cardsContainer;
        this.cardLayout = cardLayout;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(bgColor);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        // --- 1. TITLE ---
        JLabel titleLabel = new JLabel("History");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 42));
        titleLabel.setForeground(primaryPurple);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- 2. TABLE CONTAINER (The Purple Box) ---
        JPanel tableContainer = new JPanel();
        tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
        tableContainer.setBackground(primaryPurple);
        tableContainer.setBorder(new EmptyBorder(15, 20, 20, 20));
        tableContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 2a. Filter Row Setup
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

        // Days (01 to 31)
        String[] days = new String[32];
        days[0] = "Day";
        for (int i = 1; i <= 31; i++) {
            days[i] = String.format("%02d", i);
        }

        // Months
        String[] months = {"Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        // Years
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String[] years = new String[(currentYear - 2020) + 2];
        years[0] = "Year";
        for (int i = 0; i <= (currentYear - 2020); i++) {
            years[i + 1] = String.valueOf(currentYear - i);
        }

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

        // 2b. The JTable setup (EXPANDED DATA)
        String[] columns = {"Date", "Car Plate No.", "Service Type", "Payment"};
        Object[][] data = {
                {"13-1-2026", "ABC 1234", "Major Service", "RM12,345.60"},
                {"20-1-2026", "VFQ 7574", "Normal Service", "RM2,500.00"},
                {"5-2-2026", "ABC 1234", "Normal Service", "RM2,500.00"},
                {"17-2-2026", "VQR 3768", "Major Service", "RM15,350.00"},
                {"9-3-2026", "VFQ 7574", "Normal Service", "RM2,500.00"},
                {"14-3-2026", "JKE 9988", "Major Service", "RM10,000.00"},
                {"2-4-2025", "ABC 1234", "Normal Service", "RM1,500.00"},
                {"15-6-2025", "VFQ 7574", "Major Service", "RM14,200.00"},
                {"22-11-2025", "VQR 3768", "Normal Service", "RM2,100.00"},
                {"30-12-2025", "JKE 9988", "Normal Service", "RM1,800.00"},
                {"", "", "", ""},
                {"", "", "", ""}
        };

        tableModel = new DefaultTableModel(data, columns) {
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

        // --- THE MAGIC: FILTERING LOGIC ---
        rowSorter = new TableRowSorter<>(tableModel);
        historyTable.setRowSorter(rowSorter);

        // We create a single method that applies all filters at once
        Runnable applyFilters = () -> {
            String selectedService = (String) serviceCombo.getSelectedItem();
            String selectedDay = (String) dayCombo.getSelectedItem();
            String selectedMonth = (String) monthCombo.getSelectedItem();
            String selectedYear = (String) yearCombo.getSelectedItem();

            RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    String rowDate = entry.getStringValue(0);
                    String rowService = entry.getStringValue(2);

                    // Handle our empty decorative rows at the bottom
                    if (rowDate.trim().isEmpty()) {
                        // Only show empty rows if NO filters are actively applied
                        return "All".equals(selectedService) && "Day".equals(selectedDay) &&
                                "Month".equals(selectedMonth) && "Year".equals(selectedYear);
                    }

                    // 1. Check Service Type
                    if (!"All".equals(selectedService) && !selectedService.equals(rowService)) {
                        return false;
                    }

                    // 2. Check Date (Split the row date like "13-1-2026" into parts)
                    String[] dateParts = rowDate.split("-");
                    if (dateParts.length == 3) {
                        String d = dateParts[0];
                        String m = dateParts[1];
                        String y = dateParts[2];

                        // Year check
                        if (!"Year".equals(selectedYear) && !selectedYear.equals(y)) return false;

                        // Month check (monthCombo index 1 is "Jan", which perfectly matches "1" in our data!)
                        if (!"Month".equals(selectedMonth)) {
                            if (monthCombo.getSelectedIndex() != Integer.parseInt(m)) return false;
                        }

                        // Day check (Need to convert "05" from dropdown to "5" to match data)
                        if (!"Day".equals(selectedDay)) {
                            if (Integer.parseInt(selectedDay) != Integer.parseInt(d)) return false;
                        }
                    }
                    return true; // If it passes all checks, show the row!
                }
            };
            rowSorter.setRowFilter(filter);
        };

        // Add listeners to trigger the filter whenever a user clicks a dropdown
        serviceCombo.addActionListener(e -> applyFilters.run());
        dayCombo.addActionListener(e -> applyFilters.run());
        monthCombo.addActionListener(e -> applyFilters.run());
        yearCombo.addActionListener(e -> applyFilters.run());

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableContainer.add(filterRow);
        tableContainer.add(Box.createVerticalStrut(10));
        tableContainer.add(scrollPane);

        // --- 3. ACTION BUTTON ---
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
                // IMPORTANT: Because the table is filtered/sorted, the row on screen might not be the same row in the data model.
                // We use convertRowIndexToModel to get the right data!
                int modelRow = historyTable.convertRowIndexToModel(viewRow);

                String selectedDate = (String) tableModel.getValueAt(modelRow, 0);
                String selectedPlate = (String) tableModel.getValueAt(modelRow, 1);

                if (selectedPlate != null && !selectedPlate.trim().isEmpty()) {
                // Trigger CardLayout to show the Payment Details Pane here!
                    cardLayout.show(cardsContainer, "PAYMENT_DETAILS");                }
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