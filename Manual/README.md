# Final Project for CS 5004 - CineVault

Team Members:
- Ryu Hemingway
- Chiberu Egandu
- Arushi Haniyur
- Robin Rojano

## Description
CineVault is a movie catalog application that allows users to collect, organize, and browse movie information. Users can search, filter, add new movies, and maintain a personal favorites list, with each movie storing details such as title, directors, actors, genres, duration, year released, rating, poster image, and overview.

The application can be run either as a graphical desktop app or as a text-based command line program, both backed by the same underlying data and logic.

## Core Features
- Search the catalog by title
- Filter movies by genre and rating range
- Pick a random movie from a chosen genre (rated 8.0 or higher)
- Add a new movie to the catalog, saved back to the dataset
- Maintain a personal favorites list: load, search, update a rating, and delete (one favorite or all at once)
- View statistics: average rating by genre, and top rated movies, shown as a bar chart (GUI only)

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


## Project Documentation

- **Design Documents** (UML diagrams, wireframes, test history): see [`DesignDocuments/`](./DesignDocuments)
- **User Manual** (step-by-step usage guide with screenshots, below)

## Project Structure
- `src/main/java/student/model` :  core data and logic (Movie, Catalog, MovieFilter, StatisticsEngine, RandomMovieGenerator)
- `src/main/java/student/view` : Java Swing GUI (MainFrame, MoviePanel, ControlPanel, StatisticsPanel, BarChartPanel) and the command line text menu (CommandLineView)
- `src/main/java/student/controller` : connects the view to the model (MovieController)
- `src/main/java/student/files` : reads/writes movie data to CSV files (CsvLoader, CsvWriter)
- `src/main/java/student/helpers` : shared constants used across the application
- `src/main/java/student/CineVaultApplication.java` — main driver class; launches either the GUI or the command line menu depending on the arguments passed in
- `src/test/java/student` — JUnit tests, mirroring the main source structure above
- `src/main/resources/imdb_top_1000.csv` : the main movie dataset
- `src/main/resources/favorites.csv` : the user's personal favorites list, created automatically the first time a favorite is added

---

# User Manual : GUI Mode

Run with `.\gradlew.bat run` to launch the graphical window.

### Searching for a Movie
Type a title into the search box at the top of the Control Panel and press Enter. Matching movies appear as cards in the Movie Panel, each showing the poster, title, year, and rating.

If no movie matches, the Movie Panel will instead show instructions for adding it as a new movie.

_Screenshot: [search results]_

### Filtering Movies
Choose a genre from the Genre dropdown and a rating range from the Rating dropdown, then click **Filter Movies**. Matching movies appear in the Movie Panel.

_Screenshot: [filter dropdowns and results]_

### Picking a Random Movie
Choose a specific genre from the Genre dropdown, then click **Pick Random Movie**. A random movie from that genre, rated 8.0 or higher, will be shown.

_Screenshot: [random movie result]_

### Adding a New Movie
If a search comes back empty, the Movie Panel will show the required format:

    title; year; duration; rating; genres; directors; actors; poster URL; overview

Type this into the search box (using commas to separate multiple genres, directors, or actors) and click **Add New Movie To List**. The movie will be added to the catalog and saved to the dataset file.

_Screenshot: [add movie flow]_

### Managing Favorites
- **Load Favorites** : shows your saved favorite movies
- **Search Favorites** : type part of a title and press Enter to find a favorite
- **Add To Favorites** : saves whatever movies are currently shown to your favorites list
- **Update** : type `title; year; rating` into the favorites search box and click Update to change a favorite's rating
- **Delete** : type `title; year` and click Delete to remove one favorite, or type `Delete All` and click Delete to clear every favorite

_Screenshot: [favorites panel]_

### Viewing Statistics
At the bottom of the window, click **Show average rating by genre** to see a bar chart of average ratings across genres, or choose a genre and click **Show Top Rated Movies** to see the highest rated movies in that genre.

_Screenshot: [statistics bar chart]_

---

# User Manual : Command Line Mode

Run with `.\gradlew.bat run --args="-cl"` to launch the text menu instead of the GUI. The menu offers the following options:

    CineVault (command line)
    1) Search by title
    2) Filter by genre and rating
    3) Pick a random movie
    4) Add a new movie
    5) Load favorites
    6) Search favorites
    7) Add last shown movies to favorites
    8) Update a favorite's rating
    9) Delete a favorite
    10) Quit

### 1) Search by title
Type a title to find matching movies. If nothing matches, you'll be told to use option 4 to add it as a new movie.

_Screenshot: [search results]_

### 2) Filter by genre and rating
Enter a genre (or type `All` for every genre), then a minimum and maximum rating. If either rating is invalid or out of the 0–10 range, you'll be asked again until you enter valid values. If the minimum is higher than the maximum, you'll be asked to re-enter both.

_Screenshot: [filter results]_

### 3) Pick a random movie
Enter a genre to get a random movie from that genre rated 8.0 or higher.

_Screenshot: [random movie result]_

### 4) Add a new movie
Enter all movie details on one line, separated by semicolons, in the same format as the GUI:

    title; year; duration; rating; genres; directors; actors; poster URL; overview

The movie is added to the catalog and saved to the dataset file. If it already exists (same title and year), you'll be told instead of adding a duplicate.

_Screenshot: [add movie flow]_

### 5) Load favorites
Shows your saved favorite movies.

_Screenshot: [favorites list]_

### 6) Search favorites
Enter part of a title to find a matching favorite.

### 7) Add last shown movies to favorites
Saves whatever was most recently displayed (from search, filter, or random pick) to your favorites list, skipping any that are already saved.

### 8) Update a favorite's rating
Enter a favorite's title, year, and new rating. Only an exact title and year match will be updated; the poster is kept.

### 9) Delete a favorite
Enter a title and year to remove one favorite, or type `All` for the title to clear every favorite at once.

### 10) Quit
Exits the program.

_Screenshot: [full menu in terminal]_