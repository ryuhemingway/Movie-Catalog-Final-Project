package student.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import student.controller.MovieController;

/**
 * This class is for the main window for CineVault. It holds the control panel on the
 * left, the movie panel in the middle, and the statistics panel at the bottom.
 */
public class MainFrame extends JFrame {

  /** The width of the GUI window in pixels. */
  private static final int WINDOW_WIDTH = 900;
  /** The height of the GUI window in pixels. */
  private static final int WINDOW_HEIGHT = 650;

  /**
   * This method builds the main window and adds all three panels to it.
   *
   * @param controller the controller this view will use to talk to the model
   */
  public MainFrame(MovieController controller) {
    super("CineVault");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setLayout(new BorderLayout());

    MoviePanel moviePanel = new MoviePanel();
    StatisticsPanel statisticsPanel = new StatisticsPanel();
    ControlPanel controlPanel = new ControlPanel(controller, moviePanel, statisticsPanel);

    add(controlPanel, BorderLayout.WEST);
    add(moviePanel, BorderLayout.CENTER);
    add(statisticsPanel, BorderLayout.SOUTH);
  }

  /**
   * This method will show the window on screen.
   */
  public void start() {
    setVisible(true);
  }
}
