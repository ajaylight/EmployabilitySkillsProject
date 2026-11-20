package model;

import java.util.ArrayList;

/**
 * Stores the details of a customer's movie ticket booking.
 */
public class Booking {

    private String bookingId;
    private String customerId;
    private String showId;
    private ArrayList<Integer> seatNumbers;
    private double totalAmount;

    /**
     * Creates a booking with the selected seats and total ticket amount.
     */
    public Booking(String bookingId, String customerId, String showId,
                   ArrayList<Integer> seatNumbers, double totalAmount) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.showId = showId;
        this.seatNumbers = new ArrayList<Integer>(seatNumbers);
        this.totalAmount = totalAmount;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getShowId() {
        return showId;
    }

    public ArrayList<Integer> getSeatNumbers() {
        return new ArrayList<Integer>(seatNumbers);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId
                + ", Customer ID: " + customerId
                + ", Show ID: " + showId
                + ", Seats: " + seatNumbers
                + ", Total: Rs. " + totalAmount;
    }
}
