package student.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import student.controller.MovieController;
import student.model.Movie;

/**
 * This class is the left side panel with the search field, filter drop downs,
 * and the Add Movie also the  Pick Random Movie buttons.
 */
public class ControlPanel extends JPanel {

  /** The space between items in the control panel in pixels. */
  private static final int GAP = 12;
  /** How wide the search field, drop downs, and buttons should be. */
  private static final int FIELD_WIDTH = 220;
  /** How tall the search field, drop downs, and buttons should be. */
  private static final int FIELD_HEIGHT = 30;

  /** The controller this panel asks to do tasks when a button is clicked. */
  private final MovieController controller;
  /** The panel that shows the movie cards, so we can update it after a search. */
  private final MoviePanel moviePanel;
  /** This is for where the user types part of a movie title to search for. */
  private JTextField searchField;
  /** Drop down to filter by genre. */
  private JComboBox<String> genreFilter;
  /** Drop down to filter by rating. */
  private JComboBox<String> ratingFilter;


  /**
   * This builds the control panel and wires its buttons to the controller.
   *
   * @param controller the controller to call when a button is clicked
   * @param moviePanel the panel to update with new results
   * @param statisticsPanel the panel to update with new stats (not used yet)
   */
  public ControlPanel(MovieController controller, MoviePanel moviePanel,
      StatisticsPanel statisticsPanel) {
    this.controller = controller;
    this.moviePanel = moviePanel;

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(new RoundedBorder(getForeground()));

    add(createCenteredLabel("ControlPanel"));
    add(Box.createVerticalStrut(GAP));

    searchField = new JTextField("Search title ..");
    fixSize(searchField);
    add(searchField);
    add(Box.createVerticalStrut(GAP));

    add(createCenteredLabel("Filters"));
    add(Box.createVerticalStrut(GAP));

    genreFilter = new JComboBox<>(new String[] {"Genre: All"});
    fixSize(genreFilter);
    add(genreFilter);
    add(Box.createVerticalStrut(GAP));

    ratingFilter = new JComboBox<>(new String[] {"Rating: All"});
    fixSize(ratingFilter);
    add(ratingFilter);
    add(Box.createVerticalStrut(GAP * 2));

    JButton addMovieButton = new JButton("Add Movie");
    fixSize(addMovieButton);
    add(addMovieButton);
    add(Box.createVerticalStrut(GAP));

    JButton randomMovieButton = new JButton("Pick Random Movie");
    fixSize(randomMovieButton);
    add(randomMovieButton);

    // Here we link the search field so pressing Enter runs a search
    searchField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleSearch();
      }
    });

    addMovieButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleAddMovie();
      }
    });

    randomMovieButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleRandomMovie();
      }
    });
  }

  /**
   * This gives a component the same fixed width and height, and centers
   * it in the panel, so the search field, drop downs, and buttons all
   * end up matching in size.
   *
   * @param component the component to resize
   */
  private void fixSize(Component component) {
    Dimension size = new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
    component.setPreferredSize(size);
    component.setMinimumSize(size);
    component.setMaximumSize(size);
    ((javax.swing.JComponent) component).setAlignmentX(Component.CENTER_ALIGNMENT);
  }

  /**
   * This method is called when the user searches. It asks the controller for
   * matching movies and shows them in the movie panel.
   */
  private void handleSearch() {
    String query = searchField.getText();
    List<Movie> results = controller.searchMovies(query);
    moviePanel.showMovies(results);
  }

  /**
   * This method is called when Add Movie is clicked. This is temporary for now,
   * we will fill it in once we build a way to enter movie details.
   */
  private void handleAddMovie() {
    // This will be filled in once we design an add movie form out completely.
  }

  /**
   * This is called when Pick Random Movie is clicked. This is a stub for
   * now, since RandomMovieGenerator is not built yet.
   */
  private void handleRandomMovie() {
    //This will be filled in once RandomMovieGenerator is built completely
  }

  /**
   * This makes a JLabel with its text centered, so it looks like our wireframe.
   *
   * @param text the text to show
   * @return a centered JLabel
   */
  private JLabel createCenteredLabel(String text) {
    JLabel label = new JLabel(text);
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    return label;
  }
}