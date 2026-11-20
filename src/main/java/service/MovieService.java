package service;

import java.util.ArrayList;

import exceptions.InvalidShowException;
import model.Movie;
import model.Show;

/**
 * Manages the movie catalogue and the list of scheduled shows.
 */
public class MovieService {

    private ArrayList<Movie> movies;
    private ArrayList<Show> shows;

    /**
     * Creates the catalogue and adds sample movies and shows.
     */
    public MovieService() {
        movies = new ArrayList<Movie>();
        shows = new ArrayList<Show>();

        // Sample movies
        movies.add(new Movie("M101", "Interstellar", "Sci-Fi", 169));
        movies.add(new Movie("M102", "Inception", "Thriller", 148));
        movies.add(new Movie("M103", "The Dark Knight", "Action", 152));

        // Sample show timings for the movies
        shows.add(new Show("S101", "M101", "PVR Central", "10:00 AM", 200.0, 20));
        shows.add(new Show("S102", "M101", "PVR Central", "6:30 PM", 220.0, 20));
        shows.add(new Show("S103", "M102", "Cinepolis Orion", "2:00 PM", 180.0, 20));
        shows.add(new Show("S104", "M103", "PVR Central", "9:00 PM", 250.0, 20));
    }

    /**
     * Returns a copy of the movie list.
     */
    public ArrayList<Movie> getAllMovies() {
        return new ArrayList<Movie>(movies);
    }

    /**
     * Returns a copy of the show list.
     */
    public ArrayList<Show> getAllShows() {
        return new ArrayList<Show>(shows);
    }

    /**
     * Displays all movies in the catalogue.
     */
    public void displayMovies() {
        System.out.println("\n========== MOVIE CATALOGUE ==========");

        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    /**
     * Displays all scheduled shows with their available seat counts.
     */
    public void displayShows() {
        System.out.println("\n========== AVAILABLE SHOWS ==========");

        for (Show show : shows) {
            System.out.println("Show ID: " + show.getShowId()
                    + " | Movie ID: " + show.getMovieId()
                    + " | Theatre: " + show.getTheatreName()
                    + " | Time: " + show.getShowTime()
                    + " | Price: Rs. " + show.getTicketPrice()
                    + " | Available seats: " + show.getAvailableSeatCount());
        }
    }

    /**
     * Finds a movie using its movie ID.
     * Returns null if the movie does not exist.
     */
    public Movie findMovieById(String movieId) {
        for (Movie movie : movies) {
            if (movie.getMovieId().equalsIgnoreCase(movieId)) {
                return movie;
            }
        }

        return null;
    }

    /**
     * Finds a show using its show ID.
     * Throws InvalidShowException when the show ID is not found.
     */
    public Show findShowById(String showId) throws InvalidShowException {
        for (Show show : shows) {
            if (show.getShowId().equalsIgnoreCase(showId)) {
                return show;
            }
        }

        throw new InvalidShowException("Show ID " + showId + " does not exist.");
    }

    /**
     * Returns all shows scheduled for the given movie.
     */
    public ArrayList<Show> getShowsForMovie(String movieId) {
        ArrayList<Show> movieShows = new ArrayList<Show>();

        for (Show show : shows) {
            if (show.getMovieId().equalsIgnoreCase(movieId)) {
                movieShows.add(show);
            }
        }

        return movieShows;
    }
}
