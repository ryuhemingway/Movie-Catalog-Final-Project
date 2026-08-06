package student;

import java.util.List;
import student.controller.MovieController;
import student.files.CsvLoader;
import student.model.Catalog;
import student.model.Movie;
import student.model.MovieFilter;
import student.model.RandomMovieGenerator;
import student.model.StatisticsEngine;
import student.view.MainFrame;

/**
 * This will be our main driver class that launches the CineVault movie catalog application.
 */
public final class CineVaultApplication {
  /** How many movies to show right when the GUI first opens. */
  private static final int INITIAL_MOVIE_COUNT = 20;

  private CineVaultApplication() {

  }

  /**
   * This is where our application will start running.
   *
   * @param args command line arguments, not currently used
   */
  public static void main(String[] args) {
    Catalog catalog = new Catalog();

    // Temporary: load our real dataset so we have movies to search and view
    CsvLoader loader = new CsvLoader();
    List<Movie> movies = loader.loadMoviesFile("src/main/resources/imdb_top_1000.csv");
    for (Movie movie : movies) {
      catalog.addMovie(movie);
    }
    System.out.println("Loaded movie count: " + catalog.getAllMovies().size());

    MovieFilter movieFilter = new MovieFilter();
    StatisticsEngine statisticsEngine = new StatisticsEngine();
    RandomMovieGenerator randomMovieGenerator = new RandomMovieGenerator(catalog);

    MovieController controller =
        new MovieController(catalog, movieFilter, statisticsEngine, randomMovieGenerator);

    MainFrame mainFrame = new MainFrame(controller);
    mainFrame.start();

    // Show the first 20 movies right when the GUI opens, so the movie
    // panel isn't empty before the user does anything
    List<Movie> allMovies = controller.getAllMovies();
    int count = Math.min(INITIAL_MOVIE_COUNT, allMovies.size());
    List<Movie> firstMovies = allMovies.subList(0, count);
    mainFrame.showInitialMovies(firstMovies);
  }
}
