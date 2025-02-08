package pl.fylypek.librus_mobile_unofficial.json

import com.google.gson.annotations.SerializedName

data class Grade(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("info")
    val info: String = "",

    @SerializedName("value")
    val value: String = ""

)

data class Semester(
    @SerializedName("grades")
    val grades: List<Grade> = emptyList(),
    @SerializedName("tempAverage")
    val tempAverage: Double? = null,
    @SerializedName("average")
    val average: Double? = null
)

data class Subject(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("semester")
    val semesters: List<Semester> = emptyList(),
    @SerializedName("tempAverage")
    val tempAverage: Double? = null,
    @SerializedName("average")
    val average: Double? = null
)

data class GradesRoute(
    @SerializedName("grades") val grades: List<Subject> = emptyList()
)