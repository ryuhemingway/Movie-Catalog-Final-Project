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

  /** The lowest rating a movie can have and still count as a "good" pick
   * for the original single-argument getRandomMovie(genre) method. */
  private static final double MIN_RATING = 8.0;
  /** The highest possible movie rating. */
  private static final double MAX_RATING = 10.0;

  /** The catalog we pick random movies from. */
  private final Catalog catalog;
  /** Used to narrow the catalog down by genre and rating before picking. */
  private final MovieFilter movieFilter;
  /** Used to pick a random index from the narrowed down list of movies. */
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
    List<Movie> byRating = movieFilter.filterByRating(byGenre, MIN_RATING, MAX_RATING);
    if (byRating.isEmpty()) {
      return null;
    }
    int index = random.nextInt(byRating.size());
    return byRating.get(index);
  }

  /**
   * Returns a random movie that matches the user's chosen genre and
   * falls within the given rating range. Type "Genre: All" for genre
   * to skip genre filtering entirely.
   *
   * @param genre the genre to filter by, or "Genre: All" for every genre
   * @param minRating the minimum rating to allow
   * @param maxRating the maximum rating to allow
   * @return a random matching movie, or null if none match
   */
  public Movie getRandomMovie(final String genre, final double minRating, final double maxRating) {
    Objects.requireNonNull(genre, "genre must not be null");
    List<Movie> movies = catalog.getAllMovies();

    List<Movie> byGenre;
    if (genre.equals("Genre: All")) {
      // skip genre filtering entirely, so rating can work on its own
      byGenre = movies;
    } else {
      byGenre = movieFilter.filterByGenre(movies, genre);
    }

    List<Movie> byRating = movieFilter.filterByRating(byGenre, minRating, maxRating);
    if (byRating.isEmpty()) {
      return null;
    }
    int index = random.nextInt(byRating.size());
    return byRating.get(index);
  }
}
