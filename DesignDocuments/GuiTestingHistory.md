# Testing History

When it comes to testing GUIs you often have to document how you tested, and what you did. Use this document to document, including screenshots of your testing.

## Test 1: Running the application loads the initial GUI window
Ran `./gradlew.bat run`. The CineVault window opened correctly, showing the
ControlPanel on the left with the search field, genre and rating filter dropdowns as well as
Add Movie and Pick Random Movie buttons. It also showed an empty MoviePanel in the center, and
the StatisticsPanel placeholder text at the bottom. No errors appeared in the
console.


## Test 2: Searching for a movie by partial title
Typed "matrix" into the search field and pressed Enter. The MoviePanel
correctly showed the movie for "The Matrix" with its year and rating displayed. 


## Test 3: Searching for a title that does not exist
Typed a title that does not appear anywhere in our csv dataset and pressed Enter. It populated the
validation error that shows user how to add a new movie and that the movie does not exist.

## Test 4: Filtering movies by genre and rating
Selected a specific genre from the Genre dropdown and a rating range from the Rating dropdown,
then clicked Filter Movies. The MoviePanel section correctly showed only movies matching both the
selected genre and rating range.

## Test 5: Picking a random movie by genre
Selected a genre from the Genre dropdown and clicked Pick Random Movie. A single movie card
appeared, matching the selected genre. Repeated this a few times for several genres and it looked
to work as expected. 

## Test 6: Adding a new movie to the catalog
Typed a full movie entry (title; year; duration; rating; genres; directors; actors; poster
URL; overview) into the search box and clicked Add New Movie To List. Got validation that movie was 
added and did a search for the movie i just added and it appeared in the list.

## Test 7: Attempting to add a duplicate movie
Typed the same movie entry from Test 6 again and clicked Add New Movie To List. The app
correctly detected the duplicate which had the same exact title and year  and showed a message instead of adding
a second copy.

## Test 8: Loading favorites
Clicked Load Favorites. The MoviePanel showed every movie currently saved in favorites.csv file.

## Test 9: Adding movies to favorites
Searched for a movie, then clicked Add To Favorites. Clicked Load Favorites again afterward to
confirm the movie was actually saved to the favorites list, not just shown temporarily.

## Test 10: Searching favorites
Typed part of a title into the favorites search field and pressed Enter. The MoviePanel showed
only favorites whose title contained the search text.

## Test 11: Updating a favorite's rating
Typed title; year; new rating into the favorites field and clicked Update. Reloaded
favorites afterward and confirmed the rating had changed. This was an additional feature that 
allowed the user to set their desired rating for a movie after they had updated it.

## Test 12: Deleting a single favorite
Typed title; year into the favorites field and clicked Delete. Reloaded favorites afterward
and confirmed that movie was gone from the favorites csv and also the gui. 

## Test 13: Deleting all favorites
Typed Delete All into the favorites field and clicked Delete. Reloaded favorites afterward
and confirmed the list was completely empty. This functionality worked. 

## Test 14: Statistics — Show average rating by genre
Clicked Show average rating by genre in the StatisticsPanel. A bar chart appeared showing
every genre with its average rating. The first iteration had the genres and ratings scrumbled 
so i applied an additional sort, sorted from highest to lowest, with each bar labeled with
it's genre name and rounded rating since the ratings were showing several decimal places. 

## Test 15: Statistics Show Top 10 Rated Movies, "Genre: All"
Left the genre dropdown on "Genre: All" and clicked Show Top 10 Rated Movies. The MoviePanel
showed the 10 highest rated movies across the entire catalog, and the bar chart below showed
the same 10 movies with their titles and ratings.

## Test 16: Statistics Show Top 10 Rated Movies, specific genre
Selected a specific genre from the dropdown and clicked Show Top 10 Rated Movies. I confirmed
every movie shown, in both the MoviePanel cards and the bar chart, actually belonged to the
selected genre which is what out code logic intended.

## Test 17: Bug found and fixed in the chart not rendering at all
I saw that clicking either statistics button drew no visible bars at all, even though the
buttons and data logic were working. After adding a proper
getPreferredSize() override, bars appeared correctly in the view.

## Test 18: Bug found and fixed in value labels clipped off the top of gui
I saw that rating labels above tall bars ratings close to the chart's max value were being
placed at a negative y position and clipped off the top of the panel, so they were invisible.

## Test 19: Bug found and fixed genre and title labels running together
I found that the text labels below each bar genre names or movie titles were wide enough to
overlap with the neighboring bar's label, making them unreadable. Fixed by widening the gap
between bars and reducing the label font size.I then confirmed the labels no longer overlap, even for
long genre names like Biography or Adventure.

## Test 20: Bug found and fixed wide charts cut off with no scrollbar
When "Genre: All" was selected, the chart with close to 20 bars was wider than
the window and the extra bars were simply cut off since the StatisticsPanel's scroll pane had
horizontal scrolling disabled. Fixed by allowing horizontal scrolling and letting the chart
report its real width so the user can scroll left to right if there was too much data.

## Test 21: Rating labels rounded to one decimal place
Confirmed that after formatting the rating labels with String.format("%.1f", value), both
statistics charts show clean one decimal ratings for example 7.9 instead of long raw
double values for example 7.959874 etc..)

## Test 22: MoviePanel and ControlPanel scrolling on window resize
Resized the window smaller and confirmed the MoviePanel scrolls both vertically and
horizontally instead of clipping movie cards and the ControlPanel scrolls vertically instead
of cutting off buttons at the bottom when display was made smaller.

## Test 23: Favorites field empty input handling
Clicked into the favorites search field, clicked away without typing anything, then clicked
Update and Delete separately. Both correctly showed a validation message asking for the proper
input format, instead of crashing or doing nothing at all.