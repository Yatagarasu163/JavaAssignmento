package panes.Manager;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import components.FeedbackTable;
import components.FloatingComboBox;
import components.TextLabel;
import config.UIConfig;

public class ManagerFeedbackPane extends JPanel{

    private String[] columns;

    public ManagerFeedbackPane() {
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

        String[][] testData = {
            {"Normal", "Very nice", "11/9/2001"},
            {"Major", "I kena scam sia", "13/5/1963"},
            {"Normal", "I came here to look at hot mechanics. I came", "31/8/1957"}
        };
        columns = new String[]{"Service Type", "Feedback", "Date"};

        FeedbackTable feedbackTable = new FeedbackTable(testData, columns);
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
