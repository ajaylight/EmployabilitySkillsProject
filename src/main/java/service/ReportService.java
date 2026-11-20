package service;

import java.util.ArrayList;

import model.Booking;

/**
 * Displays booking and revenue information for the administrator.
 */
public class ReportService {

    private BookingService bookingService;

    /**
     * Uses BookingService to read the current booking records.
     */
    public ReportService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Displays all active bookings and the total revenue.
     */
    public void displayRevenueReport() {
        ArrayList<Booking> bookings = bookingService.getAllBookings();

        System.out.println("\n========== REVENUE REPORT ==========");

        if (bookings.isEmpty()) {
            System.out.println("No bookings have been made yet.");
        } else {
            for (Booking booking : bookings) {
                System.out.println("Booking ID: " + booking.getBookingId()
                        + " | Show ID: " + booking.getShowId()
                        + " | Amount: Rs. " + booking.getTotalAmount());
            }
        }

        System.out.println("------------------------------------");
        System.out.println("Total active bookings: " + bookings.size());
        System.out.println("Total revenue: Rs. " + bookingService.getTotalRevenue());
        System.out.println("====================================");
    }
}
