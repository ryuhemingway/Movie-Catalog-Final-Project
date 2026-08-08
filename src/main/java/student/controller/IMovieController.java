package student.controller;

import java.util.List;
import student.model.Movie;

/**
 * This interface describes everything a controller needs to do to
 * connect a view (GUI or command line) to our movie data. This lets
 * both views depend on what a controller can do, instead of depending
 * on one specific controller class.
 */
public interface IMovieController {

  /**
   * Adds a movie.
   *
   * @param movie the movie to add
   */
  void addMovie(Movie movie);

  /**
   * Gets all movies.
   *
   * @return all movies
   */
  List<Movie> getAllMovies();

  /**
   * Edits a movie.
   *
   * @param movie the updated movie
   */
  void editMovie(Movie movie);

  /**
   * Deletes a movie.
   *
   * @param movie the movie to delete
   */
  void deleteMovie(Movie movie);

  /**
   * Searches movies by title.
   *
   * @param query the search text
   * @return matching movies
   */
  List<Movie> searchMovies(String query);

  /**
   * Filters movies by genre.
   *
   * @param genre the selected genre
   * @return matching movies
   */
  List<Movie> filterMoviesByGenre(String genre);

  /**
   * Filters movies by rating.
   *
   * @param minRating the minimum rating
   * @param maxRating the maximum rating
   * @return matching movies
   */
  List<Movie> filterMoviesByRating(double minRating, double maxRating);

  /**
   * Filters movies by both genre and rating range at the same time.
   *
   * @param genre the selected genre, or "Genre: All" to skip genre filtering
   * @param minRating the minimum rating
   * @param maxRating the maximum rating
   * @return matching movies
   */
  List<Movie> filterMovies(String genre, double minRating, double maxRating);

  /**
   * Picks a random movie matching the user's favourite genre and a
   * rating of at least 8.0.
   *
   * @param genre the user's favourite genre
   * @return a random matching movie, or null if none match
   */
  Movie getRandomMovie(String genre);

  /**
   * Picks a random movie matching the given genre and rating range.
   *
   * @param genre the genre to filter by, or "Genre: All" for every genre
   * @param minRating the minimum rating to allow
   * @param maxRating the maximum rating to allow
   * @return a random matching movie, or null if none match
   */
  Movie getRandomMovie(String genre, double minRating, double maxRating);

}
