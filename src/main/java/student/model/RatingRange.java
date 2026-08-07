package student.model;

/**
 * This enum represents the fixed rating range options shown in our
 * genre/rating filters, so both the GUI dropdown and the command line
 * share the same set of ranges and bounds instead of each having their
 * own copy.
 */
public enum RatingRange {

  /** Every rating, no filtering at all. */
  ALL("Rating: All", 0.0, 10.0),
  /** Ratings of 9.0 and above. */
  NINE_AND_ABOVE("9.0 and above", 9.0, 10.0),
  /** Ratings from 8.0 up to 8.9. */
  EIGHT_TO_NINE("8.0 - 8.9", 8.0, 8.9),
  /** Ratings from 7.0 up to 7.9. */
  SEVEN_TO_EIGHT("7.0 - 7.9", 7.0, 7.9),
  /** Ratings from 6.0 up to 6.9. */
  SIX_TO_SEVEN("6.0 - 6.9", 6.0, 6.9),
  /** Any rating below 6.0. */
  BELOW_SIX("Below 6.0", 0.0, 5.9);

  /** The lowest rating any movie can have. */
  public static final double ABSOLUTE_MIN_RATING = 0.0;
  /** The highest rating any movie can have. */
  public static final double ABSOLUTE_MAX_RATING = 10.0;

  /** The text shown to the user for this range. */
  private final String label;
  /** The lowest rating included in this range. */
  private final double minRating;
  /** The highest rating included in this range. */
  private final double maxRating;

  /**
   * This builds one rating range option.
   *
   * @param label the text shown to the user
   * @param minRating the lowest rating included in this range
   * @param maxRating the highest rating included in this range
   */
  RatingRange(String label, double minRating, double maxRating) {
    this.label = label;
    this.minRating = minRating;
    this.maxRating = maxRating;
  }

  /**
   * This returns the text shown to the user for this range.
   *
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * This returns the lowest rating included in this range.
   *
   * @return the minimum rating
   */
  public double getMinRating() {
    return minRating;
  }

  /**
   * This returns the highest rating included in this range.
   *
   * @return the maximum rating
   */
  public double getMaxRating() {
    return maxRating;
  }

  /**
   * This finds the rating range matching the given label.
   *
   * @param label the label to look for
   * @return the matching range, or ALL if nothing matches
   */
  public static RatingRange fromLabel(String label) {
    for (RatingRange range : values()) {
      if (range.label.equals(label)) {
        return range;
      }
    }
    return ALL;
  }

  /**
   * This returns every range's label, in the order they should be
   * shown in a dropdown.
   *
   * @return the labels for all rating ranges
   */
  public static String[] allLabels() {
    RatingRange[] ranges = values();
    String[] labels = new String[ranges.length];
    for (int i = 0; i < ranges.length; i++) {
      labels[i] = ranges[i].label;
    }
    return labels;
  }
}
