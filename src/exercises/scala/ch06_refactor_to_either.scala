case class TvShow(title: String, start: Int, end: Int)

def extractName(raw: String): Either[String, String] = {
    val parenthesesOpen = raw.indexOf("(")
    if (parenthesesOpen > 0)
        Right(raw.substring(0, parenthesesOpen).trim)
    else Left(s"Can't extract name from: $raw")
}

def extractYearStart(raw: String): Either[String, Int] = {
    val parenthesesOpen = raw.indexOf("(")
    val dash = raw.indexOf("-")
    for {
        yearStr <-  if (parenthesesOpen != -1 && dash > parenthesesOpen + 1)
                        Right(raw.substring(parenthesesOpen + 1, dash))
                    else Left(s"Can't extract start year from: $raw")
        year    <-  yearStr.toIntOption.toRight(s"Can't parse: $yearStr")  
    } yield year
}

def extractYearEnd(raw: String): Either[String, Int] = {
    val parenthesesClose = raw.indexOf(")")
    val dash = raw.indexOf("-")
    for {
        yearStr <-  if (dash != -1 && parenthesesClose > dash + 1)
                        Right(raw.substring(dash + 1, parenthesesClose))
                    else Left(s"Can't extract end year from $raw")
        year    <-  yearStr.toIntOption.toRight(s"Can't parse: $yearStr")
    } yield year
}

def extractSingleYear(raw: String): Either[String, Int] = {
    val dash = raw.indexOf("-")
    val parenthesesOpen = raw.indexOf("(")
    val parenthesesClose = raw.indexOf(")")
    for {
        yearStr <-  if (dash == -1 && parenthesesOpen != -1 && parenthesesClose > parenthesesOpen + 1)
                        Right(raw.substring(parenthesesOpen + 1, parenthesesClose))
                    else Left(s"Can't extract single year from $raw")
        year    <-  yearStr.toIntOption.toRight(s"Can't parse $yearStr")
    } yield year
}

def parseShow(raw: String): Either[String, TvShow] = {
    for {
        name      <- extractName(raw)
        startYear <- extractYearStart(raw).orElse(extractSingleYear(raw))
        endYear   <- extractYearEnd(raw).orElse(extractSingleYear(raw))
    } yield TvShow(name, startYear, endYear)
}

def addOrResign(
    parsedShows: Either[String, List[TvShow]],
    newParsedShow: Either[String, TvShow]
): Either[String, List[TvShow]] = {
    for {
        shows      <- parsedShows
        parsedShow <- newParsedShow
    } yield shows.appended(parsedShow)
}

def parseShows(rawShows: List[String]): Either[String, List[TvShow]] = {
    val initial: Either[String, List[TvShow]] = Right(List.empty)
    rawShows
        .map(parseShow)
        .foldLeft(initial)(addOrResign)
}

val rawShows: List[String] = List(
    "Breaking Bad (2008-2013)",
    "The Wire (2002-2009)",
    "Mad Men (2007-2015)"    
)

parseShows(rawShows)

parseShows(List(
    "Chernobyl (2019)",
    "Breaking Bad (2008-2013)",
    "Mad Men (-2015)",
))

parseShows(List("The Wire (2002-2008)", "[2019]"))
