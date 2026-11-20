package model;

/**
 * Common parent class for all users of the booking system.
 * Customer and Admin will inherit these common details.
 */
public abstract class User {

    private String userId;
    private String name;

    /**
     * Creates a user with an ID and name.
     */
    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    /**
     * Each type of user provides its own role.
     */
    public abstract String getRole();
}
