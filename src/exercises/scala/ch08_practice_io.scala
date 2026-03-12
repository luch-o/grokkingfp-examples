import cats.effects.IO
import ch08_SchedulingMeetings.calendarEntriesApiCall
import ch08_SchedulingMeetings.createMeetingApiCall

case class MeetingTime(start: Int, end: Int)

def calendarEntries(name: String): IO[List[MeetingTime]] = {
    IO.delay(calendarEntriesApiCall(name))
}

def createMeeting(names: List[String], meetingTime: MeetingTime): IO[Unit] = {
    IO.delay(createMeetingApiCall(names, meetingTime))
}

def scheduledMeetings(person1: String, person2: String): IO[List[MeetingTime]] = {
    for {
        person1Meetings <- calendarEntries(person1)
        person2Meetings <- calendarEntries(person2)
    } yield person1Meetings.appendedAll(person2Meetings)
}
