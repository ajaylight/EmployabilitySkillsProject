package service;

import java.util.ArrayList;
import java.util.HashMap;

import datastructures.WaitingListQueue;
import exceptions.BookingNotFoundException;
import exceptions.InvalidShowException;
import exceptions.SeatNotAvailableException;
import model.Booking;
import model.Show;
import model.WaitListEntry;

/**
 * Handles ticket booking, cancellation, and waiting-list requests.
 */
public class BookingService {

    private MovieService movieService;
    private ArrayList<Booking> bookings;
    private HashMap<String, WaitingListQueue> waitingLists;
    private int nextBookingNumber;

    /**
     * Creates the booking service and its empty booking and waiting-list records.
     */
    public BookingService(MovieService movieService) {
        this.movieService = movieService;
        bookings = new ArrayList<Booking>();
        waitingLists = new HashMap<String, WaitingListQueue>();
        nextBookingNumber = 101;
    }

    /**
     * Books the selected seats for a customer.
     * All seats are checked before any seat is marked as booked.
     */
    public Booking bookTickets(String customerId, String showId,
                               ArrayList<Integer> seatNumbers)
            throws InvalidShowException, SeatNotAvailableException {

        Show show = movieService.findShowById(showId);

        if (seatNumbers == null || seatNumbers.size() == 0) {
            throw new SeatNotAvailableException("Please select at least one seat.");
        }

        if (seatNumbers.size() > show.getAvailableSeatCount()) {
            throw new SeatNotAvailableException("Not enough seats are available.");
        }

        // Validate every requested seat before booking any of them.
        for (int i = 0; i < seatNumbers.size(); i++) {
            int seatNumber = seatNumbers.get(i);

            if (seatNumber < 1 || seatNumber > show.getTotalSeats()) {
                throw new SeatNotAvailableException(
                        "Seat number " + seatNumber + " is invalid.");
            }

            // A customer must not select the same seat twice.
            for (int j = 0; j < i; j++) {
                if (seatNumbers.get(j) == seatNumber) {
                    throw new SeatNotAvailableException(
                            "Seat number " + seatNumber + " was selected more than once.");
                }
            }

            if (show.isSeatBooked(seatNumber)) {
                throw new SeatNotAvailableException(
                        "Seat number " + seatNumber + " is already booked.");
            }
        }

        // All selected seats are valid, so now mark them as booked.
        for (int seatNumber : seatNumbers) {
            show.bookSeat(seatNumber);
        }

        double totalAmount = seatNumbers.size() * show.getTicketPrice();
        Booking booking = createBooking(customerId, show, seatNumbers, totalAmount);
        bookings.add(booking);

        return booking;
    }

    /**
     * Adds a customer to the end of the waiting list for a show.
     */
    public void joinWaitingList(String customerId, String showId, int numberOfSeats)
            throws InvalidShowException, SeatNotAvailableException {

        Show show = movieService.findShowById(showId);

        if (numberOfSeats < 1 || numberOfSeats > show.getTotalSeats()) {
            throw new SeatNotAvailableException("Invalid number of seats requested.");
        }

        WaitingListQueue queue = waitingLists.get(show.getShowId());

        if (queue == null) {
            queue = new WaitingListQueue();
            waitingLists.put(show.getShowId(), queue);
        }

        WaitListEntry entry = new WaitListEntry(
                customerId, show.getShowId(), numberOfSeats);
        queue.enqueue(entry);
    }

    /**
     * Returns the number of customers waiting for a particular show.
     */
    public int getWaitingListCount(String showId) throws InvalidShowException {
        Show show = movieService.findShowById(showId);
        WaitingListQueue queue = waitingLists.get(show.getShowId());

        if (queue == null) {
            return 0;
        }

        return queue.getCount();
    }

    /**
     * Cancels one of the customer's bookings and checks the waiting list.
     * Returns the bookings created for customers promoted from the waiting list.
     */
    public ArrayList<Booking> cancelBooking(String bookingId, String customerId)
            throws BookingNotFoundException, InvalidShowException {

        Booking bookingToCancel = null;

        for (Booking booking : bookings) {
            if (booking.getBookingId().equalsIgnoreCase(bookingId)
                    && booking.getCustomerId().equalsIgnoreCase(customerId)) {
                bookingToCancel = booking;
                break;
            }
        }

        if (bookingToCancel == null) {
            throw new BookingNotFoundException(
                    "Booking not found for this customer: " + bookingId);
        }

        Show show = movieService.findShowById(bookingToCancel.getShowId());

        // Release all seats belonging to the cancelled booking.
        for (int seatNumber : bookingToCancel.getSeatNumbers()) {
            show.releaseSeat(seatNumber);
        }

        bookings.remove(bookingToCancel);

        // Give the newly available seats to waiting customers in FIFO order.
        return promoteWaitingCustomers(show);
    }

    /**
     * Promotes waiting customers in FIFO order when enough seats are available.
     */
    private ArrayList<Booking> promoteWaitingCustomers(Show show) {
        ArrayList<Booking> promotedBookings = new ArrayList<Booking>();
        WaitingListQueue queue = waitingLists.get(show.getShowId());

        while (queue != null && !queue.isEmpty()) {
            WaitListEntry nextEntry = queue.peek();

            // Keep the first request in the queue if it cannot be fulfilled yet.
            if (nextEntry.getNumberOfSeats() > show.getAvailableSeatCount()) {
                break;
            }

            queue.dequeue();

            ArrayList<Integer> allocatedSeats = new ArrayList<Integer>();

            // Allocate the first available seat numbers.
            for (int seatNumber = 1;
                 seatNumber <= show.getTotalSeats()
                         && allocatedSeats.size() < nextEntry.getNumberOfSeats();
                 seatNumber++) {

                if (!show.isSeatBooked(seatNumber)) {
                    show.bookSeat(seatNumber);
                    allocatedSeats.add(seatNumber);
                }
            }

            double totalAmount = allocatedSeats.size() * show.getTicketPrice();
            Booking newBooking = createBooking(
                    nextEntry.getCustomerId(), show, allocatedSeats, totalAmount);

            bookings.add(newBooking);
            promotedBookings.add(newBooking);
        }

        return promotedBookings;
    }

    /**
     * Creates a booking with the next available booking ID.
     */
    private Booking createBooking(String customerId, Show show,
                                  ArrayList<Integer> seatNumbers, double totalAmount) {
        String bookingId = "B" + nextBookingNumber;
        nextBookingNumber++;

        return new Booking(bookingId, customerId, show.getShowId(),
                seatNumbers, totalAmount);
    }

    /**
     * Returns all active bookings belonging to a customer.
     */
    public ArrayList<Booking> getCustomerBookings(String customerId) {
        ArrayList<Booking> customerBookings = new ArrayList<Booking>();

        for (Booking booking : bookings) {
            if (booking.getCustomerId().equalsIgnoreCase(customerId)) {
                customerBookings.add(booking);
            }
        }

        return customerBookings;
    }

    /**
     * Returns a copy of all active bookings.
     */
    public ArrayList<Booking> getAllBookings() {
        return new ArrayList<Booking>(bookings);
    }

    /**
     * Calculates revenue from all active bookings.
     */
    public double getTotalRevenue() {
        double totalRevenue = 0.0;

        for (Booking booking : bookings) {
            totalRevenue = totalRevenue + booking.getTotalAmount();
        }

        return totalRevenue;
    }
}
