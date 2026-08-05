package student.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * This class tests that RandomMovieGenerator properly returns a random movie
 * from the Catalog based on the user's favourite genre and a rating of at
 * least 8.0.
 */
public class RandomMovieGeneratorTest {

  /**
   * This tests that getRandomMovie(genre) returns a movie that has the
   * given genre and a rating of at least 8.0.
   */
  @Test
  void getRandomMovieByGenreReturnsMatchingMovie() {
    Catalog catalog = new Catalog();
    Movie actionMovie = new Movie.Builder("The Matrix", 1999)
        .genres(List.of("Action", "Sci-Fi"))
        .rating(8.7)
        .build();
    Movie dramaMovie = new Movie.Builder("The Godfather", 1972)
        .genres(List.of("Drama", "Crime"))
        .rating(9.2)
        .build();
    catalog.addMovie(actionMovie);
    catalog.addMovie(dramaMovie);

    RandomMovieGenerator generator = new RandomMovieGenerator(catalog);
    Movie result = generator.getRandomMovie("Action");

    assertNotNull(result);
    assertTrue(result.getGenres().contains("Action"));
    assertTrue(result.getRating() >= 8.0);
  }

  /**
   * This tests that getRandomMovie(genre) returns null when the catalog
   * has no movie with that genre and a rating of at least 8.0.
   */
  @Test
  void getRandomMovieByGenreReturnsNullWhenNoMatch() {
    Catalog catalog = new Catalog();
    Movie lowRatedAction = new Movie.Builder("B-Movie", 2005)
        .genres(List.of("Action"))
        .rating(4.5)
        .build();
    catalog.addMovie(lowRatedAction);

    RandomMovieGenerator generator = new RandomMovieGenerator(catalog);
    Movie result = generator.getRandomMovie("Action");

    assertNull(result);
  }
}