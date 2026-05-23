package components;

public class DefaultPasswordGenerator {

    public static String generate(String firstName, String userID) {
        // 1. Handle the First Name (Fallback to "User" if they haven't typed a name yet)
        String fName = (firstName == null || firstName.trim().isEmpty()) ? "User" : firstName.trim();

        // 2. Safely extract the last 4 characters of the User ID
        String lastFour;
        if (userID != null && userID.length() >= 4) {
            lastFour = userID.substring(userID.length() - 4);
        } else {
            lastFour = "0000"; // Fallback just in case the ID is somehow empty
        }

        // 3. Combine and return!
        return fName + lastFour;
    }
}