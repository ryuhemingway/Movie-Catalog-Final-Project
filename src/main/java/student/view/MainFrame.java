package student.view;
import student.model.Movie;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import student.controller.IMovieController;

/**
 * This class is for the main window for CineVault. It holds the control panel on the
 * left, the movie panel in the middle, and the statistics panel at the bottom.
 */
public class MainFrame extends JFrame {


  /** The space between panels, in pixels. */
  private static final int GAP = 15;
  /** How much extra width to leave room for, so the scrollbar doesn't
   * overlap the buttons and dropdowns. */
  private static final int SCROLL_BAR_WIDTH = 25;
  /** How fast the scroll wheel moves the panels, in pixels per click. */
  private static final int SCROLL_UNIT_INCREMENT = 16;
  /** The panel that shows movie cards, so we can update it from outside. */
  private final MoviePanel moviePanel;

  /**
   * This method builds the main window and adds all three panels to it.
   *
   * @param controller the controller this view will use to talk to the model
   */
  public MainFrame(IMovieController controller) {
    super("CineVault");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout(GAP, GAP));
    setFocusable(true);

    // This adds some empty space around the edge of the whole window
    ((javax.swing.JPanel) getContentPane()).setBorder(
        BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));

    moviePanel = new MoviePanel();
    StatisticsPanel statisticsPanel = new StatisticsPanel(controller, moviePanel);
    ControlPanel controlPanel = new ControlPanel(controller, moviePanel, statisticsPanel);

    // We wrap the control panel in a scroll pane, so if the window gets
    // too small, the user can scroll down to see the buttons at the
    // bottom instead of them just getting cut off
       // How much extra width to leave room for, so the scrollbar has its
    // own space instead of squeezing the buttons and dropdowns
    int scrollBarWidth = SCROLL_BAR_WIDTH;

    javax.swing.JScrollPane controlScrollPane = new javax.swing.JScrollPane(controlPanel);
    controlScrollPane.setBorder(BorderFactory.createEmptyBorder());
    controlScrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_UNIT_INCREMENT);
    controlScrollPane.setHorizontalScrollBarPolicy(
        javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    // We give the scroll pane a preferred size a little wider than the
    // control panel actually needs, so the scrollbar fits in that extra
    // space instead of cutting into the buttons and dropdowns
    int controlPanelWidth = controlPanel.getPreferredSize().width;
    int controlPanelHeight = controlPanel.getPreferredSize().height;
    controlScrollPane.setPreferredSize(
        new java.awt.Dimension(controlPanelWidth + scrollBarWidth, controlPanelHeight));

    // We do the same thing for the statistics panel, in case the chart
    // and buttons don't fit either
    javax.swing.JScrollPane statisticsScrollPane = new javax.swing.JScrollPane(statisticsPanel);
    statisticsScrollPane.setBorder(BorderFactory.createEmptyBorder());
    statisticsScrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_UNIT_INCREMENT);
    statisticsScrollPane.setHorizontalScrollBarPolicy(
        javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    add(controlScrollPane, BorderLayout.WEST);
    add(moviePanel, BorderLayout.CENTER);
    add(statisticsScrollPane, BorderLayout.SOUTH);

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

  /**
   * This shows a starting set of movies in the movie panel, so the
   * window isn't empty right when it opens.
   *
   * @param movies the movies to show first
   */
  public void showInitialMovies(java.util.List<Movie> movies) {
    moviePanel.showMovies(movies);
  }

}

