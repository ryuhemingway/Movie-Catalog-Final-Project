package student.view;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import student.controller.MovieController;

/**
 * This class is for the main window for CineVault. It holds the control panel on the
 * left, the movie panel in the middle, and the statistics panel at the bottom.
 */
public class MainFrame extends JFrame {


  /** The space between panels, in pixels. */
  private static final int GAP = 15;

  /**
   * This method builds the main window and adds all three panels to it.
   *
   * @param controller the controller this view will use to talk to the model
   */
  public MainFrame(MovieController controller) {
    super("CineVault");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout(GAP, GAP));
    setFocusable(true);

    // This adds some empty space around the edge of the whole window
    ((javax.swing.JPanel) getContentPane()).setBorder(
        BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));

    MoviePanel moviePanel = new MoviePanel();
    StatisticsPanel statisticsPanel = new StatisticsPanel(controller, moviePanel);
    ControlPanel controlPanel = new ControlPanel(controller, moviePanel, statisticsPanel);
    add(controlPanel, BorderLayout.WEST);
    add(moviePanel, BorderLayout.CENTER);
    add(statisticsPanel, BorderLayout.SOUTH);

    // This resizes the window to exactly fit everything we just added,
    // instead of guessing a fixed pixel size
    pack();
  }
  /**
   * This method will show the window on screen.
   */
  public void start() {
    setVisible(true);

    // Without this, the search field grabs focus automatically on
    // startup of the gui, which clears its placeholder text before we even see it.
    // Moving focus to the frame itself instead keeps the placeholder text "Search title" showing.
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        requestFocusInWindow();
      }
    });
  }
}

