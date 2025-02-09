package pl.fylypek.librus_mobile_unofficial

import org.threeten.bp.DayOfWeek
import pl.fylypek.librus_mobile_unofficial.data.ScheduleItem
import pl.fylypek.librus_mobile_unofficial.data.Semester

val gradesData: List<Semester> = listOf(
    Semester(
        name = "Semestr 1",
        subjects = emptyList()
    ),
    Semester(
        name = "Semestr 2",
        subjects = emptyList()
    )
)

val weeklyScheduleData: Map<DayOfWeek, List<ScheduleItem>> = mapOf(
    DayOfWeek.MONDAY to emptyList(),
    DayOfWeek.TUESDAY to emptyList(),
    DayOfWeek.WEDNESDAY to emptyList(),
    DayOfWeek.THURSDAY to emptyList(),
    DayOfWeek.FRIDAY to emptyList(),
    DayOfWeek.SATURDAY to emptyList(),
    DayOfWeek.SUNDAY to emptyList()
)
