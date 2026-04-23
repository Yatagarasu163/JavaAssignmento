package panes.CounterStaff;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableModel;

import components.TextLabel;
import config.UIConfig;
import components.FloatingComboBox;
import panes.CounterStaff.components.PaymentListener;
import components.FeedbackTable;

public class CounterStaffPaymentPane extends JPanel{

	private PaymentListener listener;

	public CounterStaffPaymentPane(PaymentListener listener){
		this.listener = listener;

       	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      	setBackground(Color.WHITE);
       	setBorder(new EmptyBorder(20, 20, 20, 20));
		
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
		TextLabel titleLbl = new TextLabel("Payment List");
		titleLbl.setFontSize(50);
		topPanel.add(titleLbl);
		add(topPanel);
		add(Box.createVerticalStrut(50));

		mainPanel middlePanel = new mainPanel();
		middlePanel.setBackground(UIConfig.mainBackground);
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));

        JPanel filtersPanel = new JPanel();
        filtersPanel.setOpaque(false);
        filtersPanel.setLayout(new GridLayout(1, 4));

        TextLabel serviceType = new TextLabel("Service Type: ");
        serviceType.setFontType(Font.BOLD);
        serviceType.setForeground(Color.WHITE);
        filtersPanel.add(serviceType);

        String[] options = {"Normal", "Major"};
        FloatingComboBox<String> typeComboBox = new FloatingComboBox<>(options);
        filtersPanel.add(typeComboBox);

        TextLabel statusTxt = new TextLabel("Status: ");
        statusTxt.setFontType(Font.BOLD);
        statusTxt.setForeground(Color.WHITE);
        filtersPanel.add(statusTxt);

        String[] options2 = {"Paid", "Not Paid"};
        FloatingComboBox<String> statusBox = new FloatingComboBox<>(options2);
        filtersPanel.add(statusBox); 

        middlePanel.add(filtersPanel);
        middlePanel.add(Box.createVerticalStrut(20));

        JPanel tablePanel = new JPanel();
        tablePanel.setOpaque(false);
        tablePanel.setLayout(new BorderLayout());
        String[][] data = {{"P000001","13/1/2026", "ABC 1234", "Major Service", "Not Paid", "Pay"}};
        String[] columns = {"Payment ID", "Date", "Car Plate", "Service Type", "Status", "Details"};
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // "Details" column
            }
        };
        FeedbackTable paymentTable = new FeedbackTable(model);
        paymentTable.setBackground(UIConfig.mainForeground);
        paymentTable.setForeground(UIConfig.mainBackground);
        paymentTable.getColumn("Details").setCellRenderer(new ButtonRenderer());
        paymentTable.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox(), listener));
        paymentTable.setPreferredSize(new Dimension(Integer.MAX_VALUE, 300));

        JScrollPane scrollPane = new JScrollPane(paymentTable);
        scrollPane.setPreferredSize(new Dimension(700, 200));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        middlePanel.add(tablePanel);
        add(middlePanel);


        typeComboBox.addActionListener(e -> {

        });

        statusBox.addActionListener(e -> {

        });
	}

	class mainPanel extends JPanel{

        private int radius = 5;

		public mainPanel(){
			setOpaque(false);
		}

        @Override
        protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            g2.dispose();

            super.paintComponent(g);
        }
	}


    class ButtonRenderer extends JButton implements TableCellRenderer{
        public ButtonRenderer(){
            setOpaque(true);
            setFocusPainted(false);
            setBorderPainted(false);

            setBackground(UIConfig.mainForeground);
            setForeground(UIConfig.mainBackground);
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
        private PaymentListener listener;

        public ButtonEditor(JCheckBox checkBox, PaymentListener listener){
            super(checkBox);
            this.listener = listener;

            button = new JButton();
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setBackground(UIConfig.mainBackground);
            button.setForeground(UIConfig.mainForeground);
            button.setOpaque(true);

            button.addActionListener(e -> {
                System.out.println("I AM CLICKED");
                String paymentID = table.getValueAt(row, 0).toString();
                String status = table.getValueAt(row, 4).toString();
                if(listener != null){
                    if(status == "Paid"){
                        listener.onViewReceipt(paymentID);
                    } else if (status == "Not Paid"){
                        listener.onMakePayment(paymentID);
                    } else {
                        listener.onMakePayment(paymentID);
                    }
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
