package student.view;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import student.controller.MovieController;
import student.files.CsvWriter;
import student.model.Movie;

/**
 * This class shows a text menu in the terminal and lets the user search,
 * filter, add movies, and manage favorites, using the same controller
 * the GUI uses.
 */
public class CommandLineView {

  /** Where our main movie list is stored on disk. */
  private static final String MOVIES_FILE_PATH = "src/main/resources/imdb_top_1000.csv";
  /** Where our favorites list is stored on disk. */
  private static final String FAVORITES_FILE_PATH = "src/main/resources/favorites.csv";
  /** How many fields a full new movie entry must have. */
  private static final int MOVIE_ENTRY_FIELD_COUNT = 9;
  /** The lowest possible movie rating. */
  private static final double MIN_RATING = 0.0;
  /** The highest possible movie rating. */
  private static final double MAX_RATING = 10.0;
  /** Index of the year field in a new movie entry. */
  private static final int YEAR_INDEX = 1;
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
  /** Index of the poster URL field in a favorites file row. */
  private static final int FAVORITE_POSTER_URL_INDEX = 3;

  /** The controller this view asks to do things when the user picks a menu option. */
  private final MovieController controller;
  /** Reads whatever the user types in the terminal. */
  private final Scanner scanner;
  /** This holds whatever movies were last shown, so we know what to add
   * to favorites when the user asks. */
  private List<Movie> lastShownMovies;
  /** This holds our current list of favorite movies. */
  private List<Movie> currentFavorites;

  /**
   * This builds a command line view using the given controller.
   *
   * @param controller the controller to call for search, filter, and random pick
   */
  public CommandLineView(MovieController controller) {
    this.controller = controller;
    this.scanner = new Scanner(System.in);
    this.lastShownMovies = new ArrayList<>();
    // We load whatever favorites already exist on disk right away, so
    // this view starts out already knowing about them, same as the GUI does
    this.currentFavorites = loadFavoritesFromFile();
  }

  /**
   * This starts the menu loop, and keeps running until the user quits.
   */
  public void start() {
    boolean keepRunning = true;

    // Just keep showing the menu and reacting to whatever number the
    // user typed, until they pick Quit
    while (keepRunning) {
      printMenu();
      String choice = scanner.nextLine().trim();

      if (choice.equals("1")) {
        handleSearch();
      } else if (choice.equals("2")) {
        handleFilter();
      } else if (choice.equals("3")) {
        handleRandomMovie();
      } else if (choice.equals("4")) {
        handleAddNewMovie();
      } else if (choice.equals("5")) {
        handleLoadFavorites();
      } else if (choice.equals("6")) {
        handleSearchFavorites();
      } else if (choice.equals("7")) {
        handleAddToFavorites();
      } else if (choice.equals("8")) {
        handleUpdateFavorite();
      } else if (choice.equals("9")) {
        handleDeleteFavorite();
      } else if (choice.equals("10")) {
        keepRunning = false;
      } else {
        System.out.println("Please enter a number from 1 to 10.");
      }
    }

    scanner.close();
    System.out.println("Goodbye!");
  }

  /**
   * This prints the main menu of options to the user.
   */
  private void printMenu() {
    System.out.println();
    System.out.println("CineVault (command line)");
    System.out.println("1) Search by title");
    System.out.println("2) Filter by genre and rating");
    System.out.println("3) Pick a random movie");
    System.out.println("4) Add a new movie");
    System.out.println("5) Load favorites");
    System.out.println("6) Search favorites");
    System.out.println("7) Add last shown movies to favorites");
    System.out.println("8) Update a favorite's rating");
    System.out.println("9) Delete a favorite");
    System.out.println("10) Quit");
    System.out.print("Choose an option: ");
  }

  /**
   * This asks the user for a title and prints any matching movies.
   */
  private void handleSearch() {
    System.out.print("Enter a title to search for: ");
    String query = scanner.nextLine().trim();

    List<Movie> results = controller.searchMovies(query);
    // We save whatever we just showed, so option 7 (add to favorites)
    // knows what to add later
    lastShownMovies = results;

    if (results.isEmpty()) {
      System.out.println("No movies found. Use option 4 to add a new movie.");
    } else {
      printMovies(results);
    }
  }

  /**
   * This asks the user for a genre and rating range and prints matches.
   * It keeps asking for the ratings until the user enters a valid pair.
   */
  private void handleFilter() {
    System.out.print("Enter a genre (or type All for every genre): ");
    String genre = scanner.nextLine().trim();
    if (genre.equalsIgnoreCase("All")) {
      // this exact text is what MovieController.filterMovies() expects
      // to mean "skip genre filtering"
      genre = "Genre: All";
    }

    double minRating = readValidRating("minimum");
    double maxRating = readValidRating("maximum");

    // Keep asking for the pair until minimum is not higher than maximum
    while (minRating > maxRating) {
      System.out.println("The minimum rating can't be higher than the maximum. Try again.");
      minRating = readValidRating("minimum");
      maxRating = readValidRating("maximum");
    }

    List<Movie> results = controller.filterMovies(genre, minRating, maxRating);
    lastShownMovies = results;

    if (results.isEmpty()) {
      System.out.println("No movies found with a rating between "
          + minRating + " and " + maxRating + ".");
    } else {
      printMovies(results);
    }
  }

  /**
   * This keeps asking the user for a rating until they enter a valid
   * number between 0 and 10.
   *
   * @param label whether this is the "minimum" or "maximum" rating, used
   *     in the prompt so the user knows which one we're asking for
   * @return the valid rating the user entered
   */
  private double readValidRating(String label) {
    while (true) {
      System.out.print("Enter the " + label + " rating (0 to 10): ");
      String input = scanner.nextLine().trim();

      try {
        double rating = Double.parseDouble(input);
        if (rating < MIN_RATING || rating > MAX_RATING) {
          System.out.println("The " + label + " rating must be between 0 and 10. Try again.");
        } else {
          return rating;
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number. Try again.");
      }
    }
  }

  /**
   * This asks the user for a genre and prints a random movie from it.
   */
  private void handleRandomMovie() {
    System.out.print("Enter a genre to pick a random movie from: ");
    String genre = scanner.nextLine().trim();

    // This only picks from movies rated 8.0 or higher in that genre
    Movie randomMovie = controller.getRandomMovie(genre);

    if (randomMovie == null) {
      System.out.println("No movie found in that genre with a rating of 8.0 or higher.");
    } else {
      lastShownMovies = List.of(randomMovie);
      printMovies(lastShownMovies);
    }
  }

  /**
   * This asks the user to type a full movie entry, adds it to the
   * catalog, and saves the whole catalog back out to our CSV file.
   */
  private void handleAddNewMovie() {
    System.out.println("Enter the movie details separated by semicolons, like this:");
    System.out.println("title; year; duration; rating; genres; directors; actors; "
        + "poster URL; overview");
    String text = scanner.nextLine().trim();
    String[] parts = text.split(";");

    // we need all 9 pieces of movie info or we can't build a Movie
    if (parts.length < MOVIE_ENTRY_FIELD_COUNT) {
      System.out.println("Please enter all 9 movie details separated by semicolons.");
      return;
    }

    try {
      String title = parts[0].trim();
      int year = Integer.parseInt(parts[YEAR_INDEX].trim());
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
        System.out.println("This movie already exists in our list: "
            + title + " (" + year + ")");
        return;
      }

      controller.addMovie(newMovie);

      // this saves the whole catalog back out to our CSV file, so the
      // new movie is still there next time we run the program
      CsvWriter csvWriter = new CsvWriter();
      csvWriter.writeMoviesFile(MOVIES_FILE_PATH, controller.getAllMovies());

      System.out.println("Added: " + title + " (" + year + ")");
    } catch (NumberFormatException e) {
      System.out.println("Please enter valid numbers for year, duration, and rating.");
    }
  }

  /**
   * This reloads our favorites from disk and prints them.
   */
  private void handleLoadFavorites() {
    currentFavorites = loadFavoritesFromFile();
    // whatever we just loaded also counts as "last shown", so the user
    // could add it right back to favorites if they wanted (harmless,
    // since duplicates are skipped anyway)
    lastShownMovies = currentFavorites;
    System.out.println("Loaded " + currentFavorites.size() + " favorite(s).");
    printMovies(currentFavorites);
  }

  /**
   * This asks for part of a title and prints any matching favorites.
   */
  private void handleSearchFavorites() {
    System.out.print("Enter part of a favorite's title to search for: ");
    String query = scanner.nextLine().trim().toLowerCase();

    List<Movie> matches = new ArrayList<>();
    for (Movie movie : currentFavorites) {
      if (movie.getTitle().toLowerCase().contains(query)) {
        matches.add(movie);
      }
    }

    if (matches.isEmpty()) {
      System.out.println("No favorite found matching \"" + query + "\".");
    } else {
      printMovies(matches);
    }
  }

  /**
   * This adds every movie from the last search, filter, or random pick
   * to our favorites, skipping any that are already there.
   */
  private void handleAddToFavorites() {
    if (lastShownMovies.isEmpty()) {
      System.out.println("No movies to add, please search, filter, or pick a "
          + "random movie first.");
      return;
    }

    int addedCount = 0;
    // go through every movie last shown, not just the first one
    for (Movie movie : lastShownMovies) {
      if (!currentFavorites.contains(movie)) {
        currentFavorites.add(movie);
        addedCount++;
      }
    }

    saveFavoritesToFile();

    if (addedCount == 0) {
      System.out.println("All of these movies are already in your favorites.");
    } else {
      System.out.println("Added " + addedCount + " movie(s) to favorites.");
    }
  }

  /**
   * This asks for a title, year, and new rating, and updates the
   * matching favorite if one exists.
   */
  private void handleUpdateFavorite() {
    System.out.print("Enter the favorite's title: ");
    String title = scanner.nextLine().trim();

    try {
      System.out.print("Enter the favorite's year: ");
      int year = Integer.parseInt(scanner.nextLine().trim());

      System.out.print("Enter the new rating: ");
      double newRating = Double.parseDouble(scanner.nextLine().trim());

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
        System.out.println("No favorite found matching \"" + title + "\" (" + year + "). "
            + "Use option 5 to see your current favorites first.");
        return;
      }

      currentFavorites.remove(existing);
      Movie updatedMovie = new Movie.Builder(title, year)
          .rating(newRating)
          .posterUrl(existing.getPosterUrl())
          .build();
      currentFavorites.add(updatedMovie);
      saveFavoritesToFile();

      System.out.println("Updated: " + title + " (" + year + ") to rating " + newRating);
    } catch (NumberFormatException e) {
      System.out.println("Please enter a valid year and rating.");
    }
  }

  /**
   * This asks for a title and year, and removes the matching favorite.
   * Typing "All" for the title instead clears every favorite.
   */
  private void handleDeleteFavorite() {
    System.out.print("Enter the favorite's title (or type All to delete every favorite): ");
    String title = scanner.nextLine().trim();

    if (title.equalsIgnoreCase("All")) {
      currentFavorites = new ArrayList<>();
      saveFavoritesToFile();
      System.out.println("All favorites have been deleted.");
      return;
    }

    try {
      System.out.print("Enter the favorite's year: ");
      int year = Integer.parseInt(scanner.nextLine().trim());

      // build a new list of everything except the movie we're removing
      List<Movie> updatedFavorites = new ArrayList<>();
      boolean removedOne = false;
      for (Movie movie : currentFavorites) {
        if (movie.getTitle().equalsIgnoreCase(title) && movie.getYear() == year) {
          removedOne = true;
        } else {
          updatedFavorites.add(movie);
        }
      }

      currentFavorites = updatedFavorites;
      saveFavoritesToFile();

      if (removedOne) {
        System.out.println("Removed from favorites: " + title + " (" + year + ")");
      } else {
        System.out.println("Could not find that favorite to delete.");
      }
    } catch (NumberFormatException e) {
      System.out.println("Please enter a valid year.");
    }
  }

  /**
   * This turns a comma separated string like "Action, Sci-Fi" into a
   * list of separate strings.
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
   * This reads our favorites file off disk and turns it into a list of
   * movies. Each row is title, year, rating, and the poster URL.
   *
   * @return the favorites we found, or an empty list if the file isn't there yet
   */
  private List<Movie> loadFavoritesFromFile() {
    List<Movie> favorites = new ArrayList<>();
    try (CSVReader reader = new CSVReader(new FileReader(FAVORITES_FILE_PATH))) {
      reader.readNext(); // skip the header row
      String[] row;
      while ((row = reader.readNext()) != null) {
        String title = row[0];
        int year = Integer.parseInt(row[1]);
        double rating = Double.parseDouble(row[2]);
        String posterUrl = row.length > FAVORITE_POSTER_URL_INDEX
            ? row[FAVORITE_POSTER_URL_INDEX] : "";

        Movie movie = new Movie.Builder(title, year)
            .rating(rating)
            .posterUrl(posterUrl)
            .build();
        favorites.add(movie);
      }
    } catch (Exception e) {
      // if the file doesn't exist yet, we just have no favorites, that's fine
    }
    return favorites;
  }

  /**
   * This writes our current favorites list back out to the favorites file.
   */
  private void saveFavoritesToFile() {
    try (CSVWriter writer = new CSVWriter(new FileWriter(FAVORITES_FILE_PATH))) {
      writer.writeNext(new String[] {"Title", "Year", "Rating", "PosterUrl"});
      for (Movie movie : currentFavorites) {
        writer.writeNext(new String[] {
            movie.getTitle(),
            String.valueOf(movie.getYear()),
            String.valueOf(movie.getRating()),
            movie.getPosterUrl()
        });
      }
    } catch (Exception e) {
      System.out.println("Could not save favorites file.");
    }
  }

  /**
   * This prints out a list of movies, one per line, or a message if
   * there weren't any.
   *
   * @param movies the movies to print
   */
  private void printMovies(List<Movie> movies) {
    if (movies.isEmpty()) {
      System.out.println("No movies found.");
      return;
    }

    // just print title, year, and rating on one line per movie, since
    // we don't have a GUI to draw cards with here
    for (Movie movie : movies) {
      System.out.println(movie.getTitle() + " (" + movie.getYear() + ") - "
          + movie.getRating());
    }
  }
}
