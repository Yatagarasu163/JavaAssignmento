package panes.CounterStaff;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.HierarchyEvent;
import java.util.List;
import java.util.ArrayList;
import components.TextLabel;
import config.UIConfig;
import components.FloatingComboBox;
import panes.CounterStaff.components.PaymentListener;
import components.FeedbackTable;
import IO.FileHandler;

public class CounterStaffPaymentPane extends JPanel{

	private PaymentListener listener;
    private Timer timer;
    private int lastCount = -1;
    private String typeItem;
    private String statusItem;
    private boolean initialized = true;

	public CounterStaffPaymentPane(PaymentListener listener){
		this.listener = listener;

       	setLayout(new BorderLayout());
      	setBackground(Color.WHITE);
       	setBorder(new EmptyBorder(20, 20, 20, 20));

        timer = new Timer(3000, e -> {
            List<String[]> payments = FileHandler.read(FileHandler.payment);

            if (payments.size() != lastCount) {
                    lastCount = payments.size();
                    loadPayments();
                }
        });

        loadPayments();
        
        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (isShowing()) {
                    timer.start();
                } else {
                    timer.stop();
                }
            }
        });
	}

    private void loadPayments(){
        removeAll();

		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.setLayout(new BorderLayout());
		TextLabel titleLbl = new TextLabel("Payment List");
        titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setFontSize(50);
		topPanel.add(titleLbl, BorderLayout.NORTH);
		add(topPanel, BorderLayout.NORTH);

		mainPanel middlePanel = new mainPanel();
		middlePanel.setBackground(UIConfig.mainBackground);
        middlePanel.setLayout(new BorderLayout(20,20));

        JPanel filtersPanel = new JPanel();
        filtersPanel.setOpaque(false);
        filtersPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        TextLabel serviceType = new TextLabel("Service Type: ");
        serviceType.setFontType(Font.BOLD);
        serviceType.setForeground(Color.WHITE);
        filtersPanel.add(serviceType);

        Dimension fixed = new Dimension(200, 40);

        String[] options = {"Normal", "Major", "Normal and Major"};
        FloatingComboBox<String> typeComboBox = new FloatingComboBox<>(options);
        typeComboBox.setPreferredSize(fixed);
        typeComboBox.setMaximumSize(fixed);
        if(typeItem == null){
            typeItem = "Normal and Major";
        }
        typeComboBox.setSelectedItem(typeItem);
        filtersPanel.add(typeComboBox);

        TextLabel statusTxt = new TextLabel("Status: ");
        statusTxt.setFontType(Font.BOLD);
        statusTxt.setForeground(Color.WHITE);
        filtersPanel.add(statusTxt);

        String[] options2 = {"Paid", "Not Paid", "Paid and Not Paid"};
        FloatingComboBox<String> statusBox = new FloatingComboBox<>(options2);
        statusBox.setPreferredSize(fixed);
        statusBox.setMaximumSize(fixed);
        if(statusItem == null){
            statusItem = "Paid and Not Paid";
        }
        statusBox.setSelectedItem(statusItem);
        filtersPanel.add(statusBox); 

        middlePanel.add(filtersPanel, BorderLayout.NORTH);

        String[][] data = getPayments(typeItem, statusItem);
        String[] columns = {"Payment ID", "Date", "Car Plate", "Service Type", "Status", "Details"};
        DefaultTableModel model = new DefaultTableModel(data, columns){
            @Override
            public boolean isCellEditable(int row, int column){
                return column == 5;
            }
        };
        FeedbackTable paymentTable = new FeedbackTable(model);
        paymentTable.getColumn("Details").setCellRenderer(new ButtonRenderer());
        paymentTable.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox(), listener));
        paymentTable.setBackground(UIConfig.mainForeground);
        paymentTable.setForeground(UIConfig.mainBackground);
        paymentTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(paymentTable);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);

        middlePanel.add(scrollPane, BorderLayout.CENTER);
        add(middlePanel, BorderLayout.CENTER);


        typeComboBox.addActionListener(e -> {
           this.typeItem = (String) typeComboBox.getSelectedItem(); 
           loadPayments();
        });

        statusBox.addActionListener(e -> {
            this.statusItem = (String) statusBox.getSelectedItem();
            loadPayments();
        });

        revalidate();
        repaint();
    }

    private String[][] getPayments(String typeItem, String statusItem){
        List<String[]> paymentList = FileHandler.read(FileHandler.payment);
        List<String[]> vehicles = FileHandler.read(FileHandler.vehicles);
        List<String[]> appointments = FileHandler.read(FileHandler.appointments);
        List<String[]> payments = new ArrayList<>();
        for (String[] payment : paymentList){
            String paymentID = payment[0];
            String date = payment[2];
            String vehicleID = payment[4];
            String status = payment[6];
            String carPlate = "";
            String appointmentID = payment[5];
            String serviceType = "";

            for (String[] vehicle : vehicles){
                if(vehicle[0].equalsIgnoreCase(vehicleID)){
                    carPlate = vehicle[1];
                    break;
                }
            }
                
            for(String[] appointment : appointments){
                if(appointment[0].equalsIgnoreCase(appointmentID)){
                    serviceType = appointment[2];
                }
            }

            boolean matchesType = false;
            boolean matchesStatus = false;

            if(typeItem.equalsIgnoreCase("Normal and Major")){
                matchesType = true;
            } else if(typeItem.equalsIgnoreCase("Normal")
                    && serviceType.equalsIgnoreCase("Normal Service")){
                matchesType = true;
            } else if(typeItem.equalsIgnoreCase("Major")
                    && serviceType.equalsIgnoreCase("Major Service")){
                matchesType = true;
            }

            if(statusItem.equalsIgnoreCase("Paid and Not Paid")){
                matchesStatus = true;
            } else if(statusItem.equalsIgnoreCase(status)){
                matchesStatus = true;
            }

            if(matchesType && matchesStatus){
                payments.add(new String[]{paymentID, date, carPlate, serviceType, status, "View"});
            }
        }

        String[][] finalData = payments.toArray(new String[0][]);
        return finalData;
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
                fireEditingStopped();

                if (table != null) {
                    String paymentID = table.getValueAt(row, 0).toString();
                    String status = table.getValueAt(row, 4).toString();
                    if(listener != null){
                        if(status.equalsIgnoreCase("Paid")){
                            listener.onViewReceipt(paymentID);
                        } else if (status.equalsIgnoreCase("Not Paid")){
                            listener.onMakePayment(paymentID);
                        } else {
                            listener.onMakePayment(paymentID);
                        }
                    }
                }
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
