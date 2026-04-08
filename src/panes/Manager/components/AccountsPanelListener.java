package panes.Manager.components;

import userClass.User;

public interface AccountsPanelListener {
    void onViewUserDetails(String userID);
    void onBackToList();
    void toCreateUser();
    void onCreateUser(User user);
    void onDeleteUser(String userID);
}
