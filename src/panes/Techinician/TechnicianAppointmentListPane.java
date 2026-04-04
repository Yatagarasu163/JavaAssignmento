package panes.Techinician;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TechnicianAppointmentListPane extends JPanel {

    private final Color primaryPurple = new Color(128, 128, 255);
    private final Color bgPurple = new Color(200, 200, 255);

    private List<TechnicianAppointmentPane.AppointmentData> appointments;
    private TechnicianAppointmentPane parentController;
    private TechnicianAppointmentPane.AppointmentData selectedData;

    public TechnicianAppointmentListPane(List<TechnicianAppointmentPane.AppointmentData> appointments, TechnicianAppointmentPane parentController) {
        this.appointments = appointments;
        this.parentController = parentController;
        if (!appointments.isEmpty()) {
            this.selectedData = appointments.get(0);
        }

        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        refreshList();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bgPurple);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();
    }

    public void refreshList() {
        removeAll();

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        for (TechnicianAppointmentPane.AppointmentData data : appointments) {
            boolean isSelected = (data == selectedData);
            JPanel card = createCard(data, isSelected);
            listPanel.add(card);
            listPanel.add(Box.createVerticalStrut(15));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private JPanel createCard(TechnicianAppointmentPane.AppointmentData data, boolean isSelected) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isSelected ? primaryPurple : new Color(220, 220, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
        card.setLayout(new GridBagLayout());
        card.setOpaque(false);
        card.setMaximumSize(new Dimension(280, 120));
        card.setPreferredSize(new Dimension(280, 120));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color textColor = isSelected ? Color.WHITE : Color.DARK_GRAY;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 15, 5, 15);

        JLabel timeLbl = new JLabel(data.time);
        timeLbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        timeLbl.setForeground(textColor);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
        card.add(timeLbl, gbc);

        JLabel statusLbl = new JLabel(data.status, SwingConstants.CENTER);
        statusLbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        statusLbl.setOpaque(true);

        // Dynamic Status Color
        if (data.status.equals("Completed")) statusLbl.setBackground(new Color(100, 200, 100));
        else if (data.status.equals("In Service")) statusLbl.setBackground(new Color(255, 180, 50));
        else statusLbl.setBackground(isSelected ? new Color(100, 100, 220) : primaryPurple);

        statusLbl.setForeground(Color.WHITE);
        statusLbl.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        card.add(statusLbl, gbc);

        JLabel modelLbl = new JLabel(data.model);
        modelLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        modelLbl.setForeground(textColor);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        card.add(modelLbl, gbc);

        JLabel plateLbl = new JLabel(data.plate);
        plateLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        plateLbl.setForeground(textColor);
        gbc.gridx = 0; gbc.gridy = 2;
        card.add(plateLbl, gbc);

        // Click Listener to switch details
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedData = data;
                parentController.onAppointmentSelected(data);
                refreshList();
            }
        });

        return card;
    }
}