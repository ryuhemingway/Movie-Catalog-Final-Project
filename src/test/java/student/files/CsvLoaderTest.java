package student.files;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}