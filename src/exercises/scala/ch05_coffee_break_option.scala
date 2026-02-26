case class Event(name: String, start: Int, end: Int)

def validateName(name: String): Option[String] =
  if (name.size > 0) Some(name) else None
  
def validateEnd(end: Int): Option[Int] =
  if (end < 3000) Some(end) else None
  
def validateStart(start: Int, end: Int): Option[Int] =
  if (start <= end) Some(start) else None

def parse(name: String, 
        start: Int, end: Int): Option[Event] =
for {
    validName  <- validateName(name)
    validEnd   <- validateEnd(end)
    validStart <- validateStart(start, end)
} yield Event(validName, validStart, validEnd)

def validateLength(start: Int, 
                   end: Int,
                   minLength: Int): Option[Int] = {
    val length = end - start
    if(length > 10) Some(length) else None
    }

def parseLongEvent(name: String, 
        start: Int, 
        end: Int,
        minLength: Int        
): Option[Event] =
for {
    validName   <- validateName(name)
    validEnd    <- validateEnd(end)
    validStart  <- validateStart(start, end)
    validLength <- validateLength(start, end, minLength)
} yield Event(validName, validStart, validEnd)

parseLongEvent("Apollo Program", 1961, 1972, 10)
parseLongEvent("", 1939, 1945, 10)
parseLongEvent("Apollo Program", 1972, 1961, 10)
