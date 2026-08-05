# Movie.java + MovieTest.java — walkthrough script

Personal notes for the live demo. Not part of the graded deliverable — delete or keep, your call.

**Status:** 16 tests in `MovieTest`, all passing (9 getters, 2 immutability, 5 equality).

---

## 1. The 30-second version (say this first)

> "`Movie` is the one data class everything else in CineVault is built on — the catalog holds
> them, the filter reads them, the statistics engine averages them, the CSV loader creates them.
> It stores nine things about a film: title, year, duration, rating, genres, directors, actors,
> poster URL, and an overview. The main decision I made is that a `Movie` is **immutable** —
> once you build one, it can never change. Everything else in the design follows from that."

Then walk through the three decisions below. Don't read the code line by line — say the decision,
then point at the line that implements it.

---

## 2. The three design decisions (and the *why*)

### A. It's immutable — `Movie.java:36-44`

Every field is `private final`, and there are no setters, only getters.

**Why:** if two parts of the program are holding the same `Movie`, neither can corrupt it for the
other. There's no way for a bug in the GUI to silently change data in the catalog.

**"So how do you edit a movie?"** You don't edit it — the catalog replaces the whole object with a
new one. Same idea as `String` in Java: `toUpperCase()` doesn't change the string, it hands you a
new one.

### B. Defensive copying — `Movie.java:59-61`

```java
this.genres = List.copyOf(builder.genres);
```

**Why:** three of the fields are `List`s. If I just assigned the caller's list, they could keep a
reference and add to it later, changing my movie from the outside. `List.copyOf` does two jobs at
once: it copies the values in, *and* the copy it produces is itself unmodifiable — which is why
the getter can safely hand the list straight back with no extra wrapping.

That second half is what `MovieTest.java:99-110` proves: calling `.add()` or `.clear()` on a list
you got from a getter throws `UnsupportedOperationException`.

### C. The Builder pattern — `Movie.java:196-304`

```java
Movie m = new Movie.Builder("The Matrix", 1999)
        .duration(136)
        .rating(8.7)
        .build();
```

**Why:** nine fields would mean a nine-argument constructor. And look at the types — `year` and
`duration` are both `int`, sitting right next to each other. Swap them by accident in a
constructor call and it **still compiles**; you'd only find out when a movie showed up as 136
minutes long from the year 1999. With the builder, every value is labelled at the call site, so
that mistake can't happen.

Two more points if they push:
- **Title and year are required**, so they're arguments to the `Builder` constructor itself
  (`Movie.java:214`) — you can't build a movie without them.
- **Everything else is optional** and defaults to an empty list or empty string
  (`Movie.java:200-206`) — never `null`. So a movie built from a partial CSV row has no null
  fields, and nothing downstream needs a null check.

---

## 3. `equals` and `hashCode` — the part they'll ask about

**The rule:** two movies are the same movie if they have the same title (ignoring case) and the
same release year. Rating, cast, and everything else are not part of a movie's identity.

**Why title + year:** a title alone isn't unique — there are two *Dune*s and three *A Star Is
Born*s. Title *plus* year identifies a film, and it means a re-imported or re-rated copy of the
same film is recognised as one entry rather than a duplicate.

⚠️ **Be careful how you phrase this.** `Catalog` is still an empty stub, so nothing in the code
*currently* depends on this behaviour. Say it as a design decision — "this is the identity rule
the catalog will use for de-duplicating imports" — not as "the catalog uses it," or a follow-up
question will catch you out.

**The bit that earns points — why `hashCode` looks like that** (`Movie.java:175`):

```java
return Objects.hash(title.toLowerCase(Locale.ROOT), year);
```

> "Because `equals` uses `equalsIgnoreCase`, `"The Matrix"` and `"the matrix"` are equal. The Java
> contract says equal objects **must** return the same hash code — so `hashCode` has to lowercase
> the title too. If it didn't, the two would land in different buckets and a `HashMap` or `HashSet`
> would treat one movie as two."

`MovieTest.java:126-131` is the test for exactly that: it asserts both `equals` **and** matching
hash codes for `"the matrix"`.

**If they ask why `Locale.ROOT`:** `toLowerCase()` with no argument uses the machine's locale, and
in Turkish, capital `I` lowercases to a dotless `ı`. So on a Turkish machine the same movie could
hash differently than on yours. `Locale.ROOT` pins the behaviour. (Same reason it's in
`toString`'s `String.format` — decimal separators are a comma in much of Europe.)

---

## 4. How to present the test file

The point to make: **it's organised by behaviour, not by method.** Three `@Nested` classes with
`@DisplayName`s — "getters return stored values", "list fields are immutable", "equality is by
title and year" — so the test report reads as three groups of related tests instead of one flat
list of 16. `@BeforeEach` (`MovieTest.java:25-36`) builds one sample movie fresh before every
test, so no test can be affected by another one.

Walk one test from each group:
- **Getters** — the boring but necessary group: what went in comes back out.
- **Immutability** — `assertThrows(UnsupportedOperationException.class, () -> movie.getGenres().add("Drama"))`.
  This is the interesting one: it asserts that something *isn't allowed*, not that a value is right.
- **Equality** — the case-insensitive test above, plus the negative cases: same title different
  year isn't equal (`The Matrix` 1999 vs a 2021 remake), same year different title isn't equal.

---

## 5. Two weak spots — name them before they do

The rubric explicitly asks what you'd do differently, so volunteering these reads as awareness,
not as an admission.

1. **No validation.** The Javadoc says the rating is "0 to 10", but nothing enforces it — you can
   build a movie rated 47.0, released in year -5, running -100 minutes. Your answer: *"Validation
   is going to live at the boundary where untrusted data comes in — the CSV loader and the
   controller — rather than in the model. If I did it again I'd probably put a guard in `build()`
   as well, since that's the one place every movie has to pass through."*

2. **One untested claim.** The class Javadoc says the defensive copy protects against "later
   changes to the builder's lists" — but no test shows that, because `setUp` passes `List.of(...)`,
   which is already immutable, so that path never gets exercised. The immutability tests only prove
   the *getter* is unmodifiable. Closing it is a 4-line test:

```java
@Test
void laterChangesToCallersListDoNotAffectMovie() {
  List<String> mutableGenres = new ArrayList<>(List.of("Action"));
  Movie m = new Movie.Builder("Heat", 1995).genres(mutableGenres).build();
  mutableGenres.add("Drama");
  assertEquals(List.of("Action"), m.getGenres());
}
```

Adding it before the demo is strictly better than explaining why it's missing.

---

## 6. Before demo day — build blocker

`gradle.properties` contains a hardcoded Windows path from a teammate's machine:

```
org.gradle.java.home=C:/Users/egand/.jdks/ms-17.0.19
```

On any Mac, `./gradlew build` fails immediately with *"Java home supplied is invalid"* — it never
reaches the tests. `gradlew` also isn't marked executable, so `./gradlew` gives "permission
denied" before that.

Two fixes, both one-liners:
- Delete that line from `gradle.properties` (a machine-specific path shouldn't be committed —
  Gradle finds the JDK on its own).
- `chmod +x gradlew` and commit it.

If the demo runs on a Mac and nobody's fixed this, the build dies in front of the teacher.

---

## Quick answer sheet

| If they ask… | Say |
|---|---|
| Why immutable? | Nothing can corrupt shared data; editing replaces the object. Same model as `String`. |
| Why a Builder? | Nine fields; `year` and `duration` are both `int` and adjacent — a swap would compile silently. |
| Why `List.copyOf`? | Copies in *and* returns an unmodifiable list, so the getter is safe with no extra wrapping. |
| Why is equality title + year? | Titles repeat across remakes; title + year identifies a film. Design choice for catalog de-duplication. |
| Why lowercase in `hashCode`? | `equals` ignores case, and the contract requires equal objects to hash the same — otherwise `HashMap` breaks. |
| Why `Locale.ROOT`? | Locale-dependent lowercasing (Turkish dotless ı) would change the hash across machines. |
| What would you improve? | Range validation on rating/year/duration, and a test for the builder-list defensive copy. |
