# Movie-Catalog-Final-Project Design Document

This document is meant to provide a tool for you to demonstrate the design process. e need to iterate on our designs to make them better. This document is a tool to help you do that. We will be creating our Design with marmaid UML the Marmaid UML Diagram.

## (INITIAL DESIGN): Class Diagram

Below is the UML Diagram for the Movie-Catalog Application

/*

```mermaid
---
title: Initial Design For Movie-Catelog Application
---

classDiagram
    Catalog "1" o-- "many" Movie : contains
    MovieController --> Catalog : uses
    MovieController --> MovieFilter : uses
    MovieController --> StatisticsEngine : uses
    MovieController --> RandomMovieGenerator : uses
    MovieFilter ..> Movie : filters
    StatisticsEngine ..> Movie : analyzes
    RandomMovieGenerator ..> Catalog : selects from
    CsvLoader ..> Movie : creates
    CsvWriter ..> Movie : persists
    MainFrame *-- MoviePanel
    MainFrame *-- ControlPanel
    MainFrame *-- StatisticsPanel
    MainFrame --> MovieController : delegates to

    class Movie {
        -String title
        -List~String~ directors
        -List~String~ actors
        -List~String~ genres
        -int duration
        -int year
        -double rating
        -List~String~ languages
        +getTitle() String
        +getDirectors() List~String~
        +getActors() List~String~
        +getGenres() List~String~
        +getDuration() int
        +getYear() int
        +getRating() double
        +getLanguages() List~String~
    }

    class Catalog {
        -List~Movie~ movies
        +addMovie(Movie movie) void
        +editMovie(Movie movie) void
        +deleteMovie(Movie movie) void
        +getAllMovies() List~Movie~
        +search(String query) List~Movie~
    }

    class MovieFilter {
        +filterByGenre(List~Movie~ movies, String genre) List~Movie~
        +filterByRating(List~Movie~ movies, double minRating, double maxRating) List~Movie~
        +filterByLanguage(List~Movie~ movies, String language) List~Movie~
    }

    class StatisticsEngine {
        +getAverageRating(List~Movie~ movies) double
        +generateChart(List~Movie~ movies) void
    }

    class RandomMovieGenerator {
        +getRandom(Catalog catalog) Movie
    }

    class MovieController {
        -Catalog catalog
        -MovieFilter movieFilter
        -StatisticsEngine statisticsEngine
        -RandomMovieGenerator randomMovieGenerator
    }

    class MainFrame {
        -MoviePanel moviePanel
        -ControlPanel controlPanel
        -StatisticsPanel statisticsPanel
    }

    class MoviePanel
    class ControlPanel
    class StatisticsPanel

    class CsvLoader {
        +loadMoviesFile(String filePath) Set~Movie~
    }

    class CsvWriter {
        +writeMoviesFile(String filePath, List~Movie~ movies) void
    }

    class Constants {
        <<utility>>
    }

```

*/

## (INITIAL DESIGN): Tests For Movie-Catelog Application

Brainstormed tests stage in the TDD process. Notes About TTD Below.

> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

### TESTS To Start With

#### Movie Class TESTS

1. Test that the `Movie` class properly returns the title.
2. Test that the `Movie` class properly returns directors.
3. Test that the `Movie` class properly returns actors.
4. Test that the `Movie` class properly returns genres.
5. Test that the `Movie` class properly returns duration.
6. Test that the `Movie` class properly returns the year.
7. Test that the `Movie` class properly returns ratings.
8. Test that the `Movie` class properly returns languages.
9. Test that two `Movie` objects with the same title, year, and other identifying fields are considered equal.

#### Catalog Class TESTS

1. Test that the `Catalog` class properly adds a movie using `addMovie()`.
2. Test that the `Catalog` class properly edits a movie using `editMovie()`.
3. Test that the `Catalog` class properly deletes a movie using `deleteMovie()`.
4. Test that the `Catalog` class properly returns all movies using `getAllMovies()`.
5. Test that the `Catalog` class properly finds a movie using `search()`.
6. Test that the `Catalog` class does not add a duplicate movie using `addMovie()`.
7. Test that the `Catalog` class properly handles `editMovie()` when the movie does not exist.
8. Test that the `Catalog` class properly handles `deleteMovie()` when the movie does not exist.

#### MovieFilter Class TESTS

1. Test that `filterByGenre()` properly returns only movies that match the selected genre.
2. Test that `filterByRating()` properly returns only movies that match the selected rating criteria.
3. Test that `filterByLanguage()` properly returns only movies that match the selected language.
4. Test that combining multiple filters (e.g. genre and rating) properly returns only movies that match all selected criteria.

#### StatisticsEngine Class TESTS

1. Test that `getAverageRating()` properly calculates the average rating of the movies in the catalog.
2. Test that `generateChart()` properly generates a chart using the movie rating data.
3. Test that `getAverageRating()` properly handles an empty catalog.

#### RandomMovieGenerator Class TESTS

1. Test that `getRandom()` returns a movie from the `Catalog`.
2. Test that `getRandom()` does not return a movie that is not contained in the `Catalog`.
3. Test that `getRandom()` properly handles an empty `Catalog`.

#### CsvLoader Class TESTS

1. Test that `loadMoviesFile()` properly loads movies from a valid CSV file.
2. Test that `loadMoviesFile()` properly handles a missing or invalid file.

#### CsvWriter Class TESTS

1. Test that `writeMoviesFile()` properly writes movies to a CSV file.
2. Test that `writeMoviesFile()` properly handles an empty movie list.

#### MovieController Class TESTS

1. Test that the `MovieController` class properly delegates adding a movie to the `Catalog`.
2. Test that the `MovieController` class properly delegates filtering to the `MovieFilter`.
3. Test that the `MovieController` class properly delegates statistics calculations to the `StatisticsEngine`.
4. Test that the `MovieController` class properly delegates random movie selection to the `RandomMovieGenerator`.

## (FINAL DESIGN): Movie-Catalog Application

Below is the final design of our Movie-Catalog Application

```mermaid
---
title: Final Design - CineVault Movie Catalog
---
classDiagram
    direction TB

    class IView {
        <<interface>>
        +start() void
    }

    class IMovieController {
        <<interface>>
        +addMovie(Movie) void
        +getAllMovies() List~Movie~
        +editMovie(Movie) void
        +deleteMovie(Movie) void
        +searchMovies(String) List~Movie~
        +filterMoviesByGenre(String) List~Movie~
        +filterMoviesByRating(double, double) List~Movie~
        +filterMovies(String, double, double) List~Movie~
        +getRandomMovie(String) Movie
        +getRandomMovie(String, double, double) Movie
    }

    class IFavorites {
        <<interface>>
        +loadFavorites() List~Movie~
        +saveFavorites(List~Movie~) void
    }

    class Movie {
        -String title
        -int year
        -int duration
        -double rating
        -List~String~ genres
        -List~String~ directors
        -List~String~ actors
        -String posterUrl
        -String overview
        +getTitle() String
        +getYear() int
        +getDuration() int
        +getRating() double
        +getGenres() List~String~
        +getDirectors() List~String~
        +getActors() List~String~
        +getPosterUrl() String
        +getOverview() String
        +equals(Object) boolean
        +hashCode() int
        +toString() String
    }

    class Builder {
        -String title
        -int year
        -int duration
        -double rating
        -List~String~ genres
        -List~String~ directors
        -List~String~ actors
        -String posterUrl
        -String overview
        +Builder(String, int)
        +duration(int) Builder
        +rating(double) Builder
        +genres(List~String~) Builder
        +directors(List~String~) Builder
        +actors(List~String~) Builder
        +posterUrl(String) Builder
        +overview(String) Builder
        +build() Movie
    }

    class Catalog {
        -List~Movie~ movies
        +Catalog()
        +addMovie(Movie) void
        +getAllMovies() List~Movie~
        +editMovie(Movie) void
        +deleteMovie(Movie) void
        +search(String) List~Movie~
    }

    class MovieFilter {
        +filterByGenre(List~Movie~, String) List~Movie~
        +filterByRating(List~Movie~, double, double) List~Movie~
    }

    class RandomMovieGenerator {
        -Catalog catalog
        -MovieFilter movieFilter
        -Random random
        +RandomMovieGenerator(Catalog)
        +getRandomMovie(String) Movie
        +getRandomMovie(String, double, double) Movie
    }

    class StatisticsEngine {
        +getAverageRatingByGenre(List~Movie~) Map~String,Double~
        +getTopRatedMovies(List~Movie~, int) List~Movie~
    }

    class RatingRange {
        <<enum>>
        ALL
        NINE_AND_ABOVE
        EIGHT_TO_NINE
        SEVEN_TO_EIGHT
        SIX_TO_SEVEN
        BELOW_SIX
        +getLabel() String
        +getMinRating() double
        +getMaxRating() double
        +fromLabel(String)$ RatingRange
        +allLabels()$ String[]
    }

    class MovieController {
        -Catalog catalog
        -MovieFilter movieFilter
        -StatisticsEngine statisticsEngine
        -RandomMovieGenerator randomMovieGenerator
        +addMovie(Movie) void
        +getAllMovies() List~Movie~
        +editMovie(Movie) void
        +deleteMovie(Movie) void
        +searchMovies(String) List~Movie~
        +filterMoviesByGenre(String) List~Movie~
        +filterMoviesByRating(double, double) List~Movie~
        +filterMovies(String, double, double) List~Movie~
        +getRandomMovie(String) Movie
        +getRandomMovie(String, double, double) Movie
    }

    class CineVaultApplication {
        -String MOVIES_FILE_PATH$
        +main(String[])$ void
    }

    class CsvLoader {
        +loadMoviesFile(String) List~Movie~
    }

    class CsvWriter {
        +writeMoviesFile(String, List~Movie~) void
    }

    class CsvFavoritesRepository {
        -String filePath
        +CsvFavoritesRepository(String)
        +loadFavorites() List~Movie~
        +saveFavorites(List~Movie~) void
    }

    class GuiView {
        -IMovieController controller
        +GuiView(IMovieController)
        +start() void
    }

    class CommandLineView {
        -IMovieController controller
        -Scanner scanner
        -List~Movie~ lastShownMovies
        -List~Movie~ currentFavorites
        -IFavorites favoritesRepository
        +CommandLineView(IMovieController)
        +start() void
        -printMenu() void
        -handleSearch() void
        -handleFilter() void
        -handleRandomMovie() void
        -handleAddNewMovie() void
        -handleLoadFavorites() void
        -handleSearchFavorites() void
        -handleAddToFavorites() void
        -handleUpdateFavorite() void
        -handleDeleteFavorite() void
    }

    class MainFrame {
        -MoviePanel moviePanel
        +MainFrame(IMovieController)
        +start() void
        +showInitialMovies(List~Movie~) void
    }

    class ControlPanel {
        -IMovieController controller
        -MoviePanel moviePanel
        -JTextField searchField
        -JComboBox~String~ genreFilter
        -JComboBox~String~ ratingFilter
        -JTextField favoritesSearchField
        -List~Movie~ currentFavorites
        -IFavorites favoritesRepository
        +ControlPanel(IMovieController, MoviePanel, StatisticsPanel)
        -handleSearch() void
        -handleFilterMovies() void
        -handleAddToFavorites() void
        -handleAddNewMovieToList() void
        -handleLoadFavorites() void
        -handleSearchFavorites() void
        -handleUpdateFavorite() void
        -handleDeleteFavorite() void
        -handleRandomMovie() void
    }

    class MoviePanel {
        -JPanel cardsPanel
        -List~Movie~ currentMovies
        +MoviePanel()
        +showMovies(List~Movie~) void
        +showMessage(String) void
        +getCurrentMovies() List~Movie~
    }

    class StatisticsPanel {
        -IMovieController controller
        -MoviePanel moviePanel
        -BarChartPanel chartPanel
        -JComboBox~String~ genreDropdown
        +StatisticsPanel(IMovieController, MoviePanel)
        -handleAverageByGenre() void
        -handleTopRated() void
    }

    class BarChartPanel {
        -Map~String,Double~ data
        +BarChartPanel()
        +setData(Map~String,Double~) void
        +paintComponent(Graphics) void
    }

    class RoundedBorder {
        -Color color
        +RoundedBorder(Color)
        +paintBorder(Component, Graphics, int, int, int, int) void
        +getBorderInsets(Component) Insets
    }

    %% Inheritance & Implementation
    IView <|.. GuiView
    IView <|.. CommandLineView
    IMovieController <|.. MovieController
    IFavorites <|.. CsvFavoritesRepository

    %% Composition
    Movie *-- Builder : nested
    Catalog o-- "0..*" Movie
    MovieController --> Catalog
    MovieController --> MovieFilter
    MovieController --> StatisticsEngine
    MovieController --> RandomMovieGenerator
    RandomMovieGenerator --> Catalog
    RandomMovieGenerator --> MovieFilter
    GuiView --> MainFrame
    MainFrame *-- ControlPanel
    MainFrame *-- MoviePanel
    MainFrame *-- StatisticsPanel
    StatisticsPanel *-- BarChartPanel

    %% Dependencies
    CineVaultApplication ..> IView : creates
    CineVaultApplication ..> MovieController : creates
    CineVaultApplication ..> Catalog : creates
    CineVaultApplication ..> CsvLoader : uses
    GuiView ..> IMovieController
    CommandLineView ..> IMovieController
    MainFrame ..> IMovieController
    ControlPanel ..> IMovieController
    StatisticsPanel ..> IMovieController
    ControlPanel ..> IFavorites
    ControlPanel ..> CsvWriter
    ControlPanel ..> RatingRange
    ControlPanel ..> RoundedBorder
    ControlPanel --> MoviePanel
    StatisticsPanel --> MoviePanel
    MoviePanel ..> RoundedBorder
    CommandLineView ..> IFavorites
    CommandLineView ..> CsvWriter
    CommandLineView ..> RatingRange
    CsvLoader ..> Builder
    CsvLoader ..> Movie
    CsvWriter ..> Movie
    CsvFavoritesRepository ..> Movie
    MovieFilter ..> Movie
    StatisticsEngine ..> Movie
```
