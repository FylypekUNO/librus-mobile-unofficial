package pl.fylypek.librus_mobile_unofficial

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
import pl.fylypek.librus_mobile_unofficial.ui.LoadingFragment
import pl.fylypek.librus_mobile_unofficial.ui.LoginActivity
import pl.fylypek.librus_mobile_unofficial.ui.ScheduleFragment

class MainActivity : AppCompatActivity() {

    private val domain = "http://192.168.1.21:3001"

    private var areGradesFetched = false
    private var isScheduleFetched = false

    private var currentTab: String = "grades"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidThreeTen.init(this)


        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            this,
            "librus-credentials",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val login = sharedPreferences.getString("login", "") ?: ""
        val password = sharedPreferences.getString("password", "") ?: ""

        if (login.isEmpty() || password.isEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Ustawienie nasłuchiwania pozycji w dolnej nawigacji
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_grades -> {
                    currentTab = "grades"
                    if (areGradesFetched) {
                        openFragment(GradesFragment())
                    } else {
                        openFragment(LoadingFragment.newInstance("Fetching grades..."))
                    }
                    true
                }

                R.id.nav_schedule -> {
                    currentTab = "schedule"
                    if (isScheduleFetched) {
                        openFragment(ScheduleFragment())
                    } else {
                        openFragment(LoadingFragment.newInstance("Fetching schedule..."))
                    }
                    true
                }

                R.id.action_logout -> {
                    sharedPreferences.edit().remove("login").remove("password").apply()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }

                else -> false
            }
        }

        bottomNavigationView.selectedItemId = R.id.nav_grades

        // Pobranie danych ocen i planu lekcji
        GlobalScope.launch { fetchGrades(login, password) }
        GlobalScope.launch { fetchSchedule(login, password) }
    }

    private fun openFragment(fragment: Fragment) {
        runOnUiThread {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    suspend fun fetchGrades(login: String, password: String) {
        val body = mapOf(
            "login" to login, "pass" to password
        )

        val url = "$domain/api/new/grades"
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

        withContext(Dispatchers.Main) {
            areGradesFetched = true

            if (currentTab == "grades") {
                openFragment(GradesFragment())
            }
        }
    }

    suspend fun fetchSchedule(login: String, password: String) {
        val body = mapOf(
            "login" to login, "pass" to password
        )

        val url = "$domain/api/new/schedule"
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

        withContext(Dispatchers.Main) {
            isScheduleFetched = true

            if (currentTab == "schedule") {
                openFragment(ScheduleFragment())
            }
        }
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