package student.files;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import student.model.Movie;

/**
 * This class will handle reading movie data in from a CSV file so we can build our Catalog.
 */

public class CsvLoader {

  /**
   * This loads all movies from the given CSV file path.
   *
   * @param filePath the path to the CSV file to load
   * @return a list of Movie objects built from the file's rows
   */
  public List<Movie> loadMoviesFile(String filePath) {
    // This will hold every Movie we build as we read through the file.
    List<Movie> movies = new ArrayList<>();

    // We use try with resources here so the reader automatically closes
    // itself when we're done, even if something goes wrong.
    try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

      // The first line of the CSV is just the column names (title, year, etc.),
      // not an actual movie, so we read it once and throw it away.
      String[] header = reader.readNext();

      // Now we read the file one row at a time until there are no more rows.
      String[] row;
      while ((row = reader.readNext()) != null) {

        // Each row is just an array of strings, one per column.
        // We know from our dataset that column 1 is the title
        // and column 2 is the release year.
        String title = row[1];
        int year = Integer.parseInt(row[2]);

        // Runtime looks like "136 min" in our file, so we take just the number part
        String durationText = row[4].replace(" min", "");
        int duration = Integer.parseInt(durationText);

        // IMDB rating is column 6
        double rating = Double.parseDouble(row[6]);

        // Genre is one string like "Action, Sci-Fi", so we split it into a list
        String[] genreParts = row[5].split(",\\s*");
        List<String> genres = List.of(genreParts);

        // Our file only has one director column, so we put it in a list of one
        List<String> directors = List.of(row[9]);

        Movie movie = new Movie.Builder(title, year)
            .duration(duration)
            .rating(rating)
            .genres(genres)
            .directors(directors)
            .build();
        // Add the movie we just built to our running list.
        movies.add(movie);
      }
    } catch (IOException | CsvValidationException e) {
      // If the file doesn't exist, or something goes wrong while reading it,
      // we wrap the error in a RuntimeException so it's clear which file failed.
      throw new RuntimeException("Failed to load movies from file: " + filePath, e);
    }

    // This gives back the full list of movies we built from the file.
    return movies;
  }
}