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
- View statistics: average rating by genre, and top rated movies in a genre, shown as a bar chart in the GUI or printed as text in the command line

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

<img width="918" height="725" alt="Screenshot 2026-08-10 at 5 06 39 PM" src="https://github.com/user-attachments/assets/2378378e-632e-4f4b-b9c8-9b0d2b99a29d" />
<img width="922" height="723" alt="Screenshot 2026-08-10 at 5 07 58 PM" src="https://github.com/user-attachments/assets/5e67d8bd-dda8-41d4-92f0-103989d3ea74" />


### Filtering Movies
Choose a genre from the Genre dropdown and a rating range from the Rating dropdown, then click **Filter Movies**. Matching movies appear in the Movie Panel.

<img width="1418" height="609" alt="Screenshot 2026-08-10 at 5 12 47 PM" src="https://github.com/user-attachments/assets/b33dcdf6-8ec5-4ac4-b75e-166b03942c47" />


### Picking a Random Movie
Choose a specific genre from the Genre dropdown, then click **Pick Random Movie**. A random movie from that genre, rated 8.0 or higher, will be shown.

<img width="1409" height="604" alt="Screenshot 2026-08-10 at 5 13 14 PM" src="https://github.com/user-attachments/assets/eff71be2-ca54-4460-b539-6c82a1c1ee04" />


### Adding a New Movie
If a search comes back empty, the Movie Panel will show the required format:

    title; year; duration; rating; genres; directors; actors; poster URL; overview

Type this into the search box (using commas to separate multiple genres, directors, or actors) and click **Add New Movie To List**. The movie will be added to the catalog and saved to the dataset file.

<img width="1410" height="600" alt="Screenshot 2026-08-10 at 5 18 52 PM" src="https://github.com/user-attachments/assets/76b0dc23-1c7c-4bfc-8be6-a9f352956e8b" />


### Managing Favorites
- **Load Favorites** : shows your saved favorite movies
- **Search Favorites** : type part of a title and press Enter to find a favorite
- **Add To Favorites** : saves whatever movies are currently shown to your favorites list
- **Update** : type `title; year; rating` into the favorites search box and click Update to change a favorite's rating
- **Delete** : type `title; year` and click Delete to remove one favorite, or type `Delete All` and click Delete to clear every favorite

Load Favorites: <img width="1401" height="611" alt="Screenshot 2026-08-10 at 5 20 30 PM" src="https://github.com/user-attachments/assets/ab8086dc-debb-43b0-8c44-e30b1da8a38a" />

Search Favorites: <img width="1406" height="604" alt="Screenshot 2026-08-10 at 5 21 32 PM" src="https://github.com/user-attachments/assets/19122197-1d9a-4c83-8e8d-a82042c6a4f5" />

Add to Favorites: <img width="754" height="356" alt="Screenshot 2026-08-10 at 5 22 29 PM" src="https://github.com/user-attachments/assets/6981c731-b1aa-4d77-90b1-c72cde17be58" />

Update:<img width="1385" height="595" alt="Screenshot 2026-08-10 at 5 23 15 PM" src="https://github.com/user-attachments/assets/e8c1b706-76c6-4f13-978a-70cd52972363" />

Delete:<img width="1403" height="511" alt="Screenshot 2026-08-10 at 5 23 31 PM" src="https://github.com/user-attachments/assets/14a45037-074b-42a4-8598-0e5fc05a01db" />


### Viewing Statistics
At the bottom of the window, click **Show average rating by genre** to see a bar chart of average ratings across genres, or choose a genre and click **Show Top Rated Movies** to see the highest rated movies in that genre.

_Screenshot: <img width="1419" height="844" alt="Screenshot 2026-08-10 at 5 23 57 PM" src="https://github.com/user-attachments/assets/b4b4232e-d56b-4147-ad41-0f599d63bd7d" />
<img width="1420" height="845" alt="Screenshot 2026-08-10 at 5 24 25 PM" src="https://github.com/user-attachments/assets/4b8c9bd1-9151-4ad6-8b32-9244820e239c" />


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
    10) Show average rating by genre
    11) Show top rated movies by genre
    12) Quit

### 1) Search by title
Type a title to find matching movies. If nothing matches, you'll be told to use option 4 to add it as a new movie.

<img width="480" height="569" alt="Screenshot 2026-08-10 at 5 28 41 PM" src="https://github.com/user-attachments/assets/c5d67842-580e-445f-a68e-9e41c24ab841" />


### 2) Filter by genre and rating
Enter a genre (or type `All` for every genre), then a minimum and maximum rating. If either rating is invalid or out of the 0–10 range, you'll be asked again until you enter valid values. If the minimum is higher than the maximum, you'll be asked to re-enter both.

<img width="564" height="349" alt="Screenshot 2026-08-10 at 5 29 56 PM" src="https://github.com/user-attachments/assets/5ecbca28-fa54-44ef-b011-bc6f650aa9ab" />
<img width="465" height="347" alt="Screenshot 2026-08-10 at 5 31 25 PM" src="https://github.com/user-attachments/assets/ffaae429-a7ed-433b-a7f3-7f88664cf5bd" />


### 3) Pick a random movie
Enter a genre to get a random movie from that genre rated 8.0 or higher.

<img width="666" height="336" alt="Screenshot 2026-08-10 at 5 32 27 PM" src="https://github.com/user-attachments/assets/e78f5e19-d5ad-4bfa-9fd3-9fb260051069" />

### 4) Add a new movie
Enter all movie details on one line, separated by semicolons, in the same format as the GUI:

    title; year; duration; rating; genres; directors; actors; poster URL; overview

The movie is added to the catalog and saved to the dataset file. If it already exists (same title and year), you'll be told instead of adding a duplicate.

<img width="1321" height="373" alt="Screenshot 2026-08-10 at 5 36 35 PM" src="https://github.com/user-attachments/assets/9cba3786-ddbb-428e-b2b0-d2f24366c222" />

### 5) Load favorites
Shows your saved favorite movies.
<img width="509" height="567" alt="Screenshot 2026-08-10 at 5 37 05 PM" src="https://github.com/user-attachments/assets/9eccf41f-b416-49cc-b74a-b056e723088e" />


### 6) Search favorites
Enter part of a title to find a matching favorite.
<img width="507" height="300" alt="Screenshot 2026-08-10 at 5 37 38 PM" src="https://github.com/user-attachments/assets/56a0d788-c042-443e-a2b7-111408efbb9a" />

### 7) Add last shown movies to favorites
Saves whatever was most recently displayed (from search, filter, or random pick) to your favorites list, skipping any that are already saved.
<img width="481" height="287" alt="Screenshot 2026-08-10 at 5 38 06 PM" src="https://github.com/user-attachments/assets/52d6bc7b-8c91-4d79-aeb3-57a0537b4e8c" />


### 8) Update a favorite's rating
Enter a favorite's title, year, and new rating. Only an exact title and year match will be updated; the poster is kept.
<img width="323" height="337" alt="Screenshot 2026-08-10 at 5 38 43 PM" src="https://github.com/user-attachments/assets/2b7a440b-8988-4d5a-9433-56873985ad63" />


### 9) Delete a favorite
Enter a title and year to remove one favorite, or type `All` for the title to clear every favorite at once.
<img width="590" height="317" alt="Screenshot 2026-08-10 at 5 39 12 PM" src="https://github.com/user-attachments/assets/61486cd6-e876-4f79-8fb0-d8066defdd6e" />


### 10) Show average rating by genre
Prints the average rating for every genre in the catalog, listed from the highest average rating to the lowest.

<img width="329" height="677" alt="Screenshot 2026-08-10 at 5 41 15 PM" src="https://github.com/user-attachments/assets/eddaaf35-7932-4b39-8753-7d500551732c" />

### 11) Show top rated movies by genre
Enter a genre (or type `All` for every genre) to print the 10 highest rated movies in that genre.

<img width="343" height="652" alt="Screenshot 2026-08-10 at 5 46 56 PM" src="https://github.com/user-attachments/assets/1858eb6c-ddc8-4969-afac-fa27907a91e6" />


### 12) Quit
Exits the program.

<img width="312" height="275" alt="Screenshot 2026-08-10 at 5 47 20 PM" src="https://github.com/user-attachments/assets/4f904340-1b00-4687-aa7a-eca954b710d3" />
