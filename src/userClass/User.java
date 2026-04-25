package userClass;

public class User {
    public String id, firstName, lastName, username, role, email, contact, password, address;

    public String[] getDetails(){
        String[] details = {id, firstName, lastName, username, role, email, contact, password, address};

        return details;
    }

}
