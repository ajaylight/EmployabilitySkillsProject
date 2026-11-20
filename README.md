# Movie Ticket Booking System

A console-based Java application for managing movie listings, showtimes, seat selection, ticket bookings, cancellations, waiting lists, and revenue reports.

## Project overview

This project is developed by **Team L** as a Java console project. It demonstrates object-oriented programming, Java Collections, custom exceptions, and a manually implemented data structure.

## Features

### Customer
- Browse the movie catalogue.
- View showtimes and seat availability.
- Book movie tickets by selecting seats.
- Cancel personal bookings.
- Join the waiting list when selected seats are unavailable.
- View the waiting-list count for a show.

### Admin
- View movies and showtimes.
- View the revenue report.
- Check waiting-list counts.

## Technologies
- Java 17
- Maven
- Java Collections Framework
- Git and GitHub

## Project constraints
- Console-based interface using `Scanner`.
- No graphical user interface.
- No database; data is maintained in memory while the application runs.

## Object-oriented programming
- **Encapsulation:** Model fields are private and accessed through methods.
- **Inheritance:** Customer and Admin extend the abstract User class.
- **Polymorphism:** Customer and Admin provide their own getRole() implementation.
- **Abstraction:** User defines common user information and an abstract role method.

## Other Java requirements
- Three custom checked exceptions for invalid shows, unavailable seats, and missing bookings.
- A manually implemented linked-list FIFO waiting queue.
- Collections including ArrayList, HashMap, and HashSet.

## Run the project

Make sure Java 17 and Maven are installed.

Compile the project:

```bash
mvn clean compile
```

Run the application:

```bash
java -cp target/classes app.Main
```

The demo administrator password is:

```text
admin123
```

Customer and booking data are stored in memory and reset when the application exits.

## Team
**Team L**
