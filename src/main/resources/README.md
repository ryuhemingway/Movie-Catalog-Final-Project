# Resources

Default location for resources, should you need them. 

# CineVaultApplication Resources

This folder contains the data files our CineVault reads from and writes to when running.

- **imdb_top_1000.csv** — This is the main movie dataset, loaded into the `Catalog` on startup of the 
    application via the `CsvLoader`. New movies added through the GUI or command line are added to 
    the same imdb_top_1000.csv file via the `CsvWriter`.

- **favorites.csv** — This file represents the user's personal favorites list which is managed by the `CsvFavoritesRepository` file.
  This file is created automatically the first time a favorite is added, if it doesn't already
  exist.