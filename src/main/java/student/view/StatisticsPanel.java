package student.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import student.controller.IMovieController;
import student.model.Movie;

/**
 * This class shows movie statistics: a bar chart of average rating by
 * genre, or a bar chart of the top rated movies in a chosen genre.
 */
public class StatisticsPanel extends JPanel {

  /** How tall this panel should be, in pixels, to leave room for the chart. */
  private static final int PANEL_HEIGHT = 220;
  /** How many top rated movies we show at once. */
  private static final int TOP_RATED_COUNT = 10;

  /** The controller this panel asks for statistics data. */
  private final IMovieController controller;
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
  public StatisticsPanel(IMovieController controller, MoviePanel moviePanel) {
    this.controller = controller;
    this.moviePanel = moviePanel;

    setLayout(new BorderLayout());
    setBorder(new RoundedBorder(getForeground()));

    JLabel titleLabel = new JLabel("StatisticsPanel", SwingConstants.CENTER);
    add(titleLabel, BorderLayout.NORTH);

    // this row holds both buttons and the genre dropdown, side by side
    JPanel controlsRow = new JPanel();

    JButton averageByGenreButton = new JButton("Show average rating by genre");
    controlsRow.add(averageByGenreButton);

    // small vertical line to separate the two actions visually
    controlsRow.add(new JSeparator(SwingConstants.VERTICAL));

    // "Genre: All" goes first, then every real genre found in the data,
    // sorted alphabetically
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

    JButton topRatedButton = new JButton("Show Top 10 Rated Movies");
    controlsRow.add(topRatedButton);

    add(controlsRow, BorderLayout.CENTER);

    chartPanel = new BarChartPanel();
    add(chartPanel, BorderLayout.SOUTH);

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
   * This is called when Show average rating by genre is clicked. It
   * asks the controller for the average rating per genre, sorts the
   * results highest to lowest, and charts them.
   */
  private void handleAverageByGenre() {
    Map<String, Double> averages = controller.getAverageRatingByGenre();
    Map<String, Double> sortedAverages = sortByValueDescending(averages);

    chartPanel.setData(sortedAverages);
  }

  /**
   * This method takes a map of genre names to average ratings and returns a
   * new map with the same entries, but ordered from the highest
   * rating to the lowest.
   *
   * @param unsorted the genre averages in whatever order they came in
   * @return the same genre averages, ordered highest rating first
   */
  private Map<String, Double> sortByValueDescending(Map<String, Double> unsorted) {
    // we copy the entries into a plain list first, so we can search
    // through it and remove from it as we go
    List<Map.Entry<String, Double>> remainingEntries = new ArrayList<>(unsorted.entrySet());
    Map<String, Double> sorted = new LinkedHashMap<>();

    // keep finding whichever entry has the highest rating left, adding
    // it to the sorted map, and removing it, until none are left
    while (!remainingEntries.isEmpty()) {
      Map.Entry<String, Double> best = remainingEntries.get(0);

      for (Map.Entry<String, Double> entry : remainingEntries) {
        if (entry.getValue() > best.getValue()) {
          best = entry;
        }
      }

      sorted.put(best.getKey(), best.getValue());
      remainingEntries.remove(best);
    }

    return sorted;
  }

  /**
   * This is called when Show Top Rated Movies is clicked. It asks the
   * controller for the highest rated movies in the selected genre,
   * charts them, and shows them as cards too.
   */
  private void handleTopRated() {
    String genre = (String) genreDropdown.getSelectedItem();

    List<Movie> topMovies = controller.getTopRatedMovies(genre, TOP_RATED_COUNT);

    moviePanel.showMovies(topMovies);

    // build the chart data, title mapped to its rating
    Map<String, Double> chartData = new LinkedHashMap<>();
    for (Movie movie : topMovies) {
      chartData.put(movie.getTitle(), movie.getRating());
    }
    chartPanel.setData(chartData);
  }

  /**
   * This tells Swing how wide this panel wants to be, based on how
   * wide the chart currently is. Without this, the panel always
   * reports a width of 0, so the scroll pane around it never knows it
   * needs to scroll, and bars past the edge get cut off instead.
   *
   * @return the size this panel needs to fully show its chart
   */
  @Override
  public Dimension getPreferredSize() {
    int chartWidth = chartPanel.getPreferredSize().width;
    return new Dimension(chartWidth, PANEL_HEIGHT);
  }
}
