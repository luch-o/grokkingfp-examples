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

    case class PeriodInYears(start: Int, end: Int)

    enum YearsActive {
        case StillActive(since: Int, previousPeriods: List[PeriodInYears])
        case ActiveInPast(periods: List[PeriodInYears])
    }

    case class Artist(name: String, genre: MusicGenre, origin: Location, yearsActive: YearsActive)

    enum SearchCondition {
        case SearchByGenre(genres: List[MusicGenre])
        case SearchByOrigin(locations: List[Location])
        case SearchByActiveYears(start: Int, end: Int)
        case SearchByActivityDuration(duration: Int, until: Int)
    }
}

import model._
import MusicGenre._
import YearsActive._
import SearchCondition._


def periodOverlapsWithPeriods(checkedPeriod: PeriodInYears, periods: List[PeriodInYears]): Boolean = {
    periods.exists(p =>
        p.start <= checkedPeriod.end && p.end >= checkedPeriod.start    
    )
}

def wasArtistActive(artist: Artist, searchedPeriod: PeriodInYears): Boolean = artist.yearsActive match {
      case StillActive(since, previousPeriods) => 
        since <= searchedPeriod.end || periodOverlapsWithPeriods(searchedPeriod, previousPeriods)
      case ActiveInPast(periods) =>
        periodOverlapsWithPeriods(searchedPeriod, periods)
    }

def activeLength(artist: Artist, currentYear: Int): Int = {
    val periods = artist.yearsActive.match {
        case StillActive(since, previousPeriods) =>
            previousPeriods.appended(PeriodInYears(since, currentYear))
        case ActiveInPast(periods) =>
            periods
    }
    periods
        .map(p => p.end - p.start)
        .foldLeft(0)((duration, periodDuration) => duration + periodDuration)
} 

def searchArtists(artists: List[Artist],
                  requiredConditions: List[SearchCondition]): List[Artist] =
    artists.filter(artist =>
        requiredConditions.forall(condition =>
            condition match {
                case SearchByGenre(genres) => 
                    genres.contains(artist.genre)
                case SearchByOrigin(locations) =>
                    locations.contains(artist.origin)
                case SearchByActiveYears(start, end) => 
                    wasArtistActive(artist, PeriodInYears(start, end))
                case SearchByActivityDuration(duration, until) => 
                    activeLength(artist, until) >= duration 
            }
        )
    )