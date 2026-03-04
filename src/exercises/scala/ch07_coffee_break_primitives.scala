case class Artist(
    name: String,
    genre: String,
    origin: String,
    yearsActiveStart: Int,
    isActive: Boolean,
    yearsAcrtiveEnd: Int // meant to be 0 if isActive is false
)


def searchArtists(
    artists: List[Artist],        
    genres: List[String],         
    locations: List[String],      
    searchByActiveYears: Boolean, 
    activeAfter: Int,             
    activeBefore: Int             
): List[Artist] = {
  artists.filter((artist) =>
    (genres.contains(artist.genre) || genres.isEmpty) &&
    (locations.contains(artist.origin) || locations.isEmpty) &&
    (!searchByActiveYears || (
      (artist.isActive || artist.yearsAcrtiveEnd >= activeAfter) &&
      (artist.yearsActiveStart <= activeBefore)))
  )
}

val artists = List(
  Artist("Metallica", "Heavy Metal", "U.S.", 1981, true, 0),
  Artist("Led Zeppelin", "Hard Rock", "England", 1968, false, 1980), 
  Artist("Bee Gees", "Pop", "England", 1958, false, 2003)
)

// Test cases:
// search for pop artists from England active between 1950 and 2022
searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022)

// search for artists from england active between 1950 and 2022
searchArtists(artists, List.empty, List("England"), true, 1950, 2022)

// search for artists active between 1950 and 1979
searchArtists(artists, List.empty, List.empty, true, 1950, 1979)

// search for artists active between 1981 and 1984
searchArtists(artists, List.empty, List.empty, true, 1981, 1984)

// search for heavy metal artists active between 2019 and 2022
searchArtists(artists, List("Heavy Metal"), List.empty, true, 2019, 2022)

// Search for artists from the U.S. active between 1950 and 1959:
searchArtists(artists, List.empty, List("U.S."), true, 1950, 1959)

// Search for artists without any conditions:
searchArtists(artists, List.empty, List.empty, false, 2019, 2022)
