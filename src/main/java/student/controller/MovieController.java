package student.controller;

import java.util.List;
import java.util.Map;
import student.model.Movie;
import student.model.Catalog;
import student.model.MovieFilter;
import student.model.RandomMovieGenerator;
import student.model.StatisticsEngine;


/**
 * This class will connect user actions from the view to the Catalog and other model classes in our program.
 */
// this class now implements IMovieController, so our views can depend
// on the interface instead of this specific class
public class MovieController implements IMovieController {

    /** The movie catalog holding all movies. */
    private final Catalog catalog;
    /** Filters movies by genre or rating. */
    private final MovieFilter movieFilter;
    /** Calculates statistics about the movies. */
    private final StatisticsEngine statisticsEngine;
    /** Picks a random movie matching a genre. */
    private final RandomMovieGenerator randomMovieGenerator;

    /**
     * Creates the controller using the model classes needed by the application.
     *
     * @param catalog the movie catalog
     * @param movieFilter filters movies
     * @param statisticsEngine calculates movie statistics
     * @param randomMovieGenerator selects a movie
     */
    public MovieController(
            Catalog catalog,
            MovieFilter movieFilter,
            StatisticsEngine statisticsEngine,
            RandomMovieGenerator randomMovieGenerator) {

        this.catalog = catalog;
        this.movieFilter = movieFilter;
        this.statisticsEngine = statisticsEngine;
        this.randomMovieGenerator = randomMovieGenerator;
    }

    /**
     * Adds a movie.
     *
     * @param movie the movie to add
     */
    @Override
    public void addMovie(Movie movie) {
        catalog.addMovie(movie);
    }

    /**
     * Gets all movies.
     *
     * @return all movies
     */
    @Override
    public List<Movie> getAllMovies() {
        return catalog.getAllMovies();
    }

    /**
     * Edits a movie.
     *
     * @param movie the updated movie
     */
    @Override
    public void editMovie(Movie movie) {
        catalog.editMovie(movie);
    }

    /**
     * Deletes a movie.
     *
     * @param movie the movie to delete
     */
    @Override
    public void deleteMovie(Movie movie) {
        catalog.deleteMovie(movie);
    }

    /**
     * Searches movies by title.
     *
     * @param query the search text
     * @return matching movies
     */
    @Override
    public List<Movie> searchMovies(String query) {
        return catalog.search(query);
    }

    /**
     * Filters movies by genre.
     *
     * @param genre the selected genre
     * @return matching movies
     */
    @Override
    public List<Movie> filterMoviesByGenre(String genre) {
        return movieFilter.filterByGenre(catalog.getAllMovies(), genre);
    }

    /**
     * Filters movies by rating.
     *
     * @param minRating the minimum rating
     * @param maxRating the maximum rating
     * @return matching movies
     */
    @Override
    public List<Movie> filterMoviesByRating(double minRating, double maxRating) {
        return movieFilter.filterByRating(
                catalog.getAllMovies(), minRating, maxRating);
    }

    /**
     * Filters movies by both genre and rating range at the same time.
     *
     * @param genre the selected genre, or "Genre: All" to skip genre filtering
     * @param minRating the minimum rating
     * @param maxRating the maximum rating
     * @return matching movies
     */
    @Override
    public List<Movie> filterMovies(String genre, double minRating, double maxRating) {
        List<Movie> byGenre = genre.equals("Genre: All")
                ? catalog.getAllMovies()
                : movieFilter.filterByGenre(catalog.getAllMovies(), genre);

        return movieFilter.filterByRating(byGenre, minRating, maxRating);
    }

    /**
     * This method picks a random movie matching the user's favourite genre and a
     * rating of at least 8.0.
     *
     * @param genre the user's favourite genre
     * @return a random matching movie, or null if none match
     */
    @Override
    public Movie getRandomMovie(String genre) {
        return randomMovieGenerator.getRandomMovie(genre);
    }

    @Override
    public Movie getRandomMovie(String genre, double minRating, double maxRating) {
        return randomMovieGenerator.getRandomMovie(genre, minRating, maxRating);
    }

    /**
     * Finds the average rating for each genre, across every movie.
     *
     * @return genre names mapped to their average rating
     */
    @Override
    public Map<String, Double> getAverageRatingByGenre() {
        return statisticsEngine.getAverageRatingByGenre(catalog.getAllMovies());
    }

    /**
     * Finds the highest rated movies in a genre.
     *
     * @param genre the genre to filter by, or "Genre: All" for every genre
     * @param count how many top movies to return
     * @return the highest rated movies in that genre, best first
     */
    @Override
    public List<Movie> getTopRatedMovies(String genre, int count) {
        // "Genre: All" means skip genre filtering and look across
        // every movie instead, same pattern used in filterMovies()
        List<Movie> candidates;
        if (genre.equals("Genre: All")) {
            candidates = catalog.getAllMovies();
        } else {
            candidates = movieFilter.filterByGenre(catalog.getAllMovies(), genre);
        }

        return statisticsEngine.getTopRatedMovies(candidates, count);
    }

}
