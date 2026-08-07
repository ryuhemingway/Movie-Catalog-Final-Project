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
import student.view.GuiView;
import student.view.IView;

/**
 * This will be our main driver class that launches the CineVault movie
 * catalog application. It can run in GUI mode, which is our default, or
 * command line mode if "-cl" is passed in as the first argument.
 */
public final class CineVaultApplication {

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
    // If the user typed -cl as the first argument, we run the command
    // line version. Or we run the GUI version by default.
    boolean useCommandLine = false;
    if (args.length > 0 && args[0].equalsIgnoreCase("-cl")) {
      useCommandLine = true;
    }

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

    // Both GuiView and CommandLineView implement the same IView
    // interface, so this is the only place we need to pick between them.
    // Once we have a view, we don't need to know or care which kind it
    // is, we just call start() on it.
    IView view;
    if (useCommandLine) {
      view = new CommandLineView(controller);
    } else {
      view = new GuiView(controller);
    }
    view.start();
  }
}