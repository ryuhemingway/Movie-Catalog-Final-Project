package student.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.model.Catalog;
import student.model.Movie;
import student.model.MovieFilter;
import student.model.RandomMovieGenerator;
import student.model.StatisticsEngine;


/**
 * This will test that the MovieController class properly connects user actions to the model classes.
 */

public class MovieControllerTest {
    private MovieController controller;

    @BeforeEach
    void setUp() {
        Catalog catalog = new Catalog();
        controller = new MovieController(
                catalog,
                new MovieFilter(),
                new StatisticsEngine(),
                new RandomMovieGenerator(catalog));
    }

    @Test
    void addMovieAddsMovieToCatalog() {
        Movie movie = new Movie.Builder("The Matrix", 1999).build();

        controller.addMovie(movie);

        assertEquals(List.of(movie), controller.getAllMovies());
    }
    @Test
    void getAllMoviesReturnsEveryMovie() {
        Movie firstMovie = new Movie.Builder("The Matrix", 1999).build();
        Movie secondMovie = new Movie.Builder("Inception", 2010).build();

        controller.addMovie(firstMovie);
        controller.addMovie(secondMovie);

        assertEquals(List.of(firstMovie, secondMovie), controller.getAllMovies());
    }

    @Test
    void editMovieReplacesExistingMovie() {
        Movie originalMovie = new Movie.Builder("The Matrix", 1999)
                .rating(8.7)
                .build();
        Movie updatedMovie = new Movie.Builder("The Matrix", 1999)
                .rating(9.0)
                .build();

        controller.addMovie(originalMovie);
        controller.editMovie(updatedMovie);

        assertEquals(9.0, controller.getAllMovies().get(0).getRating());
    }

    @Test
    void deleteMovieRemovesMovieFromCatalog() {
        Movie movie = new Movie.Builder("The Matrix", 1999).build();

        controller.addMovie(movie);
        controller.deleteMovie(movie);

        assertEquals(List.of(), controller.getAllMovies());
    }

    @Test
    void searchMoviesReturnsMatchingMovies() {
        Movie matrix = new Movie.Builder("The Matrix", 1999).build();
        Movie inception = new Movie.Builder("Inception", 2010).build();

        controller.addMovie(matrix);
        controller.addMovie(inception);

        assertEquals(List.of(matrix), controller.searchMovies("matrix"));
    }

    @Test
    void filterMoviesByGenreReturnsMatchingMovies() {
        Movie actionMovie = new Movie.Builder("The Matrix", 1999)
                .genres(List.of("Action", "Sci-Fi"))
                .build();
        Movie dramaMovie = new Movie.Builder("The Godfather", 1972)
                .genres(List.of("Drama", "Crime"))
                .build();

        controller.addMovie(actionMovie);
        controller.addMovie(dramaMovie);

        assertEquals(
                List.of(actionMovie),
                controller.filterMoviesByGenre("Action"));
    }

    @Test
    void filterMoviesByRatingReturnsMoviesInsideRange() {
        Movie firstMovie = new Movie.Builder("The Matrix", 1999)
                .rating(8.7)
                .build();
        Movie secondMovie = new Movie.Builder("Example Movie", 2020)
                .rating(6.0)
                .build();

        controller.addMovie(firstMovie);
        controller.addMovie(secondMovie);

        assertEquals(
            List.of(firstMovie),
            controller.filterMoviesByRating(8.0, 9.0));
    }

    /**
     * This tests that getAverageRatingByGenre() correctly averages the
     * ratings of movies that share a genre.
     */
    @Test
    void getAverageRatingByGenreReturnsCorrectAverages() {
        // Both movies are Action, with ratings 8.0 and 6.0, so the
        // average we expect back is 7.0
        Movie actionMovie = new Movie.Builder("The Matrix", 1999)
            .genres(List.of("Action"))
            .rating(8.0)
            .build();
        Movie secondActionMovie = new Movie.Builder("Example Movie", 2020)
            .genres(List.of("Action"))
            .rating(6.0)
            .build();

        controller.addMovie(actionMovie);
        controller.addMovie(secondActionMovie);

        // Ask the controller for the averages instead of calculating
        // them ourselves here, since that's the method being tested
        Map<String, Double> averages = controller.getAverageRatingByGenre();

        assertEquals(7.0, averages.get("Action"), 0.001);
    }

    /**
     * This tests that getTopRatedMovies() only returns movies from the
     * genre we asked for, ordered from highest rating to lowest.
     */
    @Test
    void getTopRatedMoviesReturnsHighestRatedMoviesInGenre() {
        // Two Action movies with different ratings, plus one Drama movie
        // that should get left out since we're only asking for Action
        Movie lowerRatedAction = new Movie.Builder("Example Movie", 2020)
            .genres(List.of("Action"))
            .rating(6.0)
            .build();
        Movie higherRatedAction = new Movie.Builder("The Matrix", 1999)
            .genres(List.of("Action"))
            .rating(8.7)
            .build();
        Movie dramaMovie = new Movie.Builder("The Godfather", 1972)
            .genres(List.of("Drama"))
            .rating(9.2)
            .build();

        controller.addMovie(lowerRatedAction);
        controller.addMovie(higherRatedAction);
        controller.addMovie(dramaMovie);

        List<Movie> topAction = controller.getTopRatedMovies("Action", 10);

        // The Drama movie should not show up here at all, and the
        // higher rated Action movie should come first
        assertEquals(List.of(higherRatedAction, lowerRatedAction), topAction);
    }

    /**
     * This tests that passing "Genre: All" skips genre filtering, so
     * movies from every genre are eligible to be in the top rated list.
     */
    @Test
    void getTopRatedMoviesReturnsAcrossAllGenresWhenGenreIsAll() {
        Movie actionMovie = new Movie.Builder("The Matrix", 1999)
            .genres(List.of("Action"))
            .rating(8.7)
            .build();
        Movie dramaMovie = new Movie.Builder("The Godfather", 1972)
            .genres(List.of("Drama"))
            .rating(9.2)
            .build();

        controller.addMovie(actionMovie);
        controller.addMovie(dramaMovie);

        List<Movie> topOverall = controller.getTopRatedMovies("Genre: All", 10);

        // Both movies should show up here, Drama first since it has the
        // higher rating, even though they're in different genres
        assertEquals(List.of(dramaMovie, actionMovie), topOverall);
    }
}
