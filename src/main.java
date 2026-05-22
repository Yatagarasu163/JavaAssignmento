import javax.swing.*;
import java.awt.*;
import panes.LoginPage;

public class main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            new LoginPage().setVisible(true);
        });
    }
}
