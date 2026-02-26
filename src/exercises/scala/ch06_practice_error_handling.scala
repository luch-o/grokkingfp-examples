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
    val parenthesesOpen = raw.indexOf("(")
    if (parenthesesOpen > 0)
        Some(raw.substring(0, parenthesesOpen).trim)
    else None
}

def extractYearStart(raw: String): Option[Int] = {
    val parenthesesOpen = raw.indexOf("(")
    val dash = raw.indexOf("-")
    for {
        yearStr <-  if (parenthesesOpen != -1 && dash > parenthesesOpen + 1)
                        Some(raw.substring(parenthesesOpen + 1, dash))
                    else None
        year    <-  yearStr.toIntOption
    } yield year
}

def extractYearEnd(raw: String): Option[Int] = {
    val parenthesesClose = raw.indexOf(")")
    val dash = raw.indexOf("-")
    for {
        yearStr <-  if (dash != -1 && parenthesesClose > dash + 1)
                        Some(raw.substring(dash + 1, parenthesesClose))
                    else None
        year    <-  yearStr.toIntOption
    } yield year
}

def extractSingleYear(raw: String): Option[Int] = {
    val dash = raw.indexOf("-")
    val parenthesesOpen = raw.indexOf("(")
    val parenthesesClose = raw.indexOf(")")
    for {
        yearStr <-  if (dash == -1 && parenthesesOpen != -1 && parenthesesClose > parenthesesOpen + 1)
                        Some(raw.substring(parenthesesOpen + 1, parenthesesClose))
                    else None
        year    <-  yearStr.toIntOption
    } yield year
}

def parseShow(raw: String): Option[TvShow] = {
    for {
        name      <- extractName(raw)
        startYear <- extractYearStart(raw).orElse(extractSingleYear(raw))
        endYear   <- extractYearEnd(raw).orElse(extractSingleYear(raw))
    } yield TvShow(name, startYear, endYear)
}

def parseShows(rawShows: List[String]): List[Option[TvShow]] = {
    rawShows.map(parseShow)
}

val onlyStartYear = "A (1992-)"
val singleYear = "B (2002)"
val onlyEndYear = "C (-2012)"
val onlyYear = "(2022)"
val missingYear = "E (-)"

val cornerCases = List(
    onlyStartYear,
    singleYear,
    onlyEndYear,
    onlyYear,
    missingYear
)

def extractYearSingleOrEnd(raw: String): Option[Int] = {
    extractSingleYear(raw).orElse(extractYearEnd(raw))
}

cornerCases.map(extractYearSingleOrEnd)

def extratYearStartOrEndOrSingle(raw: String): Option[Int] = {
    extractYearStart(raw)
        .orElse(extractYearEnd(raw))
        .orElse(extractSingleYear(raw))
}

cornerCases.map(extratYearStartOrEndOrSingle)

def extractSingleYearIfName(raw: String): Option[Int] = {
    for {
        name <- extractName(raw)
        year <- extractSingleYear(raw)
    } yield year
}

cornerCases.map(extractSingleYearIfName)

def extractYearStartOrEndOrSingleIfName(raw: String): Option[Int] = {
    for {
        name <- extractName(raw)
        year <- extratYearStartOrEndOrSingle(raw)
    } yield year
}

cornerCases.map(extractYearStartOrEndOrSingleIfName)
