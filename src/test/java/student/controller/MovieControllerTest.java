package student.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
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
        controller = new MovieController(
                new Catalog(),
                new MovieFilter(),
                new StatisticsEngine(),
                new RandomMovieGenerator());
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
}
