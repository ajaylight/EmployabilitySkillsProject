package model;

/**
 * Represents an administrator who can view system reports.
 */
public class Admin extends User {

    public Admin(String userId, String name) {
        super(userId, name);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
