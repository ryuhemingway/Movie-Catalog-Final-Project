package student.view;

import java.awt.BorderLayout;
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
import student.controller.MovieController;
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
   * works out the average rating per genre and charts it.
   *
   * NOTE: this should move into StatisticsEngine once it's built, this
   * is just here for now so the button does something.
   */
  private void handleAverageByGenre() {
    // these 3 lists line up together: genreNames.get(i) matches
    // genreSums.get(i) and genreCounts.get(i)
    List<String> genreNames = new ArrayList<>();
    List<Double> genreSums = new ArrayList<>();
    List<Integer> genreCounts = new ArrayList<>();

    for (Movie movie : controller.getAllMovies()) {
      for (String genre : movie.getGenres()) {
        int index = genreNames.indexOf(genre);

        if (index == -1) {
          // haven't seen this genre yet, add it fresh
          genreNames.add(genre);
          genreSums.add(movie.getRating());
          genreCounts.add(1);
        } else {
          // already have this genre, just add to the totals
          double newSum = genreSums.get(index) + movie.getRating();
          genreSums.set(index, newSum);

          int newCount = genreCounts.get(index) + 1;
          genreCounts.set(index, newCount);
        }
      }
    }

    // turn each genre's total and count into its average
    Map<String, Double> averages = new LinkedHashMap<>();
    for (int i = 0; i < genreNames.size(); i++) {
      double average = genreSums.get(i) / genreCounts.get(i);
      averages.put(genreNames.get(i), average);
    }

    chartPanel.setData(averages);
  }

  /**
   * This is called when Show Top Rated Movies is clicked. It gets the
   * movies in the selected genre, picks out the highest rated ones,
   * charts them, and shows them as cards too.
   */
  private void handleTopRated() {
    String genre = (String) genreDropdown.getSelectedItem();

    // "Genre: All" means skip genre filtering
    List<Movie> candidates;
    if (genre.equals("Genre: All")) {
      candidates = controller.getAllMovies();
    } else {
      candidates = controller.filterMoviesByGenre(genre);
    }

    // copy the list so we can remove from it without messing with
    // whatever the controller gave us
    List<Movie> remaining = new ArrayList<>(candidates);
    List<Movie> topMovies = new ArrayList<>();

    int count = Math.min(TOP_RATED_COUNT, remaining.size());
    for (int i = 0; i < count; i++) {
      // find the best rated one still left
      Movie best = remaining.get(0);
      for (Movie movie : remaining) {
        if (movie.getRating() > best.getRating()) {
          best = movie;
        }
      }
      // save it and take it out so next loop finds the next best
      topMovies.add(best);
      remaining.remove(best);
    }

    moviePanel.showMovies(topMovies);

    // build the chart data, title mapped to its rating
    Map<String, Double> chartData = new LinkedHashMap<>();
    for (Movie movie : topMovies) {
      chartData.put(movie.getTitle(), movie.getRating());
    }
    chartPanel.setData(chartData);
  }
}
