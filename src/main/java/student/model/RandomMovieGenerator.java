package student.model;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Picks a random movie from a {@link Catalog} for the user to discover
 * based on their favourite genre and a rating of at least 8.0.
 *
 * <p>{@code RandomMovieGenerator} holds a reference to the catalog and uses
 * {@link java.util.Random} to select a movie at a random index after
 * narrowing the pool with genre and rating filters.</p>
 */
public class RandomMovieGenerator {

  private static final double MIN_RATING = 8.0;

  private final Catalog catalog;
  private final MovieFilter movieFilter;
  private final Random random;

  /**
   * Creates a generator that draws random movies from the given catalog.
   *
   * @param catalog the catalog to pick movies from, must not be null
   */
  public RandomMovieGenerator(final Catalog catalog) {
    this.catalog = Objects.requireNonNull(catalog, "catalog must not be null");
    this.movieFilter = new MovieFilter();
    this.random = new Random();
  }

  /**
   * Returns a random movie that matches the user's favourite genre and
   * has a rating of at least 8.0.
   *
   * @param genre the user's favourite genre, must not be null
   * @return a random matching movie, or {@code null} if none match
   */
  public Movie getRandomMovie(final String genre) {
    Objects.requireNonNull(genre, "genre must not be null");
    List<Movie> movies = catalog.getAllMovies();
    List<Movie> byGenre = movieFilter.filterByGenre(movies, genre);
    List<Movie> byRating = movieFilter.filterByRating(byGenre, MIN_RATING, 10.0);
    if (byRating.isEmpty()) {
      return null;
    }
    int index = random.nextInt(byRating.size());
    return byRating.get(index);
  }
}
