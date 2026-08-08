package student.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Will test that the StatisticsEngine class properly calculates averages and generates charts correctly.
 */
public class StatisticsEngineTest {

    /**
     * Checks the average rating for Action and Drama movies.
     */
    @Test
    void getAverageRatingByGenreReturnsCorrectAverages() {
        Movie movie1 = new Movie.Builder("Movie One", 2000)
                .genres(List.of("Action", "Drama"))
                .rating(8.0)
                .build();

        Movie movie2 = new Movie.Builder("Movie Two", 2001)
                .genres(List.of("Action"))
                .rating(6.0)
                .build();

        Movie movie3 = new Movie.Builder("Movie Three", 2002)
                .genres(List.of("Drama"))
                .rating(10.0)
                .build();

        StatisticsEngine engine = new StatisticsEngine();

        Map<String, Double> result =
                engine.getAverageRatingByGenre(List.of(movie1, movie2, movie3));

        assertEquals(7.0, result.get("Action"), 0.001);
        assertEquals(9.0, result.get("Drama"), 0.001);
    }

    /**
     * Makes sure the movies with the highest ratings come first.
     */
    @Test
    void getTopRatedMoviesReturnsHighestRatedMovies() {
        Movie movie1 = new Movie.Builder("Movie One", 2000)
                .rating(7.5)
                .build();

        Movie movie2 = new Movie.Builder("Movie Two", 2001)
                .rating(9.5)
                .build();

        Movie movie3 = new Movie.Builder("Movie Three", 2002)
                .rating(8.5)
                .build();

        StatisticsEngine engine = new StatisticsEngine();

        List<Movie> result =
                engine.getTopRatedMovies(List.of(movie1, movie2, movie3), 2);

        assertEquals(2, result.size());
        assertEquals("Movie Two", result.get(0).getTitle());
        assertEquals("Movie Three", result.get(1).getTitle());
    }

    /**
     * Checks what happens when we ask for more movies than we have.
     */
    @Test
    void getTopRatedMoviesReturnsAllMoviesWhenCountIsLarger() {
        Movie movie1 = new Movie.Builder("Movie One", 2000)
                .rating(8.0)
                .build();

        Movie movie2 = new Movie.Builder("Movie Two", 2001)
                .rating(9.0)
                .build();

        StatisticsEngine engine = new StatisticsEngine();

        List<Movie> result =
                engine.getTopRatedMovies(List.of(movie1, movie2), 10);

        assertEquals(2, result.size());
    }

    /**
     * Checks the results when there are no movies in the list.
     */
    @Test
    void emptyMovieListReturnsEmptyResults() {
        StatisticsEngine engine = new StatisticsEngine();

        Map<String, Double> averages =
                engine.getAverageRatingByGenre(List.of());

        List<Movie> topMovies =
                engine.getTopRatedMovies(List.of(), 10);

        assertTrue(averages.isEmpty());
        assertTrue(topMovies.isEmpty());
    }
}
