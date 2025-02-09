package pl.fylypek.librus_mobile_unofficial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import pl.fylypek.librus_mobile_unofficial.json.GradesRoute

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Ustawienie paddingu względem insetów systemowych
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obsługa guzika "Plan Lekcji"
        findViewById<Button>(R.id.btnSchedule).setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
        }

        // Przykładowe pobieranie ocen (API) – dostosuj według potrzeb
        fetchGrades("login", "password")
            .then { grades ->
                println(grades)
            }

        val recyclerView = findViewById<RecyclerView>(R.id.rvSubjects)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Ustaw adapter dla ocen (np. z semestrami, jak wcześniej)
        recyclerView.adapter = SubjectAdapter(mockSemesters[0].subjects)
    }

    fun fetchGrades(login: String, pass: String): Promise<GradesRoute> {
        val url = "http://192.168.1.21:3000/api/grades"
        val options = FetchOptions(
            method = "POST",
            headers = mapOf("Content-Type" to "application/json"),
            body = toJson(mapOf("login" to login, "pass" to pass))
        )

        return fetch(url, options)
            .then { response -> response.json<GradesRoute>() }
            .catch { error -> println("[/api/grades] Error: $error") }
            .then {
                when (it) {
                    is Either.Resolved -> it.value
                    is Either.Rejected -> GradesRoute()
                }
            }
    }
}
