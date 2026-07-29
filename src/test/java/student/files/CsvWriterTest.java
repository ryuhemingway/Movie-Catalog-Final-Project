package student.files;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import student.model.Movie;

/**
 * This class will test that the CsvWriter class properly writes movies to a CSV file
 * and handles an empty movie list.
 */
public class CsvWriterTest {

  /**
   * This tests that writeMoviesFile() properly writes movies to a CSV file,
   * and that reading that file back gives the same movies.
   */
  @Test
  void writeMoviesFileWritesMoviesToFile() throws IOException {
    // We build a simple movie to write out and check later
    Movie movie = new Movie.Builder("The Matrix", 1999).build();
    List<Movie> movies = List.of(movie);

    // This use a temp file so we don't leave test files behind
    File outputFile = File.createTempFile("test_output", ".csv");
    outputFile.deleteOnExit();

    CsvWriter writer = new CsvWriter();
    writer.writeMoviesFile(outputFile.getPath(), movies);

    // This will read the file back in to check the write actually worked
    CsvLoader loader = new CsvLoader();
    List<Movie> loadedMovies = loader.loadMoviesFile(outputFile.getPath());

    assertEquals(1, loadedMovies.size());
    assertEquals("The Matrix", loadedMovies.get(0).getTitle());
  }



  /**
   * This tests that writeMoviesFile() properly handles an empty movie list
   * by writing just the header row, with no errors.
   */
  @Test
  void writeMoviesFileHandlesEmptyList() throws IOException {
    // Use a temporary file so we don't leave test files behind
    File outputFile = File.createTempFile("test_output_empty", ".csv");
    outputFile.deleteOnExit();

    CsvWriter writer = new CsvWriter();
    writer.writeMoviesFile(outputFile.getPath(), List.of());

    // This read the file back in to make sure writing an empty list didn't break anything
    CsvLoader loader = new CsvLoader();
    List<Movie> loadedMovies = loader.loadMoviesFile(outputFile.getPath());

    assertEquals(0, loadedMovies.size());
  }






}