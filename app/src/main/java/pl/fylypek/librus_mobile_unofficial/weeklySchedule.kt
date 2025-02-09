package pl.fylypek.librus_mobile_unofficial

import org.threeten.bp.DayOfWeek

val weeklySchedule: Map<DayOfWeek, List<ScheduleItem>> = mapOf(
    DayOfWeek.MONDAY to listOf(
        ScheduleItem("08:00 - 09:00", "Mathematics", "Mr. Smith"),
        ScheduleItem("09:15 - 10:15", "English", "Mrs. Johnson")
    ),
    DayOfWeek.TUESDAY to listOf(
        ScheduleItem("08:00 - 09:00", "History", "Mr. Brown"),
        ScheduleItem("09:15 - 10:15", "Chemistry", "Ms. Davis")
    ),
    DayOfWeek.WEDNESDAY to listOf(
        ScheduleItem("08:00 - 09:00", "Physics", "Dr. Wilson"),
        ScheduleItem("09:15 - 10:15", "Art", "Ms. Taylor")
    ),
    DayOfWeek.THURSDAY to listOf(
        ScheduleItem("08:00 - 09:00", "Biology", "Dr. Anderson"),
        ScheduleItem("09:15 - 10:15", "Geography", "Mr. Thomas")
    ),
    DayOfWeek.FRIDAY to listOf(
        ScheduleItem("08:00 - 09:00", "Physical Education", "Coach Martin"),
        ScheduleItem("09:15 - 10:15", "Music", "Ms. White")
    ),
    // Jeśli chcesz też dla weekendu – możesz ustawić puste listy:
    DayOfWeek.SATURDAY to emptyList(),
    DayOfWeek.SUNDAY to emptyList()
)
