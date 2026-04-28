package panes.Manager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import userClass.User;
import panes.Manager.components.AccountsPanelListener;


public class ManagerMainAccountPage extends JPanel implements AccountsPanelListener{
    
    CardLayout cardLayout = new CardLayout();
    private ManagerAccountsList listPage;
    private ManagerUserDetailsPane detailsPage;
    private ManagerCreateAccountPane createPage;

    public ManagerMainAccountPage() {
        setLayout(cardLayout);
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        listPage = new ManagerAccountsList(this); 
        detailsPage = new ManagerUserDetailsPane(this);
        createPage = new ManagerCreateAccountPane(this);
        add(listPage, "LIST");
        add(detailsPage, "DETAILS");
        add(createPage, "CREATE");
        cardLayout.show(this, "LIST");        
    }
    
    @Override
    public void onViewUserDetails(String userID) {
        detailsPage.loadUser(userID);

        cardLayout.show(this, "DETAILS");
    }

    public void onBackToList(){
        cardLayout.show(this, "LIST");

        listPage.updateAccounts();
    }

    public void toCreateUser(){
        cardLayout.show(this, "CREATE");
    }

    public void onCreateUser(User user){
        // Go to create account pane.
        cardLayout.show(this, "LIST");
    }

    public void onDeleteUser(String userID){
        //Delete user details from the list
    }
}
