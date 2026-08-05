package student.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
 * favorites controls, add movie controls, and the Pick Random Movie button.
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
  /** Drop down to filter by rating range. */
  private JComboBox<String> ratingFilter;
  /** This is for where the user types part of a favorite movie title to search for. */
  private JTextField favoritesSearchField;

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

    // This is the title at the top of the panel
    add(createCenteredLabel("ControlPanel"));
    add(Box.createVerticalStrut(GAP));

// This is the main search field for finding a movie by title
    searchField = new JTextField("Search title ..");
    fixSize(searchField);
    addPlaceholderBehavior(searchField, "Search title ..");
    add(searchField);
    add(Box.createVerticalStrut(GAP));

    // This section holds the genre and rating filters, plus the button
    // to actually run the filter
    add(createCenteredLabel("Filters"));
    add(Box.createVerticalStrut(GAP));

    // "Genre: All" goes first, then every real genre found in the data,
    // sorted alphabetically
    List<String> genreOptions = new ArrayList<>();
    genreOptions.add("Genre: All");

    Set<String> genreSet = new TreeSet<>();
    for (Movie movie : controller.getAllMovies()) {
      for (String genre : movie.getGenres()) {
        genreSet.add(genre);
      }
    }
    genreOptions.addAll(genreSet);

    genreFilter = new JComboBox<>(genreOptions.toArray(new String[0]));
    fixSize(genreFilter);
    add(genreFilter);
    add(Box.createVerticalStrut(GAP));

    // These are rating ranges, so every movie falls into exactly one option
    String[] ratingOptions = {
        "Rating: All",
        "9.0 and above",
        "8.0 - 8.9",
        "7.0 - 7.9",
        "6.0 - 6.9",
        "Below 6.0"
    };
    ratingFilter = new JComboBox<>(ratingOptions);
    fixSize(ratingFilter);
    add(ratingFilter);
    add(Box.createVerticalStrut(GAP));

    JButton filterMoviesButton = new JButton("Filter Movies");
    fixSize(filterMoviesButton);
    add(filterMoviesButton);
    add(Box.createVerticalStrut(GAP));

    // This button will let the user save the currently shown movie
    // to their favorites list
    JButton addToFavoritesButton = new JButton("Add To Favorites");
    fixSize(addToFavoritesButton);
    add(addToFavoritesButton);
    add(Box.createVerticalStrut(GAP * 2));

    // This section is for entering a brand new movie and adding it to
    // our existing CSV file
    JButton enterNewMovieButton = new JButton("Enter New Movie...");
    fixSize(enterNewMovieButton);
    add(enterNewMovieButton);
    add(Box.createVerticalStrut(GAP));

    JButton addNewMovieButton = new JButton("Add New Movie To List");
    fixSize(addNewMovieButton);
    add(addNewMovieButton);
    add(Box.createVerticalStrut(GAP * 2));

    // This section is for our favorites list: loading it, searching
    // through it, and adding or deleting movies from it
    JButton loadFavoritesButton = new JButton("Load Favorites");
    fixSize(loadFavoritesButton);
    add(loadFavoritesButton);
    add(Box.createVerticalStrut(GAP));

    favoritesSearchField = new JTextField("Search Favorites ...");
    fixSize(favoritesSearchField);
    addPlaceholderBehavior(favoritesSearchField, "Search Favorites ...");
    add(favoritesSearchField);
    add(Box.createVerticalStrut(GAP));

    // Add and Delete sit side by side, so we use a small row panel
    // instead of stacking them like everything else
    JPanel favoritesButtonsRow = new JPanel(new GridLayout(1, 2, GAP, 0));
    JButton addFavoriteButton = new JButton("Add");
    JButton deleteFavoriteButton = new JButton("Delete");
    favoritesButtonsRow.add(addFavoriteButton);
    favoritesButtonsRow.add(deleteFavoriteButton);
    Dimension rowSize = new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
    favoritesButtonsRow.setPreferredSize(rowSize);
    favoritesButtonsRow.setMinimumSize(rowSize);
    favoritesButtonsRow.setMaximumSize(rowSize);
    favoritesButtonsRow.setAlignmentX(Component.CENTER_ALIGNMENT);
    add(favoritesButtonsRow);
    add(Box.createVerticalStrut(GAP * 2));

    // This is the button for picking a random movie from the catalog
    JButton randomMovieButton = new JButton("Pick Random Movie");
    fixSize(randomMovieButton);
    add(randomMovieButton);

    // Below here we wire every button and the search field to its own
    // handler method, so clicking or pressing Enter calls the right code
    searchField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleSearch();
      }
    });

    filterMoviesButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleFilterMovies();
      }
    });

    addToFavoritesButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleAddToFavorites();
      }
    });

    enterNewMovieButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleEnterNewMovie();
      }
    });

    addNewMovieButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleAddNewMovieToList();
      }
    });

    loadFavoritesButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleLoadFavorites();
      }
    });

    addFavoriteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleAddFavorite();
      }
    });

    deleteFavoriteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleDeleteFavorite();
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
   * This clears a text field's placeholder text when clicked into, and
   * puts it back if the user clicks away without typing anything.
   *
   * @param field the text field to add this behavior to
   * @param placeholderText the placeholder text this field starts with
   */
  private void addPlaceholderBehavior(final JTextField field, final String placeholderText) {
    field.addFocusListener(new java.awt.event.FocusListener() {
      @Override
      public void focusGained(java.awt.event.FocusEvent e) {
        if (field.getText().equals(placeholderText)) {
          field.setText("");
        }
      }

      @Override
      public void focusLost(java.awt.event.FocusEvent e) {
        if (field.getText().isEmpty()) {
          field.setText(placeholderText);
        }
      }
    });
  }

  /**
   * This gives a component the same fixed width and height, and centers
   * it in the panel, so everything lines up consistently.
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
    if (query.equals("Search title ..")) {
      return;
    }
    List<Movie> results = controller.searchMovies(query);
    moviePanel.showMovies(results);
  }

  /**
   * This method is called when Filter Movies is clicked. Still a stub, this
   * needs MovieFilter to support both genre and rating range filtering.
   */
  private void handleFilterMovies() {
    // TODO: needs MovieFilter methods and a way to combine genre + rating
  }

  /**
   * This method is called when Add To Favorites is clicked. Still a stub, this
   * needs a favorites Catalog and CsvWriter to persist to its own file.
   */
  private void handleAddToFavorites() {
    // TODO: needs a favorites Catalog + CsvWriter persistence
  }

  /**
   * This method is called when Enter New Movie is clicked. Still a stub, this
   * will open a form for the user to type in a new movie's details.
   */
  private void handleEnterNewMovie() {
    // TODO: open a dialog with fields matching Movie's Builder
  }

  /**
   * This method is called when Add New Movie To List is clicked. Still a stub,
   * this needs CsvWriter persistence added to MovieController.
   */
  private void handleAddNewMovieToList() {
    // TODO: build a Movie from the entered fields, call controller.addMovie(),
    // then persist the whole catalog back to the CSV
  }

  /**
   * This method is called when Load Favorites is clicked. Still a stub, this
   * needs a favorites Catalog loaded from its own CSV file.
   */
  private void handleLoadFavorites() {
    // TODO: needs a favorites Catalog, then moviePanel.showMovies(favorites)
  }

  /**
   * This method is called when Add (within favorites) is clicked. Still a stub.
   */
  private void handleAddFavorite() {
    // TODO: needs a favorites Catalog
  }

  /**
   * This method is called when Delete (within favorites) is clicked. Still a stub.
   */
  private void handleDeleteFavorite() {
    // TODO: needs a favorites Catalog
  }

  /**
   * This method is called when Pick Random Movie is clicked. This is a stub for
   * now, since RandomMovieGenerator is not built yet.
   */
  private void handleRandomMovie() {
    // TODO: needs RandomMovieGenerator to be built
  }

  /**
   * This method makes a JLabel with its text centered, so it looks like our wireframe.
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