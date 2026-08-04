package student.view;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import student.model.Movie;

/**
 * This class will shows the current list of movies as a grid of cards, each one
 * showing the title, year, and rating.
 */
public class MoviePanel extends JPanel {

  /** This will be how many columns of movie cards to show per row. */
  private static final int COLUMNS = 3;

  /**
   * This method will build an empty movie panel.
   */
  public MoviePanel() {
    setLayout(new GridLayout(0, COLUMNS));
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