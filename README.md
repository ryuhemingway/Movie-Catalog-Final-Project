# Final Project for CS 5004 - (APPLICATION NAME/Update)

Team Members:
Name
Ryu Hemingway
Chiberu Egandu
Arushi Haniyur
Robin Rojano

## Description
CineVault is a movie catalog application that allows users to collect, organize, and browse movie information. Users can add, edit, and delete movies from a personal catalog, with each movie storing details such as title, directors, actors, genres, duration, year released, rating, and languages. Additional features include filtering by genre/rating/language, viewing average rating statistics and charts, and generating a random movie from the collection.

## Core Features
- Search the catalog by title
- Filter movies by genre and rating range
- Pick a random movie from a chosen genre (rated 8.0 or higher)
- Add a new movie to the catalog, saved back to the dataset
- Maintain a personal favorites list: load, search, update a rating, and delete (one favorite or all at once)
- View statistics: average rating by genre, and top rated movies, shown as a bar chart (GUI only)


**Additional features (planned):**
- Filter movies by genre, rating range, or language
- View average rating per genre with charts
- Generate a random movie from the collection
- Import/export movie data from a dataset file


## How to Compile and Run

This project uses Gradle via the included wrapper, so you don't need Gradle installed separately.

> On Mac/Linux, if `./gradlew` isn't executable yet, run `chmod +x gradlew` once first.

**To compile and run all tests:**

Mac/Linux:

    ./gradlew build

Windows PowerShell:

    .\gradlew.bat build

**To run just the tests:**

Mac/Linux:

    ./gradlew test

Windows PowerShell:

    .\gradlew.bat test

### Running the Application

CineVault can run in two modes: a graphical window (the default) or a text menu in the terminal.

**GUI mode (default):**

Mac/Linux:

    ./gradlew run

Windows PowerShell:

    .\gradlew.bat run

**Command line mode:**

Mac/Linux:

    ./gradlew run --args="-cl"

Windows PowerShell:

    .\gradlew.bat run --args="-cl"

> **Note:** On Windows, keyboard input in command line mode may not respond correctly the very first time Gradle's daemon starts. If the program doesn't respond to typed input, try running the command a second time.


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

- **Design Documents** (UML diagrams, wireframes, test history): see [`DesignDocuments/`](./DesignDocuments)
- **User Manual** (step-by-step usage guide with screenshots): see [`Manual/`](./Manual)

## Project Structure
- `src/main/java/student/model` — core data and logic (Movie, Catalog, MovieFilter, StatisticsEngine, RandomMovieGenerator)
- `src/main/java/student/view` — Java Swing GUI (MainFrame, MoviePanel, ControlPanel, StatisticsPanel, BarChartPanel) and the command line text menu (CommandLineView)
- `src/main/java/student/controller` — connects the view to the model (MovieController)
- `src/main/java/student/files` — reads/writes movie data to CSV files (CsvLoader, CsvWriter)
- `src/main/java/student/helpers` — shared constants used across the application
- `src/main/java/student/CineVaultApplication.java` — main driver class; launches either the GUI or the command line menu depending on the arguments passed in
- `src/test/java/student` — JUnit tests, mirroring the main source structure above
- `src/main/resources/imdb_top_1000.csv` — the main movie dataset
- `src/main/resources/favorites.csv` — the user's personal favorites list, created automatically the first time a favorite is added



