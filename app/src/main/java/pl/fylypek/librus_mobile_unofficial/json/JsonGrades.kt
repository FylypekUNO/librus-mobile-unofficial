package pl.fylypek.librus_mobile_unofficial.json


data class JsonSubject(
    val name: String,
    val semesters: List<List<String>>
)

data class JsonGradesResponse(
    val grades: List<JsonSubject>
)