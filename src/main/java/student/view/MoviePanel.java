package student.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import student.model.Movie;



/**
 * This class will show the current list of movies as a grid of cards, each one
 * showing the title, year, and rating.
 */
public class MoviePanel extends JPanel {

  /** How many rows of movie cards to show at once. */
  private static final int ROWS = 2;
  /** This will control  many columns of movie cards to show per row. */
  private static final int COLUMNS = 3;
  /** This will control the space between movie cards, in pixels. */
  private static final int GAP = 10;
  /** This is the inner panel that actually holds the movie cards. */
  private final JPanel cardsPanel;

  /**
   * This method will build an empty movie panel with the title at the top.
   */
  public MoviePanel() {
    setLayout(new BorderLayout());
    setBorder(new RoundedBorder(getForeground()));

    JLabel titleLabel = new JLabel("MoviePanel");
    titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
    add(titleLabel, BorderLayout.NORTH);

    // We use a fixed 2x3 grid so a single movie result doesn't stretch to fill
    // the whole panel, keeping cards a consistent size like our wireframe.
    cardsPanel = new JPanel(new GridLayout(ROWS, COLUMNS, GAP, GAP));
    add(cardsPanel, BorderLayout.CENTER);
  }

  /**
   * This method clears the panel and adds one card for each movie given.
   *
   * @param movies the movies to display
   */
  public void showMovies(List<Movie> movies) {
    cardsPanel.removeAll();

    int maxVisible = ROWS * COLUMNS;
    int count = Math.min(movies.size(), maxVisible);

    for (int i = 0; i < count; i++) {
      Movie movie = movies.get(i);
      JPanel card = new JPanel(new GridLayout(3, 1));
      card.setBorder(new RoundedBorder(getForeground()));

      JLabel titleLabel = new JLabel(movie.getTitle(), SwingConstants.CENTER);
      JLabel yearLabel = new JLabel("Year: " + movie.getYear(), SwingConstants.LEFT);
      JLabel ratingLabel = new JLabel("Rating: " + movie.getRating(), SwingConstants.LEFT);

      card.add(titleLabel);
      card.add(yearLabel);
      card.add(ratingLabel);
      cardsPanel.add(card);
    }

    // We fill any leftover slots with empty panels so the grid stays
    // fixed at 2x3 even when there are fewer results than that.
    for (int i = count; i < maxVisible; i++) {
      cardsPanel.add(new JPanel());
    }

    cardsPanel.revalidate();
    cardsPanel.repaint();
  }
}