package pl.fylypek.librus_mobile_unofficial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.DayOfWeek
import pl.fylypek.librus_mobile_unofficial.data.ScheduleItem
import pl.fylypek.librus_mobile_unofficial.data.Semester
import pl.fylypek.librus_mobile_unofficial.data.Subject
import pl.fylypek.librus_mobile_unofficial.js.FetchOptions
import pl.fylypek.librus_mobile_unofficial.js.fetch
import pl.fylypek.librus_mobile_unofficial.js.json
import pl.fylypek.librus_mobile_unofficial.js.toJson
import pl.fylypek.librus_mobile_unofficial.json.JsonGradesResponse
import pl.fylypek.librus_mobile_unofficial.json.JsonScheduleDay
import pl.fylypek.librus_mobile_unofficial.json.JsonScheduleResponse
import pl.fylypek.librus_mobile_unofficial.ui.GradesFragment
import pl.fylypek.librus_mobile_unofficial.ui.ScheduleFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidThreeTen.init(this)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Ustawienie nasłuchiwania pozycji w dolnej nawigacji
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_grades -> {
                    openFragment(GradesFragment())
                    true
                }

                R.id.nav_schedule -> {
                    openFragment(ScheduleFragment())
                    true
                }

                else -> false
            }
        }

        // Ustawienie domyślnego fragmentu
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.nav_grades
        }

        // Pobranie danych ocen i planu lekcji
        GlobalScope.launch { fetchGrades() }
        GlobalScope.launch { fetchSchedule() }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }

    suspend fun fetchGrades() {
        val body = mapOf(
            "login" to login, "pass" to password
        )

        val url = "http://192.168.1.21:3000/api/new/grades"
        val options = FetchOptions(
            method = "POST", body = toJson(body)
        )

        val response = fetch(url, options).wait()

        val json = response.json<JsonGradesResponse>()

        val semesterNames = listOf("Semestr 1", "", "Semestr 2")

        val semesters = listOf(0, 2).map { i ->
            Semester(
                name = semesterNames[i],
                subjects = json.grades.map { subject ->
                    val grades = subject.semesters[i]

                    Subject(
                        name = subject.name,
                        grades = grades
                    )
                }
            )
        }

        gradesData = semesters
    }

    suspend fun fetchSchedule() {
        val body = mapOf(
            "login" to login, "pass" to password
        )

        val url = "http://192.168.1.21:3000/api/new/schedule"
        val options = FetchOptions(
            method = "POST", body = toJson(body)
        )

        val response = fetch(url, options).wait()

        val json = response.json<JsonScheduleResponse>()
        val schedule = json.schedule

        handleDaySchedule(schedule.monday, DayOfWeek.MONDAY)
        handleDaySchedule(schedule.tuesday, DayOfWeek.TUESDAY)
        handleDaySchedule(schedule.wednesday, DayOfWeek.WEDNESDAY)
        handleDaySchedule(schedule.thursday, DayOfWeek.THURSDAY)
        handleDaySchedule(schedule.friday, DayOfWeek.FRIDAY)
        handleDaySchedule(schedule.saturday, DayOfWeek.SATURDAY)
        handleDaySchedule(schedule.sunday, DayOfWeek.SUNDAY)
    }

    fun handleDaySchedule(lessons: List<JsonScheduleDay?>, day: DayOfWeek) {
        var lesson = -1
        val result = mutableListOf<ScheduleItem>()

        val times = listOf(
            "7:05 - 7:50",
            "8:00 - 8:45",
            "8:55 - 9:40",
            "9:50 - 10:35",
            "10:55 - 11:40",
            "11:50 - 12:35",
            "12:45 - 13:30",
            "13:40 - 14:25",
            "14:35 - 15:20",
            "15:30 - 16:15",
            "16:25 - 17:10",
        )

        lessons.forEach { lessonData ->
            lesson++

            if (lessonData == null) {
                result.add(
                    ScheduleItem(
                        subject = "",
                        teacher = "",
                        time = "",
                    )
                )

                return@forEach
            }

            val time = times[lesson]

            result.add(
                ScheduleItem(
                    subject = lessonData.name,
                    teacher = lessonData.teacher,
                    time = time,
                )
            )
        }

        weeklyScheduleData = weeklyScheduleData.toMutableMap().apply {
            set(day, result)
        }
    }
}