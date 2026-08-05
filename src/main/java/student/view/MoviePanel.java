package student.view;
import student.model.Movie;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;





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
  /** How wide the poster image should be, in pixels. */
  private static final int POSTER_WIDTH = 80;
  /** How tall the poster image should be, in pixels. */
  private static final int POSTER_HEIGHT = 100;

  /**
   * This method will build an empty movie panel with the title at the top.
   */
  public MoviePanel() {
    setLayout(new BorderLayout());
    setBorder(new RoundedBorder(getForeground()));

    JLabel titleLabel = new JLabel("MoviePanel", SwingConstants.CENTER);
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
      JPanel card = new JPanel(new BorderLayout());
      card.setBorder(new RoundedBorder(getForeground()));

      // This tries to load and show the poster image. If it fails for
      // any reason (bad link, no internet, etc), we just skip the image
      // instead of crashing the whole card.
      JLabel posterLabel = new JLabel();
      posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
      try {
        ImageIcon rawIcon = new ImageIcon(new URL(movie.getPosterUrl()));
        Image scaledImage = rawIcon.getImage().getScaledInstance(
            POSTER_WIDTH, POSTER_HEIGHT, Image.SCALE_SMOOTH);
        posterLabel.setIcon(new ImageIcon(scaledImage));
      } catch (Exception e) {
        // If the poster can't be loaded, we just leave the label blank
        System.out.println("Could not load poster for " + movie.getTitle());
      }
      card.add(posterLabel, BorderLayout.NORTH);

      JPanel textPanel = new JPanel(new GridLayout(3, 1));
      textPanel.add(new JLabel(movie.getTitle(), SwingConstants.CENTER));
      textPanel.add(new JLabel("Year: " + movie.getYear(), SwingConstants.CENTER));
      textPanel.add(new JLabel("Rating: " + movie.getRating(), SwingConstants.CENTER));
      card.add(textPanel, BorderLayout.CENTER);

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