package panes.Manager;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;

import components.FeedbackTable;
import components.FloatingComboBox;
import components.TextLabel;
import config.UIConfig;
import IO.FileHandler;

public class ManagerFeedbackPane extends JPanel{

    private List<String[]> ratings = FileHandler.read(FileHandler.rating);
    private List<String[]> comments = FileHandler.read(FileHandler.comments);
    private List<String[]> appointments = FileHandler.read(FileHandler.appointments);

    private List<String[]> data_list = new ArrayList<>();


    private String[] columns;

    public ManagerFeedbackPane() {

        for(String[] rating : ratings){
            String appointmentID = rating[6];
            for(String[] appointment : appointments){
                if(appointment[0].equalsIgnoreCase(appointmentID)){
                    data_list.add(new String[]{appointment[2], rating[1], appointment[5]});
                    break;
                }
            }
        }

        for(String[] comment : comments){
            String commentAppointmentID = comment[4];
            String datetime = comment[3];
            String date = datetime.split(" ")[0];
            for(String[] appointment : appointments){
                if(appointment[0].equalsIgnoreCase(commentAppointmentID)){
                    data_list.add(new String[]{appointment[2], comment[1], date});
                    break;
                }
            }
        }
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        TextLabel title = new TextLabel("Feedback and Comments");

        //Adds title text to the pane
        topPanel.add(Box.createVerticalStrut(50));
        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(50));

        //Create view combo box
        String[] options = {"Feedback", "Comments", "Both"};
        FloatingComboBox<String> viewTypeComboBox = new FloatingComboBox<String>(options, UIConfig.cornerRadius);
        viewTypeComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        //Adds the combo box to the page
        topPanel.add(viewTypeComboBox);
        topPanel.add(Box.createVerticalStrut(50));

        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(UIConfig.mainBackground);
        tablePanel.setForeground(UIConfig.mainForeground);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tablePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));

        columns = new String[]{"Service Type", "Feedback", "Date"};
        String[][] data = data_list.toArray(new String[0][]);

        FeedbackTable feedbackTable = new FeedbackTable(data, columns);
        feedbackTable.setGridColor(Color.WHITE);
        feedbackTable.setShowGrid(true);

        feedbackTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            label.setBackground(UIConfig.mainBackground);
            label.setForeground(UIConfig.mainForeground);
        
            return label;
        }
        });


        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        scrollPane.setOpaque(false);
        scrollPane.setBackground(UIConfig.mainBackground);
        scrollPane.setForeground(UIConfig.mainForeground);        
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.getViewport().setBackground(UIConfig.mainForeground);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);





        //Adds behavior to FloatingComboBox
        viewTypeComboBox.addActionListener(e -> {
            int index = viewTypeComboBox.getSelectedIndex();
            switch (index) {
                case 0: 
                    //Set data to feedback values only
                    columns = new String[]{"Service Type", "Feedback", "Date"};
                    feedbackTable.getTableModel().setColumnIdentifiers(columns);
                    break;
                case 1: 
                    //Set data to comment values only
                    columns = new String[]{"Service Type", "Comments", "Date"};
                    feedbackTable.getTableModel().setColumnIdentifiers(columns);
                    break;
                case 2:
                    //Set data to feedback and comment values
                    columns = new String[]{"Service Type", "Feedback and Comments", "Date"};
                    feedbackTable.getTableModel().setColumnIdentifiers(columns);
                    break;
                default:
                    columns = new String[]{"Service Type", "Feedback", "Date"};
                    feedbackTable.getTableModel().setColumnIdentifiers(columns);
                    break;
            }
        });
    }
}
