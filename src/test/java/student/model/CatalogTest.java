package student.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
/**
 * This class will test that the Catalog class properly adds, edits, deletes, and searches movies.
 */
public class CatalogTest {

  /**
   * This tests that addMovie() properly adds a movie to the catalog.
   */
  @Test
  void addMovieAddsMovieToCatalog() {
    // We start with an empty catalog and one simple movie
    Catalog catalog = new Catalog();
    Movie movie = new Movie.Builder("The Matrix", 1999).build();

    catalog.addMovie(movie);

    // Check the catalog now has that one movie in it
    List<Movie> movies = catalog.getAllMovies();
    assertEquals(1, movies.size());
    assertEquals("The Matrix", movies.get(0).getTitle());
  }
  /**
   * This tests that editMovie() properly replaces an existing movie
   * with an updated version.
   */
  @Test
  void editMovieReplacesExistingMovie() {
    // This starts with one movie in the catalog
    Catalog catalog = new Catalog();
    Movie original = new Movie.Builder("The Matrix", 1999).rating(8.7).build();
    catalog.addMovie(original);

    // Same title and year means Movie treats these as the same movie,
    // but this one has an updated rating
    Movie updated = new Movie.Builder("The Matrix", 1999).rating(9.5).build();
    catalog.editMovie(updated);

    // The catalog should still have just 1 movie, but with the new rating
    List<Movie> movies = catalog.getAllMovies();
    assertEquals(1, movies.size());
    assertEquals(9.5, movies.get(0).getRating());
  }
  /**
   * This tests that deleteMovie() properly removes a movie from the catalog.
   */
  @Test
  void deleteMovieRemovesMovieFromCatalog() {
    // This starts with one movie in the catalog
    Catalog catalog = new Catalog();
    Movie movie = new Movie.Builder("The Matrix", 1999).build();
    catalog.addMovie(movie);

    catalog.deleteMovie(movie);

    // The catalog should now be empty after deleting
    List<Movie> movies = catalog.getAllMovies();
    assertEquals(0, movies.size());
  }
  /**
   * This tests that search() finds movies whose title contains the
   * given text, even if it's not the full title.
   */
  @Test
  void searchFindsMoviesByPartialTitle() {
    // This adds a couple movies with different titles
    Catalog catalog = new Catalog();
    Movie matrix = new Movie.Builder("The Matrix", 1999).build();
    Movie inception = new Movie.Builder("Inception", 2010).build();
    catalog.addMovie(matrix);
    catalog.addMovie(inception);

    // Search using only part of "The Matrix"
    List<Movie> results = catalog.search("Matrix");

    assertEquals(1, results.size());
    assertEquals("The Matrix", results.get(0).getTitle());
  }
  //Edge Cases
  /**
   * This edge case tests that addMovie() does not add a duplicate movie.
   * Movie treats same title and year as the same movie.
   */
  @Test
  void addMovieDoesNotAddDuplicateMovie() {
    Catalog catalog = new Catalog();
    Movie movie = new Movie.Builder("The Matrix", 1999).build();

    catalog.addMovie(movie);
    catalog.addMovie(movie);

    // Adding the same movie twice should not create two entries
    List<Movie> movies = catalog.getAllMovies();
    assertEquals(1, movies.size());
  }
  /**
   * This tests that editMovie() does nothing if the movie is not
   * already in the catalog, instead of crashing or adding it.
   */
  @Test
  void editMovieDoesNothingIfMovieNotFound() {
    Catalog catalog = new Catalog();
    Movie movie = new Movie.Builder("The Matrix", 1999).build();

    // Catalog is empty, so this movie was never added
    catalog.editMovie(movie);

    // Nothing should have been added here
    List<Movie> movies = catalog.getAllMovies();
    assertEquals(0, movies.size());
  }
  /**
   * This tests that deleteMovie() does nothing if the movie is not
   * already in the catalog, instead of crashing.
   */
  @Test
  void deleteMovieDoesNothingIfMovieNotFound() {
    Catalog catalog = new Catalog();
    Movie movie = new Movie.Builder("The Matrix", 1999).build();

    // Catalog is empty, so this movie was never added
    catalog.deleteMovie(movie);

    // Nothing should have changed, catalog should still be empty
    List<Movie> movies = catalog.getAllMovies();
    assertEquals(0, movies.size());
  }
}
