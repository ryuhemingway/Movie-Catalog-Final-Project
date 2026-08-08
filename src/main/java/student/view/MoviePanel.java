package student.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import student.model.Movie;
import java.util.ArrayList;

/**
 * This class will show the current list of movies as a grid of cards, each one
 * showing the title, year, and rating.
 */
public class MoviePanel extends JPanel {

  /** This will control how many columns of movie cards to show per row. */
  private static final int COLUMNS = 3;
  /** This will control the space between movie cards, in pixels. */
  private static final int GAP = 10;
  /** This is the inner panel that actually holds the movie cards. */
  private final JPanel cardsPanel;
  /** This keeps track of whatever movies are currently being shown, so
   * other panels can ask what's on screen right now. */
  private List<Movie> currentMovies;
  /** How wide the poster image should be, in pixels. */
  private static final int POSTER_WIDTH = 80;
  /** How tall the poster image should be, in pixels. */
  private static final int POSTER_HEIGHT = 120;
  /** How wide each movie card should be, in pixels. */
  private static final int CARD_WIDTH = 220;
  /** How tall each movie card should be, in pixels. */
  private static final int CARD_HEIGHT = 250;
  /** The most movies we'll ever try to display at once. */
  private static final int MAX_RESULTS = 40;
  /** The border padding around the title label, in pixels. */
  private static final int TITLE_PADDING = 10;
  /** How fast the scroll wheel moves the list, in pixels per click. */
  private static final int SCROLL_UNIT_INCREMENT = 16;
  /** How tall the scroll pane should be, in pixels. */
  private static final int SCROLL_PANE_HEIGHT = 500;
  /** How many rows the text under each poster uses (title, year, rating). */
  private static final int TEXT_ROWS = 3;

  /**
   * This method will build an empty movie panel with the title at the top.
   */
  public MoviePanel() {
    setLayout(new BorderLayout());
    setBorder(new RoundedBorder(getForeground()));

    JLabel titleLabel = new JLabel("MoviePanel", SwingConstants.CENTER);
    titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(
        TITLE_PADDING, TITLE_PADDING, TITLE_PADDING, TITLE_PADDING));
    add(titleLabel, BorderLayout.NORTH);

    // GridLayout(0, COLUMNS) means "figure out the rows on your own",
    // so the grid grows downward as we add more cards, instead of
    // being locked to a fixed number of slots
    cardsPanel = new JPanel(new GridLayout(0, COLUMNS, GAP, GAP));

// We put cardsPanel inside a wrapper anchored to the top, so it only
    // takes up as much height as it actually needs, instead of being
    // stretched to fill the whole scrollable area when there are few movies
    JPanel wrapperPanel = new JPanel(new BorderLayout());
    wrapperPanel.add(cardsPanel, BorderLayout.NORTH);

    // We put the wrapper inside a scroll pane so we can scroll up and
    // down when there are more movies than fit on screen
    JScrollPane scrollPane = new JScrollPane(wrapperPanel);
    scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_UNIT_INCREMENT);
    // We let a horizontal scrollbar show up if the window gets too
    // narrow, so cut off movies on the right can still be reached
    // instead of just disappearing
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // We give this a set preferred size up front, wide enough for a full
    // row of cards, so the window is packed with enough room from the
    // start instead of only sizing itself to whatever is shown at startup
    scrollPane.setPreferredSize(
        new java.awt.Dimension(COLUMNS * CARD_WIDTH, SCROLL_PANE_HEIGHT));

    add(scrollPane, BorderLayout.CENTER);

    currentMovies = new ArrayList<>();
  }

  /**
   * This method clears the panel and adds one card for each movie given.
   *
   * @param movies the movies to display
   */
  public void showMovies(List<Movie> movies) {
    // if there are way too many results that causes app
    // to crash, we will show a message that there are too many movies to show
    if (movies.size() > MAX_RESULTS) {
      showMessage(
          "Found " + movies.size() + " movies, that's too many to display at once. "
              + "Please narrow your search or filters (for example, pick a specific genre "
              + "or rating range) to see fewer than " + MAX_RESULTS + " results.");
      return;
    }

    // keep track of what's actually being shown right now in movie panel
    currentMovies = movies;
    // We reset the layout back to the grid here in case showMessage()
    // switched it to something else last time it was called
    cardsPanel.setLayout(new GridLayout(0, COLUMNS, GAP, GAP));
    cardsPanel.removeAll();

    for (Movie movie : movies) {
      JPanel card = new JPanel(new BorderLayout());
      card.setBorder(new RoundedBorder(getForeground()));
      // Fixing the card's size stops a long title from stretching this
      // card (and every other card in the same row) wider than expected
      java.awt.Dimension cardSize = new java.awt.Dimension(CARD_WIDTH, CARD_HEIGHT);
      card.setPreferredSize(cardSize);
      card.setMinimumSize(cardSize);
      card.setMaximumSize(cardSize);

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

      // This holds the title, year, and rating text under the poster
      JPanel textPanel = new JPanel(new GridLayout(TEXT_ROWS, 1));
      // Wrapping the title in html lets it wrap onto more than one line
      // instead of stretching the card to fit on one line
      JLabel titleLabel = new JLabel(
          "<html><div style='text-align:center; width:150px;'>"
              + movie.getTitle() + "</div></html>",
          SwingConstants.CENTER);
      textPanel.add(titleLabel);
      textPanel.add(new JLabel("Year: " + movie.getYear(), SwingConstants.CENTER));
      textPanel.add(new JLabel("Rating: " + movie.getRating(), SwingConstants.CENTER));
      card.add(textPanel, BorderLayout.CENTER);

      cardsPanel.add(card);
    }

    cardsPanel.revalidate();
    cardsPanel.repaint();
  }

  /**
   * This method clears the panel and shows a plain text message instead
   * of movie cards, like when a search finds nothing.
   *
   * @param message the message to show
   */
  public void showMessage(String message) {
    // no real movies are shown while a message is up
    currentMovies = new ArrayList<>();
    cardsPanel.removeAll();
    // We switch to a plain layout here since we just have one message,
    // not a grid of cards like showMovies() uses
    cardsPanel.setLayout(new BorderLayout());

    // Wrapping the text in html lets it wrap onto multiple lines instead
    // of running off the edge of the panel
    JLabel messageLabel = new JLabel(
        "<html><div style='text-align:center; width:400px;'>" + message + "</div></html>",
        SwingConstants.CENTER);
    cardsPanel.add(messageLabel, BorderLayout.CENTER);

    cardsPanel.revalidate();
    cardsPanel.repaint();
  }

  /**
   * This method returns whatever movies are currently being shown, no matter
   * which panel put them there.
   *
   * @return the movies currently displayed
   */
  public List<Movie> getCurrentMovies() {
    return currentMovies;
  }
}
