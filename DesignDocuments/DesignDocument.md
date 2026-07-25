# Movie-Catalog-Final-Project Design Document

This document is meant to provide a tool for you to demonstrate the design process. e need to iterate on our designs to make them better. This document is a tool to help you do that. We will be creating our Design with marmaid UML the Marmaid UML Diagram.

## (INITIAL DESIGN): Class Diagram

Below is the UML Diagram for the Movie-Catalog Application

/*
```mermaid
---
title: Initial Design For Movie-Catelog Application
---
```
*/




## (INITIAL DESIGN): Tests For Movie-Catelog Application

Brainstormed tests stage in the TDD process. Notes About TTD Below.
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
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

#### Catalog Class TESTS
1. Test that the `Catalog` class properly adds a movie using `addMovie()`.
2. Test that the `Catalog` class properly edits a movie using `editMovie()`.
3. Test that the `Catalog` class properly deletes a movie using `deleteMovie()`.
4. Test that the `Catalog` class properly returns all movies using `getAllMovies()`.
5. Test that the `Catalog` class properly finds a movie using `search()`.

#### MovieFilter Class TESTS
1. Test that `filterByGenre()` properly returns only movies that match the selected genre.
2. Test that `filterByRating()` properly returns only movies that match the selected rating criteria.
3. Test that `filterByLanguage()` properly returns only movies that match the selected language.

#### StatisticsEngine Class TESTS
1. Test that `getAverageRating()` properly calculates the average rating of the movies in the catalog.
2. Test that `generateChart()` properly generates a chart using the movie rating data.

#### RandomMovieGenerator Class TESTS
1. Test that `getRandom()` returns a movie from the `Catalog`.
2. Test that `getRandom()` does not return a movie that is not contained in the `Catalog`.







## (FINAL DESIGN):  Movie-Catalog Application

Below is the final design of our Movie-Catalog Application

/*
```mermaid
---
title: Initial Design For Movie-Catelog Application
---
```
*/
