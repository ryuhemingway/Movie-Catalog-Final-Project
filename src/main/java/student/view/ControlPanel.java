package student.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import student.controller.MovieController;
import student.model.Movie;

/**
 * This class is the left side panel with the search field, filter drop downs,
 * and the Add Movie also the  Pick Random Movie buttons.
 */
public class ControlPanel extends JPanel {

  /** The controller this panel asks to do things when a button is clicked. */
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

    setLayout(new GridLayout(9, 1));

    add(new JLabel("ControlPanel"));

    searchField = new JTextField("Search title ..");
    add(searchField);

    add(new JLabel("Filters"));

    genreFilter = new JComboBox<>(new String[] {"Genre: All"});
    add(genreFilter);

    ratingFilter = new JComboBox<>(new String[] {"Rating: All"});
    add(ratingFilter);

    add(new JLabel("Buttons"));

    JButton addMovieButton = new JButton("Add Movie");
    add(addMovieButton);

    JButton randomMovieButton = new JButton("Pick Random Movie");
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
}