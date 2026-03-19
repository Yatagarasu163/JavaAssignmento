package components;
import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel{
    
    private FloatingToggleButton accountBtn = new FloatingToggleButton("Account Creation", 20);
    private FloatingToggleButton pricingBtn = new FloatingToggleButton("Profile", 20);
    private FloatingToggleButton editBtn = new FloatingToggleButton("Edit Profile", 20 );
    private FloatingToggleButton feedbackBtn = new FloatingToggleButton("Feedback and Comments", 20 );
    private FloatingToggleButton exitBtn = new FloatingToggleButton("Exit", 20);

    public SidebarPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(128, 128,255));

        

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

    // Creates a new toggle sidebar button using this method
	// public JToggleButton createSidebarButton(String name){
	// 	JToggleButton btn = new JToggleButton(name);
	// 	btn.setBackground(new Color(128, 128, 255));
	// 	btn.setAlignmentX(Component.CENTER_ALIGNMENT);
	// 	btn.setForeground(Color.WHITE);
	// 	btn.setFont(new Font("SansSerif", Font.BOLD, 28));
	// 	btn.setFocusPainted(false);
	// 	btn.setBorderPainted(false);
	// 	btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

	// 	btn.addItemListener(e -> {
	// 		if(btn.isSelected()){
	// 			btn.setBackground(Color.WHITE);
	// 			btn.setForeground(Color.BLACK);
	// 		} else {
	// 			btn.setBackground(new Color(128, 128, 255));
	// 			btn.setForeground(Color.WHITE);
	// 		}
	// 	});

	// 	return btn;

	// }
}
