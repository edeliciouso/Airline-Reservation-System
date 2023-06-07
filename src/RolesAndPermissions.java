public class RolesAndPermissions extends User {

    // Check if admin is registered or not
    public int isPrivilegedUserOrNot(String username, String password) {
        int isFound = -1; // return -1 if admin not found
        for (int i = 0; i < adminUserNameAndPassword.length; i++) { // loop through each letter of username and password
            if (username.equals(adminUserNameAndPassword[i][0])) {
                if (password.equals(adminUserNameAndPassword[i][1])) {
                    isFound = i;
                    break;
                }
            }
        }
        return isFound;
    }

    // Check if passenger is registered or not
    public String isPassengerRegistered(String email, String password) {
        String isFound = "0"; // return 1 with user ID if passenger is registered
        for (Customer c : Customer.customerCollection) { // loop to check each customer in customerCollection
            if (email.equals(c.getEmail())) {
                if (password.equals(c.getPassword())) {
                    isFound = "1-" + c.getUserID();
                    break;
                }
            }
        }
        return isFound;
    }
}
