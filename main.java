import javax.swing.*;
import java.awt.GridLayout;

public class main{
	public static void main(String[] args) {
        //Initialise the frame 
        JFrame fr = new JFrame("Home Page");
        fr.setSize(600, 400);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLayout(new GridLayout(4, 0));
        fr.add(new JPanel());

        //Adds a panel that asks the user to select an option
        JPanel welcomePanel= new JPanel();
        welcomePanel.add(new JLabel("Welcome to insert name here!"));
        welcomePanel.add(new JLabel("Please select an option:"));
        fr.add(welcomePanel);
        fr.add(new JPanel());

        //Adds a button panel that contains "Exit", "Sign Up" and "Login" Buttons that redirects the user respectively.
        JPanel buttonPanel = new JPanel();
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            fr.dispose();
        });
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
                fr.dispose();
        });
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
                fr.dispose();
        });
        buttonPanel.add(exitButton);
        buttonPanel.add(signUpButton);
        buttonPanel.add(loginButton);
        fr.add(buttonPanel);

        //Centers the frame
        fr.setLocationRelativeTo(null);
        //Sets the frame to visible
        fr.setVisible(true);
	}
}
