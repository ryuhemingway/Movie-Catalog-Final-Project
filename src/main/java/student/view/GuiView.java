package student.view;

import java.util.List;
import student.controller.IMovieController;
import student.model.Movie;

/**
 * This class wraps MainFrame so the GUI can be started through the
 * same IView interface as CommandLineView.
 */
public class GuiView implements IView {

  /** How many movies to show right when the GUI first opens. */
  private static final int INITIAL_MOVIE_COUNT = 20;

  /** The controller the GUI will use. */
  private final IMovieController controller;

  /**
   * This builds a GUI view using the given controller.
   *
   * @param controller the controller the GUI will use
   */
  public GuiView(IMovieController controller) {
    this.controller = controller;
  }

  @Override
  public void start() {
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
