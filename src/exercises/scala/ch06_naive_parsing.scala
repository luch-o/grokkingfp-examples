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

def parseShow(raw: String): TvShow = {
    val parenthesesOpen = raw.indexOf("(")
    val parenthesesClose = raw.indexOf(")")
    val dash = raw.indexOf("-")

    val name = raw.substring(0, parenthesesOpen).trim
    val start = Integer.parseInt(raw.substring(parenthesesOpen + 1, dash))
    val end = Integer.parseInt(raw.substring(dash + 1, parenthesesClose))

    TvShow(name, start, end)
}

def parseShows(rawShows: List[String]): List[TvShow] = {
    rawShows.map(parseShow)
}

parseShows(rawShows)
