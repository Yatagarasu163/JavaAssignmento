package panes.Manager;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import panes.Manager.components.AccountsPanelListener;
import components.FeedbackTable;
import components.FloatingButton;
import components.FloatingTextField;
import config.UIConfig;
import components.TextLabel;
import IO.FileHandler;

public class ManagerAccountsList extends JPanel{
    private AccountsPanelListener listener;
    private String filename = "Users.txt";
    private String[] columns = {"User ID", "User Name", "Date Joined", "Details"};
    private FeedbackTable userTable;

    public ManagerAccountsList(AccountsPanelListener listener) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        FloatingButton addUserBtn = new FloatingButton("Add new user (+)");
        addUserBtn.setBackground(Color.WHITE);
        addUserBtn.setForeground(UIConfig.mainBackground);
        addUserBtn.setBorderPainted(true);
        addUserBtn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIConfig.mainBackground, 3, true),
            new EmptyBorder(10, 10, 10, 10)
        ));
        topPanel.add(addUserBtn, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout(10, 30));
        middlePanel.setBackground(UIConfig.mainBackground);
        middlePanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIConfig.mainBackground, 3, true),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JPanel middleTopPanel = new JPanel();
        middleTopPanel.setLayout(new BoxLayout(middleTopPanel, BoxLayout.X_AXIS));
        middleTopPanel.setBackground(UIConfig.mainBackground);
        TextLabel userIDtxt = new TextLabel("User ID: ");
        userIDtxt.setForeground(Color.WHITE);
        middleTopPanel.add(userIDtxt);
        middleTopPanel.add(Box.createHorizontalStrut(30));
        FloatingTextField userIDField = new FloatingTextField("User ID");
        userIDField.setPlaceHolderColor(Color.LIGHT_GRAY);
        userIDField.setActiveColor(Color.WHITE);
        userIDField.setForeground(Color.WHITE);
        userIDField.setCaretColor(Color.WHITE);
        middleTopPanel.add(userIDField);
        middleTopPanel.add(Box.createHorizontalStrut(50));
        TextLabel dateTxt = new TextLabel("Date: ");
        dateTxt.setForeground(Color.WHITE);
        middleTopPanel.add(dateTxt);
        
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(editor);
        middleTopPanel.add(dateSpinner);
        middlePanel.add(middleTopPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        tablePanel.setOpaque(false);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));
        tablePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        Object[][] data = getAccounts();

        DefaultTableModel tableModel = new DefaultTableModel(data, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        userTable = new FeedbackTable(tableModel);
        userTable.getColumn("Details").setCellRenderer(new ButtonRenderer());
        userTable.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox(), listener));
        userTable.setBackground(UIConfig.mainForeground);
        userTable.setForeground(UIConfig.mainBackground);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(600,200));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);

        middlePanel.add(scrollPane, BorderLayout.CENTER);
        

        add(middlePanel, BorderLayout.CENTER);


        addUserBtn.addActionListener(e -> {
            listener.toCreateUser();
        });
    }

    public String[][] getAccounts(){
        List<String[]> accounts = FileHandler.read(filename);

        List<String[]> cleanedAccounts = new ArrayList<>();
        if (accounts.size() > 0) {
            for (String[] account : accounts){
                String[] arr = {account[0], account[3], account[9], "View"};
                cleanedAccounts.add(arr);
            }
        }
        String[][] array = cleanedAccounts.toArray(new String[0][]);

        return array;
    }

    public void updateAccounts() {
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();

        model.setRowCount(0);

        String[][] accounts = getAccounts();
        for (String[] row : accounts) {
            model.addRow(row);
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer{
        public ButtonRenderer(){
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
        private AccountsPanelListener listener;

        public ButtonEditor(JCheckBox checkBox, AccountsPanelListener listener){
            super(checkBox);
            this.listener = listener;

            button = new JButton();
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setBackground(UIConfig.mainBackground);
            button.setForeground(UIConfig.mainForeground);
            button.setOpaque(true);

            button.addActionListener(e -> {
                String userID = table.getValueAt(row, 0).toString();
                if(listener != null){
                    listener.onViewUserDetails(userID);
                }
                fireEditingStopped();
            });
        }


        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
            this.table = table;
            this.row = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue(){
            return label;
        }

        @Override
        public boolean stopCellEditing(){
            return super.stopCellEditing();
        }
    }

}
