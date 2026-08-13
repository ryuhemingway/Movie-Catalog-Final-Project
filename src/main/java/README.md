# Java Src

Main java source files - you do not have to have them in the 'student' package, but you will
still want to consider other packages as it helps your project layout. 


## CineVaultApplication Package and File Structure

This folder contains the main Java source files for CineVault, organized under the `student`
package.

- **student.model** - Business logic files: `Movie`, `Catalog`, `MovieFilter`,
  `StatisticsEngine`, `RandomMovieGenerator`, and the `RatingRange` enum.

- **student.view** - The Java Swing GUI:  `MainFrame`, `MoviePanel`, `ControlPanel`,
  `StatisticsPanel`, `BarChartPanel`, `RoundedBorder` and the command line text view
  `CommandLineView` , both implementing the shared `IView` interface.

- **student.controller** - Connects the view to the model. `MovieController` implements the
  `IMovieController` interface, which both views depend on.

- **student.files** - Reads and writes movie and favorites data to CSV files: `CsvLoader`,
  `CsvWriter`, `CsvFavoritesRepository`, and the `IFavorites` interface.

`CineVaultApplication.java` is the main driver class. It
launches either the GUI  which is the default or the command line interface menu, depending on if `-cl` is
passed as an argument.

