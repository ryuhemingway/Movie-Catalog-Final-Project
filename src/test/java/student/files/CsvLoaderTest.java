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






}