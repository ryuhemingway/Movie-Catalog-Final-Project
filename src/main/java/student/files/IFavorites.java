package student.files;

import java.util.List;
import student.model.Movie;

/**
 * This interface describes anything that can load and save a list of
 * favorite movies. Right now only a CSV-backed version exists, but this
 * lets both the GUI and command line share the same favorites logic
 * instead of each having their own copy.
 */
public interface IFavorites {

  /**
   * This loads the saved favorite movies.
   *
   * @return the favorites found, or an empty list if none exist yet
   */
  List<Movie> loadFavorites();

  /**
   * This saves the given list of favorite movies, replacing whatever
   * was saved before.
   *
   * @param favorites the favorites to save
   */
  void saveFavorites(List<Movie> favorites);
}
