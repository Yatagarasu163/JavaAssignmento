package components;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import IO.FileHandler;
import java.util.ArrayList;
import java.util.List;

public class ProgramTerminator {

    // This is a static method, so we don't need to create an object to use it!
    public static void enableSafeExit(JFrame frame) {

        // 1. Stop Java from instantly closing the window
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // 2. Add the listener to the frame we passed in
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit? This will log you out.",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        // Clear the session file
                        List<String[]> empty = new ArrayList<>();
                        FileHandler.write("CurrentUser.txt", empty, false);
                    } catch (Exception ex) {
                        System.err.println("Error clearing session: " + ex.getMessage());
                    }

                    // Terminate the program completely
                    System.exit(0);
                }
            }
        });
    }
}