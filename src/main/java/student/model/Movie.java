package student.model;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents a single movie in the CineVault catalog.
 *
 * <p>A {@code Movie} is immutable: all fields are set once at construction and
 * cannot be changed afterwards. To "edit" a movie the catalog replaces the whole
 * object rather than mutating it. The list-valued fields ({@code genres},
 * {@code directors}, {@code actors}) are defensively copied on the way in and
 * exposed as unmodifiable views, so callers can never alter a movie's internal state.</p>
 *
 * <p>Two movies are considered equal when they share the same title
 * (case-insensitive) and release year. This lets the catalog treat re-imported or
 * re-rated copies of the same film as one entry.</p>
 *
 * <p>Instances are created with the fluent {@link Builder}, which avoids the
 * error prone ordering of a nine-argument constructor:</p>
 * <pre>{@code
 * Movie m = new Movie.Builder("The Matrix", 1999)
 *         .duration(136)
 *         .rating(8.7)
 *         .genres(List.of("Action", "Sci-Fi"))
 *         .directors(List.of("Lana Wachowski", "Lilly Wachowski"))
 *         .actors(List.of("Keanu Reeves", "Laurence Fishburne"))
 *         .posterUrl("https://example.com/matrix.jpg")
 *         .overview("A hacker learns the truth about his reality.")
 *         .build();
 * }</pre>
 */
public final class Movie {

  private final String title;
  private final int year;
  private final int duration;
  private final double rating;
  private final List<String> genres;
  private final List<String> directors;
  private final List<String> actors;
  private final String posterUrl;
  private final String overview;

  /**
   * Creates a movie from a fully populated {@link Builder}.
   *
   * <p>List fields are defensively copied so later changes to the builder's lists
   * do not leak into the constructed movie.</p>
   *
   * @param builder the builder holding this movie's values, must not be null
   */
  private Movie(final Builder builder) {
    this.title = builder.title;
    this.year = builder.year;
    this.duration = builder.duration;
    this.rating = builder.rating;
    this.genres = List.copyOf(builder.genres);
    this.directors = List.copyOf(builder.directors);
    this.actors = List.copyOf(builder.actors);
    this.posterUrl = builder.posterUrl;
    this.overview = builder.overview;
  }

  /**
   * Returns this movie's title.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the year this movie was released.
   *
   * @return the release year
   */
  public int getYear() {
    return year;
  }

  /**
   * Returns this movie's running time in minutes.
   *
   * @return the duration in minutes
   */
  public int getDuration() {
    return duration;
  }

  /**
   * Returns this movie's IMDb rating on a scale of 0 to 10.
   *
   * @return the rating
   */
  public double getRating() {
    return rating;
  }

  /**
   * Returns this movie's genres.
   *
   * @return an unmodifiable list of genres
   */
  public List<String> getGenres() {
    return genres;
  }

  /**
   * Returns this movie's directors.
   *
   * @return an unmodifiable list of directors
   */
  public List<String> getDirectors() {
    return directors;
  }

  /**
   * Returns this movie's leading actors.
   *
   * @return an unmodifiable list of actors
   */
  public List<String> getActors() {
    return actors;
  }

  /**
   * Returns the URL of this movie's poster image.
   *
   * @return the poster image URL
   */
  public String getPosterUrl() {
    return posterUrl;
  }

  /**
   * Returns a short plot summary of this movie.
   *
   * @return the overview text
   */
  public String getOverview() {
    return overview;
  }

  /**
   * Compares this movie to another object for equality.
   *
   * <p>Two movies are equal when they have the same title (ignoring case) and the
   * same release year; the remaining fields are not part of a movie's identity.</p>
   *
   * @param other the object to compare against
   * @return true if {@code other} is a movie with the same title and year
   */
  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Movie)) {
      return false;
    }
    final Movie movie = (Movie) other;
    return year == movie.year && title.equalsIgnoreCase(movie.title);
  }

  /**
   * Returns a hash code consistent with {@link #equals(Object)}.
   *
   * @return a hash code based on the lower-cased title and release year
   */
  @Override
  public int hashCode() {
    return Objects.hash(title.toLowerCase(Locale.ROOT), year);
  }

  /**
   * Returns a short human-readable description of this movie.
   *
   * @return a string of the form {@code "Title (year) - rating"}
   */
  @Override
  public String toString() {
    return String.format(Locale.ROOT, "%s (%d) - %.1f", title, year, rating);
  }

  /**
   * Fluent builder for {@link Movie} instances.
   *
   * <p>Title and year are required (they form a movie's identity) and are passed to
   * the constructor. Every other field is optional and defaults to an empty list or
   * empty string, so a movie can be built from a partial data source without null
   * fields.</p>
   */
  public static final class Builder {

    private final String title;
    private final int year;
    private int duration;
    private double rating;
    private List<String> genres = List.of();
    private List<String> directors = List.of();
    private List<String> actors = List.of();
    private String posterUrl = "";
    private String overview = "";

    /**
     * Creates a builder for a movie with the given required identity fields.
     *
     * @param title the movie title, must not be null
     * @param year  the release year
     */
    public Builder(final String title, final int year) {
      this.title = Objects.requireNonNull(title, "title must not be null");
      this.year = year;
    }

    /**
     * Sets the running time in minutes.
     *
     * @param newDuration the duration in minutes
     * @return this builder
     */
    public Builder duration(final int newDuration) {
      this.duration = newDuration;
      return this;
    }

    /**
     * Sets the IMDb rating (0 to 10).
     *
     * @param newRating the rating
     * @return this builder
     */
    public Builder rating(final double newRating) {
      this.rating = newRating;
      return this;
    }

    /**
     * Sets the genres.
     *
     * @param newGenres the genres, must not be null
     * @return this builder
     */
    public Builder genres(final List<String> newGenres) {
      this.genres = Objects.requireNonNull(newGenres, "genres must not be null");
      return this;
    }

    /**
     * Sets the directors.
     *
     * @param newDirectors the directors, must not be null
     * @return this builder
     */
    public Builder directors(final List<String> newDirectors) {
      this.directors = Objects.requireNonNull(newDirectors, "directors must not be null");
      return this;
    }

    /**
     * Sets the leading actors.
     *
     * @param newActors the actors, must not be null
     * @return this builder
     */
    public Builder actors(final List<String> newActors) {
      this.actors = Objects.requireNonNull(newActors, "actors must not be null");
      return this;
    }

    /**
     * Sets the poster image URL.
     *
     * @param newPosterUrl the poster URL, must not be null
     * @return this builder
     */
    public Builder posterUrl(final String newPosterUrl) {
      this.posterUrl = Objects.requireNonNull(newPosterUrl, "posterUrl must not be null");
      return this;
    }

    /**
     * Sets the plot summary.
     *
     * @param newOverview the overview text, must not be null
     * @return this builder
     */
    public Builder overview(final String newOverview) {
      this.overview = Objects.requireNonNull(newOverview, "overview must not be null");
      return this;
    }

    /**
     * Builds the immutable {@link Movie} from this builder's current values.
     *
     * @return a new movie
     */
    public Movie build() {
      return new Movie(this);
    }
  }
}
