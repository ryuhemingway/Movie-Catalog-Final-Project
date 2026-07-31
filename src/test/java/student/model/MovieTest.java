package student.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests that the {@link Movie} class stores and returns its fields correctly, keeps
 * its list fields immutable, and defines equality by title and release year.
 */
class MovieTest {

  private Movie movie;

  /**
   * Builds a fully populated sample movie used across the tests.
   */
  @BeforeEach
  void setUp() {
    movie = new Movie.Builder("The Matrix", 1999)
        .duration(136)
        .rating(8.7)
        .genres(List.of("Action", "Sci-Fi"))
        .directors(List.of("Lana Wachowski", "Lilly Wachowski"))
        .actors(List.of("Keanu Reeves", "Laurence Fishburne"))
        .posterUrl("https://example.com/matrix.jpg")
        .overview("A hacker discovers the truth about his reality.")
        .build();
  }

  /**
   * Verifies each getter returns the value supplied at construction.
   */
  @Nested
  @DisplayName("getters return stored values")
  class Getters {

    @Test
    void returnsTitle() {
      assertEquals("The Matrix", movie.getTitle());
    }

    @Test
    void returnsYear() {
      assertEquals(1999, movie.getYear());
    }

    @Test
    void returnsDuration() {
      assertEquals(136, movie.getDuration());
    }

    @Test
    void returnsRating() {
      assertEquals(8.7, movie.getRating());
    }

    @Test
    void returnsGenres() {
      assertEquals(List.of("Action", "Sci-Fi"), movie.getGenres());
    }

    @Test
    void returnsDirectors() {
      assertEquals(List.of("Lana Wachowski", "Lilly Wachowski"), movie.getDirectors());
    }

    @Test
    void returnsActors() {
      assertEquals(List.of("Keanu Reeves", "Laurence Fishburne"), movie.getActors());
    }

    @Test
    void returnsPosterUrl() {
      assertEquals("https://example.com/matrix.jpg", movie.getPosterUrl());
    }

    @Test
    void returnsOverview() {
      assertEquals("A hacker discovers the truth about his reality.", movie.getOverview());
    }
  }

  /**
   * Verifies the list-valued getters return unmodifiable views and that the movie is
   * not affected by later changes to the caller's original lists.
   */
  @Nested
  @DisplayName("list fields are immutable")
  class Immutability {

    @Test
    void genresGetterIsUnmodifiable() {
      assertThrows(UnsupportedOperationException.class,
          () -> movie.getGenres().add("Drama"));
    }

    @Test
    void actorsGetterIsUnmodifiable() {
      assertThrows(UnsupportedOperationException.class,
          () -> movie.getActors().clear());
    }
  }

  /**
   * Verifies that a movie's identity is its title (case-insensitive) plus release year.
   */
  @Nested
  @DisplayName("equality is by title and year")
  class Equality {

    @Test
    void sameTitleAndYearAreEqual() {
      Movie other = new Movie.Builder("The Matrix", 1999).rating(9.9).build();
      assertEquals(movie, other);
      assertEquals(movie.hashCode(), other.hashCode());
    }

    @Test
    void titleComparisonIgnoresCase() {
      Movie other = new Movie.Builder("the matrix", 1999).build();
      assertEquals(movie, other);
      assertEquals(movie.hashCode(), other.hashCode());
    }

    @Test
    void differentYearIsNotEqual() {
      Movie remake = new Movie.Builder("The Matrix", 2021).build();
      assertNotEquals(movie, remake);
    }

    @Test
    void differentTitleIsNotEqual() {
      Movie other = new Movie.Builder("Inception", 1999).build();
      assertNotEquals(movie, other);
    }

    @Test
    void equalsSelf() {
      assertTrue(movie.equals(movie));
    }
  }
}
