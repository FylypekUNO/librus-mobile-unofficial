package pl.fylypek.librus_mobile_unofficial

import org.threeten.bp.DayOfWeek
import pl.fylypek.librus_mobile_unofficial.data.ScheduleItem
import pl.fylypek.librus_mobile_unofficial.data.Semester

var gradesData: List<Semester> = listOf(
    Semester(
        name = "Semestr 1",
        subjects = emptyList()
    ),
    Semester(
        name = "Semestr 2",
        subjects = emptyList()
    )
)

fun clearGradesData() {
    gradesData = listOf(
        Semester(
            name = "Semestr 1",
            subjects = emptyList()
        ),
        Semester(
            name = "Semestr 2",
            subjects = emptyList()
        )
    )
}

var weeklyScheduleData: Map<DayOfWeek, List<ScheduleItem>> = mutableMapOf(
    DayOfWeek.MONDAY to emptyList(),
    DayOfWeek.TUESDAY to emptyList(),
    DayOfWeek.WEDNESDAY to emptyList(),
    DayOfWeek.THURSDAY to emptyList(),
    DayOfWeek.FRIDAY to emptyList(),
    DayOfWeek.SATURDAY to emptyList(),
    DayOfWeek.SUNDAY to emptyList()
)

fun clearWeeklyScheduleData() {
    weeklyScheduleData = mutableMapOf(
        DayOfWeek.MONDAY to emptyList(),
        DayOfWeek.TUESDAY to emptyList(),
        DayOfWeek.WEDNESDAY to emptyList(),
        DayOfWeek.THURSDAY to emptyList(),
        DayOfWeek.FRIDAY to emptyList(),
        DayOfWeek.SATURDAY to emptyList(),
        DayOfWeek.SUNDAY to emptyList()
    )
}

fun clearData() {
    clearGradesData()
    clearWeeklyScheduleData()
}