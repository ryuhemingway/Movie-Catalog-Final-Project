package student.files;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import student.model.Movie;

/**
 * This class loads and saves favorite movies using a simple CSV file:
 * title, year, rating, and poster URL.
 */
public class CsvFavoritesRepository implements IFavorites {

  /** Index of the poster URL field in a favorites file row. */
  private static final int POSTER_URL_INDEX = 3;

  /** Where the favorites file lives on disk. */
  private final String filePath;

  /**
   * This builds a repository that reads and writes to the given file.
   *
   * @param filePath the path to the favorites CSV file
   */
  public CsvFavoritesRepository(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public List<Movie> loadFavorites() {
    List<Movie> favorites = new ArrayList<>();
    try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
      reader.readNext(); // skip the header row
      String[] row;
      while ((row = reader.readNext()) != null) {
        String title = row[0];
        int year = Integer.parseInt(row[1]);
        double rating = Double.parseDouble(row[2]);
        String posterUrl = row.length > POSTER_URL_INDEX ? row[POSTER_URL_INDEX] : "";

        Movie movie = new Movie.Builder(title, year)
            .rating(rating)
            .posterUrl(posterUrl)
            .build();
        favorites.add(movie);
      }
    } catch (Exception e) {
      // if the file doesn't exist yet, we just have no favorites, that's fine
    }
    return favorites;
  }

  @Override
  public void saveFavorites(List<Movie> favorites) {
    try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
      writer.writeNext(new String[] {"Title", "Year", "Rating", "PosterUrl"});
      for (Movie movie : favorites) {
        writer.writeNext(new String[] {
            movie.getTitle(),
            String.valueOf(movie.getYear()),
            String.valueOf(movie.getRating()),
            movie.getPosterUrl()
        });
      }
    } catch (Exception e) {
      System.out.println("Could not save favorites file.");
    }
  }
}
