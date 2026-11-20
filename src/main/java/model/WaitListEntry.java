package model;

/**
 * Stores one customer's request to join a show's waiting list.
 */
public class WaitListEntry {

    private String customerId;
    private String showId;
    private int numberOfSeats;

    /**
     * Creates a waiting-list entry for a customer.
     */
    public WaitListEntry(String customerId, String showId, int numberOfSeats) {
        this.customerId = customerId;
        this.showId = showId;
        this.numberOfSeats = numberOfSeats;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getShowId() {
        return showId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId
                + ", Show ID: " + showId
                + ", Seats requested: " + numberOfSeats;
    }
}
