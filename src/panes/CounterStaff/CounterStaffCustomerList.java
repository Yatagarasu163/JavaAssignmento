package panes.CounterStaff;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import components.FeedbackTable;
import components.FloatingButton;
import config.UIConfig;
import panes.CounterStaff.components.CustomerPanelListener;
import components.TextLabel;


public class CounterStaffCustomerList extends JPanel{

    private CustomerPanelListener listener;
    
    public CounterStaffCustomerList(CustomerPanelListener listener) {
        this.listener = listener;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));


        JPanel addPanel = new JPanel();
        addPanel.setOpaque(false);
        addPanel.setLayout(new BorderLayout());
        addPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        addPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        FloatingButton addCustomer = new FloatingButton("Add new customer (+)");
        addCustomer.setBackground(UIConfig.mainForeground);
        addCustomer.setForeground(UIConfig.mainBackground);
        addCustomer.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIConfig.mainBackground, 3, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        addCustomer.setBorderPainted(true);
        addPanel.add(addCustomer, BorderLayout.EAST);


        JPanel customerDetailsPanel = new JPanel();
        customerDetailsPanel.setBackground(UIConfig.mainBackground);
        customerDetailsPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIConfig.mainBackground, 2, true),
            new EmptyBorder(30, 40, 30, 40)
        ));
        customerDetailsPanel.setLayout(new BoxLayout(customerDetailsPanel, BoxLayout.Y_AXIS));
        

        //Table Panel with custom buttons to view details
        JPanel filterPanel = new JPanel();
        filterPanel.setOpaque(false);
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        
        String[] columns = {"Customer ID", "Customer Name", "Date Joined", "Details"};
        Object[][] data = {{"CS123456", "Hao Ni Ma", "11/9/2001", "View"}, 
                           {"CS124567", "Bin Laden", "12/9/2001", "View"}};

        DefaultTableModel tableModel = new DefaultTableModel(data, columns){
            @Override 
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        FeedbackTable customerTable = new FeedbackTable(tableModel);
        customerTable.getColumn("Details").setCellRenderer(new ButtonRenderer()); //Sets the visual of the cell to a clickable button
        customerTable.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox(), listener)); // Handles the logic of clicking on the button.




        // ✅ wrap in scrollpane
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);

        filterPanel.add(scrollPane);

        customerDetailsPanel.add(Box.createVerticalStrut(20));
        customerDetailsPanel.add(filterPanel);

        add(Box.createVerticalStrut(50));
        add(addPanel);
        add(Box.createVerticalStrut(50));
        add(customerDetailsPanel);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer{
        public ButtonRenderer() {
            setOpaque(true);
            setFocusPainted(false);
            setBorderPainted(false);

            setBackground(UIConfig.mainBackground);
            setForeground(UIConfig.mainForeground);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                setText(value == null ? "" : value.toString());
                return this;
            }
    }
    
    class ButtonEditor extends DefaultCellEditor{
        private JButton button;
        private String label;
        private int row;
        private JTable table;
        private CustomerPanelListener listener;

        public ButtonEditor(JCheckBox checkBox, CustomerPanelListener listener) {
            super(checkBox);
            this.listener = listener;
             
            button = new JButton();
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setBackground(UIConfig.mainBackground);
            button.setForeground(UIConfig.mainForeground);
            button.setOpaque(true);

            button.addActionListener(e -> {
                String customerID = table.getValueAt(row, 0).toString();
                if(listener != null) {
                    listener.onViewCustomerDetails(customerID);
                }
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            this.row = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }

        @Override
        public boolean stopCellEditing(){
            return super.stopCellEditing();
        }
    }
}
