object model {
    enum MusicGenre {
        case HeavyMetal
        case Pop
        case HardRock
    }

    opaque type Location = String
    object Location {
        def apply(value: String): Location = value 
        extension(a: Location) def name: String = a
    }

    enum YearsActive {
        case StillActive(since: Int)
        case ActiveBetween(start: Int, end: Int)
    }

    case class Artist(name: String, genre: MusicGenre, origin: Location, yearsActive: YearsActive)
}

import model._
import MusicGenre._
import YearsActive._


def activeLength(artist: Artist, currentYear: Int): Int = {
    artist.yearsActive match {
        case StillActive(since) => 
            currentYear - since
        case ActiveBetween(start, end) =>
            end - start
    }
}

activeLength(
    Artist(
        "Metallica", 
        HeavyMetal, 
        Location("U.S."), 
        StillActive(1981)
    ),
    2022
)

activeLength(
    Artist(
        "Led Zeppelin", 
        HardRock, 
        Location("England"),
        ActiveBetween(1968, 1980)
    ),
    2022
)

activeLength(
    Artist(
        "Bee Gees", 
        Pop, 
        Location("England"),
        ActiveBetween(1958, 2003)
    ),
    2022
)