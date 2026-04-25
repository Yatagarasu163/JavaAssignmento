package components;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import config.UIConfig;

public class FeedbackTable extends JTable{

    private DefaultTableModel model;
    
    public FeedbackTable(Object[][] data, String[] columns) {
        setFont(new Font("SansSerif", Font.PLAIN, 25));
        setRowHeight(40);
        setOpaque(false);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        getTableHeader().setPreferredSize(new Dimension(0, 40));
        setIntercellSpacing(new Dimension(10, 10));
        setFillsViewportHeight(true);
        getTableHeader().setReorderingAllowed(false);
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        setBackground(UIConfig.mainForeground);
        setForeground(UIConfig.mainBackground);
        setShowGrid(false);

        JTableHeader header = getTableHeader();
        header.setBackground(new Color(150, 150, 255));
        header.setForeground(UIConfig.mainForeground);
        header.setFont(new Font("SansSerif", Font.BOLD, 25));

        this.model = new DefaultTableModel(data, columns){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };



        setModel(this.model);
    }

    public FeedbackTable(DefaultTableModel tableModel) {
        setFont(new Font("SansSerif", Font.PLAIN, 25));
        setRowHeight(40);
        setOpaque(false);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        getTableHeader().setPreferredSize(new Dimension(0, 40));
        setIntercellSpacing(new Dimension(10, 10));
        setFillsViewportHeight(true);
        getTableHeader().setReorderingAllowed(false);
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        setBackground(UIConfig.mainForeground);
        setForeground(UIConfig.mainBackground);
        setShowGrid(false);

        JTableHeader header = getTableHeader();
        header.setBackground(new Color(150, 150, 255));
        header.setForeground(UIConfig.mainForeground);
        header.setFont(new Font("SansSerif", Font.BOLD, 25));

        setModel(tableModel);
    }

    public DefaultTableModel getTableModel(){
        return model;
    }
}
