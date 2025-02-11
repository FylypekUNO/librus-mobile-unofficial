package pl.fylypek.librus_mobile_unofficial.json

data class JsonScheduleDay(
    val name: String = "",
    val teacher: String = "",
    val room: String = "",
)

data class JsonSchedule(
    val monday: List<JsonScheduleDay?> = emptyList(),
    val tuesday: List<JsonScheduleDay?> = emptyList(),
    val wednesday: List<JsonScheduleDay?> = emptyList(),
    val thursday: List<JsonScheduleDay?> = emptyList(),
    val friday: List<JsonScheduleDay?> = emptyList(),
    val saturday: List<JsonScheduleDay?> = emptyList(),
    val sunday: List<JsonScheduleDay?> = emptyList()
)

class JsonScheduleResponse {
    val schedule: JsonSchedule = JsonSchedule()
}