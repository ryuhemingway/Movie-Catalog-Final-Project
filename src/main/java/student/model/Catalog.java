package student.model;


import java.util.ArrayList;
import java.util.List;

/**
 * This class will hold our full collection of movies and handle adding, editing, deleting, and searching them.
 */
public class Catalog {

  // This list holds every movie currently in the catalog
  private final List<Movie> movies;

  /**
   * This method makes a new, empty catalog.
   */
  public Catalog() {
    this.movies = new ArrayList<>();
  }

  /**
   * This method adds a movie to the catalog.
   *
   * @param movie the movie to add
   */
  public void addMovie(Movie movie) {
    movies.add(movie);
  }

  /**
   * This method returns every movie in the catalog.
   *
   * @return a list of all movies
   */
  public List<Movie> getAllMovies() {
    return movies;
  }

  /**
   * This method replaces an existing movie with an updated version.
   * Since Movie treats same title and year as the same movie,
   * we find the old one by that and swap it out.
   *
   * @param updatedMovie the new version of the movie
   */
  public void editMovie(Movie updatedMovie) {
    // Finds where the old version of this movie is in the list
    int index = movies.indexOf(updatedMovie);

    if (index != -1) {
      movies.set(index, updatedMovie);
    }
  }

  /**
   * This method will remove a movie from the catalog.
   *
   * @param movie the movie to remove
   */
  public void deleteMovie(Movie movie) {
    movies.remove(movie);
  }









}