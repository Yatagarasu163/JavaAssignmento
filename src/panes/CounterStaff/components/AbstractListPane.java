package panes.CounterStaff.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import components.FeedbackTable;
import components.FloatingButton;
import config.UIConfig;

public abstract class AbstractListPane extends JPanel {

    protected DefaultTableModel tableModel;
    protected FeedbackTable dataTable;

    public AbstractListPane(String addButtonText) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel addPanel = new JPanel(new BorderLayout());
        addPanel.setOpaque(false);
        addPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        addPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        if (addButtonText != null && !addButtonText.isEmpty()) {
            FloatingButton addBtn = new FloatingButton(addButtonText);
            addBtn.setBackground(UIConfig.mainForeground);
            addBtn.setForeground(UIConfig.mainBackground);
            addBtn.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(UIConfig.mainBackground, 3, true),
                    new EmptyBorder(10, 20, 10, 20)
            ));
            addBtn.setBorderPainted(true);
            addBtn.addActionListener(e -> onAddButtonClicked());

            addPanel.add(addBtn, BorderLayout.EAST);
        }

        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(UIConfig.mainBackground);
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UIConfig.mainBackground, 2, true),
                new EmptyBorder(30, 40, 30, 40)
        ));
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        String[] columns = getColumnNames();
        String[][] data = getTableData();

        tableModel = new DefaultTableModel(data, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return getColumnName(column).equals("Details");
            }
        };

        dataTable = new FeedbackTable(tableModel);
        setupTableButtons();

        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailsPanel.add(scrollPane);

        add(Box.createVerticalStrut(20));

        if (addButtonText != null && !addButtonText.isEmpty()) {
            add(addPanel);
            add(Box.createVerticalStrut(20));
        }

        add(detailsPanel);
    }

    public void refreshTable() {
        String[][] newData = getTableData();
        tableModel.setDataVector(newData, getColumnNames());
        setupTableButtons();
    }

    private void setupTableButtons() {
        dataTable.getColumn("Details").setCellRenderer(new ButtonRenderer());
        dataTable.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    protected abstract String[] getColumnNames();
    protected abstract String[][] getTableData();
    protected abstract void onAddButtonClicked();
    protected abstract void onRowButtonClicked(String id);

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFocusPainted(false);
            setBorderPainted(false);
            setBackground(UIConfig.mainBackground);
            setForeground(UIConfig.mainForeground);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column){
            setText(value == null ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private int row;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setBackground(UIConfig.mainBackground);
            button.setForeground(UIConfig.mainForeground);
            button.setOpaque(true);

            button.addActionListener(e -> {
                String rowID = table.getValueAt(row, 0).toString();
                AbstractListPane.this.onRowButtonClicked(rowID);
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
        public Object getCellEditorValue() { return label; }
        @Override
        public boolean stopCellEditing() { return super.stopCellEditing(); }
    }
}