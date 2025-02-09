package pl.fylypek.librus_mobile_unofficial

import android.os.Bundle
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fetchGrades("login", "password")
            .then { grades ->
                println(grades)
            }

        val recyclerView = findViewById<RecyclerView>(R.id.rvSubjects)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        mockSemesters.forEach { semester ->
            tabLayout.addTab(tabLayout.newTab().setText(semester.name))
        }

        if (mockSemesters.isNotEmpty()) {
            recyclerView.adapter = SubjectAdapter(mockSemesters[0].subjects)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val index = it.position
                    val selectedSemester = mockSemesters.getOrNull(index)
                    selectedSemester?.let { semester ->
                        recyclerView.adapter = SubjectAdapter(semester.subjects)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Możesz dodać logikę, gdy zakładka przestaje być wybrana (opcjonalnie)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Opcjonalnie obsłuż ponowne wybranie tej samej zakładki
            }
        })
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
