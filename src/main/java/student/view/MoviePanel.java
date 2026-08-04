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

    cardsPanel = new JPanel(new GridLayout(0, COLUMNS, GAP, GAP));
    add(cardsPanel, BorderLayout.CENTER);
  }

  /**
   * This method clears the panel and adds one card for each movie given.
   *
   * @param movies the movies to display
   */
  public void showMovies(List<Movie> movies) {
    removeAll();

    for (Movie movie : movies) {
      JPanel card = new JPanel(new GridLayout(2, 1));
      card.setBorder(BorderFactory.createLineBorder(getForeground()));
      card.add(new JLabel(movie.getTitle()));
      card.add(new JLabel(movie.getYear() + " - " + movie.getRating()));
      add(card);
    }

    revalidate();
    repaint();
  }
}