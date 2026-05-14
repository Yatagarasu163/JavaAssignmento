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

    private static final String ratingPath = "Rating_Review.txt";
    private static final String commentPath = "Comments.txt";
    private static final String appointmentPath = "Appointment.txt";
    private List<String[]> rawRatings = FileHandler.read(ratingPath); // Rating[1]
    private List<String[]> rawComments = FileHandler.read(commentPath); // Comment[1]
    private List<String[]> rawAppointments = FileHandler.read(appointmentPath); //App[2] + App[5]
    private List<String[]> feedbackDataList = new ArrayList<>();
    private List<String[]> commentDataList = new ArrayList<>();
    private List<String[]> fullTableDataList = new ArrayList<>();

    private String[] columns;

    public ManagerFeedbackPane() {

        for(String[] rating : rawRatings){
            for(String[] appointment : rawAppointments){
                String[] temp = {appointment[2], rating[1], appointment[5]};
                feedbackDataList.add(temp);
                fullTableDataList.add(temp);
                break;
            }
        }

        for(String[] comment : rawComments){
            for(String[] appointment : rawAppointments){
                String[] dateTime = comment[3].split(" ");
                String[] temp = {appointment[2], comment[1], dateTime[0]};
                commentDataList.add(temp);
                fullTableDataList.add(temp);
                break;
            }
        }

        String[][] feedbackTableData = feedbackDataList.toArray(new String[0][]);
        String[][] commentTableData = commentDataList.toArray(new String[0][]);
        String[][] fullTableData = fullTableDataList.toArray(new String[0][]);

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

        FeedbackTable feedbackTable = new FeedbackTable(feedbackTableData, columns);
        feedbackTable.setShowGrid(true);

        feedbackTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            label.setBackground(UIConfig.mainForeground);
            label.setForeground(UIConfig.mainBackground);
        
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
                    DefaultTableModel feedbackModel = new DefaultTableModel(feedbackTableData, columns);
                    feedbackTable.setModel(feedbackModel);
                    break;
                case 1: 
                    //Set data to comment values only
                    columns = new String[]{"Service Type", "Comments", "Date"};
                    DefaultTableModel commentModel = new DefaultTableModel(commentTableData, columns);
                    feedbackTable.setModel(commentModel);
                    break;
                case 2:
                    //Set data to feedback and comment values
                    columns = new String[]{"Service Type", "Feedback and Comments", "Date"};
                    DefaultTableModel tableModel = new DefaultTableModel(fullTableData, columns);
                    feedbackTable.setModel(tableModel);
                    break;
                default:
                    columns = new String[]{"Service Type", "Feedback", "Date"};
                    DefaultTableModel defaultModel = new DefaultTableModel(feedbackTableData, columns);
                    feedbackTable.setModel(defaultModel);
                    break;
            }
        });
    }

}
