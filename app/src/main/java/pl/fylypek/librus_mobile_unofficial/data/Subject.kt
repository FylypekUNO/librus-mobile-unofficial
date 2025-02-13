package pl.fylypek.librus_mobile_unofficial.data

data class Subject(
    val name: String,
    val grades: List<String>
) {
    val gradesAsString: String
        get() = grades.joinToString(", ")
}
