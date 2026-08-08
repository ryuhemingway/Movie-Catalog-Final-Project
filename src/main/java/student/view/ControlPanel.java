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
import student.controller.IMovieController;
import student.files.CsvFavoritesRepository;
import student.files.CsvWriter;
import student.files.IFavorites;
import student.model.Movie;
import student.model.RatingRange;

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
  /** Where our main movie list is stored on disk. */
  private static final String MOVIES_FILE_PATH = "src/main/resources/imdb_top_1000.csv";
  /** Where our favorites list is stored on disk. */
  private static final String FAVORITES_FILE_PATH = "src/main/resources/favorites.csv";

  /** How many fields a full new movie entry must have. */
  private static final int MOVIE_ENTRY_FIELD_COUNT = 9;
  /** How many fields a favorite update entry must have. */
  private static final int FAVORITE_UPDATE_FIELD_COUNT = 3;
  /** Index of the duration field in a new movie entry. */
  private static final int DURATION_INDEX = 2;
  /** Index of the rating field in a new movie entry. */
  private static final int RATING_INDEX = 3;
  /** Index of the genres field in a new movie entry. */
  private static final int GENRES_INDEX = 4;
  /** Index of the directors field in a new movie entry. */
  private static final int DIRECTORS_INDEX = 5;
  /** Index of the actors field in a new movie entry. */
  private static final int ACTORS_INDEX = 6;
  /** Index of the poster URL field in a new movie entry. */
  private static final int POSTER_URL_INDEX = 7;
  /** Index of the overview field in a new movie entry. */
  private static final int OVERVIEW_INDEX = 8;
  /** Index of the year field in a favorite update entry. */
  private static final int YEAR_INDEX = 1;
  /** Index of the rating field in a favorite update entry. */
  private static final int RATING_UPDATE_INDEX = 2;
  /** Index of the poster URL field in a favorites file row. */
  private static final int FAVORITE_POSTER_URL_INDEX = 3;

  /** The controller this panel asks to do tasks when a button is clicked. */
  private final IMovieController controller;
  /** The panel that shows the movie cards, so we can update it after a search. */
  private final MoviePanel moviePanel;
  /** This is for where the user types part of a movie title to search for. */
  private JTextField searchField;
  /** Drop down to filter by genre. */
  private JComboBox<String> genreFilter;
  /** Drop down to filter by rating range. */
  private JComboBox<String> ratingFilter;
  /** This is for where the user types to search, add, or delete favorites. */
  private JTextField favoritesSearchField;
  /** This holds our current list of favorite movies. */
  private List<Movie> currentFavorites;
  /** This loads and saves our favorites, so both the GUI and command
   * line share the same favorites logic instead of each having a copy. */
  private final IFavorites favoritesRepository;

  /**
   * This builds the control panel and wires its buttons to the controller.
   *
   * @param controller the controller to call when a button is clicked
   * @param moviePanel the panel to update with new results
   * @param statisticsPanel the panel to update with new stats (not used yet)
   */
  public ControlPanel(IMovieController controller, MoviePanel moviePanel,
      StatisticsPanel statisticsPanel) {
    this.controller = controller;
    this.moviePanel = moviePanel;
    this.favoritesRepository = new CsvFavoritesRepository(FAVORITES_FILE_PATH);
    this.currentFavorites = favoritesRepository.loadFavorites();

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(new RoundedBorder(getForeground()));

    buildTitleLabel();
    buildSearchField();
    buildFilterSection();
    buildFavoritesActionButtons();
    buildFavoritesListSection();
    buildRandomMovieButton();
  }

  /**
   * This adds the title label at the top of the panel.
   */
  private void buildTitleLabel() {
    add(createCenteredLabel("ControlPanel"));
    add(Box.createVerticalStrut(GAP));
  }

  /**
   * This builds the main search field, used both for finding a movie by
   * title, and for typing a full new movie entry when nothing is found.
   */
  private void buildSearchField() {
    searchField = new JTextField("Search title ..");
    fixSize(searchField);
    addPlaceholderBehavior(searchField, "Search title ..");
    add(searchField);
    add(Box.createVerticalStrut(GAP));

    searchField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleSearch();
      }
    });
  }

  /**
   * This builds the genre and rating drop downs, plus the button to
   * actually run the filter.
   */
  private void buildFilterSection() {
    add(createCenteredLabel("Filters"));
    add(Box.createVerticalStrut(GAP));

    List<String> genreOptions = buildGenreOptions();
    genreFilter = new JComboBox<>(genreOptions.toArray(new String[0]));
    fixSize(genreFilter);
    add(genreFilter);
    add(Box.createVerticalStrut(GAP));

// These are rating ranges, so every movie falls into exactly one option
    ratingFilter = new JComboBox<>(RatingRange.allLabels());
    fixSize(ratingFilter);
    add(ratingFilter);
    add(Box.createVerticalStrut(GAP));

    JButton filterMoviesButton = new JButton("Filter Movies");
    fixSize(filterMoviesButton);
    add(filterMoviesButton);
    add(Box.createVerticalStrut(GAP));

    filterMoviesButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleFilterMovies();
      }
    });
  }

  /**
   * This builds the list of genre options for the drop down: "Genre: All"
   * first, then every real genre found in the data, sorted alphabetically.
   *
   * @return the genre options to show in the drop down
   */
  private List<String> buildGenreOptions() {
    List<String> genreOptions = new ArrayList<>();
    genreOptions.add("Genre: All");

    Set<String> genreSet = new TreeSet<>();
    for (Movie movie : controller.getAllMovies()) {
      for (String genre : movie.getGenres()) {
        genreSet.add(genre);
      }
    }
    genreOptions.addAll(genreSet);
    return genreOptions;
  }

  /**
   * This builds the Add To Favorites and Add New Movie To List buttons.
   */
  private void buildFavoritesActionButtons() {
    // This button lets the user save whatever movie is currently shown
    // to their favorites list
    JButton addToFavoritesButton = new JButton("Add To Favorites");
    fixSize(addToFavoritesButton);
    add(addToFavoritesButton);
    add(Box.createVerticalStrut(GAP * 2));

    // Entering a brand new movie now reuses the search field above, so
    // we only need this one button here to actually save it
    JButton addNewMovieButton = new JButton("Add New Movie To List");
    fixSize(addNewMovieButton);
    add(addNewMovieButton);
    add(Box.createVerticalStrut(GAP * 2));

    addToFavoritesButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleAddToFavorites();
      }
    });

    addNewMovieButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleAddNewMovieToList();
      }
    });
  }

  /**
   * This builds the favorites list section: loading it, and searching,
   * updating, or deleting using the box below.
   */
  private void buildFavoritesListSection() {
    JButton loadFavoritesButton = new JButton("Load Favorites");
    fixSize(loadFavoritesButton);
    add(loadFavoritesButton);
    add(Box.createVerticalStrut(GAP));

    favoritesSearchField = new JTextField("Search Favorites ...");
    fixSize(favoritesSearchField);
    addPlaceholderBehavior(favoritesSearchField, "Search Favorites ...");
    add(favoritesSearchField);
    add(Box.createVerticalStrut(GAP));

    // Update and Delete sit side by side, so we use a small row panel
    // instead of stacking them like everything else
    JPanel favoritesButtonsRow = new JPanel(new GridLayout(1, 2, GAP, 0));
    JButton updateFavoriteButton = new JButton("Update");
    JButton deleteFavoriteButton = new JButton("Delete");
    favoritesButtonsRow.add(updateFavoriteButton);
    favoritesButtonsRow.add(deleteFavoriteButton);
    Dimension rowSize = new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
    favoritesButtonsRow.setPreferredSize(rowSize);
    favoritesButtonsRow.setMinimumSize(rowSize);
    favoritesButtonsRow.setMaximumSize(rowSize);
    favoritesButtonsRow.setAlignmentX(Component.CENTER_ALIGNMENT);
    add(favoritesButtonsRow);
    add(Box.createVerticalStrut(GAP * 2));

    loadFavoritesButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleLoadFavorites();
      }
    });

    favoritesSearchField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleSearchFavorites();
      }
    });

    updateFavoriteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleUpdateFavorite();
      }
    });

    deleteFavoriteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleDeleteFavorite();
      }
    });
  }

  /**
   * This builds the Pick Random Movie button.
   */
  private void buildRandomMovieButton() {
    JButton randomMovieButton = new JButton("Pick Random Movie");
    fixSize(randomMovieButton);
    add(randomMovieButton);

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
   * This will split a comma list like "Action, Sci-Fi" into separate strings.
   * Same idea as how CsvLoader splits genres.
   *
   * @param text the comma separated text to split
   * @return a list of the separate values, empty ones skipped
   */
  private List<String> splitCommaList(String text) {
    List<String> result = new ArrayList<>();
    // split on comma plus any spaces after it
    String[] parts = text.split(",\\s*");
    for (String part : parts) {
      // skip blanks, like if there's an extra comma at the end
      if (!part.isEmpty()) {
        result.add(part);
      }
    }
    return result;
  }

  /**
   * This method is called when the user searches. If a movie is found, we
   * show it. If not, we show a message telling the user how to add it
   * as a new movie using this same search box.
   */
  private void handleSearch() {
    String query = searchField.getText();
    if (query.equals("Search title ..")) {
      return;
    }

    List<Movie> results = controller.searchMovies(query);

    if (results.isEmpty()) {
      // nothing found, so tell the user how to add it themselves
      moviePanel.showMessage(
          "Movie not found. To add a new movie, type its title; year; "
              + "duration; rating; genres; directors; actors; poster URL; overview "
              + "(separated by semicolons) into this box, then click "
              + "Add New Movie To List.");
    } else {
      moviePanel.showMovies(results);
    }
  }

  /**
   * This is called when Filter Movies is clicked. It asks the controller
   * for movies matching both the genre and rating dropdowns and shows
   * them in the movie panel.
   */
  private void handleFilterMovies() {
    String selectedGenre = (String) genreFilter.getSelectedItem();
    String selectedRatingLabel = (String) ratingFilter.getSelectedItem();
    RatingRange ratingRange = RatingRange.fromLabel(selectedRatingLabel);

    List<Movie> results = controller.filterMovies(
        selectedGenre, ratingRange.getMinRating(), ratingRange.getMaxRating());
    moviePanel.showMovies(results);
  }

  /**
   * This is called when Add To Favorites is clicked. It saves every
   * movie currently shown to our favorites list, skipping any that are
   * already there.
   */
  private void handleAddToFavorites() {
    List<Movie> moviesShown = moviePanel.getCurrentMovies();

    if (moviesShown.isEmpty()) {
      moviePanel.showMessage("No movies to add, please search for some first");
      return;
    }

    int addedCount = 0;

    // go through every movie currently shown, not just the first one
    for (Movie movie : moviesShown) {
      if (!currentFavorites.contains(movie)) {
        currentFavorites.add(movie);
        addedCount++;
      }
    }

    // ask our favorites repository to save the favorites file for us
    favoritesRepository.saveFavorites(currentFavorites);

    if (addedCount == 0) {
      moviePanel.showMessage("All of these movies are already in your favorites");
    } else {
      moviePanel.showMessage("Added " + addedCount + " movie(s) to favorites");
    }
  }

  /**
   * This is called when Add New Movie To List is clicked. It reads the
   * full movie entry the user typed into the search box, adds it to the
   * catalog, and saves the whole list back out to our CSV file.
   */
  private void handleAddNewMovieToList() {
    String text = searchField.getText();
    String[] parts = text.split(";");

    // we need all 9 pieces of movie info or we can't build a Movie
    if (parts.length < MOVIE_ENTRY_FIELD_COUNT) {
      moviePanel.showMessage(
          "Please enter all 9 movie details separated by semicolons: "
              + "title; year; duration; rating; genres; directors; actors; poster URL; overview");
      return;
    }

    try {
      String title = parts[0].trim();
      int year = Integer.parseInt(parts[1].trim());
      int duration = Integer.parseInt(parts[DURATION_INDEX].trim());
      double rating = Double.parseDouble(parts[RATING_INDEX].trim());
      List<String> genres = splitCommaList(parts[GENRES_INDEX].trim());
      List<String> directors = splitCommaList(parts[DIRECTORS_INDEX].trim());
      List<String> actors = splitCommaList(parts[ACTORS_INDEX].trim());
      String posterUrl = parts[POSTER_URL_INDEX].trim();
      String overview = parts[OVERVIEW_INDEX].trim();

      // build the movie the same way CsvLoader does
      Movie newMovie = new Movie.Builder(title, year)
          .duration(duration)
          .rating(rating)
          .genres(genres)
          .directors(directors)
          .actors(actors)
          .posterUrl(posterUrl)
          .overview(overview)
          .build();

      // Movie counts two movies as the same if they share a title and
      // year, so we check that before adding anything
      if (controller.getAllMovies().contains(newMovie)) {
        moviePanel.showMessage(
            "This movie already exists in our list: " + title + " (" + year + ")");
        return;
      }

      controller.addMovie(newMovie);

      // this saves the whole catalog back out to our CSV file, so the
      // new movie is still there next time we run the program
      CsvWriter csvWriter = new CsvWriter();
      csvWriter.writeMoviesFile(MOVIES_FILE_PATH, controller.getAllMovies());

      moviePanel.showMovies(List.of(newMovie));
    } catch (NumberFormatException e) {
      moviePanel.showMessage("Please enter valid numbers for year, duration, and rating");
    }
  }

  /**
   * This is called when Load Favorites is clicked. It reads our
   * favorites file and shows them in the movie panel.
   */
  private void handleLoadFavorites() {
    // ask our favorites repository to load the favorites file for us
    currentFavorites = favoritesRepository.loadFavorites();
    moviePanel.showMovies(currentFavorites);
  }

  /**
   * This is called when the user presses Enter in the favorites search
   * box. It looks through our current favorites for any titles that
   * contain what was typed, and shows the matches.
   */
  private void handleSearchFavorites() {
    String query = favoritesSearchField.getText();
    if (query.equals("Search Favorites ...") || query.isEmpty()) {
      return;
    }

    // just the title part, in case the user typed title;year;rating
    String titleQuery = query.split(";")[0].trim().toLowerCase();

    List<Movie> matches = new ArrayList<>();
    for (Movie movie : currentFavorites) {
      if (movie.getTitle().toLowerCase().contains(titleQuery)) {
        matches.add(movie);
      }
    }

    if (matches.isEmpty()) {
      moviePanel.showMessage(
          "No favorite found matching \"" + titleQuery
              + "\". Click Load Favorites first if you haven't yet.");
    } else {
      moviePanel.showMovies(matches);
    }
  }

  /**
   * This is called when Update is clicked. It only updates the rating of
   * a favorite that already exists (matching title and year). If nothing
   * matches, we tell the user how to enter it correctly instead of
   * adding a brand new favorite by mistake.
   */
  private void handleUpdateFavorite() {
    String text = favoritesSearchField.getText();
    if (text.equals("Search Favorites ...") || text.isEmpty()) {
      return;
    }

    String[] parts = text.split(";");
    if (parts.length < FAVORITE_UPDATE_FIELD_COUNT) {
      moviePanel.showMessage("Please enter title; year; rating to update a favorite");
      return;
    }

    try {
      String title = parts[0].trim();
      int year = Integer.parseInt(parts[YEAR_INDEX].trim());
      double newRating = Double.parseDouble(parts[RATING_UPDATE_INDEX].trim());

      // look for an existing favorite with the same title and year, so
      // we can carry over its poster instead of losing it
      Movie existing = null;
      for (Movie movie : currentFavorites) {
        if (movie.getTitle().equalsIgnoreCase(title) && movie.getYear() == year) {
          existing = movie;
        }
      }

      if (existing == null) {
        // nothing matched, so we tell the user instead of adding a new one
        moviePanel.showMessage(
            "No favorite found matching \"" + title + "\" (" + year + "). "
                + "To update a favorite, click Load Favorites first, check the exact "
                + "title and year shown there, then enter title; year; desired rating again.");
        return;
      }

      currentFavorites.remove(existing);
      Movie updatedMovie = new Movie.Builder(title, year)
          .rating(newRating)
          .posterUrl(existing.getPosterUrl())
          .build();
      currentFavorites.add(updatedMovie);
      // ask our favorites repository to save the favorites file for us
      favoritesRepository.saveFavorites(currentFavorites);
      moviePanel.showMovies(List.of(updatedMovie));
    } catch (NumberFormatException e) {
      moviePanel.showMessage("Please enter a valid year and rating");
    }
  }

  /**
   * This is called when Delete is clicked. It removes the favorite that
   * matches the title and year typed into the favorites box. Only
   * title and year are needed, not a rating. Typing "Delete All" instead
   * clears out every favorite at once.
   */
  private void handleDeleteFavorite() {
    String text = favoritesSearchField.getText();
    if (text.equals("Search Favorites ...") || text.isEmpty()) {
      return;
    }

    if (text.trim().equalsIgnoreCase("Delete All")) {
      currentFavorites = new ArrayList<>();
      // ask our favorites repository to save the favorites file for us
      favoritesRepository.saveFavorites(currentFavorites);
      moviePanel.showMessage("All favorites have been deleted");
      return;
    }

    String[] parts = text.split(";");

    // we only want exactly 2 fields here: title and year
    if (parts.length != 2) {
      moviePanel.showMessage(
          "To delete one movie from favorites, please enter only the title and year, "
              + "separated by a semicolon, for example: Movie Title; 2019. "
              + "To delete all movies, type Delete All and then click Delete.");
      return;
    }

    String titleToRemove = parts[0].trim();
    int yearToRemove;
    try {
      yearToRemove = Integer.parseInt(parts[1].trim());
    } catch (NumberFormatException e) {
      moviePanel.showMessage("Please enter a valid year for the existing favorite movie");
      return;
    }

    List<Movie> updatedFavorites = new ArrayList<>();
    boolean removedOne = false;
    for (Movie movie : currentFavorites) {
      if (movie.getTitle().equalsIgnoreCase(titleToRemove) && movie.getYear() == yearToRemove) {
        removedOne = true;
      } else {
        updatedFavorites.add(movie);
      }
    }

    currentFavorites = updatedFavorites;
    // ask our favorites repository to save the favorites file for us
    favoritesRepository.saveFavorites(currentFavorites);

    if (removedOne) {
      moviePanel.showMessage(
          "Removed from favorites: " + titleToRemove + " (" + yearToRemove + ")");
    } else {
      moviePanel.showMessage("Could not find the favorite movie to delete");
    }
  }

  /**
   * This is called when Pick Random Movie is clicked. It asks the
   * controller for a random movie in the selected genre and shows it.
   */
  private void handleRandomMovie() {
    String selectedGenre = (String) genreFilter.getSelectedItem();
    String selectedRatingLabel = (String) ratingFilter.getSelectedItem();
    RatingRange ratingRange = RatingRange.fromLabel(selectedRatingLabel);

    // this will now work with just a genre, just a rating range, both, or
    // neither, since "Genre: All" skips genre filtering on its own
    Movie randomMovie = controller.getRandomMovie(
        selectedGenre, ratingRange.getMinRating(), ratingRange.getMaxRating());

    if (randomMovie != null) {
      moviePanel.showMovies(List.of(randomMovie));
    } else {
      moviePanel.showMessage("No movie found matching that genre and rating range");
    }
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
