package student.controller;

import student.model.Catalog;
import student.model.MovieFilter;
import student.model.RandomMovieGenerator;
import student.model.StatisticsEngine;


/**
 * This class will connect user actions from the view to the Catalog and other model classes in our program.
 */
public class MovieController {

    private final Catalog catalog;
    private final MovieFilter movieFilter;
    private final StatisticsEngine statisticsEngine;
    private final RandomMovieGenerator randomMovieGenerator;

    /**
     * Creates the controller using the model classes needed by the application.
     *
     * @param catalog the movie catalog
     * @param movieFilter filters movies
     * @param statisticsEngine calculates movie statistics
     * @param randomMovieGenerator selects a movie
     */
    public MovieController(
            Catalog catalog,
            MovieFilter movieFilter,
            StatisticsEngine statisticsEngine,
            RandomMovieGenerator randomMovieGenerator) {

        this.catalog = catalog;
        this.movieFilter = movieFilter;
        this.statisticsEngine = statisticsEngine;
        this.randomMovieGenerator = randomMovieGenerator;
    }
}
