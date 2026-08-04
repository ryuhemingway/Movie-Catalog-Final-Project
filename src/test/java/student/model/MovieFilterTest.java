package student.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MovieFilter}.
 */
class MovieFilterTest {

  private MovieFilter filter;
  private List<Movie> movies;

  /**
   * Builds a small sample catalog of movies to filter in each test.
   */
  @BeforeEach
  void setUp() {
    filter = new MovieFilter();

    Movie matrix = new Movie.Builder("The Matrix", 1999)
        .genres(List.of("Action", "Sci-Fi"))
        .rating(8.7)
        .build();

    Movie inception = new Movie.Builder("Inception", 2010)
        .genres(List.of("Action", "Thriller"))
        .rating(8.8)
        .build();

    Movie notebook = new Movie.Builder("The Notebook", 2004)
        .genres(List.of("Romance", "Drama"))
        .rating(7.8)
        .build();

    Movie room = new Movie.Builder("The Room", 2003)
        .genres(List.of("Drama"))
        .rating(3.7)
        .build();

    movies = List.of(matrix, inception, notebook, room);
  }

  // ---- filterByGenre ----

  @Test
  void filterByGenreReturnsOnlyMatchingMovies() {
    List<Movie> results = filter.filterByGenre(movies, "Action");

    assertEquals(2, results.size());
    assertTrue(results.stream().anyMatch(m -> m.getTitle().equals("The Matrix")));
    assertTrue(results.stream().anyMatch(m -> m.getTitle().equals("Inception")));
  }

  @Test
  void filterByGenreIsCaseInsensitive() {
    List<Movie> results = filter.filterByGenre(movies, "action");

    assertEquals(2, results.size());
  }

  @Test
  void filterByGenreReturnsEmptyListWhenNoMoviesMatch() {
    List<Movie> results = filter.filterByGenre(movies, "Horror");

    assertTrue(results.isEmpty());
  }

  @Test
  void filterByGenreThrowsOnNullMovies() {
    assertThrows(NullPointerException.class, () -> filter.filterByGenre(null, "Action"));
  }

  @Test
  void filterByGenreThrowsOnNullGenre() {
    assertThrows(NullPointerException.class, () -> filter.filterByGenre(movies, null));
  }

  // ---- filterByRating ----

  @Test
  void filterByRatingReturnsOnlyMoviesWithinRange() {
    List<Movie> results = filter.filterByRating(movies, 8.0, 9.0);

    assertEquals(2, results.size());
    assertTrue(results.stream().anyMatch(m -> m.getTitle().equals("The Matrix")));
    assertTrue(results.stream().anyMatch(m -> m.getTitle().equals("Inception")));
  }

  @Test
  void filterByRatingIncludesBoundaryValues() {
    List<Movie> results = filter.filterByRating(movies, 8.7, 8.7);

    assertEquals(1, results.size());
    assertEquals("The Matrix", results.get(0).getTitle());
  }

  @Test
  void filterByRatingReturnsEmptyListWhenNoMoviesMatch() {
    List<Movie> results = filter.filterByRating(movies, 9.5, 10.0);

    assertTrue(results.isEmpty());
  }

  @Test
  void filterByRatingThrowsWhenMinIsGreaterThanMax() {
    assertThrows(IllegalArgumentException.class, () -> filter.filterByRating(movies, 9.0, 5.0));
  }

  @Test
  void filterByRatingThrowsOnNullMovies() {
    assertThrows(NullPointerException.class, () -> filter.filterByRating(null, 0.0, 10.0));
  }
}
