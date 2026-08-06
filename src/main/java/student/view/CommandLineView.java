package student.view;

import java.util.List;
import java.util.Scanner;
import student.controller.MovieController;
import student.model.Movie;

/**
 * This class shows a text menu in the terminal and lets the user search,
 * filter, and pick a random movie, using the same controller the GUI uses.
 */
public class CommandLineView {

  /** The controller this view asks to do things when the user picks a menu option. */
  private final MovieController controller;
  /** Reads whatever the user types in the terminal. */
  private final Scanner scanner;

  /**
   * This method builds a command line view using the given controller.
   *
   * @param controller the controller to call for search, filter, and random pick
   */
  public CommandLineView(MovieController controller) {
    this.controller = controller;
    this.scanner = new Scanner(System.in);
  }

  /**
   * This method starts the menu loop, and keeps running until the user quits.
   */
  public void start() {
    boolean keepRunning = true;

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
        keepRunning = false;
      } else {
        System.out.println("Please enter a number from 1 to 4.");
      }
    }

    scanner.close();
    System.out.println("Goodbye!");
  }

  /**
   * This method prints the main menu of options to the user.
   */
  private void printMenu() {
    System.out.println();
    System.out.println("CineVault (command line)");
    System.out.println("1) Search by title");
    System.out.println("2) Filter by genre and rating");
    System.out.println("3) Pick a random movie");
    System.out.println("4) Quit");
    System.out.print("Choose an option: ");
  }

  /**
   * This method asks the user for a title and prints any matching movies.
   */
  private void handleSearch() {
    System.out.print("Enter a title to search for: ");
    String query = scanner.nextLine().trim();

    List<Movie> results = controller.searchMovies(query);
    printMovies(results);
  }

  /**
   * This method asks the user for a genre and rating range and prints matches.
   */
  private void handleFilter() {
    System.out.print("Enter a genre (or type All for every genre): ");
    String genre = scanner.nextLine().trim();
    if (genre.equalsIgnoreCase("All")) {
      genre = "Genre: All";
    }

    try {
      System.out.print("Enter the minimum rating (0 to 10): ");
      double minRating = Double.parseDouble(scanner.nextLine().trim());

      System.out.print("Enter the maximum rating (0 to 10): ");
      double maxRating = Double.parseDouble(scanner.nextLine().trim());

      // The minimum can't be higher than the maximum, so we check for
      // that here instead of letting it crash further down
      if (minRating > maxRating) {
        System.out.println("The minimum rating can't be higher than the maximum. Try again.");
        return;
      }

      List<Movie> results = controller.filterMovies(genre, minRating, maxRating);
      printMovies(results);
    } catch (NumberFormatException e) {
      System.out.println("Please enter valid numbers for the minimum and maximum rating.");
    }
  }

  /**
   * This method asks the user for a genre and prints a random movie from it.
   */
  private void handleRandomMovie() {
    System.out.print("Enter a genre to pick a random movie from: ");
    String genre = scanner.nextLine().trim();

    Movie randomMovie = controller.getRandomMovie(genre);

    if (randomMovie == null) {
      System.out.println("No movie found in that genre with a rating of 8.0 or higher.");
    } else {
      printMovies(List.of(randomMovie));
    }
  }

  /**
   * This method prints out a list of movies, one per line, or a message if
   * there weren't any.
   *
   * @param movies the movies to print
   */
  private void printMovies(List<Movie> movies) {
    if (movies.isEmpty()) {
      System.out.println("No movies found.");
      return;
    }

    for (Movie movie : movies) {
      System.out.println(movie.getTitle() + " (" + movie.getYear() + ") - "
          + movie.getRating());
    }
  }
}