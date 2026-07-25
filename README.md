# Final Project for CS 5004 - (APPLICATION NAME/Update)

Team Members:
Name
Ryu Hemingway
Chiberu Egandu
Arushi Haniyur
Robin Rojano

## Description
CineVault is a movie catalog application that allows users to collect, organize, and browse movie information. Users can add, edit, and delete movies from a personal catalog, with each movie storing details such as title, directors, actors, genres, duration, year released, rating, and languages. Additional features include filtering by genre/rating/language, viewing average rating statistics and charts, and generating a random movie from the collection.

**Core features:**
- Add, edit, and delete movies from the catalog
- Store movie details: title, duration, directors, actors, genres, year released, rating (out of 10), and languages
- Browse and search the full catalog

**Additional features (planned):**
- Filter movies by genre, rating range, or language
- View average rating per genre with charts
- Generate a random movie from the collection
- Import/export movie data from a dataset file

## Project Status
This project is currently in the early setup stage. The package structure, blank class stubs, and initial test brainstorm are in place, and the team is about to begin individual work using TDD (Red-Green-Refactor). No features have been implemented yet.

## How to Compile and Run

This project uses Gradle via the included wrapper, so you don't need Gradle installed separately.

**To compile and run all tests:**
./gradlew build
(Windows PowerShell: `.\gradlew.bat build`)


**To run the application:**
(Windows PowerShell: `.\gradlew.bat run`)

**To run just the tests:**
./gradlew test
(Windows PowerShell: `.\gradlew.bat test`)
If you want to run only the JUnit tests without a full build (useful while doing TDD):





## Project Documentation

- **Design Documents** (initial UML diagram, brainstormed tests): see [`DesignDocuments/`](./DesignDocuments)
- **User Manual**: not yet written — will be added as features are completed, see [`Manual/`](./Manual)

## Project Structure
- `src/main/java/student/model` — core data and logic (Movie, Catalog, MovieFilter, StatisticsEngine, RandomMovieGenerator)
- `src/main/java/student/view` — Java Swing GUI (MainFrame, MoviePanel, ControlPanel, StatisticsPanel)
- `src/main/java/student/controller` — connects the view to the model (MovieController)
- `src/main/java/student/files` — reads/writes movie data to CSV files (CsvLoader, CsvWriter)
- `src/main/java/student/helpers` — shared constants used across the application
- `src/main/java/student/CineVaultApplication.java` — main driver class that launches the application
- `src/test/java/student` — JUnit tests, mirroring the main source structure above



