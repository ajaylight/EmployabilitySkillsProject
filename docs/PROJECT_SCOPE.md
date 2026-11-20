# Team L - Final Project Scope (Locked)

**Project:** Movie Ticket Booking System  
**Language:** Java  
**Interface:** Console menu using `Scanner`  
**Storage:** In-memory only; no GUI or database

This is the locked feature scope for the submitted version. Do not add optional features that make the project harder to explain or test.

## Customer functions

1. View the movie catalogue.
2. View scheduled shows and seat availability.
3. Book one or more seats.
4. View and cancel the current customer's active bookings.
5. View the waiting-list count for a show.

## Admin functions

1. View movies.
2. View shows and seat availability.
3. View the revenue report.
4. View the waiting-list count.

Revenue reports must not appear in the customer menu.

## Waiting-list behaviour

- Each show has its own waiting-list queue.
- Requests are processed in FIFO (First In, First Out) order.
- The count represents the number of waiting requests, not the number of seats requested.
- After a cancellation, the system promotes the oldest request only when enough seats are available for that request.
- Promotion in this basic version creates the booking automatically; customer confirmation and timed holds are outside the locked scope.

## Required academic concepts

- OOP: encapsulation, inheritance, abstraction, and polymorphism.
- At least two custom exception classes, handled using `try-catch`.
- One self-implemented data structure or algorithm used in a real feature. This project uses a linked-node FIFO queue.
- Java Collections used naturally, including `ArrayList`, `HashMap`, and `HashSet`.
- Comments for classes, methods, and non-obvious logic.
- At least 15 genuine, meaningful Git commits made during development.

## Deliberate limitations

- Data is stored in memory and resets when the program exits.
- There is no GUI, database, payment gateway, SMS, email, or external booking integration.
- The admin password is a demonstration credential, not production authentication.
