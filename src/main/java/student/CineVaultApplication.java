package student;

import student.controller.MovieController;
import student.model.Catalog;
import student.model.MovieFilter;
import student.model.RandomMovieGenerator;
import student.model.StatisticsEngine;
import student.view.MainFrame;

/**
 * This will be our main driver class that launches the CineVault movie catalog application.
 */
public final class CineVaultApplication {

  private CineVaultApplication() {

  }

  /**
   * This is where our application will start running.
   *
   * @param args command line arguments, not currently used
   */
  public static void main(String[] args) {
    Catalog catalog = new Catalog();
    MovieFilter movieFilter = new MovieFilter();
    StatisticsEngine statisticsEngine = new StatisticsEngine();
    RandomMovieGenerator randomMovieGenerator = new RandomMovieGenerator();

    MovieController controller =
        new MovieController(catalog, movieFilter, statisticsEngine, randomMovieGenerator);

    MainFrame mainFrame = new MainFrame(controller);
    mainFrame.start();
  }
}