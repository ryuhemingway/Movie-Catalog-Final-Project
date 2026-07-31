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





}
