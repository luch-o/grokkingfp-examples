import cats.effect.IO
import cats.implicits.*
import cats.effect.unsafe.implicits.global

import ch08_SchedulingMeetings.calendarEntriesApiCall
import ch08_SchedulingMeetings.createMeetingApiCall


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

def overlaps(candidate: MeetingTime, existingMeetings: List[MeetingTime]): Boolean = {
    existingMeetings.exists(meeting =>
        (meeting.startHour < candidate.endHour) && (meeting.endHour > candidate.startHour)
    )
}

def possibleMeetings(
    existingMeetings: List[MeetingTime],
    startHour: Int,
    endHour: Int,
    lengthHours: Int
): List[MeetingTime] = {
        List.range(startHour, endHour, lengthHours)
            .map(start => MeetingTime(start, start + lengthHours))
            .filter(meeting => !overlaps(meeting, existingMeetings))
    }

def schedule(
    person1: String,
    person2: String,
    lengthHours: Int
): IO[Option[MeetingTime]] = {
    val workingHours = MeetingTime(startHour=8, endHour=16)
    for {
        existingMeetings <- scheduledMeetings(person1, person2)
    } yield possibleMeetings(
        existingMeetings, 
        workingHours.startHour, 
        workingHours.endHour, 
        lengthHours
    ).headOption
}