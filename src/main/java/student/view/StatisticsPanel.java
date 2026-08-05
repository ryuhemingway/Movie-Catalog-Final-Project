package student.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import student.controller.MovieController;
import student.model.Movie;
import java.util.ArrayList;

/**
 * This class shows the average rating statistics at the bottom of the GUI window.
 */
public class StatisticsPanel extends JPanel {

  /** How tall this panel should be, in pixels, to leave room for a chart. */
  private static final int PANEL_HEIGHT = 200;
  /** The controller this panel asks for statistics data. */
  private final MovieController controller;
  /** The panel that shows movie cards, so top rated results can appear there too. */
  private final MoviePanel moviePanel;
  /** The chart showing whichever data was last requested. */
  private final BarChartPanel chartPanel;
  /** Drop down to choose which genre to find top rated movies in. */
  private JComboBox<String> genreDropdown;

  /**
   * This builds the statistics panel and wires its buttons to the controller.
   *
   * @param controller the controller to ask for statistics data
   * @param moviePanel the panel to update with top rated movie results
   */
  public StatisticsPanel(MovieController controller, MoviePanel moviePanel) {
    this.controller = controller;
    this.moviePanel = moviePanel;

    setLayout(new BorderLayout());
    setBorder(new RoundedBorder(getForeground()));
    setPreferredSize(new java.awt.Dimension(0, PANEL_HEIGHT));

    JLabel titleLabel = new JLabel("StatisticsPanel", SwingConstants.CENTER);
    add(titleLabel, BorderLayout.NORTH);

    // This row holds both buttons and the genre dropdown, side by side
    JPanel controlsRow = new JPanel();

    JButton averageByGenreButton = new JButton("Show average rating by genre");
    controlsRow.add(averageByGenreButton);

    // A small vertical line just to visually separate the two actions
    controlsRow.add(new JSeparator(SwingConstants.VERTICAL));

    // "Genre: All" always goes first, then every real genre sorted alphabetically
    List<String> genreOptions = new ArrayList<>();
    genreOptions.add("Genre: All");

    Set<String> genreSet = new TreeSet<>();
    List<Movie> allMovies = controller.getAllMovies();
    for (Movie movie : allMovies) {
      for (String genre : movie.getGenres()) {
        genreSet.add(genre);
      }
    }
    genreOptions.addAll(genreSet);

    genreDropdown = new JComboBox<>(genreOptions.toArray(new String[0]));
    controlsRow.add(genreDropdown);

    JButton topRatedButton = new JButton("Show Top Rated Movies");
    controlsRow.add(topRatedButton);

    add(controlsRow, BorderLayout.CENTER);

    chartPanel = new BarChartPanel();
    add(chartPanel, BorderLayout.SOUTH);

    // Here we link each button to its own handler method
    averageByGenreButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleAverageByGenre();
      }
    });

    topRatedButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleTopRated();
      }
    });
  }

  /**
   * This is called when Show average rating by genre is clicked. It will
   * ask the controller for the average rating per genre and chart it,
   * once StatisticsEngine supports this.
   */
  private void handleAverageByGenre() {
    // Needs the MovieController.getAverageRatingByGenre() -> Map<String, Double>
    // chartPanel.setData(controller.getAverageRatingByGenre());
  }

  /**
   * This method will be called when Show Top Rated Movies is clicked. It will ask the
   * controller for the top rated movies in the selected genre, chart
   * their ratings, and also show them as cards in the movie panel, once
   * MovieController supports this.
   */
  private void handleTopRated() {
    // Needs the MovieController.getTopRatedMovies(genre, count) -> List<Movie>
    // String genre = (String) genreDropdown.getSelectedItem();
    // List<Movie> topMovies = controller.getTopRatedMovies(genre, 4);
    // moviePanel.showMovies(topMovies);
    //
    // Map<String, Double> chartData = new LinkedHashMap<>();
    // for (Movie movie : topMovies) {
    //   chartData.put(movie.getTitle(), movie.getRating());
    // }
    // chartPanel.setData(chartData);
  }
}