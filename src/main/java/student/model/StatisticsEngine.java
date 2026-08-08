package student.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class will calculate statistics on our movie catalog, like average rating,
 * or generate charts from that data etc.
 */
public class StatisticsEngine {

    /**
     * Finds the average rating for each movie genre.
     *
     * @param movies list of movies
     * @return genres and their average ratings
     */
    public Map<String, Double> getAverageRatingByGenre(List<Movie> movies) {
        List<String> genreNames = new ArrayList<>();
        List<Double> genreSums = new ArrayList<>();
        List<Integer> genreCounts = new ArrayList<>();

        for (Movie movie : movies) {
            for (String genre : movie.getGenres()) {
                int index = genreNames.indexOf(genre);

                if (index == -1) {
                    genreNames.add(genre);
                    genreSums.add(movie.getRating());
                    genreCounts.add(1);
                } else {
                    genreSums.set(index, genreSums.get(index) + movie.getRating());
                    genreCounts.set(index, genreCounts.get(index) + 1);
                }
            }
        }

        Map<String, Double> averages = new LinkedHashMap<>();

        // calculate the average for each genre
        for (int i = 0; i < genreNames.size(); i++) {
            double average = genreSums.get(i) / genreCounts.get(i);
            averages.put(genreNames.get(i), average);
        }

        return averages;
    }

    /**
     * Finds the highest rated movies from a list.
     *
     * @param movies list of movies
     * @param count number of movies to return
     * @return list of top rated movies
     */
    public List<Movie> getTopRatedMovies(List<Movie> movies, int count) {
        // make a copy so the original movie list does not change
        List<Movie> remaining = new ArrayList<>(movies);
        List<Movie> topMovies = new ArrayList<>();

        int numberToReturn = Math.min(count, remaining.size());

        for (int i = 0; i < numberToReturn; i++) {
            Movie best = remaining.get(0);

            // find the highest rated movie still in the list
            for (Movie movie : remaining) {
                if (movie.getRating() > best.getRating()) {
                    best = movie;
                }
            }

            topMovies.add(best);
            remaining.remove(best);
        }

        return topMovies;
    }
}
