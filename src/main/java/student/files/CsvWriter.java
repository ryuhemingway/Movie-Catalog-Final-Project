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
        // We write duration back out as "136 min" so CsvLoader can read it the same way
        String durationText = movie.getDuration() + " min";

        // Join the genre list back into one comma separated string
        String genresText = String.join(", ", movie.getGenres());

        // Our file only keeps one director, so we just grab the first one from the list
        String directorText = movie.getDirectors().isEmpty() ? "" : movie.getDirectors().get(0);

        // Our file has 4 separate star columns, so we grab up to 4 actors from the list
        List<String> actors = movie.getActors();
        String star1 = actors.size() > 0 ? actors.get(0) : "";
        String star2 = actors.size() > 1 ? actors.get(1) : "";
        String star3 = actors.size() > 2 ? actors.get(2) : "";
        String star4 = actors.size() > 3 ? actors.get(3) : "";

        String[] row = {movie.getPosterUrl(), movie.getTitle(), String.valueOf(movie.getYear()), "",
            durationText, genresText, String.valueOf(movie.getRating()), movie.getOverview(), "", directorText,
            star1, star2, star3, star4, "", ""};
        writer.writeNext(row);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to write movies to file: " + filePath, e);
    }
  }
}