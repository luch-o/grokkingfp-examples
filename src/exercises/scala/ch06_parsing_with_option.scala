case class TvShow(title: String, start: Int, end: Int)

def sortShows(shows: List[TvShow]): List[TvShow] = {
    shows
        .sortBy(show => show.end -  show.start)
        .reverse
}

val shows = List(TvShow("Breaking Bad", 2008, 2013),
                 TvShow("The Wire", 2002, 2008),
                 TvShow("Mad Men", 2007, 2015))

sortShows(shows)

val rawShows: List[String] = List(
    "Breaking Bad (2008-2013)",
    "The Wire (2002-2009)",
    "Mad Men (2007-2015)"    
)

def extractName(raw: String): Option[String] = {

}

def extractYearStart(raw: String): Option[Int] = {

}

def extractYearEnd(raw: String): Option[Int] = {

}

def parseShow(raw: String): Option[TvShow] = {
    for {
        name      <- extractName(raw)
        startYear <- extractYearStart(raw)
        endYear   <- extractYearEnd(raw)
    } yield TvShow(name, startYear, endYear)
}

def parseShows(rawShows: List[String]): List[TvShow] = {
    rawShows.map(parseShow)
}

parseShows(rawShows)
