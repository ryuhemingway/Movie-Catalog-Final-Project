package student.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import student.model.Movie;

/**
 * This class will test that the CsvLoader class properly loads movies from a CSV file
 * and handles bad file input from the user.
 */
public class CsvLoaderTest {

  /**
   * This tests that loadMoviesFile() properly loads all movies from a valid CSV file.
   */
  @Test
  void loadMoviesFileLoadsAllMoviesFromValidFile() {
    CsvLoader loader = new CsvLoader();

    List<Movie> movies = loader.loadMoviesFile("src/test/resources/test_movies.csv");

    assertEquals(3, movies.size());
  }

  /**
   * This tests that loadMoviesFile() properly handles a missing or invalid file
   * by throwing an exception instead of silently failing.
   */
  @Test
  void loadMoviesFileThrowsExceptionForMissingFile() {
    CsvLoader loader = new CsvLoader();

    assertThrows(RuntimeException.class, () ->
        loader.loadMoviesFile("src/test/resources/does_not_exist.csv"));
  }


  /**
   * This tests that loadMoviesFile() correctly reads the title and year
   * of each movie, not just the total count.
   */
  @Test
  void loadMoviesFileReadsCorrectTitleAndYear() {
    CsvLoader loader = new CsvLoader();

    List<Movie> movies = loader.loadMoviesFile("src/test/resources/test_movies.csv");

    Movie firstMovie = movies.get(0);
    assertEquals("The Matrix", firstMovie.getTitle());
    assertEquals(1999, firstMovie.getYear());
  }


  /**
   * This tests that loadMoviesFile() correctly parses the duration field,
   * stripping " min" from the raw CSV value.
   */
  @Test
  void loadMoviesFileReadsCorrectDuration() {
    CsvLoader loader = new CsvLoader();

    List<Movie> movies = loader.loadMoviesFile("src/test/resources/test_movies.csv");

    // The Matrix's runtime in our file is "136 min", so we just want the number 136
    Movie firstMovie = movies.get(0);
    assertEquals(136, firstMovie.getDuration());
  }


  /**
   * This tests that loadMoviesFile() correctly reads the rating field.
   */
  @Test
  void loadMoviesFileReadsCorrectRating() {
    CsvLoader loader = new CsvLoader();

    List<Movie> movies = loader.loadMoviesFile("src/test/resources/test_movies.csv");

    // The Matrix's rating in our file is 8.7
    Movie firstMovie = movies.get(0);
    assertEquals(8.7, firstMovie.getRating());
  }


  /**
   * This tests that loadMoviesFile() correctly splits the genre field
   * into a list of separate genres.
   */
  @Test
  void loadMoviesFileReadsCorrectGenres() {
    CsvLoader loader = new CsvLoader();

    List<Movie> movies = loader.loadMoviesFile("src/test/resources/test_movies.csv");

    // The Matrix's genre in our file is "Action, Sci-Fi", so we want it split into two parts
    Movie firstMovie = movies.get(0);
    assertEquals(List.of("Action", "Sci-Fi"), firstMovie.getGenres());
  }

  /**
   * This tests that loadMoviesFile() correctly reads the director field,
   * wrapping the single director name in a list.
   */
  @Test
  void loadMoviesFileReadsCorrectDirectors() {
    CsvLoader loader = new CsvLoader();

    List<Movie> movies = loader.loadMoviesFile("src/test/resources/test_movies.csv");

    // Our file only has one director listed per movie, but Movie stores a list
    Movie firstMovie = movies.get(0);
    assertEquals(List.of("Lana Wachowski"), firstMovie.getDirectors());
  }

  /**
   * This tests that loadMoviesFile() correctly combines the four star
   * columns into one list of actors.
   */
  @Test
  void loadMoviesFileReadsCorrectActors() {
    CsvLoader loader = new CsvLoader();

    List<Movie> movies = loader.loadMoviesFile("src/test/resources/test_movies.csv");

    // Our file has 4 star columns for The Matrix, so we expect all 4 in one list
    Movie firstMovie = movies.get(0);
    assertEquals(List.of("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss", "Hugo Weaving"),
        firstMovie.getActors());
  }














}