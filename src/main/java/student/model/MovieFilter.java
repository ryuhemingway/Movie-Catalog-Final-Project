package student.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class filters a list of movies by genre, rating range
 *
 * <p>{@code MovieFilter} does not hold a reference to the {@link Catalog} itself.
 * Instead, each method takes in a list of movies (typically obtained from
 * {@link Catalog#getAllMovies()}) and returns a new list containing only the
 * movies that match the given criteria. This keeps filtering decoupled from
 * how the movies are stored, and makes each method easy to test in isolation.</p>
 */
public class MovieFilter {

  /**
   * Returns all movies from the given list that belong to the specified genre.
   * The match is not case sensitive, so "action" will match "Action".
   *
   * @param movies the list of movies to filter, must not be null
   * @param genre  the genre to filter by, must not be null
   * @return a new list containing only movies that have the given genre
   */
  public List<Movie> filterByGenre(final List<Movie> movies, final String genre) {
    Objects.requireNonNull(movies, "movies must not be null");
    Objects.requireNonNull(genre, "genre must not be null");

    List<Movie> results = new ArrayList<>();
    for (Movie movie : movies) {
      for (String movieGenre : movie.getGenres()) {
        if (movieGenre.equalsIgnoreCase(genre)) {
          results.add(movie);
          break;
        }
      }
    }
    return results;
  }

  /**
   * Returns all movies from the given list whose rating falls within the
   * given range, inclusive on both ends.
   *
   * @param movies    the list of movies to filter, must not be null
   * @param minRating the lowest acceptable rating, inclusive
   * @param maxRating the highest acceptable rating, inclusive
   * @return a new list containing only movies with a rating between
   *     {@code minRating} and {@code maxRating}
   * @throws IllegalArgumentException if {@code minRating} is greater than {@code maxRating}
   */
  public List<Movie> filterByRating(
      final List<Movie> movies, final double minRating, final double maxRating) {
    Objects.requireNonNull(movies, "movies must not be null");
    if (minRating > maxRating) {
      throw new IllegalArgumentException("minRating must not be greater than maxRating");
    }

    List<Movie> results = new ArrayList<>();
    for (Movie movie : movies) {
      double rating = movie.getRating();
      if (rating >= minRating && rating <= maxRating) {
        results.add(movie);
      }
    }
    return results;
  }
}
