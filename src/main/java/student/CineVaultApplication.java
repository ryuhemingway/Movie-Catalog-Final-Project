package student;

import java.util.List;
import student.controller.MovieController;
import student.files.CsvLoader;
import student.model.Catalog;
import student.model.Movie;
import student.model.MovieFilter;
import student.model.RandomMovieGenerator;
import student.model.StatisticsEngine;
import student.view.CommandLineView;
import student.view.MainFrame;

/**
 * This will be our main driver class that launches the CineVault movie
 * catalog application. It can run in GUI mode, which is our default, or
 * command line mode if "-cl" is passed in as the first argument.
 */
public final class CineVaultApplication {

  /** How many movies to show right when the GUI first opens. */
  private static final int INITIAL_MOVIE_COUNT = 20;
  /** Where our main movie list is stored on disk. */
  private static final String MOVIES_FILE_PATH = "src/main/resources/imdb_top_1000.csv";

  private CineVaultApplication() {

  }

  /**
   * This is where our application will start running.
   *
   * @param args command line arguments; pass "-cl" as the first one to
   *     run in command line mode instead of the GUI
   */
  public static void main(String[] args) {

    boolean useCommandLine = args.length > 0 && args[0].equalsIgnoreCase("-cl");


    // We build the same model and controller either way, only the view
    // GUI window or command line menu changes based on the mode
    Catalog catalog = new Catalog();
    CsvLoader loader = new CsvLoader();
    List<Movie> movies = loader.loadMoviesFile(MOVIES_FILE_PATH);
    for (Movie movie : movies) {
      catalog.addMovie(movie);
    }

    MovieFilter movieFilter = new MovieFilter();
    StatisticsEngine statisticsEngine = new StatisticsEngine();
    RandomMovieGenerator randomMovieGenerator = new RandomMovieGenerator(catalog);
    MovieController controller =
        new MovieController(catalog, movieFilter, statisticsEngine, randomMovieGenerator);

    if (useCommandLine) {
      CommandLineView commandLineView = new CommandLineView(controller);
      commandLineView.start();
    } else {
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
}