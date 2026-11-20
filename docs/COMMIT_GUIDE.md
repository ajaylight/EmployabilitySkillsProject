# Team L - 15-Commit Development Plan

This plan is for building the final project in small, reviewable stages. The finished ZIP is the reference for the intended final result.

> **Important:** Do not extract the finished project into a Git repository and commit all files as one upload. Keep the ZIP as a reference copy, create a clean working folder/repository, and implement the stages below in order. Each commit must contain the code or documentation for that stage. Do not create empty commits, split one unchanged file into artificial commits, backdate commits, or make commits on behalf of teammates.

## The 15 commits

| # | Commit message | Work to complete | Suggested files |
|---:|---|---|---|
| 1 | `Set up Java console project structure` | Create the Java project, packages, `.gitignore`, Maven configuration, and a minimal `Main` class that starts successfully. | `pom.xml`, `.gitignore`, `src/main/java/app/Main.java` |
| 2 | `Add abstract User base class` | Add common user fields, constructor, getters, and abstract `getRole()` method. | `model/User.java` |
| 3 | `Add Customer and Admin user types` | Extend `User` with `Customer` and `Admin`; override `getRole()` in both classes. | `model/Customer.java`, `model/Admin.java` |
| 4 | `Add Movie model` | Create the movie fields, constructor, getters, and a readable movie display method. | `model/Movie.java` |
| 5 | `Add Show model and seat tracking` | Store show details and seat status; add methods to book and release a seat and count available seats. | `model/Show.java` |
| 6 | `Add Booking model` | Store booking ID, customer, show, selected seats, total amount, and cancellation status. | `model/Booking.java` |
| 7 | `Add waiting-list request model` | Store the customer and number of seats requested in each waiting-list entry. | `model/WaitListEntry.java` |
| 8 | `Add custom booking exceptions` | Create and document the custom exceptions used for missing bookings, invalid shows, and unavailable seats. | `exceptions/BookingNotFoundException.java`, `exceptions/InvalidShowException.java`, `exceptions/SeatNotAvailableException.java` |
| 9 | `Implement FIFO waiting-list queue` | Write the linked-node queue yourself with `enqueue`, `dequeue`, `peek`, `isEmpty`, and `size`. Do not replace it with a library queue. | `datastructures/WaitingListQueue.java` |
| 10 | `Add sample movies and show lookup` | Store sample movies and shows in `ArrayList`; implement methods to display and find them by ID. | `service/MovieService.java` |
| 11 | `Implement ticket booking and seat validation` | Add booking storage, validate the show and seats, prevent duplicate selections, calculate the amount, and save a booking. Use a `HashSet` for duplicate-seat checking and a `HashMap` for booking records. | `service/BookingService.java` |
| 12 | `Implement booking cancellation and seat release` | Allow a customer to cancel only their own active booking; release the seats and handle a booking-not-found error. | `service/BookingService.java`, `app/Main.java` |
| 13 | `Connect waiting list to booking cancellation` | Add the join operation, per-show queue, queue count, and FIFO promotion when enough seats become free. | `service/BookingService.java`, `app/Main.java` |
| 14 | `Add admin revenue report` | Calculate total active-booking revenue and revenue grouped by show. Keep this option out of the customer menu. | `service/ReportService.java`, `app/Main.java` |
| 15 | `Complete customer and admin menus` | Connect all features, handle invalid menu input, update the README, and run the full manual test checklist. | `app/Main.java`, `README.md` |

## Test checklist before the final commit

- [ ] Customer can view movies and showtimes.
- [ ] Customer can book available seats.
- [ ] A duplicate, invalid, or already-booked seat is rejected.
- [ ] Customer can see their bookings and cancel only their own booking.
- [ ] Cancelling a booking releases its seats.
- [ ] A waiting-list request is added to the correct show's FIFO queue.
- [ ] The oldest request is promoted only when enough seats are available.
- [ ] Waiting-list count shows the number of requests, not the number of seats.
- [ ] Admin login is required to see revenue reports.
- [ ] Revenue excludes cancelled bookings.
- [ ] The project compiles and runs from a clean terminal.

## Git workflow

Configure Git using your own name and the email associated with your GitHub account:

```bash
git config --global user.name "Your Name"
git config --global user.email "your-github-email@example.com"
```

After completing each stage, review what changed and commit only that work:

```bash
git status
git diff --staged
git add <files-for-this-stage>
git commit -m "Meaningful message from the table above"
```

Check the history regularly:

```bash
git log --oneline --decorate
```

After creating the GitHub repository, connect and push it:

```bash
git remote add origin https://github.com/<your-username>/MovieTicketBookingSystem.git
git branch -M main
git push -u origin main
```

## Team contribution

Every member should implement and commit identifiable parts of the work using their own GitHub identity. Divide the stages among members, review each other's code, and make sure every member can explain the whole application. Each member should also have access to the repository in their own GitHub account as required by the assignment.

A commit is meaningful only when it records a real change. The commit count alone does not prove contribution; the code diff, commit message, author, and project understanding matter.
