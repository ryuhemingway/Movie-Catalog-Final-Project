package student.files;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import student.model.Movie;

/**
 * This  class will handle writing our Catalog movie data out to a CSV file.
 */
public class CsvWriter {

  /**
   * This writes the given list of movies out to a CSV file at the given path.
   *
   * @param filePath the path to write the CSV file to
   * @param movies the movies to write out
   */
  public void writeMoviesFile(String filePath, List<Movie> movies) {
    try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
      // Write the same header CsvLoader expects, so we can read this file back in
      String[] header = {"Poster_Link", "Series_Title", "Released_Year", "Certificate",
          "Runtime", "Genre", "IMDB_Rating", "Overview", "Meta_score", "Director",
          "Star1", "Star2", "Star3", "Star4", "No_of_Votes", "Gross"};
      writer.writeNext(header);

      for (Movie movie : movies) {
        String[] row = {"", movie.getTitle(), String.valueOf(movie.getYear()), "",
            "", "", "", "", "", "", "", "", "", "", "", ""};
        writer.writeNext(row);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to write movies to file: " + filePath, e);
    }
  }
}