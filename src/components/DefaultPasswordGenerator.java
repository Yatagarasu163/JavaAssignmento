package components;

public class DefaultPasswordGenerator {

    public static String generate(String firstName, String userID) {
        String fName = (firstName == null || firstName.trim().isEmpty()) ? "User" : firstName.trim();

        String lastFour;
        if (userID != null && userID.length() >= 4) {
            lastFour = userID.substring(userID.length() - 4);
        } else {
            lastFour = "0000";
        }

        return fName + lastFour;
    }
}