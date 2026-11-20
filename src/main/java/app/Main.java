package app;

import java.util.ArrayList;
import java.util.Scanner;

import exceptions.BookingNotFoundException;
import exceptions.InvalidShowException;
import exceptions.SeatNotAvailableException;
import model.Admin;
import model.Booking;
import model.Customer;
import model.Movie;
import model.Show;
import model.User;
import service.BookingService;
import service.MovieService;
import service.ReportService;

/**
 * Main class of the Movie Ticket Booking System.
 * Displays the menus and takes input from the user.
 */
public class Main {

    private Scanner scanner;
    private MovieService movieService;
    private BookingService bookingService;
    private ReportService reportService;

    private Customer currentCustomer;
    private int customerCounter;

    public Main() {
        scanner = new Scanner(System.in);

        movieService = new MovieService();
        bookingService = new BookingService(movieService);
        reportService = new ReportService(bookingService);

        customerCounter = 100;
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    /**
     * Starts the application and displays the role selection menu.
     */
    public void start() {
        System.out.println("====================================");
        System.out.println("     MOVIE TICKET BOOKING SYSTEM");
        System.out.println("====================================");

        int choice;

        do {
            System.out.println("\n1. Continue as Customer");
            System.out.println("2. Login as Admin");
            System.out.println("0. Exit");

            choice = readInt("Choose your role: ");

            switch (choice) {
                case 1:
                    customerLogin();
                    break;
                case 2:
                    adminLogin();
                    break;
                case 0:
                    System.out.println("Thank you for using the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    /**
     * Creates a customer for the current application session.
     */
    private void customerLogin() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        customerCounter++;
        String customerId = "C" + customerCounter;

        currentCustomer = new Customer(customerId, name);

        showWelcome(currentCustomer);
        customerMenu();

        currentCustomer = null;
    }

    /**
     * Checks the demo password before showing admin options.
     */
    private void adminLogin() {
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        if (password.equals("admin123")) {
            Admin admin = new Admin("A001", "Administrator");
            showWelcome(admin);
            adminMenu();
        } else {
            System.out.println("Incorrect password. Admin access denied.");
        }
    }

    /**
     * Uses the parent User type to demonstrate polymorphism.
     */
    private void showWelcome(User user) {
        System.out.println("\nWelcome, " + user.getName()
                + " (" + user.getRole() + ")");
        System.out.println("Your ID: " + user.getUserId());
    }

    /**
     * Displays options available to customers.
     * Revenue reports are not included in this menu.
     */
    private void customerMenu() {
        int choice;

        do {
            System.out.println("\n----------- CUSTOMER MENU -----------");
            System.out.println("1. View movies");
            System.out.println("2. View shows and seat availability");
            System.out.println("3. Book tickets");
            System.out.println("4. Cancel my booking");
            System.out.println("5. View waiting-list count");
            System.out.println("0. Logout");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewMovies();
                    break;
                case 2:
                    viewShowsAndSeats();
                    break;
                case 3:
                    bookTickets();
                    break;
                case 4:
                    cancelBooking();
                    break;
                case 5:
                    viewWaitingListCount();
                    break;
                case 0:
                    System.out.println("Customer logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    /**
     * Displays options available only to the administrator.
     */
    private void adminMenu() {
        int choice;

        do {
            System.out.println("\n------------- ADMIN MENU ------------");
            System.out.println("1. View movies");
            System.out.println("2. View shows and seat availability");
            System.out.println("3. View revenue report");
            System.out.println("4. View waiting-list count");
            System.out.println("0. Logout");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewMovies();
                    break;
                case 2:
                    viewShowsAndSeats();
                    break;
                case 3:
                    reportService.displayRevenueReport();
                    break;
                case 4:
                    viewWaitingListCount();
                    break;
                case 0:
                    System.out.println("Admin logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    /**
     * Displays the movie catalogue.
     */
    private void viewMovies() {
        movieService.displayMovies();
    }

    /**
     * Displays all shows and the seat map for a selected show.
     */
    private void viewShowsAndSeats() {
        displayAllShows();

        System.out.print("Enter show ID to view seats (or 0 to return): ");
        String showId = scanner.nextLine();

        if (showId.equals("0")) {
            return;
        }

        try {
            Show show = movieService.findShowById(showId);
            displaySeatMap(show);
        } catch (InvalidShowException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays show information along with the movie title.
     */
    private void displayAllShows() {
        System.out.println("\n--------------- SHOWS ---------------");

        ArrayList<Show> shows = movieService.getAllShows();

        for (Show show : shows) {
            Movie movie = movieService.findMovieById(show.getMovieId());
            String movieTitle = "Unknown Movie";

            if (movie != null) {
                movieTitle = movie.getTitle();
            }

            System.out.println(show.getShowId()
                    + " | " + movieTitle
                    + " | " + show.getTheatreName()
                    + " | " + show.getShowTime()
                    + " | Rs. " + show.getTicketPrice()
                    + " | Available seats: " + show.getAvailableSeatCount()
                    + "/" + show.getTotalSeats());
        }
    }

    /**
     * Displays each seat as available or booked.
     */
    private void displaySeatMap(Show show) {
        System.out.println("\nSeat map: A = Available, B = Booked");

        for (int seatNumber = 1;
             seatNumber <= show.getTotalSeats();
             seatNumber++) {

            if (show.isSeatBooked(seatNumber)) {
                System.out.print("[" + seatNumber + ":B] ");
            } else {
                System.out.print("[" + seatNumber + ":A] ");
            }

            if (seatNumber % 5 == 0) {
                System.out.println();
            }
        }

        System.out.println("\nAvailable seats: " + show.getAvailableSeatCount());
    }

    /**
     * Books tickets for the current customer.
     * If the requested seats are unavailable, the customer can join the waiting list.
     */
    private void bookTickets() {
        displayAllShows();

        System.out.print("Enter show ID: ");
        String showId = scanner.nextLine();

        try {
            Show show = movieService.findShowById(showId);
            displaySeatMap(show);

            int seatCount = readInt("How many seats do you want? ");

            if (seatCount <= 0 || seatCount > show.getTotalSeats()) {
                System.out.println("Please enter a valid number of seats.");
                return;
            }

            ArrayList<Integer> seatNumbers = new ArrayList<Integer>();

            for (int i = 1; i <= seatCount; i++) {
                int seatNumber = readInt("Enter seat number " + i + ": ");
                seatNumbers.add(seatNumber);
            }

            try {
                Booking booking = bookingService.bookTickets(
                        currentCustomer.getUserId(), showId, seatNumbers);

                System.out.println("\nBooking successful!");
                System.out.println(booking);

            } catch (SeatNotAvailableException e) {
                System.out.println("Booking failed: " + e.getMessage());
                System.out.print("Would you like to join the waiting list? (y/n): ");
                String answer = scanner.nextLine();

                if (answer.equalsIgnoreCase("y")) {
                    try {
                        bookingService.joinWaitingList(
                                currentCustomer.getUserId(), showId, seatCount);

                        System.out.println("You have joined the waiting list.");
                        System.out.println("Requests are processed in FIFO order.");

                    } catch (InvalidShowException | SeatNotAvailableException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }

        } catch (InvalidShowException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays and cancels only the current customer's active bookings.
     */
    private void cancelBooking() {
        ArrayList<Booking> myBookings =
                bookingService.getCustomerBookings(currentCustomer.getUserId());

        if (myBookings.isEmpty()) {
            System.out.println("You do not have any active bookings.");
            return;
        }

        System.out.println("\n----------- YOUR BOOKINGS -----------");

        for (Booking booking : myBookings) {
            System.out.println(booking);
        }

        System.out.print("Enter booking ID to cancel: ");
        String bookingId = scanner.nextLine();

        try {
            ArrayList<Booking> promotedBookings =
                    bookingService.cancelBooking(
                            bookingId, currentCustomer.getUserId());

            System.out.println("Booking cancelled successfully.");

            if (promotedBookings.isEmpty()) {
                System.out.println("No waiting-list request could be promoted yet.");
            } else {
                for (Booking booking : promotedBookings) {
                    System.out.println("Waiting-list request promoted automatically:");
                    System.out.println(booking);
                }
            }

        } catch (BookingNotFoundException | InvalidShowException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays the number of customers waiting for a selected show.
     */
    private void viewWaitingListCount() {
        System.out.print("Enter show ID: ");
        String showId = scanner.nextLine();

        try {
            int count = bookingService.getWaitingListCount(showId);
            System.out.println("Waiting-list requests for "
                    + showId.toUpperCase() + ": " + count);
        } catch (InvalidShowException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads an integer without allowing invalid text to crash the application.
     */
    private int readInt(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
