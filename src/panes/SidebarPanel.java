package panes;
import javax.swing.*;
import java.awt.*;
import components.FloatingToggleButton;
import components.TextLabel;

public class SidebarPanel extends JPanel{
    
    private FloatingToggleButton accountBtn = new FloatingToggleButton("Account Creation", 20);
    private FloatingToggleButton pricingBtn = new FloatingToggleButton("Profile", 20);
    private FloatingToggleButton editBtn = new FloatingToggleButton("Edit Profile", 20 );
    private FloatingToggleButton feedbackBtn = new FloatingToggleButton("Feedback and Comments", 20 );
    private FloatingToggleButton exitBtn = new FloatingToggleButton("Exit", 20);

    public SidebarPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(128, 128,255));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20,20));
        

        TextLabel appLabel = new TextLabel("APU- ASC");

        int componentSpace = 50;


        ButtonGroup grp = new ButtonGroup();

        grp.add(accountBtn);
        grp.add(pricingBtn);
        grp.add(editBtn);
        grp.add(feedbackBtn);
        grp.add(exitBtn);
        
        add(Box.createVerticalStrut(componentSpace));
        add(appLabel);
        add(Box.createVerticalStrut(componentSpace));
        add(accountBtn);
        add(Box.createVerticalStrut(componentSpace));
        add(pricingBtn);
        add(Box.createVerticalStrut(componentSpace));
        add(editBtn);
        add(Box.createVerticalStrut(componentSpace));
        add(feedbackBtn);
        add(Box.createVerticalStrut(componentSpace));
        add(exitBtn);
        add(Box.createVerticalStrut(componentSpace));
    }

    public JToggleButton getPricingBtn() {
        return this.pricingBtn;
    }
    
    public JToggleButton getAccountBtn(){
        return this.accountBtn;
    }
    
    public JToggleButton getEditBtn(){
        return this.editBtn;
    }

    public JToggleButton getFeedbackBtn(){
        return this.feedbackBtn;
    }

    public JToggleButton getExitBtn(){
        return this.exitBtn;
    }
}
